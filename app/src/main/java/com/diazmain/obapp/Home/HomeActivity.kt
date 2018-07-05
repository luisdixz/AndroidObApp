package com.diazmain.obapp.Home

import android.content.Context
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
import com.diazmain.obapp.Home.model.MeasuresResult
import com.diazmain.obapp.Home.model.MeasuresValue
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
import com.diazmain.obapp.Home.model.LastMeasures
import com.diazmain.obapp.Threads.InternetAccessibility
import com.diazmain.obapp.Threads.UpdateHomeUI


/**
 * @author LuisDixz
 * <p>Clase principal, sirve de contenedor para los fragment.<br>Se hace uso del componente ViewPager para cambiar de fragment</p>
 */
class HomeActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {

    private var measuresList: List<MeasuresValue> = ArrayList()
    internal val apContext: Context = this

    lateinit var USER_ID: String
    lateinit var USER_NAME: String
    lateinit var USER_LASTNAME: String
    lateinit var USER_USERNAME: String

    var isReady: Boolean = false

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        /*UpdateHomeUI(apContext).execute(
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
        )*/
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
            startActivity(Intent(this, SplashScreen::class.java))
        }



        bottMenu.setOnNavigationItemSelectedListener {
            selectItem(it)
        }

        val pageAdapter = HomeFragmentAdapter(supportFragmentManager)
        myViewPager.adapter = pageAdapter
        myViewPager.addOnPageChangeListener(this)
        myViewPager.offscreenPageLimit = 2
        /*try {

            loadMeasuresData()

        } catch (e: Exception) {
            Log.wtf("Exception", e.message)
            Log.wtf("Exception", e.localizedMessage)
        }

        if (measuresList.isEmpty()) {
            refreshMeasuresData()
        }*/


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
                finish()
                startActivity(Intent(apContext, SplashScreen::class.java))
                return SharedPrefManager.getInstance(apContext)?.logout()!!
            }
            R.id.action_refresh -> {
                try {
                    //refreshMeasuresData()
                    getMeasuresFromServer()
                } catch (e: Exception) {
                    Log.wtf("action_refresh -> Exception", e.localizedMessage)
                }
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun getMeasuresFromServer()  {
        //if (hasActiveInternetConnection(apContext)) {

        measuresList = ArrayList()

        if (isNetworkAvailable()) {
            InternetAccessibility {
                internet ->
                if (internet) {
                    cvRefreshing.visibility = View.VISIBLE
                    val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
                    interceptor.level = HttpLoggingInterceptor.Level.BODY
                    val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

                    val retrofit: Retrofit = Retrofit.Builder()
                            .baseUrl(APIUrl.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(client)
                            .build()

                    val service: APIService = retrofit.create(APIService::class.java)

                    val call: Call<MeasuresResult> = service.getAllMeasures(2)

                    call.enqueue(object : Callback<MeasuresResult> {
                        override fun onFailure(call: Call<MeasuresResult>?, t: Throwable?) {
                            Log.wtf("Failure", t.toString())
                            if (t is SocketTimeoutException) {
                                Snackbar.make(activity_home, "Error de conexión: Conexión lenta", Snackbar.LENGTH_LONG).show()
                            } else {
                                Snackbar.make(activity_home, "Error al obtener datos del servidor", Snackbar.LENGTH_LONG).show()
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
                            Log.w("MeasuresArraySize", measuresList.size.toString())

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
                        }
                    })

                    /*if (call.isCanceled) {
                        sent = false
                        Log.w("isCanceled", call.isCanceled.toString())
                    }*/

                } else {
                    Snackbar.make(activity_home,"La conexión a internet es inestable...", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
        //Log.w("conectedState", conected.toString())
    }

    internal fun loadMeasuresData() {
        Log.w("loadMeasuresData", "Llegó aquí")
        measuresList = SharedPrefManager.getInstance(apContext)!!.getAllMeasures()
        Log.w("MeasuresArraySize", measuresList.size.toString())
        if (!measuresList.isEmpty())
            /*UpdateHomeUI(apContext).execute(
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
            )*/
            refreshUI()
    }

    private fun refreshUI() {
        val last: LastMeasures = SharedPrefManager.getInstance(apContext)!!.getLastMeasures()

        when {
            last.months == 0 -> {
                tvResWeight.setText(getString(R.string.label_prog_anuncio_default))
                tvResWaist.setText(getString(R.string.label_prog_anuncio_default))
                tvResFat.setText(getString(R.string.label_prog_anuncio_default))
            }
            last.months == 1 ->{
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
        Log.w("Llegó aquí", "UpdateUI")
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null
    }



}
