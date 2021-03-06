package com.diazmain.obapp.home.fragments

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * @author LuisDixz
 * <p>Adaptador del ViewPager</p>
 */
class HomeFragmentAdapter (fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> HomeDashboardFragment.newInstance()
            1 -> ReminderSummaryFragment.newInstance()
            2 -> ProgressFragment.newInstance()
            3 -> ProfileFragment.newInstance()
            else -> HomeDashboardFragment.newInstance()
        }
    }

    override fun getCount(): Int {
        return 4
    }
}