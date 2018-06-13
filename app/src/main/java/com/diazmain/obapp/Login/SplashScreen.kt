package com.diazmain.obapp.Login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.diazmain.obapp.Home.HomeActivity
import com.diazmain.obapp.Login.helper.SharedPrefManager
import com.diazmain.obapp.R
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreen : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        if (SharedPrefManager.getInstance(this)?.isLoggedIn()!!) {
            this.finish()
            startActivity(Intent(this, HomeActivity::class.java))
        }

        buttonSignIn.setOnClickListener(this)
        buttonSignUp.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        if (v == buttonSignIn) {
            startActivity(Intent(this, SignInActivity::class.java))
        } else if (v == buttonSignUp) {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}
