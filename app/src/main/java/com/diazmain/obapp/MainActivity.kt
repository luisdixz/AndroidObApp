package com.diazmain.obapp

import android.content.res.Resources
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.ViewPager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {
    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        val currentItem = bottMenu.menu.getItem(position).itemId
        if (currentItem != bottMenu.selectedItemId) {
            bottMenu.menu.getItem(position).isChecked = true
            bottMenu.menu.findItem(bottMenu.selectedItemId).isChecked = false
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottMenu.setOnNavigationItemSelectedListener {
            selectItem(it)
        }

        val pageAdapter = HomeFragmentAdapter(supportFragmentManager)
        myViewPager.adapter = pageAdapter
        myViewPager.addOnPageChangeListener(this)
    }

    private fun selectItem(item: MenuItem) : Boolean {
        when (item.itemId) {
            R.id.action_home -> myViewPager.currentItem = 0
            R.id.action_profile -> myViewPager.currentItem = 1
            R.id.action_progress -> myViewPager.currentItem = 2
            else -> myViewPager.currentItem = 0
        }

        return true
    }

    fun goPerfil(view: View) {

    }
}
