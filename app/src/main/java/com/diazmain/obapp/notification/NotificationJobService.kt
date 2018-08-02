package com.diazmain.obapp.notification

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import com.diazmain.obapp.home.HomeActivity
import com.diazmain.obapp.R
import com.diazmain.obapp.helper.SharedPrefManager

class NotificationJobService : JobService() {

    @SuppressLint("ServiceCast")
    override fun onStartJob(params: JobParameters?): Boolean {

        val contentPendingIntent: PendingIntent = PendingIntent.getActivity(
                this,
                0,
                Intent(this, HomeActivity::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT)

        val manager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this)
                .setContentTitle(getString(R.string.notification_title_confirm_appoint))
                .setContentText(getString(R.string.notification_defaultTo_content_confirm_appoint) + SharedPrefManager.getInstance(this)!!.getAppoint().fecha)
                .setContentIntent(contentPendingIntent)
                .setSmallIcon(R.drawable.ic_alarm)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true)

        manager.notify(0, builder.build())

        return false
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        return true
    }
}