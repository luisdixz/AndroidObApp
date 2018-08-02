package com.diazmain.obapp.reminder

import android.app.Activity
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.CompoundButton
import com.diazmain.obapp.home.model.meals.IndividualMealArrayElement
import com.diazmain.obapp.home.model.meals.OptionsPerMeal
import com.diazmain.obapp.R
import com.diazmain.obapp.reminder.pojo.Alimentos
import com.diazmain.obapp.reminder.pojo.CamposCheck
import com.diazmain.obapp.reminder.pojo.Comidas
import com.diazmain.obapp.reminder.pojo.FoodSwapInfo
import com.diazmain.obapp.helper.SharedPrefManager
import kotlinx.android.synthetic.main.activity_meal.*
import java.text.SimpleDateFormat
import java.util.*

class MealActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener, View.OnClickListener, AdapterView.OnItemSelectedListener {

    private lateinit var comMenu: OptionsPerMeal
    private var comHora: String = "00:00"
    private var USER_ID: Int = 0
    private val swaps: ArrayList<FoodSwapInfo> = ArrayList()
    private val checks: ArrayList<CamposCheck> = ArrayList()
    private val comida: ArrayList<Alimentos> = ArrayList()
    private val finalExtras: StringBuilder = StringBuilder()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        USER_ID = intent.getExtras().getInt("USER_ID")
        comMenu = SharedPrefManager.getInstance(this)!!.getMealsMenu().menuCal.comida

        cbco1.setOnCheckedChangeListener(this)
        cbco2.setOnCheckedChangeListener(this)
        cbco3.setOnCheckedChangeListener(this)
        cbco4.setOnCheckedChangeListener(this)
        cbco5.setOnCheckedChangeListener(this)
        cbco6.setOnCheckedChangeListener(this)

        btnComTime.setOnClickListener(this)
        imbComNewExtra.setOnClickListener(this)
        btnComSave.setOnClickListener(this)

        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
                this,
                R.array.meal_options,
                R.layout.spinner_item
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spCom.adapter = adapter
        spCom.onItemSelectedListener = this

