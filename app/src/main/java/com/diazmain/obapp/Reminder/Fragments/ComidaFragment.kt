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
import com.diazmain.obapp.Reminder.Pojo.CamposCheck
import com.diazmain.obapp.Reminder.ReminderActivity
import kotlinx.android.synthetic.main.activity_reminder.*
import kotlinx.android.synthetic.main.fragment_colacion2.*
import kotlinx.android.synthetic.main.fragment_comida.*

class ComidaFragment : Fragment(), CompoundButton.OnCheckedChangeListener {

    companion object {
        fun newInstance() = ComidaFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        //(activity as ReminderActivity).comida.clear()
        //(activity as ReminderActivity).checks.clear()

        clearChecks()

        cbco1.setOnCheckedChangeListener(this)
        cbco2.setOnCheckedChangeListener(this)
        cbco3.setOnCheckedChangeListener(this)
        cbco4.setOnCheckedChangeListener(this)
        cbco5.setOnCheckedChangeListener(this)
        cbco6.setOnCheckedChangeListener(this)

        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comida, container, false)
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when (buttonView) {
            cbco1 -> {
                if (cbco1.isChecked) {
                    tilComPorcion1.visibility = View.VISIBLE
                    (activity as ReminderActivity).checks.add(CamposCheck(3,cbco1.text.toString(), tilComPorcion1, tietComPorcion1))
                } else {
                    val it = (activity as ReminderActivity).checks.iterator()
                    while (it.hasNext()) {
                        val check: CamposCheck = it.next()
                        if (check.equals(cbco1))
                            it.remove()
                    }
                    tilComPorcion1.visibility = View.GONE
                }
            }
            cbco2 -> {
                if (cbco2.isChecked) {
                    tilComPorcion2.visibility = View.VISIBLE
                    (activity as ReminderActivity).checks.add(CamposCheck(3,cbco2.text.toString(), tilComPorcion2, tietComPorcion2))
                } else {
                    val it = (activity as ReminderActivity).checks.iterator()
                    while (it.hasNext()) {
                        val check: CamposCheck = it.next()
                        if (check.equals(cbco2))
                            it.remove()
                    }
                    tilComPorcion2.visibility = View.GONE
                }
            }
            cbco3 -> {
                if (cbco3.isChecked) {
                    tilComPorcion3.visibility = View.VISIBLE
                    (activity as ReminderActivity).checks.add(CamposCheck(3,cbco3.text.toString(), tilComPorcion3, tietComPorcion3))
                } else {
                    val it = (activity as ReminderActivity).checks.iterator()
                    while (it.hasNext()) {
                        val check: CamposCheck = it.next()
                        if (check.equals(cbco3))
                            it.remove()
                    }
                    tilComPorcion3.visibility = View.GONE
                }
            }
            cbco4 -> {
                if (cbco4.isChecked) {
                    tilComPorcion4.visibility = View.VISIBLE
                    (activity as ReminderActivity).checks.add(CamposCheck(3,cbco4.text.toString(), tilComPorcion4, tietComPorcion4))
                } else {
                    val it = (activity as ReminderActivity).checks.iterator()
                    while (it.hasNext()) {
                        val check: CamposCheck = it.next()
                        if (check.equals(cbco4))
                            it.remove()
                    }
                    tilComPorcion4.visibility = View.GONE
                }
            }
            cbco5 -> {
                if (cbco5.isChecked) {
                    tilComPorcion5.visibility = View.VISIBLE
                    (activity as ReminderActivity).checks.add(CamposCheck(3,cbco5.text.toString(), tilComPorcion5, tietComPorcion5))
                } else {
                    val it = (activity as ReminderActivity).checks.iterator()
                    while (it.hasNext()) {
                        val check: CamposCheck = it.next()
                        if (check.equals(cbco5))
                            it.remove()
                    }
                    tilComPorcion5.visibility = View.GONE
                }
            }
            cbco6 -> {
                if (cbco6.isChecked) {
                    tilComPorcion6.visibility = View.VISIBLE
                    (activity as ReminderActivity).checks.add(CamposCheck(3,cbco6.text.toString(), tilComPorcion6, tietComPorcion6))
                } else {
                    val it = (activity as ReminderActivity).checks.iterator()
                    while (it.hasNext()) {
                        val check: CamposCheck = it.next()
                        if (check.equals(cbco6))
                            it.remove()
                    }
                    tilComPorcion6.visibility = View.GONE
                }
            }
        }
    }

    fun clearChecks() {
        val it = (activity as ReminderActivity).checks.iterator()
        while (it.hasNext()) {
            val check: CamposCheck = it.next()
            if (check.pagina == 3)
                it.remove()
        }
    }

}
