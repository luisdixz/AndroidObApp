package com.diazmain.obapp.Reminder.Fragments

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.CompoundButton
import android.widget.Toast

import com.diazmain.obapp.R
import com.diazmain.obapp.Reminder.Pojo.Alimentos
import com.diazmain.obapp.Reminder.Pojo.CamposCheck
import com.diazmain.obapp.Reminder.ReminderActivity
import kotlinx.android.synthetic.main.activity_reminder.*
import kotlinx.android.synthetic.main.fragment_cena.*
import kotlinx.android.synthetic.main.fragment_colacion1.*
import kotlinx.android.synthetic.main.fragment_desayuno.*
import java.text.SimpleDateFormat
import java.util.*

class Colacion1Fragment : Fragment(), CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    companion object {
        fun newInstance() = Colacion1Fragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        //(activity as ReminderActivity).colacion1.clear()
        //(activity as ReminderActivity).checks.clear()

        clearChecks()

        cbc11.setOnCheckedChangeListener(this)
        cbc12.setOnCheckedChangeListener(this)
        cbc13.setOnCheckedChangeListener(this)
        cbc14.setOnCheckedChangeListener(this)

        btnCo1Time.setOnClickListener(this)

        (activity as ReminderActivity).window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

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
                    tilCo1Porcion1.visibility = View.VISIBLE
                    (activity as ReminderActivity).checks.add(CamposCheck(2,cbc11.text.toString(), tilCo1Porcion1, tietCo1Porcion1))
                } else {
                    val it = (activity as ReminderActivity).checks.iterator()
                    while (it.hasNext()) {
                        val check: CamposCheck = it.next()
                        if (check.equals(cbc11))
                            it.remove()
                    }
                    tilCo1Porcion1.visibility = View.GONE
                }
            }
            cbc12 -> {
                if (cbc12.isChecked) {
                    tilCo1Porcion2.visibility = View.VISIBLE
                    (activity as ReminderActivity).checks.add(CamposCheck(2,cbc12.text.toString(), tilCo1Porcion2, tietCo1Porcion2))
                } else {
                    val it = (activity as ReminderActivity).checks.iterator()
                    while (it.hasNext()) {
                        val check: CamposCheck = it.next()
                        if (check.equals(cbc12))
                            it.remove()
                    }
                    tilCo1Porcion2.visibility = View.GONE
                }
            }
            cbc13 -> {
                if (cbc13.isChecked) {
                    tilCo1Porcion3.visibility = View.VISIBLE
                    (activity as ReminderActivity).checks.add(CamposCheck(2,cbc13.text.toString(), tilCo1Porcion3, tietCo1Porcion3))
                } else {
                    val it = (activity as ReminderActivity).checks.iterator()
                    while (it.hasNext()) {
                        val check: CamposCheck = it.next()
                        if (check.equals(cbc13))
                            it.remove()
                    }
                    tilCo1Porcion3.visibility = View.GONE
                }
            }
            cbc14 -> {
                if (cbc14.isChecked) {
                    tilCo1Porcion4.visibility = View.VISIBLE
                    (activity as ReminderActivity).checks.add(CamposCheck(2,cbc14.text.toString(), tilCo1Porcion4, tietCo1Porcion4))
                } else {
                    val it = (activity as ReminderActivity).checks.iterator()
                    while (it.hasNext()) {
                        val check: CamposCheck = it.next()
                        if (check.equals(cbc14))
                            it.remove()
                    }
                    tilCo1Porcion4.visibility = View.GONE
                }
            }
        }
    }

    override fun onClick(v: View?) {
        if (v == btnCo1Time) {
            pickDate()
        }
    }


    fun pickDate() {
        val cal = Calendar.getInstance()

        TimePickerDialog(
                (activity as ReminderActivity).fragment_colacion1.context,
                TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                    run {
                        cal.set(Calendar.HOUR_OF_DAY, hour)
                        cal.set(Calendar.MINUTE, minute)
                        //Log.w("Hora sin formato", cal.toString())
                        //Log.w("Hora con formato", SimpleDateFormat("HH:mm").format(cal.time))
                        val hora: String = SimpleDateFormat("HH:mm").format(cal.time)
                        tietCo1Time.setText(hora)
                        (activity as ReminderActivity).desHora = hora
                    }
                },
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                false).show()
    }

    fun clearChecks() {
        val it = (activity as ReminderActivity).checks.iterator()
        while (it.hasNext()) {
            val check: CamposCheck = it.next()
            if (check.pagina == 2)
                it.remove()
        }
    }
}
