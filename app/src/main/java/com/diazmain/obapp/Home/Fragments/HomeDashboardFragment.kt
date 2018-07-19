package com.diazmain.obapp.Home.Fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.diazmain.obapp.Home.HomeActivity
import com.diazmain.obapp.Home.model.GenericResult
import com.diazmain.obapp.Login.SplashScreen
import com.diazmain.obapp.helper.SharedPrefManager
import com.diazmain.obapp.R
import com.diazmain.obapp.Reminder.ReminderActivity
import com.diazmain.obapp.api.APIService
import com.diazmain.obapp.api.APIUrl
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_home_dashboard.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeDashboardFragment: Fragment(), View.OnClickListener {
    companion object {
        fun newInstance() = HomeDashboardFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        cvNextAppo.setOnClickListener(this)

        //(activity as HomeActivity).isReady = true
        //Log.w("isMeasuresListEmpty", SharedPrefManager.getInstance((activity as HomeActivity).apContext)!!.isMeasuresArrayEmpty().toString())

        Log.w("Location","onActivityCreated -> HomeDashboardFragment")
        /*if (SharedPrefManager.getInstance((activity as HomeActivity).apContext)!!.isMeasuresArrayEmpty())
            (activity as HomeActivity).getAllFromServer()
        else
            (activity as HomeActivity).loadAllFromStorage()*/


        btnCancelAppo.setOnClickListener(this)
        btnConfirmAppo.setOnClickListener(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home_dashboard, container, false)
    }

    override fun onClick(v: View?) {
        if (v == cvNextAppo) {
            //Checar el status de la cita
            startActivity(Intent(activity, ReminderActivity::class.java).putExtra("USER_ID", (activity as HomeActivity).USER_ID))

            /*if (SharedPrefManager.getInstance((activity as HomeActivity).apContext)?.getAppointStatus() == 2) {

                if (SharedPrefManager.getInstance((activity as HomeActivity).apContext)?.isMenuStored()!!)
                    startActivity(Intent(activity, ReminderActivity::class.java).putExtra("USER_ID", (activity as HomeActivity).USER_ID))
                else
                    Snackbar.make(activity_home, R.string.info_unknown_error_meals, Snackbar.LENGTH_SHORT).show()
            }*/
        } else if (v == btnCancelAppo) {
            //Enviar request para cancelar cita
            cancelAppoint()
        } else if (v == btnConfirmAppo) {
            //Enviar request para confirmar cita
            confirmAppoint()
            /*startActivity(Intent(activity, ReminderActivity::class.java))
            llNextAppo.visibility = View.GONE*/
        }
    }

    private fun cancelAppoint() {
        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

        val service: APIService = retrofit.create(APIService::class.java)

        val call: Call<GenericResult> = service.setAppointStatus(
                (activity as HomeActivity).incomingAppoint.idCita,
                0
        )

        call.enqueue(object : Callback<GenericResult> {
            override fun onFailure(call: Call<GenericResult>?, t: Throwable?) {
                Snackbar.make(activity_home, getString(R.string.notification_appoint_cancel_error), Snackbar.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<GenericResult>?, response: Response<GenericResult>?) {
                if (!response?.body()?.error!!) {
                    SharedPrefManager.getInstance((activity as HomeActivity))!!.setAppointStatus(0)
                    startActivity(Intent(activity, ReminderActivity::class.java))
                    llNextAppo.visibility = View.GONE
                } else {
                    Log.wtf("onResponse -> Error", response.message()+" -> HomeDashboardFragment")
                }
            }
        })
    }

    private fun confirmAppoint() {
        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

        val service: APIService = retrofit.create(APIService::class.java)


        Log.w("incomingAppoint -> id", (activity as HomeActivity).incomingAppoint.idCita.toString())
        val call: Call<GenericResult> = service.setAppointStatus(
                (activity as HomeActivity).incomingAppoint.idCita,
                2
        )

        call.enqueue(object : Callback<GenericResult> {
            override fun onFailure(call: Call<GenericResult>?, t: Throwable?) {
                Snackbar.make(activity_home, getString(R.string.notification_appoint_confirm_error), Snackbar.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<GenericResult>?, response: Response<GenericResult>?) {
                Log.w("Location", "onResponse -> HomeDashboardFragment")
                Log.w("Response message", response?.body()?.message)
                SharedPrefManager.getInstance((activity as HomeActivity))!!.setAppointStatus(2)

                if (SharedPrefManager.getInstance((activity as HomeActivity).apContext)?.isMenuStored()!!)
                    startActivity(Intent(activity, ReminderActivity::class.java).putExtra("USER_ID", (activity as HomeActivity).USER_ID))
                else
                    Snackbar.make(activity_home, R.string.info_unknown_error_meals, Snackbar.LENGTH_SHORT).show()

                llNextAppo.visibility = View.GONE
            }
        })
    }

    /*override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        //Log.w("isVisibleToUser -> HomeDashboardFragment", isVisibleToUser.toString())
        if ((activity as HomeActivity).isReady) {
            if (isVisibleToUser) {
                if (SharedPrefManager.getInstance((activity as HomeActivity).apContext)!!.isMeasuresArrayEmpty())
                    (activity as HomeActivity).getMeasuresFromServer()
                else
                    (activity as HomeActivity).loadMeasuresData()
            }
        }
    }*/

}
