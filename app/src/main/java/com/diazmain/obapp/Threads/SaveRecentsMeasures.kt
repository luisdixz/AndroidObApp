package com.diazmain.obapp.Threads

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.diazmain.obapp.Home.model.MeasuresResult
import com.diazmain.obapp.Home.model.MeasuresValue
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
import java.net.SocketTimeoutException

class SaveRecentsMeasures (_id: Int, _context: Context, _delegate: AsyncResponse) : AsyncTask<Void, Void, Boolean>() {

    public interface AsyncResponse {
        fun onProcessFinish(output: Boolean)
    }

    val id: Int = _id
    @SuppressLint("StaticFieldLeak")
    val context: Context = _context
    //private val delegate: AsyncResponse = _delegate

    private lateinit var measuresResult: List<MeasuresValue>

    override fun doInBackground(vararg params: Void?): Boolean? {
        //Puede ser opcional
        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        // Esto no jajaj
        measuresResult = ArrayList()

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

        val service: APIService = retrofit.create(APIService::class.java)

        val call: Call<MeasuresResult> = service.getAllMeasures(id)


        //call.execute()
        call.enqueue(object : Callback<MeasuresResult> {
            override fun onFailure(call: Call<MeasuresResult>?, t: Throwable?) {
                Log.wtf("onFailure -> SaveRecentsMeasures", t.toString())
                /*if (t is SocketTimeoutException) {

                } else {

                }*/
            }

            override fun onResponse(call: Call<MeasuresResult>?, response: Response<MeasuresResult>?) {
                SharedPrefManager.getInstance(context)?.storeAllMeasures(response?.body()?.measures?.toList()!!)
            }

        })

        /*Log.w("Measures -> cintura", measuresResult[0].cintura.toString())
        return measuresResult*/
        return SharedPrefManager.getInstance(context)?.isMeasuresArrayEmpty()
    }

    @SuppressLint("LongLogTag")
    override fun onPostExecute(result: Boolean) {
        //delegate.onProcessFinish(result)
        Log.w("SaveRecentsMeasures -> callback", result.toString())
    }

}