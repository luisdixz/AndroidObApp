package com.diazmain.obapp.Login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import com.diazmain.obapp.Home.HomeActivity
import com.diazmain.obapp.Login.api.APIService
import com.diazmain.obapp.Login.api.APIUrl
import com.diazmain.obapp.Login.helper.SharedPrefManager
import com.diazmain.obapp.Login.model.Result
import com.diazmain.obapp.Login.model.User
import com.diazmain.obapp.R
import kotlinx.android.synthetic.main.activity_sign_up.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignUpActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        buttonSignUp.setOnClickListener(this)
        pbSignUp.visibility = View.INVISIBLE
    }

    private fun userSignUp() {
        pbSignUp.visibility = View.VISIBLE

        val name: String = tietName.text.toString()
        val lastname: String = tietLastName.text.toString()
        val username: String = tietUsername.text.toString()
        val password: String = tietPass.text.toString()

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service: APIService = retrofit.create(APIService::class.java)

        val user: User = User(name,lastname, username, password)

        val call: Call<Result> = service.createUser(
                user.getName(),
                user.getLastname(),
                user.getUsername(),
                user.getPassword()
        )

        call.enqueue(object : Callback<Result> {
            override fun onFailure(call: Call<Result>?, t: Throwable?) {
                pbSignUp.visibility = View.INVISIBLE
                Snackbar.make(
                        activity_sign_up,
                        t.toString(),
                        Snackbar.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(call: Call<Result>?, response: Response<Result>?) {
                pbSignUp.visibility = View.INVISIBLE
                Snackbar.make(
                        activity_sign_up,
                        response?.body()?.getMessage().toString(),
                        Snackbar.LENGTH_SHORT
                ).show()

                if (!response?.body()?.getError()!!) {
                    finish()
                    SharedPrefManager.getInstance(applicationContext)?.userLogin(response.body().getUser())
                    startActivity(Intent(applicationContext, HomeActivity::class.java))
                }
            }

        })
    }

    override fun onClick(v: View?) {
        if (v == buttonSignUp) {
            userSignUp()
        }
    }

}
