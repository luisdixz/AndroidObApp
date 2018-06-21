package com.diazmain.obapp.Reminder

import android.annotation.SuppressLint
import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v4.view.ViewPager
import android.support.v4.view.ViewPager.OnPageChangeListener
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.diazmain.obapp.Login.api.APIService
import com.diazmain.obapp.Login.api.APIUrl
import com.diazmain.obapp.Login.model.Result
import com.diazmain.obapp.R
import com.diazmain.obapp.Reminder.Fragments.ReminderFragmentAdapter
import kotlinx.android.synthetic.main.activity_reminder.*
import com.diazmain.obapp.Reminder.Fragments.FragmentLifecycle
import com.diazmain.obapp.Reminder.Pojo.Alimentos
import com.diazmain.obapp.Reminder.Pojo.Comidas
import com.diazmain.obapp.Reminder.Pojo.Recordatorio
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.Gson


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

    var isEmpty: ArrayList<Boolean> = ArrayList()

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(newPosition: Int) {

        /*val fragmentToShow = pageAdapter.getItem(newPosition) as FragmentLifecycle
        fragmentToShow.onResumeFragment()

        val fragmentToHide = pageAdapter.getItem(currentPosition) as FragmentLifecycle
        fragmentToHide.onPauseFragment()

        currentPosition = newPosition*/



        val currentItem = bottMenu.menu.getItem(newPosition).itemId
        if (currentItem != bottMenu.selectedItemId) {
            bottMenu.menu.getItem(newPosition).isChecked = true
            bottMenu.menu.findItem(bottMenu.selectedItemId).isChecked = false
        }

        //Log.v(ContentValues.TAG, desayuno.size.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)

        bottMenu.setOnNavigationItemSelectedListener {
            selectItem(it)
        }


        myViewPager.adapter = pageAdapter
        myViewPager.addOnPageChangeListener(this)

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
        val desayunoAll = Comidas(1, desayuno)
        val colacion1All = Comidas(1, colacion1)
        val comidaAll = Comidas(1, comida)
        val colacion2All = Comidas(1, colacion2)
        val cenaAll = Comidas(1, cena)

        val recordatorio: Recordatorio = Recordatorio(1, "2018-06-20",30, desayunoAll,colacion1All, comidaAll, colacion2All, cenaAll)

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service: APIService = retrofit.create(APIService::class.java)

        val call: Call<Result> = service.addReminder(recordatorio)

        call.enqueue(this)
    }

    override fun onFailure(call: Call<Result>?, t: Throwable?) {
        Toast.makeText(applicationContext, t.toString(), Toast.LENGTH_SHORT).show()

    }

    @SuppressLint("LongLogTag")
    override fun onResponse(call: Call<Result>?, response: Response<Result>?) {
        Toast.makeText(applicationContext, "Enviado", Toast.LENGTH_SHORT).show()
        Log.w ("2.0 getFeed > Full json res wrapped in gson => ", Gson().toJson(response))
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
}
