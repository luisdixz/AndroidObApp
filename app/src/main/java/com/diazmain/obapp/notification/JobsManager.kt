package com.diazmain.obapp.notification

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Context.JOB_SCHEDULER_SERVICE
import com.diazmain.obapp.helper.SharedPrefManager
import java.text.SimpleDateFormat

class JobsManager (_context: Context) {


    private var JOB_ID = 0
    private lateinit var mScheduler: JobScheduler
    private val context: Context = _context
    private val dayFormat: SimpleDateFormat = SimpleDateFormat("dd")

    fun scheduleJob(status: Int) {
        mScheduler = context.getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler

        val appoint: String = SharedPrefManager.getInstance(context)!!.getAppoint().fecha
        var appointDay: String = ""
        var diffDays: Int = 0
        if (!appoint.isEmpty()) {
            appointDay = SharedPrefManager.getInstance(context)!!.getAppoint().fecha.split("-")[2]
            diffDays = Integer.parseInt(appointDay) - Integer.parseInt(dayFormat.format(System.currentTimeMillis()))

        }
        val serviceName: ComponentName = ComponentName(context.packageName, NotificationJobService::class.java.name)
        val builder: JobInfo.Builder = JobInfo.Builder(JOB_ID, serviceName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE)
                .setRequiresDeviceIdle(true)
                .setRequiresCharging(false)
         //       .setPeriodic(1000*60*60*2)
                .setPeriodic(1000*15)
        //        .setMinimumLatency(1000*15)

        if (diffDays< 3 && diffDays>0) {
            val jobInfo: JobInfo = builder.build()
            mScheduler.schedule(jobInfo)
        } else {
            cancelJobs()
        }
    }

    fun cancelJobs() {
            mScheduler = context.getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
            mScheduler.cancelAll()
    }
}