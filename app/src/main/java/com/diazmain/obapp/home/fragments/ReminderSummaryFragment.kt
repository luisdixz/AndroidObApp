package com.diazmain.obapp.home.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.diazmain.obapp.home.HomeActivity
import com.diazmain.obapp.login.model.Result

import com.diazmain.obapp.R
import com.diazmain.obapp.reminder.*
import com.diazmain.obapp.reminder.pojo.Comidas
import com.diazmain.obapp.reminder.pojo.Recordatorio
import com.diazmain.obapp.api.APIService
import com.diazmain.obapp.api.APIUrl
import com.diazmain.obapp.helper.SharedPrefManager
import com.diazmain.obapp.room.ReminderEntity
import com.diazmain.obapp.threads.local_queries.DisplayReminder
import com.diazmain.obapp.threads.local_queries.InsertNewReminder
import com.diazmain.obapp.threads.local_queries.ReminderFromToday
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_reminder_summary.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*


class ReminderSummaryFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() = ReminderSummaryFragment()
    }

    private val currentCalender = Calendar.getInstance(Locale.getDefault())
    private val dateFormatForDisplaying = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    private val dateFormatForMonth = SimpleDateFormat("MMMM - yyyy", Locale.getDefault())

    private val dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    private val dayFormat: SimpleDateFormat = SimpleDateFormat("dd")


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tvMonth.setText(dateFormatForMonth.format(System.currentTimeMillis()).toString().capitalize())
        val cita = SharedPrefManager.getInstance((activity as HomeActivity).apContext)!!.getAppoint()
        tvCurrentDate.setText(dateFormatForDisplaying.format(System.currentTimeMillis()))

        imvNextMonth.setOnClickListener(this)
        imvPreviousMonth.setOnClickListener(this)

        tvCurrentDate.setText(dateFormatForDisplaying.format(Calendar.getInstance().timeInMillis))

        compactCalendarView.setListener(object : CompactCalendarView.CompactCalendarViewListener {
            override fun onDayClick(dateClicked: Date) {
                val clicked: String = dateFormat.format(dateClicked).toString()
                val appoDate: String = SharedPrefManager.getInstance((activity as HomeActivity).apContext)!!.getAppoint().fecha
                tvCurrentDate.setText(dateFormatForDisplaying.format(dateClicked))

                if (clicked.equals(appoDate)) {
                    val builder: AlertDialog.Builder = AlertDialog.Builder((activity as HomeActivity).apContext)
                    builder.setTitle(getString(R.string.label_agenda))
                    builder.setMessage(SharedPrefManager.getInstance((activity as HomeActivity).apContext)!!.getAppoint().toString())
                    builder.setPositiveButton(getString(R.string.btn_accept), null)
                    val alert: AlertDialog = builder.create()
                    alert.show()
                }
                DisplayReminder((activity as HomeActivity).apContext, clicked, (activity as HomeActivity).USER_ID)
                        .execute(
                                tvexlBreakfastContent,
                                tvexlCollation1Content,
                                tvexlMealContent,
                                tvexlCollation2Content,
                                tvexlDinnerContent)

            }

            override fun onMonthScroll(firstDayOfNewMonth: Date) {
                tvMonth.setText(SimpleDateFormat("MMMM - yyyy").format(firstDayOfNewMonth).toString().capitalize())
            }
        })

        ReminderFromToday(
                (activity as HomeActivity).apContext,
                dateFormat.format(System.currentTimeMillis()),
                (activity as HomeActivity).USER_ID,
                object : ReminderFromToday.TaskCompleted {
                    override fun onTodayReminderNotRegistered(isStored: Boolean) {
                        if (isStored) {
                            SharedPrefManager.getInstance((activity as HomeActivity).apContext)!!.cleanReminderParts()
                        }
                        checkDayMealsHistory()
                    }
                }
        ).execute()

        DisplayReminder(
                (activity as HomeActivity).apContext,
                dateFormat.format(System.currentTimeMillis()).toString(),
                (activity as HomeActivity).USER_ID)
                .execute(
                        tvexlBreakfastContent,
                        tvexlCollation1Content,
                        tvexlMealContent,
                        tvexlCollation2Content,
                        tvexlDinnerContent)

        //checkDayMealsHistory()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reminder_summary, container, false)
    }

    override fun onClick(v: View?) {
        when (v) {
            imvNextMonth -> {
                compactCalendarView.scrollRight()
            }
            imvPreviousMonth -> {
                compactCalendarView.scrollLeft()
            }
            cvReminderBreakfast -> {
                startActivity(Intent((activity as HomeActivity).apContext, BreakfastActivity::class.java).putExtra("USER_ID", (activity as HomeActivity).USER_ID))
            }
            cvReminderCollation1 -> {
                startActivity(Intent((activity as HomeActivity).apContext, FirstCollationActivity::class.java).putExtra("USER_ID", (activity as HomeActivity).USER_ID))
            }
            cvReminderMeal -> {
                startActivity(Intent((activity as HomeActivity).apContext, MealActivity::class.java).putExtra("USER_ID", (activity as HomeActivity).USER_ID))
            }
            cvReminderCollation2 -> {
                startActivity(Intent((activity as HomeActivity).apContext, SecondCollationActivity::class.java).putExtra("USER_ID", (activity as HomeActivity).USER_ID))
            }
            cvReminderDinner -> {
                startActivity(Intent((activity as HomeActivity).apContext, DinnerActivity::class.java).putExtra("USER_ID", (activity as HomeActivity).USER_ID))
            }
            btnSendReminder -> {
                sendData()
            }
        }
    }

    private fun fillPastReminderContent() {
        try {
            val recordatorio: Recordatorio = SharedPrefManager.getInstance((activity as HomeActivity).apContext)!!.getLastCompleteReminder()
            tvexlBreakfastContent.setText(recordatorio.desayuno.toString())
            tvexlCollation1Content.setText(recordatorio.colacion1.toString())
            tvexlMealContent.setText(recordatorio.comida.toString())
            tvexlCollation2Content.setText(recordatorio.colacion2.toString())
            tvexlDinnerContent.setText(recordatorio.cena.toString())
        } catch (e: Exception) {
            Log.wtf("fillPastReminderContent", e.printStackTrace().toString())
        }
    }

    private fun sendData() {
        if ((activity as HomeActivity).isNetworkAvailable()) {

            var fecha: String = ""
            try {
                fecha = SharedPrefManager.getInstance((activity as HomeActivity).apContext)!!.getLastCompleteReminderDate()
            } catch (e: NullPointerException) {
                e.printStackTrace()
            }

            if (!fecha.isEmpty() && !fecha.equals(dateFormat.format(System.currentTimeMillis()))) {
                val recordatorio: Recordatorio = SharedPrefManager.getInstance((activity as HomeActivity).apContext)!!.getLastCompleteReminder()
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
                call.enqueue(object : Callback<Result> {
                    override fun onFailure(call: Call<Result>?, t: Throwable?) {
                        Snackbar.make((activity as HomeActivity).activity_home, getString(R.string.notification_unstable_connection), Snackbar.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<Result>?, response: Response<Result>?) {
                        llSendButton.visibility = View.GONE
                        Snackbar.make((activity as HomeActivity).activity_home, response!!.body().getMessage(), Snackbar.LENGTH_LONG).show()
                    }
                })
            }

        } else {
            Snackbar.make((activity as HomeActivity).activity_home, getString(R.string.label_connection_failure), Snackbar.LENGTH_LONG).show()
        }
    }

    fun checkDayMealsHistory() {
        //NukeTable jejeje

        /*var fecha: String = ""
        var ahora: String = ""
        try {
            fecha = SharedPrefManager.getInstance((activity as HomeActivity).apContext)!!.getLastCompleteReminderDate()
            ahora = SharedPrefManager.getInstance((activity as HomeActivity).apContext)!!.getTodayMealRegistaredDate()
            Log.w("LastCompleteReminder", fecha)
            val differentDate: Boolean = !fecha.equals(dateFormat.format(System.currentTimeMillis()))
            val itIsNotNow: Boolean = !ahora.equals(dateFormat.format(System.currentTimeMillis()))
            if (differentDate && !fecha.isEmpty() && !ahora.isEmpty() && !itIsNotNow) {
                SharedPrefManager.getInstance((activity as HomeActivity).apContext)!!.cleanReminderParts()
            }
        } catch (e: Exception) {
            Log.wtf("Exception", e.printStackTrace().toString())
        }*/

        var flag1: Boolean = false
        var flag2: Boolean = false
        var flag3: Boolean = false
        var flag4: Boolean = false
        var flag5: Boolean = false

        if (SharedPrefManager.getInstance((activity as HomeActivity).apContext)!!.isBreakfastStored()) {
            imvBreakfastState.setBackgroundResource(R.drawable.ic_check_circle_green)
            cvReminderBreakfast.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    val builder: AlertDialog.Builder = AlertDialog.Builder((activity as HomeActivity).apContext)
                    builder.setTitle(getString(R.string.tab_breakfast))
                    builder.setMessage(SharedPrefManager.getInstance((activity as HomeActivity).apContext)!!.getReminderPart(1).toString())
                    builder.setPositiveButton(getString(R.string.btn_accept), null)
                    val alert: AlertDialog = builder.create()
                    alert.show()
                }
            })
            flag1 = true
        } else {
            imvBreakfastState.setBackgroundResource(R.drawable.ic_info_blue)
            cvReminderBreakfast.setOnClickListener(this)
        }

        if (SharedPrefManager.getInstance((activity as HomeActivity).apContext)!!.isFirstCollationStored()) {
            imvCol1State.setBackgroundResource(R.drawable.ic_check_circle_green)
            cvReminderCollation1.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    val builder: AlertDialog.Builder = AlertDialog.Builder((activity as HomeActivity).apContext)
                    builder.setTitle(getString(R.string.tab_collation1))
                    builder.setMessage(SharedPrefManager.getInstance((activity as HomeActivity).apContext)!!.getReminderPart(2).toString())
                    builder.setPositiveButton(getString(R.string.btn_accept), null)
                    val alert: AlertDialog = builder.create()
                    alert.show()
                }
            })
            flag2 = true
        } else {
            imvCol1State.setBackgroundResource(R.drawable.ic_info_blue)
            cvReminderCollation1.setOnClickListener(this)
        }

        if (SharedPrefManager.getInstance((activity as HomeActivity).apContext)!!.isMealTimeStored()) {
            imvMealState.setBackgroundResource(R.drawable.ic_check_circle_green)
            cvReminderMeal.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    val builder: AlertDialog.Builder = AlertDialog.Builder((activity as HomeActivity).apContext)
                    builder.setTitle(getString(R.string.tab_lunch))
                    builder.setMessage(SharedPrefManager.getInstance((activity as HomeActivity).apContext)!!.getReminderPart(3).toString())
                    builder.setPositiveButton(getString(R.string.btn_accept), null)
                    val alert: AlertDialog = builder.create()
                    alert.show()
                }
            })
            flag3 = true
        } else {
            imvMealState.setBackgroundResource(R.drawable.ic_info_blue)
            cvReminderMeal.setOnClickListener(this)
        }

        if (SharedPrefManager.getInstance((activity as HomeActivity).apContext)!!.isSecondCollationStored()) {
            imvCol2State.setBackgroundResource(R.drawable.ic_check_circle_green)
            cvReminderCollation2.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    val builder: AlertDialog.Builder = AlertDialog.Builder((activity as HomeActivity).apContext)
                    builder.setTitle(getString(R.string.tab_collation2))
                    builder.setMessage(SharedPrefManager.getInstance((activity as HomeActivity).apContext)!!.getReminderPart(4).toString())
                    builder.setPositiveButton(getString(R.string.btn_accept), null)
                    val alert: AlertDialog = builder.create()
                    alert.show()
                }
            })
            flag4 = true
        } else {
            imvCol2State.setBackgroundResource(R.drawable.ic_info_blue)
            cvReminderCollation2.setOnClickListener(this)
        }

        if (SharedPrefManager.getInstance((activity as HomeActivity).apContext)!!.isDinnerStored()) {
            imvDinnerState.setBackgroundResource(R.drawable.ic_check_circle_green)
            cvReminderDinner.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    val builder: AlertDialog.Builder = AlertDialog.Builder((activity as HomeActivity).apContext)
                    builder.setTitle(getString(R.string.tab_dinner))
                    builder.setMessage(SharedPrefManager.getInstance((activity as HomeActivity).apContext)!!.getReminderPart(5).toString())
                    builder.setPositiveButton(getString(R.string.btn_accept), null)
                    val alert: AlertDialog = builder.create()
                    alert.show()
                }
            })
            flag5 = true
        } else {
            imvDinnerState.setBackgroundResource(R.drawable.ic_info_blue)
            cvReminderDinner.setOnClickListener(this)
        }

        if (flag1 && flag2 && flag3 && flag4 && flag5) {

            val desayuno: Comidas = SharedPrefManager.getInstance((activity as HomeActivity).apContext)!!.getReminderPart(1)
            val colacion1: Comidas = SharedPrefManager.getInstance((activity as HomeActivity).apContext)!!.getReminderPart(2)
            val comida: Comidas = SharedPrefManager.getInstance((activity as HomeActivity).apContext)!!.getReminderPart(3)
            val colacion2: Comidas = SharedPrefManager.getInstance((activity as HomeActivity).apContext)!!.getReminderPart(4)
            val cena: Comidas = SharedPrefManager.getInstance((activity as HomeActivity).apContext)!!.getReminderPart(5)

            val recordatorio: Recordatorio = Recordatorio(
                    (activity as HomeActivity).USER_ID,
                    dateFormat.format(System.currentTimeMillis()).toString(),
                    1,
                    desayuno,
                    colacion1,
                    comida,
                    colacion2,
                    cena)

            InsertNewReminder(
                    (activity as HomeActivity).apContext,
                    ReminderEntity(
                            dateFormat.format(System.currentTimeMillis()),
                            (activity as HomeActivity).USER_ID,
                            recordatorio
                    )
            ).execute()

            SharedPrefManager.getInstance((activity as HomeActivity).apContext)!!.storeLastCompleteReminder(recordatorio)

            //val remEntity: ReminderEntity? = null

            Log.w("Cita", SharedPrefManager.getInstance((activity as HomeActivity).apContext)!!.getAppoint().fecha)

            var diaCita: Int = 0
            val fecha: String = SharedPrefManager.getInstance((activity as HomeActivity).apContext)!!.getAppoint().fecha
            if (!fecha.isEmpty()) {
                diaCita = Integer.parseInt(fecha.split("-")[2])
            }

            val diffDays: Int = diaCita - Integer.parseInt(dayFormat.format(System.currentTimeMillis()))

            if (!((diffDays > 2) || (diffDays < 1)) && !fecha.equals(dateFormat.format(System.currentTimeMillis()))) {
                llSendButton.visibility = View.VISIBLE
                sendData()
            } else {
                llSendButton.visibility = View.GONE
            }

        }
    }

    override fun onResume() {
        super.onResume()
        Log.w("ReminderSummaryFragment", "onResume")
        checkDayMealsHistory()
    }
}
