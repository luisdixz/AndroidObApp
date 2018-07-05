package com.diazmain.obapp.Reminder

import android.annotation.SuppressLint
import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v4.view.ViewPager.OnPageChangeListener
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.diazmain.obapp.api.APIService
import com.diazmain.obapp.api.APIUrl
import com.diazmain.obapp.Login.model.Result
import com.diazmain.obapp.R
import com.diazmain.obapp.Reminder.Fragments.ReminderFragmentAdapter
import kotlinx.android.synthetic.main.activity_reminder.*
import com.diazmain.obapp.Reminder.Pojo.Alimentos
import com.diazmain.obapp.Reminder.Pojo.CamposCheck
import com.diazmain.obapp.Reminder.Pojo.Comidas
import com.diazmain.obapp.Reminder.Pojo.Recordatorio
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


class ReminderActivity : AppCompatActivity(), OnPageChangeListener, Callback<Result> {

    internal var currentPosition = 0
    private val pageAdapter = ReminderFragmentAdapter(supportFragmentManager)


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

    override fun onPageScrollStateChanged(state: Int) {

        //Log.i("Estado", state.toString())
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

        var cambio: Boolean = true

        /*when (position) {
            0 -> {
                if (desayuno.isEmpty()){
                    for (i in checks.indices) {
                        if (!validateEmptyField(checks[i].til, checks[i].tiet)) {
                            //cambio = false
                            myViewPager.currentItem = 0
                        } else {
                            desayuno.add(Alimentos(checks[i].comida, checks[i].tiet.text.toString()))
                        }
                    }
                }

                //desayuno.clear()
            }
            1 -> {
                if (colacion1.isEmpty()) {
                    for (i in checks.indices) {
                        if (!validateEmptyField(checks[i].til, checks[i].tiet)) {
                            //cambio = false
                            myViewPager.currentItem = 1
                        } else {
                            colacion1.add(Alimentos(checks[i].comida, checks[i].tiet.text.toString()))
                        }
                    }
                }
                //colacion1.clear()
            }
            2 -> {
                if (comida.isEmpty()){
                    for (i in checks.indices) {
                        if (!validateEmptyField(checks[i].til, checks[i].tiet)) {
                            //cambio = false
                            myViewPager.currentItem = 2
                        } else {
                            comida.add(Alimentos(checks[i].comida, checks[i].tiet.text.toString()))
                        }
                    }
                }
                //comida.clear()
            }
            3 -> {
                //Log.i("isEmpty", colacion2.isEmpty().toString())
                if (colacion2.isEmpty()){
                    for (i in checks.indices) {
                        if (!validateEmptyField(checks[i].til, checks[i].tiet)) {
                            //cambio = false
                            myViewPager.currentItem = 3
                        } else {
                            colacion2.add(Alimentos(checks[i].comida, checks[i].tiet.text.toString()))
                        }
                    }
                }
                //colacion2.clear()
            }
            4 -> {
                if (cena.isEmpty()){
                    for (i in checks.indices) {
                        if (!validateEmptyField(checks[i].til, checks[i].tiet)) {
                            //cambio = false
                            myViewPager.currentItem = 4
                        } else {
                            cena.add(Alimentos(checks[i].comida, checks[i].tiet.text.toString()))
                        }
                    }
                }
                //cena.clear()
            }
        }*/

        /*if (!cambio){
            myViewPager.setPagingEnabled(false)
            //bottMenu.isEnabled = false
        } else {
            myViewPager.setPagingEnabled(true)
        }*/

        //Log.i("Posición", position.toString())
    }

