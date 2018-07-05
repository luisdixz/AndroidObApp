package com.diazmain.obapp.Reminder.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.diazmain.obapp.R

import com.diazmain.obapp.Reminder.Pojo.CamposCheck
import com.diazmain.obapp.Reminder.ReminderActivity
import kotlinx.android.synthetic.main.fragment_cena.*

class CenaFragment : Fragment(), View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    companion object {
        fun newInstance() = CenaFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        //(activity as ReminderActivity).cena.clear()
        //Log.w("checkSizeCena", (activity as ReminderActivity).checks.size.toString())
        //(activity as ReminderActivity).checks.clear()

        clearChecks()

        cbce1.setOnCheckedChangeListener(this)
        cbce2.setOnCheckedChangeListener(this)
        cbce3.setOnCheckedChangeListener(this)
        cbce4.setOnCheckedChangeListener(this)

        btnFin.setOnClickListener(this)
        btnFin.isEnabled = true
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cena, container, false)
    }

    override fun onClick(v: View?) {
        if (v == btnFin) {
            (activity as ReminderActivity).sendData()
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        var activar: Boolean = false
        when (buttonView) {
            cbce1 -> {
                if (cbce1.isChecked) {
                    tilCenPorcion1.visibility = View.VISIBLE
                    (activity as ReminderActivity).checks.add(CamposCheck(5,cbce1.text.toString(), tilCenPorcion1, tietCenPorcion1))
                } else {
                    val it = (activity as ReminderActivity).checks.iterator()
                    while (it.hasNext()) {
                        val check: CamposCheck = it.next()
                        if (check.equals(cbce1))
                            it.remove()
                    }
                    tilCenPorcion1.visibility = View.GONE
                }
            }
            cbce2 -> {
                if (cbce2.isChecked) {
                    tilCenPorcion2.visibility = View.VISIBLE
                    (activity as ReminderActivity).checks.add(CamposCheck(5,cbce2.text.toString(), tilCenPorcion2, tietCenPorcion2))
                } else {
                    val it = (activity as ReminderActivity).checks.iterator()
                    while (it.hasNext()) {
                        val check: CamposCheck = it.next()
                        if (check.equals(cbce2))
                            it.remove()
                    }
                    tilCenPorcion2.visibility = View.GONE
                }
            }
            cbce3 -> {
                if (cbce3.isChecked) {
                    tilCenPorcion3.visibility = View.VISIBLE
                    (activity as ReminderActivity).checks.add(CamposCheck(5,cbce3.text.toString(), tilCenPorcion3, tietCenPorcion3))
                } else {
                    val it = (activity as ReminderActivity).checks.iterator()
                    while (it.hasNext()) {
                        val check: CamposCheck = it.next()
                        if (check.equals(cbce3))
                            it.remove()
                    }
                    tilCenPorcion3.visibility = View.GONE
                }
            }
            cbce4 -> {
                if (cbce4.isChecked) {
                    tilCenPorcion4.visibility = View.VISIBLE
                    (activity as ReminderActivity).checks.add(CamposCheck(5,cbce4.text.toString(), tilCenPorcion4, tietCenPorcion4))
                } else {
                    val it = (activity as ReminderActivity).checks.iterator()
                    while (it.hasNext()) {
                        val check: CamposCheck = it.next()
                        if (check.equals(cbce4))
                            it.remove()
                    }
                    tilCenPorcion4.visibility = View.GONE
                }
            }
        }
    }

    fun clearChecks() {
        val it = (activity as ReminderActivity).checks.iterator()
        while (it.hasNext()) {
            val check: CamposCheck = it.next()
            if (check.pagina == 5)
                it.remove()
        }
    }
}
