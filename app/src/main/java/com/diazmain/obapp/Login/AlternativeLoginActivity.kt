package com.diazmain.obapp.Login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import com.diazmain.obapp.Home.HomeActivity
import com.diazmain.obapp.Login.model.Result
import com.diazmain.obapp.R
import com.diazmain.obapp.api.APIService
import com.diazmain.obapp.api.APIUrl
import com.diazmain.obapp.helper.SharedPrefManager
import kotlinx.android.synthetic.main.activity_alternative_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AlternativeLoginActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alternative_login)

        if (SharedPrefManager.getInstance(this)?.isLoggedIn()!!) {
            this.finish()
            startActivity(Intent(this, HomeActivity::class.java))
        }

        //btnSignIn.setMode(ActionProcessButton.Mode.ENDLESS)
        btnSignIn.setOnClickListener(this)
        pbSignIn.visibility = View.GONE
    }

    override fun onClick(v: View?) {
        if (v == btnSignIn) {
            userSignIn()
        }
        if (v == tbPassForgotten) {

        }
    }

    fun userSignIn() {
        pbSignIn.visibility = View.VISIBLE
        btnSignIn.isEnabled = false
        btnSignIn.alpha = 0.75f
        //btnSignIn.progress = 1
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
                pbSignIn.visibility = View.GONE
                btnSignIn.isEnabled = false
                btnSignIn.alpha = 1f
                //btnSignIn.progress = -1
                Snackbar.make(
                        activity_alternative_login,
                        getString(R.string.label_connection_failure),
                        Snackbar.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(call: Call<Result>?, response: Response<Result>?) {
                pbSignIn.visibility = View.GONE
                btnSignIn.isEnabled = false
                btnSignIn.alpha = 1f
                if (!response?.body()?.getError()!!) {
                    Log.w("Login -> UserID", response.body().getUser().getId().toString())
                    Log.w("Login -> User Name", response.body().getUser().getName())
                    //btnSignIn.progress = 100
                    SharedPrefManager.getInstance(applicationContext)?.userLogin(response.body().getUser())
                    finish()
                    startActivity(Intent(applicationContext, HomeActivity::class.java))
                } else {
                    //btnSignIn.progress = -1
                    Snackbar.make(
                            activity_alternative_login,
                            getString(R.string.label_wrong_login),
                            Snackbar.LENGTH_SHORT
                    ).show()
                }
            }

        })

    }
}
