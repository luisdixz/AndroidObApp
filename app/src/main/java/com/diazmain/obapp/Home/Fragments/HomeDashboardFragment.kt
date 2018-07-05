package com.diazmain.obapp.Home.Fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.diazmain.obapp.Home.HomeActivity
import com.diazmain.obapp.Login.SplashScreen
import com.diazmain.obapp.helper.SharedPrefManager
import com.diazmain.obapp.R
import com.diazmain.obapp.Reminder.ReminderActivity
import kotlinx.android.synthetic.main.fragment_home_dashboard.*

class HomeDashboardFragment: Fragment(), View.OnClickListener {
    companion object {
        fun newInstance() = HomeDashboardFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        cvNextAppo.setOnClickListener(this)

        (activity as HomeActivity).isReady = true
        //Log.w("isMeasuresListEmpty", SharedPrefManager.getInstance((activity as HomeActivity).apContext)!!.isMeasuresArrayEmpty().toString())

        if (SharedPrefManager.getInstance((activity as HomeActivity).apContext)!!.isMeasuresArrayEmpty())
            (activity as HomeActivity).getMeasuresFromServer()
        else
            (activity as HomeActivity).loadMeasuresData()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home_dashboard, container, false)
    }

    override fun onClick(v: View?) {
        if (v == cvNextAppo) {
            startActivity(Intent(activity, ReminderActivity::class.java))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> {
                (activity as HomeActivity).finish()
                startActivity(Intent((activity as HomeActivity).apContext, SplashScreen::class.java))
                return SharedPrefManager.getInstance((activity as HomeActivity).apContext)?.logout()!!
            }
            R.id.action_refresh -> {
                try {
                    Toast.makeText((activity as HomeActivity).apContext, "Estoy en HomeDashboardFragment", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Log.wtf("action_refresh -> Exception", e.localizedMessage)
                }
                return true
            }
            else -> { return super.onOptionsItemSelected(item) }

        }
    }

    /*override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        //Log.w("isVisibleToUser -> HomeDashboardFragment", isVisibleToUser.toString())
        if ((activity as HomeActivity).isReady) {
            if (isVisibleToUser) {
                if (SharedPrefManager.getInstance((activity as HomeActivity).apContext)!!.isMeasuresArrayEmpty())
                    (activity as HomeActivity).getMeasuresFromServer()
                else
                    (activity as HomeActivity).loadMeasuresData()
            }
        }
    }*/

}
