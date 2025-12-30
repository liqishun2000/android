package com.example.android.training.coroutine

/**
 * suspendCancellableCoroutine 挂起协程等待恢复
 * 如果被挂起的协程已经取消，那么CancellableContinuation.isActive = false
 * resume可以调用，但是没有反应。
 *
 * 如果没有可取消的方法，那么即使外面的协程被取消了，里面没有做处理的话也还是会继续运行代码。
 * suspend方法中有一个coroutineContext.isActive可看是否存活
 * */