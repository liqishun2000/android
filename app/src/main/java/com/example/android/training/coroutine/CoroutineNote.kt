package com.example.android.training.coroutine

/**
 * suspendCancellableCoroutine 挂起协程等待恢复
 * 如果被挂起的协程已经取消，那么CancellableContinuation.isActive = false
 * resume可以调用，但是没有反应。
 * */