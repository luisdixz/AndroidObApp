package com.diazmain.obapp.Notification

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.diazmain.obapp.Home.HomeActivity
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


        Log.w("Location", "onStartJob -> NotificationJobService")
        when (params!!.jobId) {
            0 -> { Log.w("Params", "params = " + 0)}
            1 -> { Log.w("Params", "params = " + 1)}
            2 -> { Log.w("Params", "params = " + 2)}
        }

        manager.notify(0, builder.build())

        return false
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        return true
    }
}