package com.diazmain.obapp.Reminder

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v4.view.ViewPager.OnPageChangeListener
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.diazmain.obapp.Home.model.meals.MealMenuResult
import com.diazmain.obapp.api.APIService
import com.diazmain.obapp.api.APIUrl
import com.diazmain.obapp.Login.model.Result
import com.diazmain.obapp.R
import com.diazmain.obapp.Reminder.Fragments.ReminderFragmentAdapter
import com.diazmain.obapp.Reminder.Pojo.*
import kotlinx.android.synthetic.main.activity_reminder.*
import com.diazmain.obapp.helper.SharedPrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.text.SimpleDateFormat
import java.util.*


class ReminderActivity : AppCompatActivity(), OnPageChangeListener, Callback<Result> {

    internal var currentPosition = 0
    private val pageAdapter = ReminderFragmentAdapter(supportFragmentManager)
    internal lateinit var mealsMenu: MealMenuResult
    internal lateinit var context: Context
    internal var USER_ID: Int = 0

    //private var desayunoAll: Comidas? = null
    var desayuno: ArrayList<Alimentos> = ArrayList()

    //private var colacion1All: Comidas? = null
    var colacion1: ArrayList<Alimentos> = ArrayList()

    //private var comidaAll: Comidas? = null
    var comida: ArrayList<Alimentos> = ArrayList()

    //private var colacion2All: Comidas? = null
    var colacion2: ArrayList<Alimentos> = ArrayList()

    //private var cenaAll: Comidas? = null
    var cena: ArrayList<Alimentos> = ArrayList()

    //var isEmpty: ArrayList<Boolean> = ArrayList()
    var checks: ArrayList<CamposCheck> = ArrayList()

    var swaps: ArrayList<FoodSwapInfo> = ArrayList()

    var desHora: String = "00:00"
    var co1Hora: String = "00:00"
    var comHora: String = "00:00"
    var co2Hora: String = "00:00"
    var cenHora: String = "00:00"

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(newPosition: Int) {

        val currentItem = bottMenu.menu.getItem(newPosition).itemId
        if (currentItem != bottMenu.selectedItemId) {
            bottMenu.menu.getItem(newPosition).isChecked = true
            bottMenu.menu.findItem(bottMenu.selectedItemId).isChecked = false
        }

        Log.i(ContentValues.TAG, newPosition.toString())
        Log.w("Swap -> size", swaps.size.toString())
        if (swaps.size != 0)
            Log.w("Swap", "Comida: " + swaps[0].food + " - Swap1: " + swaps[0].swap1 + " - Swap2: " + swaps[0].swap2 + " - Swap3: " + swaps[0].swap3)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)

        USER_ID = intent.getExtras().getInt("USER_ID")

        bottMenu.setOnNavigationItemSelectedListener {
            selectItem(it)
        }

        //Esto es lo que he estado agregando
        context = this.applicationContext
        mealsMenu = SharedPrefManager.getInstance(context)!!.getMealsMenu()
        //Aqui se acaba lo nuevo jeje

