package com.diazmain.obapp.Reminder.Fragments

import android.content.ContentValues
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton

import com.diazmain.obapp.R
import com.diazmain.obapp.Reminder.Pojo.Alimentos
import com.diazmain.obapp.Reminder.ReminderActivity
import kotlinx.android.synthetic.main.fragment_desayuno.*

class DesayunoFragment : Fragment(), CompoundButton.OnCheckedChangeListener, FragmentLifecycle {

    companion object {
        fun newInstance() = DesayunoFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        (activity as ReminderActivity).desayuno.clear()

        cbd1.setOnCheckedChangeListener(this)
        cbd2.setOnCheckedChangeListener(this)
        cbd3.setOnCheckedChangeListener(this)
        cbd4.setOnCheckedChangeListener(this)

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
                if (cbd1.isChecked){
                    (activity as ReminderActivity).desayuno.add(Alimentos(cbd1.text.toString(), tietDesPorcion1.text.toString()))
                    Log.i("Acción", "Después de agregar al array 1")
                }
                else{
                    val it = (activity as ReminderActivity).desayuno.iterator()
                    while (it.hasNext()) {
                        val alimentos: Alimentos = it.next()
                        if (alimentos.alimento.equals(cbd1.text.toString()))
                            it.remove()
                    }
                }
            }
            cbd2 -> {
                if (cbd2.isChecked){
                    (activity as ReminderActivity).desayuno.add(Alimentos(cbd2.text.toString(), tietDesPorcion2.text.toString()))
                    Log.i("Acción", "Después de agregar al array 2")
                }
                else{
                    val it = (activity as ReminderActivity).desayuno.iterator()
                    while (it.hasNext()) {
                        val alimentos: Alimentos = it.next()
                        if (alimentos.alimento.equals(cbd2.text.toString()))
                            it.remove()
                    }
                }
            }
            cbd3 -> {
                Log.i("Acción", "Se activó el evento del checkbox 3")
                if (cbd3.isChecked){
                    (activity as ReminderActivity).desayuno.add(Alimentos(cbd3.text.toString(), tietDesPorcion3.text.toString()))
                    Log.i("Acción", "Después de agregar al array 3")
                }
                else{
                    val it = (activity as ReminderActivity).desayuno.iterator()
                    while (it.hasNext()) {
                        val alimentos: Alimentos = it.next()
                        if (alimentos.alimento.equals(cbd3.text.toString()))
                            it.remove()
                    }
                }
            }
            cbd4 -> {
                Log.i("Acción", "Se activó el evento del checkbox 4")
                if (cbd4.isChecked){
                    (activity as ReminderActivity).desayuno.add(Alimentos(cbd4.text.toString(), tietDesPorcion4.text.toString()))
                    Log.i("Acción", "Después de agregar al array 4")
                }
                else {
                    val it = (activity as ReminderActivity).desayuno.iterator()
                    while (it.hasNext()) {
                        val alimentos: Alimentos = it.next()
                        if (alimentos.alimento.equals(cbd4.text.toString()))
                            it.remove()
                    }
                }
            }
        }
    }

    override fun onPauseFragment() {
        Log.i(ContentValues.TAG, "onPauseFragment()")
    }

    override fun onResumeFragment() {

    }

}
