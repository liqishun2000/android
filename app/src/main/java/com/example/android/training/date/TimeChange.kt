package com.example.android.training.date

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter

object TimeChange {

    fun registerUpdateTimeReceiver(
        context: Context,
        timeChange: () -> Unit = {},
        timeTick: () -> Unit = {},
    ) {
        val mTimeUpdateReceiver: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent?) {
                if (intent == null) {
                    return
                }
                val action = intent.action
                if (action.isNullOrEmpty()) {
                    return
                }

                if (action == Intent.ACTION_TIME_TICK) {
                    //system every 1 min send broadcast
                    timeTick()
                } else if (action == Intent.ACTION_TIME_CHANGED) {
                    //system hand change time send broadcast
                    timeChange()
                }
            }
        }

        //register time update
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_TIME_TICK)
        filter.addAction(Intent.ACTION_TIME_CHANGED)
        context.registerReceiver(mTimeUpdateReceiver, filter)
    }


}