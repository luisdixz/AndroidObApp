package com.diazmain.obapp.Reminder.Fragments

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CompoundButton
import android.widget.Toast
import com.diazmain.obapp.Login.api.APIService
import com.diazmain.obapp.Login.api.APIUrl
import com.diazmain.obapp.Login.model.Result
import com.diazmain.obapp.R

import com.diazmain.obapp.R.layout.fragment_cena
import com.diazmain.obapp.Reminder.Pojo.Alimentos
import com.diazmain.obapp.Reminder.ReminderActivity
import kotlinx.android.synthetic.main.activity_reminder.*
import kotlinx.android.synthetic.main.fragment_cena.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CenaFragment : Fragment(), View.OnClickListener, CompoundButton.OnCheckedChangeListener, FragmentLifecycle {

    companion object {
        fun newInstance() = CenaFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        (activity as ReminderActivity).cena.clear()

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
                    (activity as ReminderActivity).cena.add(Alimentos(cbce1.text.toString(), tietCenPorcion1.text.toString()))
                } else {
                    val it = (activity as ReminderActivity).cena.iterator()
                    while (it.hasNext()) {
                        val alimentos: Alimentos = it.next()
                        if (alimentos.alimento.equals(cbce1.text.toString()))
                            it.remove()
                    }
                }
            }
            cbce2 -> {
                if (cbce1.isChecked) {
                    (activity as ReminderActivity).cena.add(Alimentos(cbce2.text.toString(), tietCenPorcion2.text.toString()))
                } else {
                    val it = (activity as ReminderActivity).cena.iterator()
                    while (it.hasNext()) {
                        val alimentos: Alimentos = it.next()
                        if (alimentos.alimento.equals(cbce2.text.toString()))
                            it.remove()
                    }
                }
            }
            cbce3 -> {
                if (cbce3.isChecked) {
                    (activity as ReminderActivity).cena.add(Alimentos(cbce3.text.toString(), tietCenPorcion3.text.toString()))
                } else {
                    val it = (activity as ReminderActivity).cena.iterator()
                    while (it.hasNext()) {
                        val alimentos: Alimentos = it.next()
                        if (alimentos.alimento.equals(cbce3.text.toString()))
                            it.remove()
                    }
                }
            }
            cbce4 -> {
                if (cbce4.isChecked) {
                    (activity as ReminderActivity).cena.add(Alimentos(cbce4.text.toString(), tietCenPorcion4.text.toString()))
                } else {
                    val it = (activity as ReminderActivity).cena.iterator()
                    while (it.hasNext()) {
                        val alimentos: Alimentos = it.next()
                        if (alimentos.alimento.equals(cbce4.text.toString()))
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