        initViewPager()
    }

    private fun selectItem(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_step1 -> {
                myViewPager.currentItem = 0
            }
            R.id.action_step2 -> {
                myViewPager.currentItem = 1
            }
            R.id.action_step3 -> {
                myViewPager.currentItem = 2
            }
            R.id.action_step4 -> {
                myViewPager.currentItem = 3
            }
            R.id.action_step5 -> {
                myViewPager.currentItem = 4
            }
            else -> {
                myViewPager.currentItem = 0
            }
        }
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun sendData() {

        if (fillArrays()) {
            val desayunoAll = Comidas(desHora, desayuno)
            val colacion1All = Comidas(co1Hora, colacion1)
            val comidaAll = Comidas(comHora, comida)
            val colacion2All = Comidas(co2Hora, colacion2)
            val cenaAll = Comidas(cenHora, cena)

            Log.w("Array -> desayunoAll", desayunoAll.toString())
            Log.w("Array -> colacion1All", colacion1All.toString())
            Log.w("Array -> comidaAll", comidaAll.toString())
            Log.w("Array -> colacion2All", colacion2All.toString())
            Log.w("Array -> cenaAll", cenaAll.toString())

            val dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            val dayFormat: SimpleDateFormat = SimpleDateFormat("dd")

            val recordatorio: Recordatorio = Recordatorio(USER_ID, dateFormat.format(System.currentTimeMillis()).toString(), Integer.parseInt(dayFormat.format(System.currentTimeMillis()).toString()), desayunoAll, colacion1All, comidaAll, colacion2All, cenaAll)

            val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val retrofit: Retrofit = Retrofit.Builder()
                    .baseUrl(APIUrl.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()

            val service: APIService = retrofit.create(APIService::class.java)

            val call: Call<Result> = service.addReminder(recordatorio)

            call.enqueue(this)
        }

    }

    override fun onFailure(call: Call<Result>?, t: Throwable?) {
        Toast.makeText(applicationContext, t.toString(), Toast.LENGTH_SHORT).show()

    }

    override fun onResponse(call: Call<Result>?, response: Response<Result>?) {
        Toast.makeText(applicationContext, "Enviado", Toast.LENGTH_SHORT).show()
        //Log.w("2.0 getFeed > Full json res wrapped in pretty printed gson => ", GsonBuilder().setPrettyPrinting().create().toJson(response))
        SharedPrefManager.getInstance(applicationContext)?.setAppointStatus(0)
        finish()
    }

    fun validateEmptyField(til: TextInputLayout, tiet: TextInputEditText): Boolean {

        if (til.visibility == View.VISIBLE) {
            if (tiet.text.isEmpty()) {
                til.error = getString(R.string.validate_empty)
                return false
            } else {
                til.isErrorEnabled = false
            }
        }

        return true
    }

    private fun fillArrays(): Boolean {
        for (i in checks.indices) {
            when (checks[i].pagina) {
                1 -> {
                    if (!validateEmptyField(checks[i].til, checks[i].tiet)) {
                        //cambio = false
                        Log.w("EditTextError", checks[i].tiet.id.toString())
                        myViewPager.currentItem = 0
                        return false
                    } else {
                        //var cambio: String = ""
                        desayuno.add(Alimentos(checks[i].comida.text.toString(), checks[i].tiet.text.toString(), getFoodSwap(1, i)))
                    }
                }
                2 -> {
                    if (!validateEmptyField(checks[i].til, checks[i].tiet)) {
                        //cambio = false
                        myViewPager.currentItem = 1
                        return false
                    } else {
                        colacion1.add(Alimentos(checks[i].comida.text.toString(), checks[i].tiet.text.toString(), getFoodSwap(2, i)))
                    }
                }
                3 -> {
                    if (!validateEmptyField(checks[i].til, checks[i].tiet)) {
                        //cambio = false
                        myViewPager.currentItem = 2
                        return false
                    } else {
                        comida.add(Alimentos(checks[i].comida.text.toString(), checks[i].tiet.text.toString(), getFoodSwap(3, i)))
                    }
                }
                4 -> {
                    if (!validateEmptyField(checks[i].til, checks[i].tiet)) {
                        //cambio = false
                        myViewPager.currentItem = 3
                        return false
                    } else {
                        colacion2.add(Alimentos(checks[i].comida.text.toString(), checks[i].tiet.text.toString(), getFoodSwap(4, i)))
                    }
                }
                5 -> {
                    if (!validateEmptyField(checks[i].til, checks[i].tiet)) {
                        //cambio = false
                        myViewPager.currentItem = 4
                        return false
                    } else {
                        cena.add(Alimentos(checks[i].comida.text.toString(), checks[i].tiet.text.toString(), getFoodSwap(5, i)))
                    }
                }
            }
        }
        Log.w("Array -> checks", checks.toString())

        Log.wtf("checksSize", checks.size.toString())
        checks.clear()
        Log.wtf("checksSize", checks.size.toString())
        return true
    }

    private fun getFoodSwap(pag: Int, index: Int): String {
        val builder: StringBuilder = StringBuilder()

        for (i in swaps.indices) {
            if (swaps[i].pagina == pag) {
                if (swaps[i].food.equals(checks[index].comida))
                    if (!swaps[i].swap1.isEmpty())
                        builder.append(swaps[i].swap1 + ", ")
                    else if (!swaps[i].swap2.isEmpty())
                        builder.append(swaps[i].swap2 + ", ")
                    else if (!swaps[i].swap3.isEmpty())
                        builder.append(swaps[i].swap1 + ", ")
            }
        }

        //val result: String = builder.substring(0, builder.length - 2)
        val result: String = builder.toString()
        //val res2: String = result.substring(0, result.length - 2)

        if (result.length != 0)
            return result.substring(0, result.length - 2)
        else
            return result
    }

    fun initViewPager() {
        myViewPager.adapter = pageAdapter
        myViewPager.addOnPageChangeListener(this)
        myViewPager.offscreenPageLimit = 4
    }
}