    override fun onPageSelected(newPosition: Int) {

        /*val fragmentToShow = pageAdapter.getItem(newPosition) as FragmentLifecycle
        fragmentToShow.onResumeFragment()

        val fragmentToHide = pageAdapter.getItem(currentPosition) as FragmentLifecycle
        fragmentToHide.onPauseFragment()

        currentPosition = newPosition*/

        /*when (newPosition) {
            1 -> {
                if (desayuno.isEmpty())
                    for (i in checks.indices) {
                        if (!validateEmptyField(checks[i].til, checks[i].tiet)) {
                            //cambio = false
                            myViewPager.currentItem = 0
                        } else {
                            desayuno.add(Alimentos(checks[i].comida, checks[i].tiet.text.toString()))
                        }
                    }
                //desayuno.clear()
            }
            2 -> {
                if (colacion1.isEmpty())
                    for (i in checks.indices) {
                        if (!validateEmptyField(checks[i].til, checks[i].tiet)) {
                            //cambio = false
                            myViewPager.currentItem = 1
                        } else {
                            colacion1.add(Alimentos(checks[i].comida, checks[i].tiet.text.toString()))
                        }
                    }
                //colacion1.clear()
            }
            3 -> {
                if (comida.isEmpty())
                    for (i in checks.indices) {
                        if (!validateEmptyField(checks[i].til, checks[i].tiet)) {
                            //cambio = false
                            myViewPager.currentItem = 2
                        } else {
                            comida.add(Alimentos(checks[i].comida, checks[i].tiet.text.toString()))
                        }
                    }
                //comida.clear()
            }
            4 -> {
                //Log.i("isEmpty", colacion2.isEmpty().toString())
                if (colacion2.isEmpty())
                    for (i in checks.indices) {
                        if (!validateEmptyField(checks[i].til, checks[i].tiet)) {
                            //cambio = false
                            myViewPager.currentItem = 3
                        } else {
                            colacion2.add(Alimentos(checks[i].comida, checks[i].tiet.text.toString()))
                        }
                    }
                //colacion2.clear()
            }
            5 -> {
                if (cena.isEmpty())
                    for (i in checks.indices) {
                        if (!validateEmptyField(checks[i].til, checks[i].tiet)) {
                            //cambio = false
                            myViewPager.currentItem = 4
                        } else {
                            cena.add(Alimentos(checks[i].comida, checks[i].tiet.text.toString()))
                        }
                    }
                //cena.clear()
            }
        }*/

        val currentItem = bottMenu.menu.getItem(newPosition).itemId
        if (currentItem != bottMenu.selectedItemId) {
            bottMenu.menu.getItem(newPosition).isChecked = true
            bottMenu.menu.findItem(bottMenu.selectedItemId).isChecked = false
        }

        Log.i(ContentValues.TAG, newPosition.toString())

        //Log.v(ContentValues.TAG, desayuno.size.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)


        bottMenu.setOnNavigationItemSelectedListener {

            selectItem(it)
        }

        initViewPager()

    }



    private fun selectItem(item : MenuItem) : Boolean {
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
            val desayunoAll = Comidas(1, desayuno)
            val colacion1All = Comidas(1, colacion1)
            val comidaAll = Comidas(1, comida)
            val colacion2All = Comidas(1, colacion2)
            val cenaAll = Comidas(1, cena)

            Log.w("Array -> desayunoAll", desayunoAll.toString())
            Log.w("Array -> colacion1All", colacion1All.toString())
            Log.w("Array -> comidaAll", comidaAll.toString())
            Log.w("Array -> colacion2All", colacion2All.toString())
            Log.w("Array -> cenaAll", cenaAll.toString())

            val recordatorio: Recordatorio = Recordatorio(1, "2018-07-02",2, desayunoAll,colacion1All, comidaAll, colacion2All, cenaAll)

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

    @SuppressLint("LongLogTag")
    override fun onResponse(call: Call<Result>?, response: Response<Result>?) {
        Toast.makeText(applicationContext, "Enviado", Toast.LENGTH_SHORT).show()
        //Log.w("2.0 getFeed > Full json res wrapped in pretty printed gson => ", GsonBuilder().setPrettyPrinting().create().toJson(response))
        finish()
    }

    fun validateEmptyField(til: TextInputLayout, tiet: TextInputEditText) : Boolean {

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
                        desayuno.add(Alimentos(checks[i].comida, checks[i].tiet.text.toString()))
                    }
                }
                2 -> {
                    if (!validateEmptyField(checks[i].til, checks[i].tiet)) {
                        //cambio = false
                        myViewPager.currentItem = 1
                        return false
                    } else {
                        colacion1.add(Alimentos(checks[i].comida, checks[i].tiet.text.toString()))
                    }
                }
                3 -> {
                    if (!validateEmptyField(checks[i].til, checks[i].tiet)) {
                        //cambio = false
                        myViewPager.currentItem = 2
                        return false
                    } else {
                        comida.add(Alimentos(checks[i].comida, checks[i].tiet.text.toString()))
                    }
                }
                4 -> {
                    if (!validateEmptyField(checks[i].til, checks[i].tiet)) {
                        //cambio = false
                        myViewPager.currentItem = 3
                        return false
                    } else {
                        colacion2.add(Alimentos(checks[i].comida, checks[i].tiet.text.toString()))
                    }
                }
                5 -> {
                    if (!validateEmptyField(checks[i].til, checks[i].tiet)) {
                        //cambio = false
                        myViewPager.currentItem = 4
                        return false
                    } else {
                        Log.wtf("Comida", checks[i].comida)
                        cena.add(Alimentos(checks[i].comida, checks[i].tiet.text.toString()))
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

    fun initViewPager() {
        myViewPager.adapter = pageAdapter
        myViewPager.addOnPageChangeListener(this)
        myViewPager.offscreenPageLimit = 4
    }


    /*private class RegisterUser : AsyncTask<User, Void, Boolean>() {

        override fun doInBackground(vararg params: User?): Boolean {

        }

        override fun onPostExecute(result: Boolean?) {
            super.onPostExecute(result)

        }

    }*/
}
