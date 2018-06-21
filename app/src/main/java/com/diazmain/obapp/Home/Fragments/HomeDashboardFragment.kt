package com.diazmain.obapp.Home.Fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home_dashboard, container, false)
    }

    override fun onClick(v: View?) {
        if (v == cvNextAppo) {
            startActivity(Intent(activity, ReminderActivity::class.java))
        }
    }


}