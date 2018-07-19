package com.diazmain.obapp.Home

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.diazmain.obapp.Home.Fragments.HomeFragmentAdapter
import com.diazmain.obapp.Login.SplashScreen
import com.diazmain.obapp.api.APIService
import com.diazmain.obapp.api.APIUrl
import com.diazmain.obapp.helper.SharedPrefManager
import com.diazmain.obapp.R
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_home_dashboard.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketTimeoutException
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.diazmain.obapp.Home.model.*
import com.diazmain.obapp.Home.model.meals.MealMenuResult
import com.diazmain.obapp.Login.AlternativeLoginActivity
import com.diazmain.obapp.Notification.JobsManager
import com.diazmain.obapp.Threads.*
import com.diazmain.obapp.Threads.HomeThreadSaversManager.AsyncResponse
import kotlinx.android.synthetic.main.fragment_progress.*


/**
 * @author LuisDixz
 * <p>Clase principal, sirve de contenedor para los fragment.<br>Se hace uso del componente ViewPager para cambiar de fragment</p>
 */
class HomeActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {

    internal var measuresList: List<MeasuresValue> = ArrayList()
    internal lateinit var incomingAppoint: CitasValue
    internal val apContext: Context = this


    internal var USER_ID: Int = 0
    lateinit var USER_NAME: String
    lateinit var USER_LASTNAME: String
    lateinit var USER_USERNAME: String


    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    /**
     * Método para cambiar el icono seleccionado por el fragment en el que se encuentra el usuario actualmente
     */
    override fun onPageSelected(position: Int) {
        val currentItem = bottMenu.menu.getItem(position).itemId
        if (currentItem != bottMenu.selectedItemId) {
            bottMenu.menu.getItem(position).isChecked = true
            bottMenu.menu.findItem(bottMenu.selectedItemId).isChecked = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if (!SharedPrefManager.getInstance(apContext)?.isLoggedIn()!!) {
            finish()
            startActivity(Intent(this, AlternativeLoginActivity::class.java))
        }

        bottMenu.setOnNavigationItemSelectedListener {
            selectItem(it)
        }

        val pageAdapter = HomeFragmentAdapter(supportFragmentManager)
        myViewPager.adapter = pageAdapter
        myViewPager.addOnPageChangeListener(this)
        myViewPager.offscreenPageLimit = 2

        Log.w("Location", "onCreate -> HomeActivity")
        USER_ID = SharedPrefManager.getInstance(apContext)!!.getUser().getId()
        USER_NAME = SharedPrefManager.getInstance(apContext)!!.getUser().getName()
        USER_LASTNAME = SharedPrefManager.getInstance(apContext)!!.getUser().getLastname()
        USER_USERNAME = SharedPrefManager.getInstance(apContext)!!.getUser().getUsername()
        Log.w("USER_ID", USER_ID.toString())

        //val save: SaveIncomingAppoint = SaveIncomingAppoint(1, apContext)
        //save.execute()
    }

    //Método para cambiar de fragment a través del ViewPager
    private fun selectItem(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_home -> myViewPager.currentItem = 0
            R.id.action_profile -> myViewPager.currentItem = 1
            R.id.action_progress -> myViewPager.currentItem = 2
            else -> myViewPager.currentItem = 0
        }

        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.settings_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_logout -> {
                var ret: Boolean = false
                val alBuilder: AlertDialog.Builder = AlertDialog.Builder(apContext)
                        .setTitle(getString(R.string.title_attention))
                        .setMessage(getString(R.string.label_confirm_logout))
                        .setPositiveButton(getString(R.string.btn_accept), DialogInterface.OnClickListener() {
                            dialog, which ->
                            JobsManager(apContext).cancelJobs()
                            finish()
                            startActivity(Intent(apContext, AlternativeLoginActivity::class.java))
                            ret = SharedPrefManager.getInstance(apContext)?.logout()!!
                        })
                        .setNegativeButton(getString(R.string.btn_cancel), null)
                val alert: AlertDialog = alBuilder.create()
                alert.show()
                return ret
            }
            R.id.action_refresh -> {
                try {
                    //refreshMeasuresData()
                    getAllFromServer()
                } catch (e: Exception) {
                    Log.wtf("action_refresh -> Exception", e.localizedMessage)
                }
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun getAllFromServer() {
        if (isNetworkAvailable()) {
            InternetAccessibility { internet ->
                if (internet) {
                    getMeasuresFromServer()
                    getAppointFromServer()
                    getMealsMenuFromServer()
                }else {
                    Snackbar.make(activity_home, getString(R.string.notification_unstable_connection), Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun getMeasuresFromServer() {
        Log.w("USER_ID", USER_ID.toString())

        measuresList = ArrayList()
        cvRefreshing.visibility = View.VISIBLE

        /*Log.w("Location", "Before saverManager")
        val saverManager: HomeThreadSaversManager = HomeThreadSaversManager(1, apContext, object : AsyncResponse {
            override fun onProcessFinish(output: Boolean) {
                Snackbar.make(activity_home, "Carga de datos finalizada? -> "+output.toString(), Snackbar.LENGTH_LONG).show()
                measuresList = SharedPrefManager.getInstance(apContext)!!.getAllMeasures()
                UpdateHomeUI(apContext).execute(
                        tvResWeight,
                        tvResWaist,
                        tvResFat,
                        imWeightLittleProgress,
                        imWaistLittleProgress,
                        imFatLittleProgress,
                        tvPreviousWeight,
                        tvPreviousWaist,
                        tvPreviousFat,
                        tvNextWeight,
                        tvNextWaist,
                        tvNextFat
                )
                Log.w("Próxima cita", SharedPrefManager.getInstance(apContext)!!.getAppoint().fecha)
                cvRefreshing.visibility = View.GONE
            }
        })

        saverManager.execute()*/

        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

        val service: APIService = retrofit.create(APIService::class.java)

        val call: Call<MeasuresResult> = service.getAllMeasures(USER_ID)

        call.enqueue(object : Callback<MeasuresResult> {
            override fun onFailure(call: Call<MeasuresResult>?, t: Throwable?) {
                Log.wtf("onFailure -> getMeasuresFromServer()", t.toString())
                if (t is SocketTimeoutException) {
                    Snackbar.make(activity_home, getString(R.string.label_connection_failure), Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(activity_home, getString(R.string.notification_error_fetch), Snackbar.LENGTH_LONG).show()
                }
                cvRefreshing.visibility = View.GONE
            }

            override fun onResponse(call: Call<MeasuresResult>?, response: Response<MeasuresResult>?) {
                Log.wtf("Response", response?.body()?.measures?.size.toString())
                cvRefreshing.visibility = View.GONE

                val measuresResponse = response?.body()?.measures
                SharedPrefManager.getInstance(apContext)?.storeAllMeasures(measuresResponse!!.toList())
                //Snackbar.make(activity_home, "Conexión exitosa", Snackbar.LENGTH_LONG).show()
                //Log.w("SharedPreferencesSize", SharedPrefManager.getInstance(applicationContext)?.getAllMeasures()?.size.toString())
                //Log.w("conectedState", conected.toString())
                measuresList = SharedPrefManager.getInstance(apContext)!!.getAllMeasures()
                //Log.w("MeasuresArraySize", measuresList.size.toString())

                if (measuresList.size != 0) {
                    UpdateHomeUI(apContext).execute(
                            tvResWeight,
                            tvResWaist,
                            tvResFat,
                            imWeightLittleProgress,
                            imWaistLittleProgress,
                            imFatLittleProgress,
                            tvPreviousWeight,
                            tvPreviousWaist,
                            tvPreviousFat,
                            tvNextWeight,
                            tvNextWaist,
                            tvNextFat,
                            imWeightState,
                            imWaistState,
                            imFatState
                    )
                    UpdateProgressUI(apContext, measuresList).execute(
                            line_view,
                            imProgressWeigthTrend,
                            imProgressWaistTrend,
                            imProgressFatTrend,
                            tvProgressWeigthPercent,
                            tvProgressWaistPercent,
                            tvProgressFatPercent
                    )

                }
            }
        })

    }

    private fun getAppointFromServer() {
        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

        val service: APIService = retrofit.create(APIService::class.java)

        val call: Call<Citas> = service.getAllAppoint(USER_ID)

        call.enqueue(object : Callback<Citas> {
            override fun onFailure(call: Call<Citas>?, t: Throwable?) {
                Log.wtf("onFailure -> SaveIncomingAppoint", t.toString())
                Snackbar.make(activity_home, getString(R.string.notification_error_fetch), Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Citas>?, response: Response<Citas>?) {
                Log.w("Location", "onResponse -> getAppoitnmentFromServer")
                Log.w("Response", response?.body()?.citas.toString())
                if (SharedPrefManager.getInstance(apContext)?.storeAppoint(response?.body()?.citas!!)!!) {
                    UpdateHomeAppointUI(apContext).execute(
                            llNextAppo,
                            tvAppoDate
                    )
                }
                /*if (response?.body()?.citas?.size != 0){
                    if (SharedPrefManager.getInstance(apContext)?.storeAppoint(response?.body()?.citas!![0])!!){
                        UpdateHomeAppointUI(apContext).execute(
                                llNextAppo,
                                tvAppoDate
                        )
                    }
                }*/
            }
        })
    }

    internal fun getMealsMenuFromServer() {
        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

        val service: APIService = retrofit.create(APIService::class.java)
        val call: Call<MealMenuResult> = service.getMeals(USER_ID)
        call.enqueue(object: Callback<MealMenuResult> {
            override fun onFailure(call: Call<MealMenuResult>?, t: Throwable?) {
                Log.w("throwable -> MealMenu", t.toString())
                //Toast.makeText(apContext, t.toString(), Toast.LENGTH_LONG).show()
                Snackbar.make(activity_home, getString(R.string.notification_error_fetch), Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<MealMenuResult>?, response: Response<MealMenuResult>) {
                //Toast.makeText(apContext, response!!.body().menuCal.desayuno.op1[0].alimento, Toast.LENGTH_LONG).show()
                //Log.w("Response -> MealMenu", response!!.body().menuCal.desayuno.op1[0].cambio.cambioTres.size.toString())
                /*Log.w("Desayuno -> op1" , response.body().menuCal.desayuno.op1.size.toString())
                Log.w("Desayuno -> op2" , response.body().menuCal.desayuno.op2.size.toString())
                Log.w("Desayuno -> op3" , response.body().menuCal.desayuno.op3.size.toString())
                Log.w("Desayuno -> op4" , response.body().menuCal.desayuno.op4.size.toString())
                Log.w("Desayuno -> op5" , response.body().menuCal.desayuno.op5.size.toString())
                Log.w("Colación1 -> op1" , response.body().menuCal.colacion1.op1.size.toString())
                Log.w("Colación1 -> op2" , response.body().menuCal.colacion1.op2.size.toString())
                Log.w("Colación1 -> op3" , response.body().menuCal.colacion1.op3.size.toString())
                Log.w("Colación1 -> op4" , response.body().menuCal.colacion1.op4.size.toString())
                Log.w("Colación1 -> op5" , response.body().menuCal.colacion1.op5.size.toString())
                Log.w("Comida -> op1" , response.body().menuCal.comida.op1.size.toString())
                Log.w("Comida -> op2" , response.body().menuCal.comida.op2.size.toString())
                Log.w("Comida -> op3" , response.body().menuCal.comida.op3.size.toString())
                Log.w("Comida -> op4" , response.body().menuCal.comida.op4.size.toString())
                Log.w("Comida -> op5" , response.body().menuCal.comida.op5.size.toString())
                Log.w("Colación2 -> op1" , response.body().menuCal.colacion2.op1.size.toString())
                Log.w("Colación2 -> op2" , response.body().menuCal.colacion2.op2.size.toString())
                Log.w("Colación2 -> op3" , response.body().menuCal.colacion2.op3.size.toString())
                Log.w("Colación2 -> op4" , response.body().menuCal.colacion2.op4.size.toString())
                Log.w("Colación2 -> op5" , response.body().menuCal.colacion2.op5.size.toString())
                Log.w("Cena -> op1" , response.body().menuCal.cena.op1.size.toString())
                Log.w("Cena -> op2" , response.body().menuCal.cena.op2.size.toString())
                Log.w("Cena -> op3" , response.body().menuCal.cena.op3.size.toString())
                Log.w("Cena -> op4" , response.body().menuCal.cena.op4.size.toString())
                Log.w("Cena -> op5" , response.body().menuCal.cena.op5.size.toString())*/
                SharedPrefManager.getInstance(apContext)!!.storeMealsMenu(response.body())

            }

        })
    }

    fun loadAllFromStorage() {
        loadMeasuresData()
        loadNextAppoint()
    }

    internal fun loadMeasuresData() {
        //Log.w("loadMeasuresData", "Llegó aquí")
        measuresList = SharedPrefManager.getInstance(apContext)!!.getAllMeasures()
        //Log.w("MeasuresArraySize", measuresList.size.toString())
        if (!measuresList.isEmpty()) {
            UpdateHomeUI(apContext).execute(
                    tvResWeight,
                    tvResWaist,
                    tvResFat,
                    imWeightLittleProgress,
                    imWaistLittleProgress,
                    imFatLittleProgress,
                    tvPreviousWeight,
                    tvPreviousWaist,
                    tvPreviousFat,
                    tvNextWeight,
                    tvNextWaist,
                    tvNextFat,
                    imWeightState,
                    imWaistState,
                    imFatState
            )
            UpdateProgressUI(apContext, measuresList).execute(
                    line_view,
                    imProgressWeigthTrend,
                    imProgressWaistTrend,
                    imProgressFatTrend,
                    tvProgressWeigthPercent,
                    tvProgressWaistPercent,
                    tvProgressFatPercent
            )
        }
        //refreshUI()
    }

    internal fun loadNextAppoint() {
        incomingAppoint = SharedPrefManager.getInstance(apContext)!!.getAppoint()
        UpdateHomeAppointUI(apContext).execute(
                llNextAppo,
                tvAppoDate
        )
    }

    private fun refreshUI() {
        val last: LastMeasures = SharedPrefManager.getInstance(apContext)!!.getLastMeasures()

        when {
            last.months == 0 -> {
                tvResWeight.setText(getString(R.string.label_prog_anuncio_default))
                tvResWaist.setText(getString(R.string.label_prog_anuncio_default))
                tvResFat.setText(getString(R.string.label_prog_anuncio_default))
            }
            last.months == 1 -> {
                // TODO cambiar el label por default para este caso
                tvResWeight.setText(getString(R.string.label_prog_anuncio_default))
                tvResWaist.setText(getString(R.string.label_prog_anuncio_default))
                tvResFat.setText(getString(R.string.label_prog_anuncio_default))

                imWeightLittleProgress.setBackgroundResource(R.drawable.icono_bajada)
                imWaistLittleProgress.setBackgroundResource(R.drawable.icono_bajada)
                imFatLittleProgress.setBackgroundResource(R.drawable.icono_bajada)
            }
            else -> {
                when {
                    last.lastMonth.cintura < last.currentMonth.cintura -> {
                        tvResWaist.setText(getString(R.string.label_prog_up))
                        imWaistLittleProgress.setBackgroundResource(R.drawable.icono_subida)
                    }
                    last.lastMonth.cintura > last.currentMonth.cintura -> {
                        tvResWaist.setText(getString(R.string.label_prog_down))
                        imWaistLittleProgress.setBackgroundResource(R.drawable.icono_bajada)
                    }
                    else -> {
                        tvResWaist.setText(getString(R.string.label_prog_stay))
                        imWaistLittleProgress.setBackgroundResource(R.drawable.icono_bajada)
                    }
                }
                when {
                    last.lastMonth.peso < last.currentMonth.peso -> {
                        tvResWeight.setText(getString(R.string.label_prog_up))
                        imWeightLittleProgress.setBackgroundResource(R.drawable.icono_subida)
                    }
                    last.lastMonth.peso > last.currentMonth.peso -> {
                        tvResWeight.setText(getString(R.string.label_prog_down))
                        imWeightLittleProgress.setBackgroundResource(R.drawable.icono_bajada)
                    }
                    else -> {
                        tvResWeight.setText(getString(R.string.label_prog_stay))
                        imWeightLittleProgress.setBackgroundResource(R.drawable.icono_bajada)
                    }
                }
                when {
                    last.lastMonth.grasa < last.currentMonth.grasa -> {
                        tvResFat.setText(getString(R.string.label_prog_up))
                        imFatLittleProgress.setBackgroundResource(R.drawable.icono_subida)
                    }
                    last.lastMonth.grasa > last.currentMonth.grasa -> {
                        tvResFat.setText(getString(R.string.label_prog_down))
                        imFatLittleProgress.setBackgroundResource(R.drawable.icono_bajada)
                    }
                    else -> {
                        tvResFat.setText(getString(R.string.label_prog_stay))
                        imFatLittleProgress.setBackgroundResource(R.drawable.icono_bajada)
                    }
                }
            }
        }

        tvPreviousWeight.setText(last.lastMonth.peso.toString() + getString(R.string.label_kilo))
        tvNextWeight.setText(last.currentMonth.peso.toString() + getString(R.string.label_kilo))
        tvPreviousWaist.setText(last.lastMonth.cintura.toString() + getString(R.string.label_centimeter))
        tvNextWaist.setText(last.currentMonth.cintura.toString() + getString(R.string.label_centimeter))
        tvPreviousFat.setText(last.lastMonth.grasa.toString() + getString(R.string.label_percent))
        tvNextFat.setText(last.currentMonth.grasa.toString() + getString(R.string.label_percent))
        Log.w("Llegó aquí", "refreshUI")
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null
    }

}
