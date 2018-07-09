package com.diazmain.obapp.Threads

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.diazmain.obapp.Home.model.Citas
import com.diazmain.obapp.Home.model.CitasValue
import com.diazmain.obapp.api.APIService
import com.diazmain.obapp.api.APIUrl
import com.diazmain.obapp.helper.SharedPrefManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SaveIncomingAppoint (_id: Int, _context: Context, _delegate: AsyncResponse) : AsyncTask<Void, Void, Boolean>() {

    // Hacer request
    // guardar lista con sharedpreferences

    public interface AsyncResponse {
        fun onProcessFinish(output: Boolean)
    }

    private val id: Int = _id
    private val context: Context = _context

    private lateinit var cita: CitasValue

    override fun doInBackground(vararg params: Void?): Boolean {

        cita = CitasValue(1,"000000", "000000", "000000", 1)

        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

        val service: APIService = retrofit.create(APIService::class.java)

        val call: Call<Citas> = service.getAllAppoint(id)

        call.enqueue(object : Callback<Citas> {
            override fun onFailure(call: Call<Citas>?, t: Throwable?) {
                Log.wtf("onFailure -> SaveIncomingAppoint", t.toString())
                cancel(true)
            }

            override fun onResponse(call: Call<Citas>?, response: Response<Citas>?) {
                //SharedPrefManager.getInstance(context)?.storeAppoint(response?.body().)
            }
        })

        //return resultCita
        return true
    }

    override fun onCancelled() {
        Log.w("onCancelled", "SaveIncomingAppoint")
    }

    @SuppressLint("LongLogTag")
    override fun onPostExecute(result: Boolean) {
        Log.w("SaveIncomingAppoint -> callback", result.toString())
    }

}