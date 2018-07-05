package com.diazmain.obapp.Login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import com.diazmain.obapp.Home.HomeActivity
import com.diazmain.obapp.api.APIService
import com.diazmain.obapp.api.APIUrl
import com.diazmain.obapp.helper.SharedPrefManager
import com.diazmain.obapp.Login.model.Result
import com.diazmain.obapp.R
import kotlinx.android.synthetic.main.activity_sign_in.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignInActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        btnLogIn.setOnClickListener(this)
        pbSignIn.visibility = View.INVISIBLE
    }

    private fun userSignIn() {
        pbSignIn.visibility = View.VISIBLE

        val username: String = tietUsername.text.toString()
        val password: String = tietPass.text.toString()

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service: APIService = retrofit.create(APIService::class.java)

        val call: Call<Result> = service.userLogin(username, password)

        call.enqueue(object : Callback<Result> {
            override fun onFailure(call: Call<Result>?, t: Throwable?) {
                pbSignIn.visibility = View.INVISIBLE
                Snackbar.make(
                        activity_sign_in,
                        t.toString(),
                        Snackbar.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(call: Call<Result>?, response: Response<Result>?) {
                pbSignIn.visibility = View.INVISIBLE
                if (!response?.body()?.getError()!!) {
                    finish()
                    SharedPrefManager.getInstance(applicationContext)?.userLogin(response.body().getUser())
                    startActivity(Intent(applicationContext, HomeActivity::class.java))
                } else {
                    Snackbar.make(
                            activity_sign_in,
                            "Invalid email or password",
                            Snackbar.LENGTH_SHORT
                    ).show()
                }
            }

        })
    }

    override fun onClick(v: View?) {
        if (v == btnLogIn) {
            userSignIn()
        }
    }
}
