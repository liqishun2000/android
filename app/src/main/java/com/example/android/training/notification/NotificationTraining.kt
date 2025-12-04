package com.example.android.training.notification

/**
 *  PendingIntent.getActivity(
 *             LibNtySdkImpl.app, reqCode, intent,
 *             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT else PendingIntent.FLAG_UPDATE_CURRENT
 *         )
 *  其中reqCode和intent对应，如果reqCode有相同的，那么intent参数会错误
 *  重启进程后单例会被重置
 *  切换语言如果重启进程，reqCode可能会有异常
 *
 *  intent 的flag要注意，有些手机移除activity栈后需要：
 *  Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
 *  否则点击通知栏后 activity栈起不来
 * */