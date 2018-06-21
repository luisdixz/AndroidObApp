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
import kotlinx.android.synthetic.main.fragment_comida.*

class ComidaFragment : Fragment(), FragmentLifecycle, CompoundButton.OnCheckedChangeListener {

    companion object {
        fun newInstance() = ComidaFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        (activity as ReminderActivity).comida.clear()

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
                    (activity as ReminderActivity).comida.add(Alimentos(cbco1.text.toString(), tietComPorcion1.text.toString()))
                } else {
                    val it = (activity as ReminderActivity).comida.iterator()
                    while (it.hasNext()) {
                        val alimentos: Alimentos = it.next()
                        if (alimentos.alimento.equals(cbco1.text.toString()))
                            it.remove()
                    }
                }
            }
            cbco2 -> {
                if (cbco2.isChecked) {
                    (activity as ReminderActivity).comida.add(Alimentos(cbco2.text.toString(), tietComPorcion2.text.toString()))
                } else {
                    val it = (activity as ReminderActivity).comida.iterator()
                    while (it.hasNext()) {
                        val alimentos: Alimentos = it.next()
                        if (alimentos.alimento.equals(cbco2.text.toString()))
                            it.remove()
                    }
                }
            }
            cbco3 -> {
                if (cbco3.isChecked) {
                    (activity as ReminderActivity).comida.add(Alimentos(cbco3.text.toString(), tietComPorcion3.text.toString()))
                } else {
                    val it = (activity as ReminderActivity).comida.iterator()
                    while (it.hasNext()) {
                        val alimentos: Alimentos = it.next()
                        if (alimentos.alimento.equals(cbco3.text.toString()))
                            it.remove()
                    }
                }
            }
            cbco4 -> {
                if (cbco4.isChecked) {
                    (activity as ReminderActivity).comida.add(Alimentos(cbco4.text.toString(), tietComPorcion4.text.toString()))
                } else {
                    val it = (activity as ReminderActivity).comida.iterator()
                    while (it.hasNext()) {
                        val alimentos: Alimentos = it.next()
                        if (alimentos.alimento.equals(cbco4.text.toString()))
                            it.remove()
                    }
                }
            }
            cbco5 -> {
                if (cbco5.isChecked) {
                    (activity as ReminderActivity).comida.add(Alimentos(cbco5.text.toString(), tietComPorcion5.text.toString()))
                } else {
                    val it = (activity as ReminderActivity).comida.iterator()
                    while (it.hasNext()) {
                        val alimentos: Alimentos = it.next()
                        if (alimentos.alimento.equals(cbco5.text.toString()))
                            it.remove()
                    }
                }
            }
            cbco6 -> {
                if (cbco6.isChecked) {
                    (activity as ReminderActivity).comida.add(Alimentos(cbco6.text.toString(), tietComPorcion6.text.toString()))
                } else {
                    val it = (activity as ReminderActivity).comida.iterator()
                    while (it.hasNext()) {
                        val alimentos: Alimentos = it.next()
                        if (alimentos.alimento.equals(cbco6.text.toString()))
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