        superFunFunction(0)
    }

    override fun onClick(v: View?) {
        when (v) {
            btnComTime -> {
                pickTime()
            }
            btnComSave -> {
                storeMeal()
            }
            imbComNewExtra -> {
                appendExtraFood()
            }
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when (buttonView) {
            cbco1 -> {
                if (cbco1.isChecked) {
                    tilComPorcion1.visibility = View.VISIBLE
                    checks.add(CamposCheck(1, cbco1, tilComPorcion1, tietComPorcion1))
                    if (imbCom1.visibility != View.GONE) {
                        imbCom1.isEnabled = true
                        imbCom1.alpha = 1f
                    }
                } else {
                    val it = checks.iterator()
                    while (it.hasNext()) {
                        val check: CamposCheck = it.next()
                        if (check.equals(cbco1))
                            it.remove()
                    }
                    tilComPorcion1.visibility = View.GONE
                    if (imbCom1.visibility != View.GONE) {
                        imbCom1.isEnabled = false
                        imbCom1.alpha = 0.5f
                    }
                }
            }
            cbco2 -> {
                if (cbco2.isChecked) {
                    tilComPorcion2.visibility = View.VISIBLE
                    checks.add(CamposCheck(1, cbco2, tilComPorcion2, tietComPorcion2))
                    if (imbCom2.visibility != View.GONE) {
                        imbCom2.isEnabled = true
                        imbCom2.alpha = 1f
                    }
                } else {
                    val it = checks.iterator()
                    while (it.hasNext()) {
                        val check: CamposCheck = it.next()
                        if (check.equals(cbco2))
                            it.remove()
                    }
                    tilComPorcion2.visibility = View.GONE
                    if (imbCom2.visibility != View.GONE) {
                        imbCom2.isEnabled = false
                        imbCom2.alpha = 0.5f
                    }
                }
            }
            cbco3 -> {
                if (cbco3.isChecked) {
                    tilComPorcion3.visibility = View.VISIBLE
                    checks.add(CamposCheck(1, cbco3, tilComPorcion3, tietComPorcion3))
                    if (imbCom3.visibility != View.GONE) {
                        imbCom3.isEnabled = true
                        imbCom3.alpha = 1f
                    }
                } else {
                    val it = checks.iterator()
                    while (it.hasNext()) {
                        val check: CamposCheck = it.next()
                        if (check.equals(cbco3))
                            it.remove()
                    }
                    tilComPorcion3.visibility = View.GONE
                    if (imbCom3.visibility != View.GONE) {
                        imbCom3.isEnabled = false
                        imbCom3.alpha = 0.5f
                    }
                }
            }
            cbco4 -> {
                if (cbco4.isChecked) {
                    tilComPorcion4.visibility = View.VISIBLE
                    checks.add(CamposCheck(1, cbco4, tilComPorcion4, tietComPorcion4))
                    if (imbCom4.visibility != View.GONE) {
                        imbCom4.isEnabled = true
                        imbCom4.alpha = 1f
                    }
                } else {
                    val it = checks.iterator()
                    while (it.hasNext()) {
                        val check: CamposCheck = it.next()
                        if (check.equals(cbco4))
                            it.remove()
                    }
                    tilComPorcion4.visibility = View.GONE
                    if (imbCom4.visibility != View.GONE) {
                        imbCom4.isEnabled = false
                        imbCom4.alpha = 0.5f
                    }
                }
            }
            cbco5 -> {
                if (cbco5.isChecked) {
                    tilComPorcion5.visibility = View.VISIBLE
                    checks.add(CamposCheck(1, cbco5, tilComPorcion5, tietComPorcion5))
                    if (imbCom5.visibility != View.GONE) {
                        imbCom5.isEnabled = true
                        imbCom5.alpha = 1f
                    }
                } else {
                    val it = checks.iterator()
                    while (it.hasNext()) {
                        val check: CamposCheck = it.next()
                        if (check.equals(cbco5))
                            it.remove()
                    }
                    tilComPorcion5.visibility = View.GONE
                    if (imbCom5.visibility != View.GONE) {
                        imbCom5.isEnabled = false
                        imbCom5.alpha = 0.5f
                    }
                }
            }
            cbco6 -> {
                if (cbco6.isChecked) {
                    tilComPorcion6.visibility = View.VISIBLE
                    checks.add(CamposCheck(1, cbco6, tilComPorcion6, tietComPorcion6))
                    if (imbCom6.visibility != View.GONE) {
                        imbCom6.isEnabled = true
                        imbCom6.alpha = 1f
                    }
                } else {
                    val it = checks.iterator()
                    while (it.hasNext()) {
                        val check: CamposCheck = it.next()
                        if (check.equals(cbco6))
                            it.remove()
                    }
                    tilComPorcion6.visibility = View.GONE
                    if (imbCom6.visibility != View.GONE) {
                        imbCom3.isEnabled = false
                        imbCom6.alpha = 0.5f
                    }
                }
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        superFunFunction(position)
    }

    private fun storeMeal() {
        val dateFormat: SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
        if (fillFoodArray()) {
            if (validateEmptyField(tilComTime, tietComTime)) {
                SharedPrefManager.getInstance(this)!!.storeReminderPart(3, Comidas(comHora, comida, finalExtras.toString()),dateFormat.format(System.currentTimeMillis()).toString())
                finish()
            }
        }
    }

    private fun hideKeyboardFrom(v: View) {
        val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    private fun appendExtraFood() {
        if (validateEmptyField(tilComNewExtra, tietComNewExtra)) {
            finalExtras.append(tietComNewExtra.text.toString() + "\n")
            tietComNewExtra.setText("")
            hideKeyboardFrom(tietComNewExtra)
        }
        tvViewExtras.setText(finalExtras.toString())
    }

    private fun pickTime() {
        val cal = Calendar.getInstance()

        TimePickerDialog(
                this,
                TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                    run {
                        cal.set(Calendar.HOUR_OF_DAY, hour)
                        cal.set(Calendar.MINUTE, minute)
                        //Log.w("Hora sin formato", cal.toString())
                        //Log.w("Hora con formato", SimpleDateFormat("HH:mm").format(cal.time))
                        val hora: String = SimpleDateFormat("HH:mm").format(cal.time)
                        tietComTime.setText(hora)
                        comHora = hora
                    }
                },
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                false).show()
    }

    private fun validateEmptyField(til: TextInputLayout, tiet: TextInputEditText): Boolean {

        if (til.visibility == View.VISIBLE) {
            if (tiet.text.isEmpty()) {
                til.error = getString(R.string.validate_empty)
                return false
            } else {
                til.isErrorEnabled = false
            }
        }

        return true
    }

    private fun fillFoodArray(): Boolean {
        for (i in checks.indices) {

            if (!validateEmptyField(checks[i].til, checks[i].tiet)) {
                //cambio = false
                Log.w("EditTextError", checks[i].tiet.id.toString())
                return false
            } else {
                //var cambio: String = ""
                comida.add(Alimentos(checks[i].comida.text.toString(), checks[i].tiet.text.toString(), getFoodSwap(1, i)))
            }
        }
        return true
    }

    private fun getFoodSwap(pag: Int, index: Int): String {
        val builder: StringBuilder = StringBuilder()

        for (i in swaps.indices) {
            if (swaps[i].pagina == pag) {
                if (swaps[i].food.equals(checks[index].comida.text.toString()))
                    if (!swaps[i].swap1.isEmpty())
                        builder.append(swaps[i].swap1 + ", ")
                    else if (!swaps[i].swap2.isEmpty())
                        builder.append(swaps[i].swap2 + ", ")
                    else if (!swaps[i].swap3.isEmpty())
                        builder.append(swaps[i].swap3 + ", ")
            }
        }
        val result: String = builder.toString()

        if (result.length != 0)
            return result.substring(0, result.length - 2)
        else
            return result
    }

    private fun superFunFunction(op: Int) {
        //Log.w("Position -> ", op.toString())
        when (op) {
            0 -> {
                val optionsSize: Int = comMenu.op1.size
                val mealElement: ArrayList<IndividualMealArrayElement> = getIndividualMealArray()

                for (i: Int in 0..5) {
                    if (i >= optionsSize) {
                        mealElement[i].clay.visibility = View.GONE
                        mealElement[i].til.visibility = View.GONE

                    } else {
                        mealElement[i].clay.visibility = View.VISIBLE

                        mealElement[i].cbox.setText(comMenu.op1[i].alimento)
                        mealElement[i].tiet.setText(comMenu.op1[i].porcion)

                        // Comprobar que el array de cambio contiene información
                        var b1: Boolean = false
                        var b2: Boolean = false
                        var b3: Boolean = false

                        try {
                            b1 = false
                            val b = comMenu.op1[i].cambio.cambioUno.isEmpty()
                            b1 = b
                        } catch (e: NullPointerException) {
                            Log.wtf("error", e.toString())
                            b1 = true
                        }
                        try {
                            b2 = false
                            val b = comMenu.op1[i].cambio.cambioDos.isEmpty()
                            b2 = b
                        } catch (e: NullPointerException) {
                            Log.wtf("error", e.toString())
                            b2 = true
                        }
                        try {
                            b3 = false
                            val b = comMenu.op1[i].cambio.cambioTres.isEmpty()
                            b3 = b
                        } catch (e: NullPointerException) {
                            Log.wtf("error", e.toString())
                            b3 = true
                        }

                        /*Log.w("b1 -> "+i, b1.toString())
                        Log.w("b2 -> "+i, b2.toString())
                        Log.w("b3 -> "+i , b3.toString())*/

                        if (!(b1 && b2 && b3)) {
                            mealElement[i].ibutton.visibility = View.VISIBLE
                            mealElement[i].ibutton.isEnabled = false
                            mealElement[i].ibutton.alpha = 0.5f
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
                    Log.w("For -> 1", i.toString())
                }
                Log.w("Op -> 1", "Finalized")
            }
            1 -> {
                val optionsSize: Int = comMenu.op2.size
                val mealElement: ArrayList<IndividualMealArrayElement> = getIndividualMealArray()

                for (i: Int in 0..5) {
                    if (i >= optionsSize) {
                        mealElement[i].clay.visibility = View.GONE
                        mealElement[i].til.visibility = View.GONE

                    } else {
                        mealElement[i].clay.visibility = View.VISIBLE

                        mealElement[i].cbox.setText(comMenu.op2[i].alimento)
                        mealElement[i].tiet.setText(comMenu.op2[i].porcion)

                        // Comprobar que el array de cambio contiene información
                        var b1: Boolean
                        var b2: Boolean
                        var b3: Boolean

                        try {
                            b1 = false
                            val b = comMenu.op2[i].cambio.cambioUno.isEmpty()
                            b1 = b
                        } catch (e: NullPointerException) {
                            Log.wtf("error", e.toString())
                            b1 = true
                        }
                        try {
                            b2 = false
                            val b = comMenu.op2[i].cambio.cambioDos.isEmpty()
                            b2 = b
                        } catch (e: NullPointerException) {
                            Log.wtf("error", e.toString())
                            b2 = true
                        }
                        try {
                            b3 = false
                            val b = comMenu.op2[i].cambio.cambioTres.isEmpty()
                            b3 = b
                        } catch (e: NullPointerException) {
                            Log.wtf("error", e.toString())
                            b3 = true
                        }

                        if (!(b1 && b2 && b3)) {
                            mealElement[i].ibutton.visibility = View.VISIBLE
                            mealElement[i].ibutton.isEnabled = false
                            mealElement[i].ibutton.alpha = 0.5f
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
                val optionsSize: Int = comMenu.op3.size
                val mealElement: ArrayList<IndividualMealArrayElement> = getIndividualMealArray()

                for (i: Int in 0..5) {
                    if (i >= optionsSize) {
                        mealElement[i].clay.visibility = View.GONE
                        mealElement[i].til.visibility = View.GONE

                    } else {
                        mealElement[i].clay.visibility = View.VISIBLE

                        mealElement[i].cbox.setText(comMenu.op3[i].alimento)
                        mealElement[i].tiet.setText(comMenu.op3[i].porcion)

                        // Comprobar que el array de cambio contiene información
                        var b1: Boolean
                        var b2: Boolean
                        var b3: Boolean

                        try {
                            b1 = false
                            val b = comMenu.op3[i].cambio.cambioUno.isEmpty()
                            b1 = b
                        } catch (e: NullPointerException) {
                            Log.wtf("error", e.toString())
                            b1 = true
                        }
                        try {
                            b2 = false
                            val b = comMenu.op3[i].cambio.cambioDos.isEmpty()
                            b2 = b
                        } catch (e: NullPointerException) {
                            Log.wtf("error", e.toString())
                            b2 = true
                        }
                        try {
                            b3 = false
                            val b = comMenu.op3[i].cambio.cambioTres.isEmpty()
                            b3 = b
                        } catch (e: NullPointerException) {
                            Log.wtf("error", e.toString())
                            b3 = true
                        }

                        if (!(b1 && b2 && b3)) {
                            mealElement[i].ibutton.visibility = View.VISIBLE
                            mealElement[i].ibutton.isEnabled = false
                            mealElement[i].ibutton.alpha = 0.5f
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
                val optionsSize: Int = comMenu.op4.size
                val mealElement: ArrayList<IndividualMealArrayElement> = getIndividualMealArray()

                for (i: Int in 0..5) {
                    if (i >= optionsSize) {
                        mealElement[i].clay.visibility = View.GONE
                        mealElement[i].til.visibility = View.GONE

                    } else {
                        mealElement[i].clay.visibility = View.VISIBLE

                        mealElement[i].cbox.setText(comMenu.op4[i].alimento)
                        mealElement[i].tiet.setText(comMenu.op4[i].porcion)

                        // Comprobar que el array de cambio contiene información
                        var b1: Boolean
                        var b2: Boolean
                        var b3: Boolean

                        try {
                            b1 = false
                            val b = comMenu.op4[i].cambio.cambioUno.isEmpty()
                            b1 = b
                        } catch (e: NullPointerException) {
                            Log.wtf("error", e.toString())
                            b1 = true
                        }
                        try {
                            b2 = false
                            val b = comMenu.op4[i].cambio.cambioDos.isEmpty()
                            b2 = b
                        } catch (e: NullPointerException) {
                            Log.wtf("error", e.toString())
                            b2 = true
                        }
                        try {
                            b3 = false
                            val b = comMenu.op4[i].cambio.cambioTres.isEmpty()
                            b3 = b
                        } catch (e: NullPointerException) {
                            Log.wtf("error", e.toString())
                            b3 = true
                        }

                        if (!(b1 && b2 && b3)) {
                            mealElement[i].ibutton.visibility = View.VISIBLE
                            mealElement[i].ibutton.isEnabled = false
                            mealElement[i].ibutton.alpha = 0.5f
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
                val optionsSize: Int = comMenu.op5.size
                val mealElement: ArrayList<IndividualMealArrayElement> = getIndividualMealArray()

                for (i: Int in 0..5) {
                    if (i >= optionsSize) {
                        mealElement[i].clay.visibility = View.GONE
                        mealElement[i].til.visibility = View.GONE

                    } else {
                        mealElement[i].clay.visibility = View.VISIBLE

                        mealElement[i].cbox.setText(comMenu.op5[i].alimento)
                        mealElement[i].tiet.setText(comMenu.op5[i].porcion)

                        // Comprobar que el array de cambio contiene información
                        var b1: Boolean
                        var b2: Boolean
                        var b3: Boolean

                        try {
                            b1 = false
                            val b = comMenu.op5[i].cambio.cambioUno.isEmpty()
                            b1 = b
                        } catch (e: NullPointerException) {
                            Log.wtf("error", e.toString())
                            b1 = true
                        }
                        try {
                            b2 = false
                            val b = comMenu.op5[i].cambio.cambioDos.isEmpty()
                            b2 = b
                        } catch (e: NullPointerException) {
                            Log.wtf("error", e.toString())
                            b2 = true
                        }
                        try {
                            b3 = false
                            val b = comMenu.op5[i].cambio.cambioTres.isEmpty()
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
                            mealElement[i].ibutton.isEnabled = false
                            mealElement[i].ibutton.alpha = 0.5f
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
        array.add(IndividualMealArrayElement(imbCom1, clyCom1, cbco1, tilComPorcion1, tietComPorcion1, ArrayList()))
        array.add(IndividualMealArrayElement(imbCom2, clyCom2, cbco2, tilComPorcion2, tietComPorcion2, ArrayList()))
        array.add(IndividualMealArrayElement(imbCom3, clyCom3, cbco3, tilComPorcion3, tietComPorcion3, ArrayList()))
        array.add(IndividualMealArrayElement(imbCom4, clyCom4, cbco4, tilComPorcion4, tietComPorcion4, ArrayList()))
        array.add(IndividualMealArrayElement(imbCom5, clyCom5, cbco5, tilComPorcion5, tietComPorcion5, ArrayList()))
        array.add(IndividualMealArrayElement(imbCom6, clyCom6, cbco6, tilComPorcion6, tietComPorcion6, ArrayList()))

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
        val inflater: LayoutInflater = LayoutInflater.from(this)
        val promptView: View = inflater.inflate(R.layout.dialog_swap_fruits, null)

        val swapOne: ArrayList<CheckBox> = getDialog1CheckBoxes(promptView)

        var cambioUno: List<String> = ArrayList()
        when (op) {
            0 -> {
                cambioUno = comMenu.op1[i].cambio.cambioUno
            }
            1 -> {
                cambioUno = comMenu.op2[i].cambio.cambioUno
            }
            2 -> {
                cambioUno = comMenu.op3[i].cambio.cambioUno
            }
            3 -> {
                cambioUno = comMenu.op4[i].cambio.cambioUno
            }
            4 -> {
                cambioUno = comMenu.op5[i].cambio.cambioUno
            }
        }

        for (n in swapOne.indices) {
            swapOne[n].setText(cambioUno[n])
            Log.w("swapOne.text", swapOne[n].text.toString())
            Log.w("cambioUno.text", cambioUno[n])
        }

        val alBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        alBuilder.setView(promptView)
        alBuilder.setTitle(R.string.label_title_1S)
        alBuilder.setCancelable(false)
        alBuilder.setPositiveButton(R.string.btn_accept, DialogInterface.OnClickListener() { dialog, which ->

            for (x in swapOne.indices) {
                if (swapOne[x].isChecked) {
                    if (swaps.size == 0) {
                        swaps.add(FoodSwapInfo(1, i, meal[i].cbox.text.toString(), swapOne[x].text.toString(), "", ""))
                    } else {
                        val it = swaps.iterator()
                        while (it.hasNext()) {
                            val food: FoodSwapInfo = it.next()
                            if (food.food.equals(meal[i].cbox.text.toString())) {
                                if (swaps.contains(food)) {
                                    it.remove()
                                }
                            }
                        }
                        swaps.add(FoodSwapInfo(1, i, meal[i].cbox.text.toString(), swapOne[x].text.toString(), "", ""))
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
        val inflater: LayoutInflater = LayoutInflater.from(this)
        val promptView: View = inflater.inflate(R.layout.dialog_swap_nuts, null)

        val swapTwo: ArrayList<CheckBox> = getDialog2CheckBoxes(promptView)

        var cambioDos: List<String> = ArrayList()
        when (op) {
            0 -> {
                cambioDos = comMenu.op1[i].cambio.cambioDos
            }
            1 -> {
                cambioDos = comMenu.op2[i].cambio.cambioDos
            }
            2 -> {
                cambioDos = comMenu.op3[i].cambio.cambioDos
            }
            3 -> {
                cambioDos = comMenu.op4[i].cambio.cambioDos
            }
            4 -> {
                cambioDos = comMenu.op5[i].cambio.cambioDos
            }
        }

        for (n in swapTwo.indices) {
            swapTwo[n].setText(cambioDos[n])
        }

        val alBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        alBuilder.setView(promptView)
        alBuilder.setTitle(R.string.label_title_2S)
        alBuilder.setCancelable(false)
        alBuilder.setPositiveButton(R.string.btn_accept, DialogInterface.OnClickListener() { dialog, which ->

            for (x in swapTwo.indices) {
                if (swapTwo[x].isChecked) {
                    if (swaps.size == 0) {
                        swaps.add(FoodSwapInfo(1, i, meal[i].cbox.text.toString(), "", swapTwo[x].text.toString(), ""))
                    } else {
                        val it = swaps.iterator()
                        while (it.hasNext()) {
                            val food: FoodSwapInfo = it.next()
                            if (food.food.equals(meal[i].cbox.text.toString())) {
                                if (swaps.contains(food)) {
                                    it.remove()
                                }
                            }
                        }
                        swaps.add(FoodSwapInfo(1, i, meal[i].cbox.text.toString(), "", swapTwo[x].text.toString(), ""))
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
        val inflater: LayoutInflater = LayoutInflater.from(this)
        val promptView: View = inflater.inflate(R.layout.dialog_swap_meats, null)

        val swapThree: ArrayList<CheckBox> = getDialog3CheckBoxes(promptView)

        var cambioTres: List<String> = ArrayList()
        when (op) {
            0 -> {
                cambioTres = comMenu.op1[i].cambio.cambioTres
            }
            1 -> {
                cambioTres = comMenu.op2[i].cambio.cambioTres
            }
            2 -> {
                cambioTres = comMenu.op3[i].cambio.cambioTres
            }
            3 -> {
                cambioTres = comMenu.op4[i].cambio.cambioTres
            }
            4 -> {
                cambioTres = comMenu.op5[i].cambio.cambioTres
            }
        }

        for (n in swapThree.indices) {
            swapThree[n].setText(cambioTres[n])
        }

        val alBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        alBuilder.setView(promptView)
        alBuilder.setTitle(R.string.label_title_3S)
        alBuilder.setCancelable(false)
        alBuilder.setPositiveButton(R.string.btn_accept, DialogInterface.OnClickListener() { dialog, which ->

            for (x in swapThree.indices) {
                if (swapThree[x].isChecked) {
                    if (swaps.size == 0) {
                        swaps.add(FoodSwapInfo(1, i, meal[i].cbox.text.toString(), "", "", swapThree[x].text.toString()))
                    } else {
                        val it = swaps.iterator()
                        while (it.hasNext()) {
                            val food: FoodSwapInfo = it.next()
                            if (!food.swap3.equals(swaps[i].swap3)) {
                                if (food.food.equals(swaps[i].food)) {
                                    it.remove()
                                }
                            }
                        }
                        Log.w("CheckBox -> toString", meal[i].toString())
                        swaps.add(FoodSwapInfo(1, i, meal[i].cbox.text.toString(), "", "", swapThree[x].text.toString()))
                    }
                    /*if (!meal[i].swap.contains(swapThree[x].text.toString()))
                        meal[i].swap.add(swapThree[x].text.toString())*/
                    //cambiopor.add(swapThree[x].text.toString())
                }
            }
            Log.w("Swap 3 size", meal[i].swap.size.toString())
        })
        alBuilder.setNegativeButton(R.string.btn_cancel, DialogInterface.OnClickListener() { dialog, which ->
            meal[i].swap.clear()
            dialog.cancel()
        })

        return alBuilder.create()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
