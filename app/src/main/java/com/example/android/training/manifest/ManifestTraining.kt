package com.example.android.training.manifest

/**
 * 官网:
 * https://developer.android.google.cn/guide/topics/manifest/activity-element?hl=zh-cn#autoremrecents
 *
 * 用FLAG_ACTIVITY_NEW_TASK + taskAffinity 可以启动一个新任务栈
 * 该activity添加noHistory autoRemoveFromRecents 可以不显示在最近屏幕
 * noHistory 不会留在任务栈中 autoRemove 自动清除没有activity的任务栈
 * */