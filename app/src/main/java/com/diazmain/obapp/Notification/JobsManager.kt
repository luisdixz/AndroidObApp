package com.diazmain.obapp.Notification

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Context.JOB_SCHEDULER_SERVICE
import android.widget.Toast

class JobsManager (_context: Context) {


    private var JOB_ID = 0
    private lateinit var mScheduler: JobScheduler
    private val context: Context = _context

    fun scheduleJob(status: Int) {
        mScheduler = context.getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler

        //JOB_ID = status

        val serviceName: ComponentName = ComponentName(context.packageName, NotificationJobService::class.java.name)
        val builder: JobInfo.Builder = JobInfo.Builder(JOB_ID, serviceName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE)
                .setRequiresDeviceIdle(true)
                .setRequiresCharging(false)
                .setPeriodic(1000*60*60*2)
        //        .setPeriodic(1000*60)

        val jobInfo: JobInfo = builder.build()
        mScheduler.schedule(jobInfo)
    }

    fun cancelJobs() {
        //if (mScheduler != null){
            mScheduler = context.getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
            mScheduler.cancelAll()
            //Toast.makeText(context, "jobsCanceled", Toast.LENGTH_SHORT).show()
        //}
    }
}