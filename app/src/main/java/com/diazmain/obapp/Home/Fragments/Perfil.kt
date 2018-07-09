package com.diazmain.obapp.Home.Fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.diazmain.obapp.Home.HomeActivity
import com.diazmain.obapp.Login.SplashScreen
import com.diazmain.obapp.helper.SharedPrefManager
import com.diazmain.obapp.Login.model.User
import com.diazmain.obapp.R
import kotlinx.android.synthetic.main.fragment_perfil.*

class Perfil : Fragment(), View.OnClickListener {
    companion object {
        fun newInstance() = Perfil()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        //btnPerfilLogout.setOnClickListener(this)

        val user: User = SharedPrefManager.getInstance((activity as HomeActivity).applicationContext)?.getUser()!!

        val nombre: String = user.getName()+" "+user.getLastname()

        tvProfileName.setText(nombre)
        tvProfileUsername.setText(user.getUsername())

        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_perfil, container, false)
    }

    override fun onClick(v: View?) {
        /*if (v == btnPerfilLogout) {
            activity?.finish()
            startActivity(Intent(activity, SplashScreen::class.java))
            SharedPrefManager.getInstance(activity?.applicationContext!!)?.logout()
        }*/
    }

}
