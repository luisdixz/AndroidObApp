package com.diazmain.obapp.Reminder.Fragments

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
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
import android.widget.Toast

import com.diazmain.obapp.R
import com.diazmain.obapp.Reminder.Pojo.Alimentos
import com.diazmain.obapp.Reminder.ReminderActivity
import kotlinx.android.synthetic.main.activity_reminder.*
import kotlinx.android.synthetic.main.fragment_colacion1.*

class Colacion1Fragment : Fragment(), FragmentLifecycle, CompoundButton.OnCheckedChangeListener {

    companion object {
        fun newInstance() = Colacion1Fragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        (activity as ReminderActivity).colacion1.clear()

        cbc11.setOnCheckedChangeListener(this)
        cbc12.setOnCheckedChangeListener(this)
        cbc13.setOnCheckedChangeListener(this)
        cbc14.setOnCheckedChangeListener(this)

        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_colacion1, container, false)
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when (buttonView) {
            cbc11 -> {
                if (cbc11.isChecked) {
                    (activity as ReminderActivity).colacion1.add(Alimentos(cbc11.text.toString(), tietCo1Porcion1.text.toString()))
                } else {
                    val it = (activity as ReminderActivity).colacion1.iterator()
                    while (it.hasNext()){
                        val alimentos: Alimentos = it.next()
                        if (alimentos.alimento.equals(cbc11.text.toString()))
                            it.remove()
                    }
                }
            }
            cbc12 -> {
                if (cbc12.isChecked) {
                    (activity as ReminderActivity).colacion1.add(Alimentos(cbc12.text.toString(), tietCo1Porcion2.text.toString()))
                } else {
                    val it = (activity as ReminderActivity).colacion1.iterator()
                    while (it.hasNext()){
                        val alimentos: Alimentos = it.next()
                        if (alimentos.alimento.equals(cbc12.text.toString()))
                            it.remove()
                    }
                }
            }
            cbc13 -> {
                if (cbc13.isChecked) {
                    (activity as ReminderActivity).colacion1.add(Alimentos(cbc13.text.toString(), tietCo1Porcion3.text.toString()))
                } else {
                    val it = (activity as ReminderActivity).colacion1.iterator()
                    while (it.hasNext()){
                        val alimentos: Alimentos = it.next()
                        if (alimentos.alimento.equals(cbc13.text.toString()))
                            it.remove()
                    }
                }
            }
            cbc14 -> {
                if (cbc14.isChecked) {
                    (activity as ReminderActivity).colacion1.add(Alimentos(cbc14.text.toString(), tietCo1Porcion4.text.toString()))
                } else {
                    val it = (activity as ReminderActivity).colacion1.iterator()
                    while (it.hasNext()){
                        val alimentos: Alimentos = it.next()
                        if (alimentos.alimento.equals(cbc14.text.toString()))
                            it.remove()
                    }
                }
            }
        }
    }

    override fun onPauseFragment() {
        Log.i(TAG, "onPauseFragment()");
    }

    override fun onResumeFragment() {
        Log.i(TAG, "onResumeFragment()");
    }

}
