package com.diazmain.obapp.Reminder.Fragments

import android.content.ContentValues
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton

import com.diazmain.obapp.R
import com.diazmain.obapp.Reminder.Pojo.Alimentos
import com.diazmain.obapp.Reminder.Pojo.CamposCheck
import com.diazmain.obapp.Reminder.ReminderActivity
import kotlinx.android.synthetic.main.fragment_desayuno.*

class DesayunoFragment : Fragment(), CompoundButton.OnCheckedChangeListener {

    companion object {
        fun newInstance() = DesayunoFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        //(activity as ReminderActivity).desayuno.clear()
        //(activity as ReminderActivity).checks.clear()

        clearChecks()

        cbd1.setOnCheckedChangeListener(this)
        cbd2.setOnCheckedChangeListener(this)
        cbd3.setOnCheckedChangeListener(this)
        cbd4.setOnCheckedChangeListener(this)


        //checkCheckeablesChecked()


        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_desayuno, container, false)
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when (buttonView) {
            cbd1 -> {
                if (cbd1.isChecked) {
                    Log.w("isChecked", cbd1.isChecked.toString())
                    tilDesPorcion1.visibility = View.VISIBLE
                    (activity as ReminderActivity).checks.add(CamposCheck(1,cbd1.text.toString(),tilDesPorcion1, tietDesPorcion1))
                    Log.wtf("checksSize", (activity as ReminderActivity).checks.size.toString())
                } else {
                    val it = (activity as ReminderActivity).checks.iterator()
                    while (it.hasNext()) {
                        val check: CamposCheck = it.next()
                        if (check.equals(cbd1))
                            it.remove()
                    }
                    tilDesPorcion1.visibility = View.GONE
                }
            }
            cbd2 -> {
                if (cbd2.isChecked) {
                    tilDesPorcion2.visibility = View.VISIBLE
                    (activity as ReminderActivity).checks.add(CamposCheck(1,cbd2.text.toString(),tilDesPorcion2, tietDesPorcion2))
                } else {
                    val it = (activity as ReminderActivity).checks.iterator()
                    while (it.hasNext()) {
                        val check: CamposCheck = it.next()
                        if (check.equals(cbd2))
                            it.remove()
                    }
                    tilDesPorcion2.visibility = View.GONE
                }
            }
            cbd3 -> {
                if (cbd3.isChecked) {
                    tilDesPorcion3.visibility = View.VISIBLE
                    (activity as ReminderActivity).checks.add(CamposCheck(1,cbd3.text.toString(),tilDesPorcion3, tietDesPorcion3))
                } else {
                    val it = (activity as ReminderActivity).checks.iterator()
                    while (it.hasNext()) {
                        val check: CamposCheck = it.next()
                        if (check.equals(cbd3))
                            it.remove()
                    }
                    tilDesPorcion3.visibility = View.GONE
                }
            }
            cbd4 -> {
                if (cbd4.isChecked) {
                    tilDesPorcion4.visibility = View.VISIBLE
                    (activity as ReminderActivity).checks.add(CamposCheck(1,cbd4.text.toString(),tilDesPorcion4, tietDesPorcion4))
                } else {
                    val it = (activity as ReminderActivity).checks.iterator()
                    while (it.hasNext()) {
                        val check: CamposCheck = it.next()
                        if (check.equals(cbd4))
                            it.remove()
                    }
                    tilDesPorcion4.visibility = View.GONE
                }
            }
        }
    }

    fun clearChecks() {
        val it = (activity as ReminderActivity).checks.iterator()
        while (it.hasNext()) {
            val check: CamposCheck = it.next()
            if (check.pagina == 1)
                it.remove()
        }

        /*for (i in (activity as ReminderActivity).checks.indices) {
            if ((activity as ReminderActivity).checks[i].pagina == 1) {
                (activity as ReminderActivity).checks[i].remove
            }
        }*/
    }

    fun checkCheckeablesChecked () {

    }

}
