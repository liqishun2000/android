package com.example.android.training.coroutine

import com.example.android.ktx.runCatchingIgnore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


private fun main() {

    trainingSimplifyCallback()
}

//region 异步回调简化
private fun trainingSimplifyCallback() {

    val job = CoroutineScope(Dispatchers.IO).launch {

        //注意协程取消异常，正常要抛出去
        val firstResult = runCatching {
            println("开始第一步")
            simplifyCallback("first")
        }


        val firstFold = firstResult.fold({
            //成功
            println("$it Success")
            it
        }, {
            //失败
            println("$it fail")
            null
        })

        println("firstFold:$firstFold")

        val secondResult = firstFold?.runCatching {
            println("开始第二步")
            simplifyCallback("$this Completed, Second")
        }

        val secondFold = secondResult?.fold({
            println("$it Success")
            it
        }, {
            println("$it fail")
            null
        })

        println("$secondFold Completed")
    }



    runBlocking {
        coroutineScope {
            //取消测试
//            delay(500)
//            job.cancel()
            delay(5_000)
        }
    }
}

private suspend fun simplifyCallback(info: String): String {
    return suspendCancellableCoroutine { continuation ->
        callbackDelay(info) {
            continuation.resume(info)
        }
    }
}

private fun callbackDelay(info: String, callback: (String) -> Unit) {
    CoroutineScope(Dispatchers.Default).launch {
        delay(1000)
        callback(info)
    }
}
//endregion

//region 启动方式
private fun trainingCoroutineLaunch() {

    println("开始runBlocking")
    //runBlocking是一个桥接函数，用于在协程的上下文中阻塞当前线程，直到所有的协程完成。
    runBlocking {
        val flow = flow {
            for (i in 0 until 3) {
                emit(i)
                delay(1000)
            }
        }

        val suspendCancellableCoroutine = suspendCancellableCoroutine { continuation ->
            println("挂起协程")
            launch {
                try {
                    println("挂起协程:启动")
                    // 模拟异步操作
                    delay(1000)
                    // 成功完成任务
                    continuation.resume("Success")
                } catch (e: Exception) {
                    // 处理异常并恢复
                    continuation.resumeWithException(e)
                }
            }
        }
        println("suspendCancellableCoroutine:$suspendCancellableCoroutine")


        //launch函数用于启动一个新的协程，并且它返回一个Job对象，表示协程的工作。
        launch {
            for (i in 0 until 10) {
                print("开始执行launch")
            }
            println()
            delay(1000)
            println("launch结束")
        }

        //async函数用于启动一个新的协程，并且它返回一个Deferred对象，可以用来获取协程的结果。await挂起当前协程
        val asyncResult = async {
            println("开始执行async")
            delay(1000)
            "asyncResult"
        }
        println("准备打印asyncResult")
        println(asyncResult.await())
        println("asyncResult打印结束")

        //coroutineScope用于在一个新的协程中创建一个作用域，它会等待所有子协程完成
        coroutineScope {
            println("开始执行coroutineScope")
            flow.collect { i ->
                println("collect数据：$i")
            }
        }
        println("结束runBlocking")
    }



    println("main结束")
}
//endregion