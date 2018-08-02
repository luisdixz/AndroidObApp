package com.diazmain.obapp.home.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.diazmain.obapp.home.HomeActivity
import com.diazmain.obapp.helper.SharedPrefManager
import com.diazmain.obapp.login.model.User
import com.diazmain.obapp.R
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment(), View.OnClickListener {
    companion object {
        fun newInstance() = ProfileFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val user: User = SharedPrefManager.getInstance((activity as HomeActivity).applicationContext)?.getUser()!!

        if (SharedPrefManager.getInstance((activity as HomeActivity).apContext)!!.isMeasuresArrayEmpty())
            (activity as HomeActivity).getAllFromServer()
        else
            (activity as HomeActivity).loadAllFromStorage()

        val nombre: String = user.getName()
        val ape: String = user.getLastname()

        tvProfileName.setText(nombre)
        tvProfileLastname.setText(ape)
        tvProfileUsername.setText(user.getUsername())
        tvProfileBirth.setText(user.getBirth())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onClick(v: View?) {
        /*if (v == tbProfileEdit) {
            showEditDialog()
        }*/
        /*if (v == btnPerfilLogout) {
            activity?.finish()
            startActivity(Intent(activity, SplashScreen::class.java))
            SharedPrefManager.getInstance(activity?.applicationContext!!)?.logout()
        }*/
    }

    private fun showEditDialog() {
        val lay: LayoutInflater = LayoutInflater.from((activity as HomeActivity).apContext)
        val promptView: View = lay.inflate(R.layout.dialog_edit_profile, null)

        val alertDialog: AlertDialog.Builder = AlertDialog.Builder((activity as HomeActivity).apContext)
        alertDialog.setView(promptView)
        alertDialog.setTitle(getString(R.string.title_edit_profile))

        alertDialog
                .setCancelable(false)
                .setPositiveButton(getString(R.string.btn_accept), DialogInterface.OnClickListener() {
                    dialogInterface: DialogInterface, i: Int ->
                    run {

                        //val user: User =
                    }
                })
    }

}
