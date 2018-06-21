package com.diazmain.obapp.Reminder.Fragments

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CompoundButton

import com.diazmain.obapp.R
import com.diazmain.obapp.Reminder.Pojo.Alimentos
import com.diazmain.obapp.Reminder.ReminderActivity
import kotlinx.android.synthetic.main.activity_reminder.*
import kotlinx.android.synthetic.main.fragment_colacion2.*

class Colacion2Fragment : Fragment(), FragmentLifecycle, CompoundButton.OnCheckedChangeListener {

    companion object {
        fun newInstance() = Colacion2Fragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        (activity as ReminderActivity).colacion2.clear()

        cbc21.setOnCheckedChangeListener(this)
        cbc22.setOnCheckedChangeListener(this)
        cbc23.setOnCheckedChangeListener(this)

        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_colacion2, container, false)
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when (buttonView) {
            cbc21 -> {
                if (cbc21.isChecked) {
                    (activity as ReminderActivity).colacion2.add(Alimentos(cbc21.text.toString(), tietCo2Porcion1.text.toString()))
                } else {
                    val it = (activity as ReminderActivity).colacion2.iterator()
                    while (it.hasNext()) {
                        val alimentos: Alimentos = it.next()
                        if (alimentos.alimento.equals(cbc21.text.toString()))
                            it.remove()
                    }
                }
            }
            cbc22 -> {
                if (cbc22.isChecked) {
                    (activity as ReminderActivity).colacion2.add(Alimentos(cbc22.text.toString(), tietCo2Porcion2.text.toString()))
                } else {
                    val it = (activity as ReminderActivity).colacion2.iterator()
                    while (it.hasNext()) {
                        val alimentos: Alimentos = it.next()
                        if (alimentos.alimento.equals(cbc22.text.toString()))
                            it.remove()
                    }
                }
            }
            cbc23 -> {
                if (cbc23.isChecked) {
                    (activity as ReminderActivity).colacion2.add(Alimentos(cbc23.text.toString(), tietCo2Porcion3.text.toString()))
                } else {
                    val it = (activity as ReminderActivity).colacion2.iterator()
                    while (it.hasNext()) {
                        val alimentos: Alimentos = it.next()
                        if (alimentos.alimento.equals(cbc23.text.toString()))
                            it.remove()
                    }
                }
            }
        }
    }

    override fun onPauseFragment() {
        Log.i(ContentValues.TAG, "onPauseFragment()");
    }

    override fun onResumeFragment() {
        Log.i(ContentValues.TAG, "onResumeFragment()");
    }

}
