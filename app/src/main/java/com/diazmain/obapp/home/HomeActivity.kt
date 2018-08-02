package com.diazmain.obapp.home

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
import com.diazmain.obapp.home.fragments.HomeFragmentAdapter
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
import android.support.v7.app.AlertDialog
import com.diazmain.obapp.home.model.*
import com.diazmain.obapp.home.model.meals.MealMenuResult
import com.diazmain.obapp.login.AlternativeLoginActivity
import com.diazmain.obapp.notification.JobsManager
import com.diazmain.obapp.threads.*
import com.diazmain.obapp.threads.local_queries.RowsCount
import kotlinx.android.synthetic.main.fragment_progress.*
import kotlinx.android.synthetic.main.fragment_reminder_summary.*
import java.text.SimpleDateFormat


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
        myViewPager.offscreenPageLimit = 3

        USER_ID = SharedPrefManager.getInstance(apContext)!!.getUser().getId()
        USER_NAME = SharedPrefManager.getInstance(apContext)!!.getUser().getName()
        USER_LASTNAME = SharedPrefManager.getInstance(apContext)!!.getUser().getLastname()
        USER_USERNAME = SharedPrefManager.getInstance(apContext)!!.getUser().getUsername()

        RowsCount(apContext, "users").execute()
        RowsCount(apContext, "reminders").execute()
    }

    //Método para cambiar de fragment a través del ViewPager
    private fun selectItem(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_home -> myViewPager.currentItem = 0
            R.id.action_reminder -> myViewPager.currentItem = 1
            R.id.action_progress -> myViewPager.currentItem = 2
            R.id.action_profile -> myViewPager.currentItem = 3
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
                    Snackbar.make(
                            activity_home,
                            getString(R.string.notification_unstable_connection),
                            Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun getMeasuresFromServer() {
        measuresList = ArrayList()
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

        val call: Call<MeasuresResult> = service.getAllMeasures(USER_ID)

        call.enqueue(object : Callback<MeasuresResult> {
            override fun onFailure(call: Call<MeasuresResult>?, t: Throwable?) {
                if (t is SocketTimeoutException) {
                    Snackbar.make(activity_home, getString(R.string.label_connection_failure), Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(activity_home, getString(R.string.notification_error_fetch), Snackbar.LENGTH_LONG).show()
                }
                cvRefreshing.visibility = View.GONE
            }

            override fun onResponse(call: Call<MeasuresResult>?, response: Response<MeasuresResult>?) {
                cvRefreshing.visibility = View.GONE

                val measuresResponse = response?.body()?.measures
                SharedPrefManager.getInstance(apContext)?.storeAllMeasures(measuresResponse!!.toList())
                measuresList = SharedPrefManager.getInstance(apContext)!!.getAllMeasures()

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
                try {
                    if (!response!!.body().citas.fecha.isEmpty()) {
                        SharedPrefManager.getInstance(apContext)?.storeAppoint(response.body().citas)
                        UpdateHomeAppointUI(apContext).execute(
                                llNextAppo,
                                tvAppoDate
                        )
                        MarkEventsOnCalendar(apContext, compactCalendarView).execute(
                                response!!.body().citas.fecha,
                                response.body().citas.hora
                        )
                    }
                } catch (e: NullPointerException) {
                    e.printStackTrace()
                }
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
                Snackbar.make(
                        activity_home,
                        getString(R.string.notification_error_fetch),
                        Snackbar.LENGTH_LONG
                ).show()
            }

            override fun onResponse(call: Call<MealMenuResult>?, response: Response<MealMenuResult>) {
                SharedPrefManager.getInstance(apContext)!!.storeMealsMenu(response.body())
            }
        })
    }

    fun loadAllFromStorage() {
        loadMeasuresData()
        loadNextAppoint()
    }

    internal fun loadMeasuresData() {
        measuresList = SharedPrefManager.getInstance(apContext)!!.getAllMeasures()
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
    }

    internal fun loadNextAppoint() {
        incomingAppoint = SharedPrefManager.getInstance(apContext)!!.getAppoint()
        UpdateHomeAppointUI(apContext).execute(
                llNextAppo,
                tvAppoDate
        )
        MarkEventsOnCalendar(apContext, compactCalendarView).execute(
                incomingAppoint.fecha,
                incomingAppoint.hora
        )
    }

    internal fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null
    }

    override fun onResume() {
        super.onResume()
        var fecha = SharedPrefManager.getInstance(apContext)!!.getAppoint().fecha
        var diaAppo = 0;
        if (!fecha.isEmpty())
            diaAppo = Integer.parseInt(fecha.split("-")[2])
        else
            Log.w("fecha", false.toString())

        val dia: Int = diaAppo - Integer.parseInt(SimpleDateFormat("dd").format(System.currentTimeMillis()))

        if (!(dia>=0 || dia <=2)) {
            JobsManager(apContext).cancelJobs()
        } else {
            JobsManager(apContext).scheduleJob(0)
        }
    }

}
