package com.example.android.training.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

private val ioScope = CoroutineScope(Dispatchers.IO)
private fun ioScope(block:suspend ()->Unit) = ioScope.launch { block.invoke() }

private fun main() {

    trainingSuspendCancellableCoroutine()
}

//region 限制协程并发

//semaphore.withPermit 需要整块运行结束才释放许可
private suspend fun semaphoreTraining() = coroutineScope{
    val semaphore = Semaphore(3) // 最多允许 3 个协程并发执行

    val jobs = (1..10).map { i ->
        launch {
            semaphore.withPermit {
                println("Task $i started  [${Thread.currentThread().name}]")
                delay(1000) // 模拟耗时操作
                println("Task $i finished")
            }
        }
    }

    println("All tasks done")
}

//delay就会释放并行槽位
private suspend fun parallelismTraining() = withContext(Dispatchers.Default.limitedParallelism(3)) {

    val jobs = (1..10).map { i ->
        launch {
            println("Task $i started  [${Thread.currentThread().name}]")
            delay(1000) // 模拟耗时操作
            println("Task $i finished")
        }
    }

    println("All tasks done")
}


//endregion

//region withContext 也会挂起协程等待里面子job全部完成
//endregion

//region suspendCancellableCoroutine
private fun trainingSuspendCancellableCoroutine(){
    runBlocking {
        suspendCancellableCoroutineContent()

        delay(2000)
    }
}

private suspend fun suspendCancellableCoroutineContent(){
    return suspendCancellableCoroutine { continuation ->
        testCallback {
            println("testCallback")
            continuation.resume(Unit)
        }
        continuation.invokeOnCancellation {
            println("continuation.invokeOnCancellation")
        }
    }
}

private fun testCallback(block:()->Unit){
    ioScope {
        delay(1000)
        block.invoke()
    }
}
//endregion

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