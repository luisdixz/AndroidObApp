package com.diazmain.obapp.Reminder.Fragments

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

class CustomViewPager(context: Context, attrs: AttributeSet?) : ViewPager(context, attrs) {

    private var enabled: Boolean?

    init {
        this.enabled = true
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if (this.enabled!!) {
            return super.onTouchEvent(ev)
        }
        return false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (this.enabled!!) {
            return super.onInterceptTouchEvent(ev)
        }
        return false
    }

    public fun setPagingEnabled(enabled: Boolean) {
        this.enabled = enabled
    }

}