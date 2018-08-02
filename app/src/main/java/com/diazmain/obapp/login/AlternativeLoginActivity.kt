package com.diazmain.obapp.login

import android.arch.persistence.room.Room
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import com.diazmain.obapp.home.HomeActivity
import com.diazmain.obapp.login.model.Result
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
import com.diazmain.obapp.room.*
import com.diazmain.obapp.threads.local_queries.InsertNewUser


class AlternativeLoginActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alternative_login)

        initRoomDatabase()

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
        /*if (v == tbPassForgotten) {

        }*/
    }

    fun initRoomDatabase() {

        Room.databaseBuilder(applicationContext, AppRoomDatabase::class.java, "database")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()



        //Quitar después...
        /*if (!SharedPrefManager.getInstance(applicationContext)!!.getLastCompleteReminder().equals(null)) {

            val rec: Recordatorio = SharedPrefManager.getInstance(applicationContext)!!.getLastCompleteReminder()
            val remDao: ReminderDAO? = null
            var recFinal: ReminderEntity = ReminderEntity("30/07/2018",1, rec)
            remDao?.insert(recFinal)
            recFinal = remDao?.findLast()!!
        }*/
    }

    fun userSignIn() {
        pbSignIn.visibility = View.VISIBLE
        btnSignIn.isEnabled = false
        btnSignIn.alpha = 0.75f
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
                btnSignIn.isEnabled = true
                btnSignIn.alpha = 1f
                Snackbar.make(
                        activity_alternative_login,
                        getString(R.string.label_connection_failure),
                        Snackbar.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(call: Call<Result>?, response: Response<Result>?) {
                pbSignIn.visibility = View.GONE
                btnSignIn.isEnabled = true
                btnSignIn.alpha = 1f
                if (!response?.body()?.getError()!!) {

                    /*val db = DatabaseModule().provideDatabase(applicationContext)
                    val usDAO: UserDAO = DatabaseModule().provideUserDao(db)*/
                    val user: UserEntity = UserEntity(
                            response.body().getUser().getId(),
                            response.body().getUser().getName(),
                            response.body().getUser().getLastname(),
                            response.body().getUser().getUsername(),
                            response.body().getUser().getBirth()
                    )

                    InsertNewUser(applicationContext, user).execute()

                    /*Log.w("userID", user.id.toString())
                    usDAO.insert(user)*/

                    SharedPrefManager.getInstance(applicationContext)?.userLogin(response.body().getUser())
                    finish()
                    startActivity(Intent(applicationContext, HomeActivity::class.java))
                } else {
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
