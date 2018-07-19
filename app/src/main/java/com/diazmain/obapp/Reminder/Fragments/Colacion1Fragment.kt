package com.diazmain.obapp.Reminder.Fragments

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import com.diazmain.obapp.Home.model.meals.IndividualMealArrayElement
import com.diazmain.obapp.Home.model.meals.OptionsPerMeal

import com.diazmain.obapp.R
import com.diazmain.obapp.Reminder.Pojo.CamposCheck
import com.diazmain.obapp.Reminder.Pojo.FoodSwapInfo
import com.diazmain.obapp.Reminder.ReminderActivity
import kotlinx.android.synthetic.main.fragment_colacion1.*
import java.text.SimpleDateFormat
import java.util.*

class Colacion1Fragment : Fragment(), CompoundButton.OnCheckedChangeListener, View.OnClickListener, AdapterView.OnItemSelectedListener {

    lateinit var co1Menu: OptionsPerMeal

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
        cbc15.setOnCheckedChangeListener(this)
        cbc16.setOnCheckedChangeListener(this)

        btnCo1Time.setOnClickListener(this)

        (activity as ReminderActivity).window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        co1Menu = (activity as ReminderActivity).mealsMenu.menuCal.colacion1

        val sp: Spinner = (activity as ReminderActivity).findViewById(R.id.spCo1)
        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
                (activity as ReminderActivity).context,
                R.array.meal_options,
                R.layout.spinner_item
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp.adapter = adapter
        sp.onItemSelectedListener = this
        superFunFunction(0)

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
                    (activity as ReminderActivity).checks.add(CamposCheck(2,cbc11, tilCo1Porcion1, tietCo1Porcion1))
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
                    (activity as ReminderActivity).checks.add(CamposCheck(2,cbc12, tilCo1Porcion2, tietCo1Porcion2))
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
                    (activity as ReminderActivity).checks.add(CamposCheck(2,cbc13, tilCo1Porcion3, tietCo1Porcion3))
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
                    (activity as ReminderActivity).checks.add(CamposCheck(2,cbc14, tilCo1Porcion4, tietCo1Porcion4))
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
            cbc15 -> {
                if (cbc15.isChecked) {
                    tilCo1Porcion5.visibility = View.VISIBLE
                    (activity as ReminderActivity).checks.add(CamposCheck(2,cbc15, tilCo1Porcion5, tietCo1Porcion5))
                } else {
                    val it = (activity as ReminderActivity).checks.iterator()
                    while (it.hasNext()) {
                        val check: CamposCheck = it.next()
                        if (check.equals(cbc15))
                            it.remove()
                    }
                    tilCo1Porcion5.visibility = View.GONE
                }
            }
            cbc16 -> {
                if (cbc16.isChecked) {
                    tilCo1Porcion6.visibility = View.VISIBLE
                    (activity as ReminderActivity).checks.add(CamposCheck(2,cbc16, tilCo1Porcion6, tietCo1Porcion6))
                } else {
                    val it = (activity as ReminderActivity).checks.iterator()
                    while (it.hasNext()) {
                        val check: CamposCheck = it.next()
                        if (check.equals(cbc16))
                            it.remove()
                    }
                    tilCo1Porcion6.visibility = View.GONE
                }
            }
        }
    }

    override fun onClick(v: View?) {
        if (v == btnCo1Time) {
            pickDate()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        superFunFunction(position)
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

    fun superFunFunction(op: Int) {
        when (op) {
            0 -> {
                val optionsSize: Int = co1Menu.op1.size
                val mealElement: ArrayList<IndividualMealArrayElement> = getIndividualMealArray()

                for (i: Int in 0..5) {
                    if (i >= optionsSize) {
                        mealElement[i].clay.visibility = View.GONE
                        mealElement[i].til.visibility = View.GONE

                    } else {
                        mealElement[i].clay.visibility = View.VISIBLE

                        mealElement[i].cbox.setText(co1Menu.op1[i].alimento)
                        mealElement[i].tiet.setText(co1Menu.op1[i].porcion)

                        // Comprobar que el array de cambio contiene información
                        var b1: Boolean = false
                        var b2: Boolean = false
                        var b3: Boolean = false

                        try {
                            b1 = false
                            val b = co1Menu.op1[i].cambio.cambioUno.isEmpty()
                            b1 = b
                        } catch (e: NullPointerException) {
                            Log.wtf("error", e.toString())
                            b1 = true
                        }
                        try {
                            b2 = false
                            val b = co1Menu.op1[i].cambio.cambioDos.isEmpty()
                            b2 = b
                        } catch (e: NullPointerException) {
                            Log.wtf("error", e.toString())
                            b2 = true
                        }
                        try {
                            b3 = false
                            val b = co1Menu.op1[i].cambio.cambioTres.isEmpty()
                            b3 = b
                        } catch (e: NullPointerException) {
                            Log.wtf("error", e.toString())
                            b3 = true
                        }

                        if (!(b1 && b2 && b3)) {
                            mealElement[i].ibutton.visibility = View.VISIBLE
                            mealElement[i].ibutton.setOnClickListener() { v ->
                                if (!b1) {
                                    getDialog1(i, mealElement, op).show()
                                }
                                if (!b2) {
                                    getDialog2(i, mealElement, op).show()
                                }
                                if (!b3) {
                                    getDialog3(i, mealElement, op).show()
                                }
                            }
                        } else {
                            mealElement[i].ibutton.visibility = View.GONE
                        }
                    }
                }
            }
            1 -> {
                val optionsSize: Int = co1Menu.op2.size
                val mealElement: ArrayList<IndividualMealArrayElement> = getIndividualMealArray()

                for (i: Int in 0..5) {
                    if (i >= optionsSize) {
                        mealElement[i].clay.visibility = View.GONE
                        mealElement[i].til.visibility = View.GONE

                    } else {
                        mealElement[i].clay.visibility = View.VISIBLE

                        mealElement[i].cbox.setText(co1Menu.op2[i].alimento)
                        mealElement[i].tiet.setText(co1Menu.op2[i].porcion)

                        // Comprobar que el array de cambio contiene información
                        var b1: Boolean
                        var b2: Boolean
                        var b3: Boolean

                        try {
                            b1 = false
                            val b = co1Menu.op2[i].cambio.cambioUno.isEmpty()
                            b1 = b
                        } catch (e: NullPointerException) {
                            Log.wtf("error", e.toString())
                            b1 = true
                        }
                        try {
                            b2 = false
                            val b = co1Menu.op2[i].cambio.cambioDos.isEmpty()
                            b2 = b
                        } catch (e: NullPointerException) {
                            Log.wtf("error", e.toString())
                            b2 = true
                        }
                        try {
                            b3 = false
                            val b = co1Menu.op2[i].cambio.cambioTres.isEmpty()
                            b3 = b
                        } catch (e: NullPointerException) {
                            Log.wtf("error", e.toString())
                            b3 = true
                        }

                        if (!(b1 && b2 && b3)) {
                            mealElement[i].ibutton.visibility = View.VISIBLE
                            mealElement[i].ibutton.setOnClickListener() { v ->
                                if (!b1) {
                                    getDialog1(i, mealElement, op).show()
                                }
                                if (!b2) {
                                    getDialog2(i, mealElement, op).show()
                                }
                                if (!b3) {
                                    getDialog3(i, mealElement, op).show()
                                }
                            }
                        } else {
                            mealElement[i].ibutton.visibility = View.GONE
                        }
                    }
                }
            }
            2 -> {
                val optionsSize: Int = co1Menu.op3.size
                val mealElement: ArrayList<IndividualMealArrayElement> = getIndividualMealArray()

                for (i: Int in 0..5) {
                    if (i >= optionsSize) {
                        mealElement[i].clay.visibility = View.GONE
                        mealElement[i].til.visibility = View.GONE

                    } else {
                        mealElement[i].clay.visibility = View.VISIBLE

                        mealElement[i].cbox.setText(co1Menu.op3[i].alimento)
                        mealElement[i].tiet.setText(co1Menu.op3[i].porcion)

                        // Comprobar que el array de cambio contiene información
                        var b1: Boolean
                        var b2: Boolean
                        var b3: Boolean

                        try {
                            b1 = false
                            val b = co1Menu.op3[i].cambio.cambioUno.isEmpty()
                            b1 = b
                        } catch (e: NullPointerException) {
                            Log.wtf("error", e.toString())
                            b1 = true
                        }
                        try {
                            b2 = false
                            val b = co1Menu.op3[i].cambio.cambioDos.isEmpty()
                            b2 = b
                        } catch (e: NullPointerException) {
                            Log.wtf("error", e.toString())
                            b2 = true
                        }
                        try {
                            b3 = false
                            val b = co1Menu.op3[i].cambio.cambioTres.isEmpty()
                            b3 = b
                        } catch (e: NullPointerException) {
                            Log.wtf("error", e.toString())
                            b3 = true
                        }

                        if (!(b1 && b2 && b3)) {
                            mealElement[i].ibutton.visibility = View.VISIBLE
                            mealElement[i].ibutton.setOnClickListener() { v ->
                                if (!b1) {
                                    getDialog1(i, mealElement, op).show()
                                }
                                if (!b2) {
                                    getDialog2(i, mealElement, op).show()
                                }
                                if (!b3) {
                                    getDialog3(i, mealElement, op).show()
                                }
                            }
                        } else {
                            mealElement[i].ibutton.visibility = View.GONE
                        }
                    }
                }
            }
            3 -> {
                val optionsSize: Int = co1Menu.op4.size
                val mealElement: ArrayList<IndividualMealArrayElement> = getIndividualMealArray()

                for (i: Int in 0..5) {
                    if (i >= optionsSize) {
                        mealElement[i].clay.visibility = View.GONE
                        mealElement[i].til.visibility = View.GONE

                    } else {
                        mealElement[i].clay.visibility = View.VISIBLE

                        mealElement[i].cbox.setText(co1Menu.op4[i].alimento)
                        mealElement[i].tiet.setText(co1Menu.op4[i].porcion)

                        // Comprobar que el array de cambio contiene información
                        var b1: Boolean
                        var b2: Boolean
                        var b3: Boolean

                        try {
                            b1 = false
                            val b = co1Menu.op4[i].cambio.cambioUno.isEmpty()
                            b1 = b
                        } catch (e: NullPointerException) {
                            Log.wtf("error", e.toString())
                            b1 = true
                        }
                        try {
                            b2 = false
                            val b = co1Menu.op4[i].cambio.cambioDos.isEmpty()
                            b2 = b
                        } catch (e: NullPointerException) {
                            Log.wtf("error", e.toString())
                            b2 = true
                        }
                        try {
                            b3 = false
                            val b = co1Menu.op4[i].cambio.cambioTres.isEmpty()
                            b3 = b
                        } catch (e: NullPointerException) {
                            Log.wtf("error", e.toString())
                            b3 = true
                        }

                        if (!(b1 && b2 && b3)) {
                            mealElement[i].ibutton.visibility = View.VISIBLE
                            mealElement[i].ibutton.setOnClickListener() { v ->
                                if (!b1) {
                                    getDialog1(i, mealElement, op).show()
                                }
                                if (!b2) {
                                    getDialog2(i, mealElement, op).show()
                                }
                                if (!b3) {
                                    getDialog3(i, mealElement, op).show()
                                }
                            }
                        } else {
                            mealElement[i].ibutton.visibility = View.GONE
                        }
                    }
                }
            }
            4 -> {
                val optionsSize: Int = co1Menu.op5.size
                val mealElement: ArrayList<IndividualMealArrayElement> = getIndividualMealArray()

                for (i: Int in 0..5) {
                    if (i >= optionsSize) {
                        mealElement[i].clay.visibility = View.GONE
                        mealElement[i].til.visibility = View.GONE

                    } else {
                        mealElement[i].clay.visibility = View.VISIBLE

                        mealElement[i].cbox.setText(co1Menu.op5[i].alimento)
                        mealElement[i].tiet.setText(co1Menu.op5[i].porcion)

                        // Comprobar que el array de cambio contiene información
                        var b1: Boolean
                        var b2: Boolean
                        var b3: Boolean

                        try {
                            b1 = false
                            val b = co1Menu.op5[i].cambio.cambioUno.isEmpty()
                            b1 = b
                        } catch (e: NullPointerException) {
                            Log.wtf("error", e.toString())
                            b1 = true
                        }
                        try {
                            b2 = false
                            val b = co1Menu.op5[i].cambio.cambioDos.isEmpty()
                            b2 = b
                        } catch (e: NullPointerException) {
                            Log.wtf("error", e.toString())
                            b2 = true
                        }
                        try {
                            b3 = false
                            val b = co1Menu.op5[i].cambio.cambioTres.isEmpty()
                            b3 = b
                        } catch (e: NullPointerException) {
                            Log.wtf("error", e.toString())
                            b3 = true
                        }

                        /*Log.w("isCambio n empty", (b1 || b2 || b3).toString() )
                        Log.w("isCambio1 empty", b1.toString() )
                        Log.w("isCambio2 empty", b2.toString() )
                        Log.w("isCambio3 empty", b3.toString() )
                        Log.w("jejej","jejeje")*/

                        if (!(b1 && b2 && b3)) {
                            mealElement[i].ibutton.visibility = View.VISIBLE
                            mealElement[i].ibutton.setOnClickListener() { v ->
                                if (!b1) {
                                    getDialog1(i, mealElement, op).show()
                                }
                                if (!b2) {
                                    getDialog2(i, mealElement, op).show()
                                }
                                if (!b3) {
                                    getDialog3(i, mealElement, op).show()
                                }
                            }
                        } else {
                            mealElement[i].ibutton.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    private fun getIndividualMealArray(): ArrayList<IndividualMealArrayElement> {
        val array: ArrayList<IndividualMealArrayElement> = ArrayList()
        array.add(IndividualMealArrayElement(imbCo11, clyCo11, cbc11, tilCo1Porcion1, tietCo1Porcion1, ArrayList()))
        array.add(IndividualMealArrayElement(imbCo12, clyCo12, cbc12, tilCo1Porcion2, tietCo1Porcion2, ArrayList()))
        array.add(IndividualMealArrayElement(imbCo13, clyCo13, cbc13, tilCo1Porcion3, tietCo1Porcion3, ArrayList()))
        array.add(IndividualMealArrayElement(imbCo14, clyCo14, cbc14, tilCo1Porcion4, tietCo1Porcion4, ArrayList()))
        array.add(IndividualMealArrayElement(imbCo15, clyCo15, cbc15, tilCo1Porcion5, tietCo1Porcion5, ArrayList()))
        array.add(IndividualMealArrayElement(imbCo16, clyCo16, cbc16, tilCo1Porcion6, tietCo1Porcion6, ArrayList()))

        return array
    }

    private fun getDialog1CheckBoxes(v: View): ArrayList<CheckBox> {
        val array: ArrayList<CheckBox> = ArrayList()

        val cbFru1: CheckBox = v.findViewById(R.id.cbFru1)
        val cbFru2: CheckBox = v.findViewById(R.id.cbFru2)
        val cbFru3: CheckBox = v.findViewById(R.id.cbFru3)
        val cbFru4: CheckBox = v.findViewById(R.id.cbFru4)
        val cbFru5: CheckBox = v.findViewById(R.id.cbFru5)
        val cbFru6: CheckBox = v.findViewById(R.id.cbFru6)
        val cbFru7: CheckBox = v.findViewById(R.id.cbFru7)
        val cbFru8: CheckBox = v.findViewById(R.id.cbFru8)
        val cbFru9: CheckBox = v.findViewById(R.id.cbFru9)
        val cbFru10: CheckBox = v.findViewById(R.id.cbFru10)
        val cbFru11: CheckBox = v.findViewById(R.id.cbFru11)
        val cbFru12: CheckBox = v.findViewById(R.id.cbFru12)
        val cbFru13: CheckBox = v.findViewById(R.id.cbFru13)
        val cbFru14: CheckBox = v.findViewById(R.id.cbFru14)
        val cbFru15: CheckBox = v.findViewById(R.id.cbFru15)
        val cbFru16: CheckBox = v.findViewById(R.id.cbFru16)
        val cbFru17: CheckBox = v.findViewById(R.id.cbFru17)
        val cbFru18: CheckBox = v.findViewById(R.id.cbFru18)
        val cbFru19: CheckBox = v.findViewById(R.id.cbFru19)
        val cbFru20: CheckBox = v.findViewById(R.id.cbFru20)
        val cbFru21: CheckBox = v.findViewById(R.id.cbFru21)
        val cbFru22: CheckBox = v.findViewById(R.id.cbFru22)
        val cbFru23: CheckBox = v.findViewById(R.id.cbFru23)
        val cbFru24: CheckBox = v.findViewById(R.id.cbFru24)
        val cbFru25: CheckBox = v.findViewById(R.id.cbFru25)
        val cbFru26: CheckBox = v.findViewById(R.id.cbFru26)
        val cbFru27: CheckBox = v.findViewById(R.id.cbFru27)
        val cbFru28: CheckBox = v.findViewById(R.id.cbFru28)
        val cbFru29: CheckBox = v.findViewById(R.id.cbFru29)

        array.add(cbFru1)
        array.add(cbFru2)
        array.add(cbFru3)
        array.add(cbFru4)
        array.add(cbFru5)
        array.add(cbFru6)
        array.add(cbFru7)
        array.add(cbFru8)
        array.add(cbFru9)
        array.add(cbFru10)
        array.add(cbFru11)
        array.add(cbFru12)
        array.add(cbFru13)
        array.add(cbFru14)
        array.add(cbFru15)
        array.add(cbFru16)
        array.add(cbFru17)
        array.add(cbFru18)
        array.add(cbFru19)
        array.add(cbFru20)
        array.add(cbFru21)
        array.add(cbFru22)
        array.add(cbFru23)
        array.add(cbFru24)
        array.add(cbFru25)
        array.add(cbFru26)
        array.add(cbFru27)
        array.add(cbFru28)
        array.add(cbFru29)
        return array
    }

    private fun getDialog2CheckBoxes(v: View): ArrayList<CheckBox> {
        val array: ArrayList<CheckBox> = ArrayList()
        val cbNuts1: CheckBox = v.findViewById(R.id.cbNuts1)
        val cbNuts2: CheckBox = v.findViewById(R.id.cbNuts2)
        val cbNuts3: CheckBox = v.findViewById(R.id.cbNuts3)
        val cbNuts4: CheckBox = v.findViewById(R.id.cbNuts4)
        val cbNuts5: CheckBox = v.findViewById(R.id.cbNuts5)
        val cbNuts6: CheckBox = v.findViewById(R.id.cbNuts6)
        val cbNuts7: CheckBox = v.findViewById(R.id.cbNuts7)
        val cbNuts8: CheckBox = v.findViewById(R.id.cbNuts8)
        val cbNuts9: CheckBox = v.findViewById(R.id.cbNuts9)
        val cbNuts10: CheckBox = v.findViewById(R.id.cbNuts10)
        val cbNuts11: CheckBox = v.findViewById(R.id.cbNuts11)
        val cbNuts12: CheckBox = v.findViewById(R.id.cbNuts12)
        array.add(cbNuts1)
        array.add(cbNuts2)
        array.add(cbNuts3)
        array.add(cbNuts4)
        array.add(cbNuts5)
        array.add(cbNuts6)
        array.add(cbNuts7)
        array.add(cbNuts8)
        array.add(cbNuts9)
        array.add(cbNuts10)
        array.add(cbNuts11)
        array.add(cbNuts12)
        return array
    }

    private fun getDialog3CheckBoxes(v: View): ArrayList<CheckBox> {
        val array: ArrayList<CheckBox> = ArrayList()
        val cbMeat1: CheckBox = v.findViewById(R.id.cbMeat1)
        val cbMeat2: CheckBox = v.findViewById(R.id.cbMeat2)
        val cbMeat3: CheckBox = v.findViewById(R.id.cbMeat3)
        val cbMeat4: CheckBox = v.findViewById(R.id.cbMeat4)
        val cbMeat5: CheckBox = v.findViewById(R.id.cbMeat5)
        val cbMeat6: CheckBox = v.findViewById(R.id.cbMeat6)
        val cbMeat7: CheckBox = v.findViewById(R.id.cbMeat7)
        val cbMeat8: CheckBox = v.findViewById(R.id.cbMeat8)
        val cbMeat9: CheckBox = v.findViewById(R.id.cbMeat9)
        val cbMeat10: CheckBox = v.findViewById(R.id.cbMeat10)
        array.add(cbMeat1)
        array.add(cbMeat2)
        array.add(cbMeat3)
        array.add(cbMeat4)
        array.add(cbMeat5)
        array.add(cbMeat6)
        array.add(cbMeat7)
        array.add(cbMeat8)
        array.add(cbMeat9)
        array.add(cbMeat10)
        return array
    }

    private fun getDialog1(i: Int, meal: ArrayList<IndividualMealArrayElement>, op: Int): AlertDialog {
        val inflater: LayoutInflater = LayoutInflater.from((activity as ReminderActivity).context)
        val promptView: View = inflater.inflate(R.layout.dialog_swap_fruits, null)

        val swapOne: ArrayList<CheckBox> = getDialog1CheckBoxes(promptView)

        var cambioUno: List<String> = ArrayList()
        when(op) {
            0 -> { cambioUno = co1Menu.op1[i].cambio.cambioUno }
            1 -> { cambioUno = co1Menu.op2[i].cambio.cambioUno }
            2 -> { cambioUno = co1Menu.op3[i].cambio.cambioUno }
            3 -> { cambioUno = co1Menu.op4[i].cambio.cambioUno }
            4 -> { cambioUno = co1Menu.op5[i].cambio.cambioUno }
        }

        for (n in swapOne.indices) {
            swapOne[n].setText(cambioUno[n])
        }

        val alBuilder: AlertDialog.Builder = AlertDialog.Builder(this.context)
        alBuilder.setView(promptView)
        alBuilder.setTitle(R.string.label_title_1S)
        alBuilder.setCancelable(false)
        alBuilder.setPositiveButton(R.string.btn_accept, DialogInterface.OnClickListener() { dialog, which ->

            for (x in swapOne.indices) {
                if (swapOne[x].isChecked) {
                    if ((activity as ReminderActivity).swaps.size == 0) {
                        (activity as ReminderActivity).swaps.add(FoodSwapInfo(2, i, meal[i].cbox.text.toString(), swapOne[x].text.toString(), "", ""))
                    } else {
                        val it = (activity as ReminderActivity).swaps.iterator()
                        while(it.hasNext()) {
                            val food: FoodSwapInfo = it.next()
                            if (food.food.equals(meal[i].cbox.text.toString())){
                                it.remove()
                            }
                        }
                        (activity as ReminderActivity).swaps.add(FoodSwapInfo(2, i, meal[i].cbox.text.toString(), swapOne[x].text.toString(), "", ""))
                    }
                }
            }
        })
        alBuilder.setNegativeButton(R.string.btn_cancel, DialogInterface.OnClickListener() { dialog, which ->
            meal[i].swap.clear()
            dialog.cancel()
        })

        return alBuilder.create()
    }

    private fun getDialog2(i: Int, meal: ArrayList<IndividualMealArrayElement>, op: Int): AlertDialog {
        val inflater: LayoutInflater = LayoutInflater.from((activity as ReminderActivity).context)
        val promptView: View = inflater.inflate(R.layout.dialog_swap_nuts, null)

        val swapTwo: ArrayList<CheckBox> = getDialog2CheckBoxes(promptView)

        var cambioDos: List<String> = ArrayList()
        when(op) {
            0 -> { cambioDos = co1Menu.op1[i].cambio.cambioDos }
            1 -> { cambioDos = co1Menu.op2[i].cambio.cambioDos }
            2 -> { cambioDos = co1Menu.op3[i].cambio.cambioDos }
            3 -> { cambioDos = co1Menu.op4[i].cambio.cambioDos }
            4 -> { cambioDos = co1Menu.op5[i].cambio.cambioDos }
        }

        for (n in swapTwo.indices) {
            swapTwo[n].setText(cambioDos[n])
        }

        val alBuilder: AlertDialog.Builder = AlertDialog.Builder(this.context)
        alBuilder.setView(promptView)
        alBuilder.setTitle(R.string.label_title_2S)
        alBuilder.setCancelable(false)
        alBuilder.setPositiveButton(R.string.btn_accept, DialogInterface.OnClickListener() { dialog, which ->

            for (x in swapTwo.indices) {
                if (swapTwo[x].isChecked) {
                    if ((activity as ReminderActivity).swaps.size == 0) {
                        (activity as ReminderActivity).swaps.add(FoodSwapInfo(2, i, meal[i].cbox.text.toString(), "", swapTwo[x].text.toString(), ""))
                    } else {
                        val it = (activity as ReminderActivity).swaps.iterator()
                        while(it.hasNext()) {
                            val food: FoodSwapInfo = it.next()
                            if (food.food.equals(meal[i].cbox.text.toString())){
                                it.remove()
                            }
                        }
                        (activity as ReminderActivity).swaps.add(FoodSwapInfo(2, i, meal[i].cbox.text.toString(), "", swapTwo[x].text.toString(), ""))
                    }
                }
            }
        })
        alBuilder.setNegativeButton(R.string.btn_cancel, DialogInterface.OnClickListener() { dialog, which ->
            meal[i].swap.clear()
            dialog.cancel()
        })

        return alBuilder.create()
    }

    private fun getDialog3(i: Int, meal: ArrayList<IndividualMealArrayElement>, op: Int): AlertDialog {
        val inflater: LayoutInflater = LayoutInflater.from((activity as ReminderActivity).context)
        val promptView: View = inflater.inflate(R.layout.dialog_swap_meats, null)

        val swapThree: ArrayList<CheckBox> = getDialog3CheckBoxes(promptView)

        var cambioTres: List<String> = ArrayList()
        when(op) {
            0 -> { cambioTres = co1Menu.op1[i].cambio.cambioTres }
            1 -> { cambioTres = co1Menu.op2[i].cambio.cambioTres }
            2 -> { cambioTres = co1Menu.op3[i].cambio.cambioTres }
            3 -> { cambioTres = co1Menu.op4[i].cambio.cambioTres }
            4 -> { cambioTres = co1Menu.op5[i].cambio.cambioTres }
        }

        for (n in swapThree.indices) {
            swapThree[n].setText(cambioTres[n])
        }

        val alBuilder: AlertDialog.Builder = AlertDialog.Builder(this.context)
        alBuilder.setView(promptView)
        alBuilder.setTitle(R.string.label_title_3S)
        alBuilder.setCancelable(false)
        alBuilder.setPositiveButton(R.string.btn_accept, DialogInterface.OnClickListener() { dialog, which ->

            for (x in swapThree.indices) {
                if (swapThree[x].isChecked) {
                    if ((activity as ReminderActivity).swaps.size == 0) {
                        (activity as ReminderActivity).swaps.add(FoodSwapInfo(2, i, meal[i].cbox.text.toString(), "", "", swapThree[x].text.toString()))
                    } else {
                        val it = (activity as ReminderActivity).swaps.iterator()
                        while(it.hasNext()) {
                            val food: FoodSwapInfo = it.next()
                            if (food.food.equals(meal[i].cbox.text.toString())){
                                it.remove()
                            }
                        }
                        (activity as ReminderActivity).swaps.add(FoodSwapInfo(2, i, meal[i].cbox.text.toString(), "", "", swapThree[x].text.toString()))
                    }
                }
            }
        })
        alBuilder.setNegativeButton(R.string.btn_cancel, DialogInterface.OnClickListener() { dialog, which ->
            meal[i].swap.clear()
            dialog.cancel()
        })

        return alBuilder.create()
    }
}
