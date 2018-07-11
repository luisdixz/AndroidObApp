package com.diazmain.obapp.Reminder.Fragments

import android.app.TimePickerDialog
import android.content.ContentValues
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

import com.diazmain.obapp.R
import com.diazmain.obapp.Reminder.Pojo.Alimentos
import com.diazmain.obapp.Reminder.Pojo.CamposCheck
import com.diazmain.obapp.Reminder.ReminderActivity
import kotlinx.android.synthetic.main.activity_reminder.*
import kotlinx.android.synthetic.main.fragment_colacion1.*
import kotlinx.android.synthetic.main.fragment_colacion2.*
import kotlinx.android.synthetic.main.fragment_comida.*
import kotlinx.android.synthetic.main.fragment_desayuno.*
import java.text.SimpleDateFormat
import java.util.*

class Colacion2Fragment : Fragment(), CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    companion object {
        fun newInstance() = Colacion2Fragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        //(activity as ReminderActivity).colacion2.clear()
        //(activity as ReminderActivity).checks.clear()

        clearChecks()

        cbc21.setOnCheckedChangeListener(this)
        cbc22.setOnCheckedChangeListener(this)
        cbc23.setOnCheckedChangeListener(this)

        btnCo2Time.setOnClickListener(this)

        (activity as ReminderActivity).window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

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
                    tilCo2Porcion1.visibility = View.VISIBLE
                    (activity as ReminderActivity).checks.add(CamposCheck(4,cbc21.text.toString(), tilCo2Porcion1, tietCo2Porcion1))
                } else {
                    val it = (activity as ReminderActivity).checks.iterator()
                    while (it.hasNext()) {
                        val check: CamposCheck = it.next()
                        if (check.equals(cbc21))
                            it.remove()
                    }
                    tilCo2Porcion1.visibility = View.GONE
                }
            }
            cbc22 -> {
                if (cbc22.isChecked) {
                    tilCo2Porcion2.visibility = View.VISIBLE
                    (activity as ReminderActivity).checks.add(CamposCheck(4,cbc22.text.toString(), tilCo2Porcion2, tietCo2Porcion2))
                } else {
                    val it = (activity as ReminderActivity).checks.iterator()
                    while (it.hasNext()) {
                        val check: CamposCheck = it.next()
                        if (check.equals(cbc22))
                            it.remove()
                    }
                    tilCo2Porcion2.visibility = View.GONE
                }
            }
            cbc23 -> {
                if (cbc23.isChecked) {
                    tilCo2Porcion3.visibility = View.VISIBLE
                    (activity as ReminderActivity).checks.add(CamposCheck(4,cbc23.text.toString(), tilCo2Porcion3, tietCo2Porcion3))
                } else {
                    val it = (activity as ReminderActivity).checks.iterator()
                    while (it.hasNext()) {
                        val check: CamposCheck = it.next()
                        if (check.equals(cbc23))
                            it.remove()
                    }
                    tilCo2Porcion3.visibility = View.GONE
                }
            }
        }
    }

    override fun onClick(v: View?) {
        if (v == btnCo2Time) {
            pickDate()
        }
    }


    fun pickDate() {
        val cal = Calendar.getInstance()

        TimePickerDialog(
                (activity as ReminderActivity).fragment_colacion2.context,
                TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                    run {
                        cal.set(Calendar.HOUR_OF_DAY, hour)
                        cal.set(Calendar.MINUTE, minute)
                        //Log.w("Hora sin formato", cal.toString())
                        //Log.w("Hora con formato", SimpleDateFormat("HH:mm").format(cal.time))
                        val hora: String = SimpleDateFormat("HH:mm").format(cal.time)
                        tietCo2Time.setText(hora)
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
            if (check.pagina == 4)
                it.remove()
        }
    }
}
