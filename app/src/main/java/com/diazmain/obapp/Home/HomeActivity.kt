package com.diazmain.obapp.Home

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.MenuItem
import com.diazmain.obapp.Home.Fragments.HomeFragmentAdapter
import com.diazmain.obapp.Login.SplashScreen
import com.diazmain.obapp.Login.helper.SharedPrefManager
import com.diazmain.obapp.R
import kotlinx.android.synthetic.main.activity_home.*

/**
 * @author LuisDixz
 * <p>Clase principal, sirve de contenedor para los fragment.<br>Se hace uso del componente ViewPager para cambiar de fragment</p>
 */
class HomeActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {
    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    /**
     * Método para cambiar el icono seleccionado por el fragment en el que se encuentra el usuario actualmente
     */
    override fun onPageSelected(position: Int) {
        val currentItem = bottMenu.menu.getItem(position).itemId
        if (currentItem != bottMenu.selectedItemId) {
            bottMenu.menu.getItem(position).isChecked = true
            bottMenu.menu.findItem(bottMenu.selectedItemId).isChecked = false
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        /*if (!SharedPrefManager.getInstance(this)?.isLoggedIn()!!) {
            finish()
            startActivity(Intent(this, SplashScreen::class.java))
        }*/

        bottMenu.setOnNavigationItemSelectedListener {
            selectItem(it)
        }

        val pageAdapter = HomeFragmentAdapter(supportFragmentManager)
        myViewPager.adapter = pageAdapter
        myViewPager.addOnPageChangeListener(this)
    }

    //Método para cambiar de fragment a través del ViewPager
    private fun selectItem(item: MenuItem) : Boolean {
        when (item.itemId) {
            R.id.action_home -> myViewPager.currentItem = 0
            R.id.action_profile -> myViewPager.currentItem = 1
            R.id.action_progress -> myViewPager.currentItem = 2
            else -> myViewPager.currentItem = 0
        }

        return true
    }
}
