package com.diazmain.obapp.Reminder.Fragments

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class ReminderFragmentAdapter (fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> DesayunoFragment.newInstance()
            1 -> Colacion1Fragment.newInstance()
            2 -> ComidaFragment.newInstance()
            3 -> Colacion2Fragment.newInstance()
            4 -> CenaFragment.newInstance()
            else -> DesayunoFragment.newInstance()
        }
    }

    override fun getCount(): Int {
        return 5
    }
}