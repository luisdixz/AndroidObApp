package com.diazmain.obapp

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class HomeFragmentAdapter (fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> HomeDashboardFragment.newInstance()
            1 -> Perfil.newInstance()
            2 -> ProgressFragment.newInstance()
            else -> HomeDashboardFragment.newInstance()
        }
    }

    override fun getCount(): Int {
        return 3
    }
}