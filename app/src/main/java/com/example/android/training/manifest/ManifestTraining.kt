package com.example.android.training.manifest

/**
 * 官网:
 * https://developer.android.google.cn/guide/topics/manifest/activity-element?hl=zh-cn#autoremrecents
 *
 * 用FLAG_ACTIVITY_NEW_TASK + taskAffinity 可以启动一个新任务栈
 * 该activity添加noHistory autoRemoveFromRecents 可以不显示在最近屏幕
 * noHistory 不会留在任务栈中 autoRemove 自动清除没有activity的任务栈
 *
 * 即使activity调用finish,这个任务栈也还是会显示在最近屏幕。加了autoRemove后，
 * 最后一个activity finish后会清除该任务栈。
 *
 * 如果是最后一个任务栈(主页)，最后一个activity(MainActivity) finish后会显示在
 * 最近屏幕。点击后会恢复，但是该栈没有activity,所以会从启动页开始走。
 * */