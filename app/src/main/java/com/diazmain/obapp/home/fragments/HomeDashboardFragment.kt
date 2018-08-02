package com.diazmain.obapp.home.fragments

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.diazmain.obapp.home.HomeActivity
import com.diazmain.obapp.home.model.GenericResult
import com.diazmain.obapp.helper.SharedPrefManager
import com.diazmain.obapp.R
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
        btnCancelAppo.setOnClickListener(this)
        btnConfirmAppo.setOnClickListener(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home_dashboard, container, false)
    }

    override fun onClick(v: View?) {
        if (v == btnCancelAppo) {
            //Enviar request para cancelar cita
            cancelAppoint()
        } else if (v == btnConfirmAppo) {
            //Enviar request para confirmar cita
            confirmAppoint()
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
        val call: Call<GenericResult> = service.setAppointStatus(
                (activity as HomeActivity).incomingAppoint.idCita,
                2
        )

        call.enqueue(object : Callback<GenericResult> {
            override fun onFailure(call: Call<GenericResult>?, t: Throwable?) {
                Snackbar.make(
                        activity_home,
                        getString(R.string.notification_appoint_confirm_error),
                        Snackbar.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<GenericResult>?, response: Response<GenericResult>?) {
                SharedPrefManager.getInstance((activity as HomeActivity).apContext)!!.setAppointStatus(2)
                llNextAppo.visibility = View.GONE
            }
        })
    }

}
