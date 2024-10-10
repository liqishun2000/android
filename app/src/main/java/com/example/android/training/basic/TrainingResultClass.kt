package com.example.android.training.basic

import com.sd.lib.retry.fRetry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

private fun main() {

    trainingResultClass()

    //阻塞线程
    runBlocking {
        coroutineScope {
            delay(3000)
        }
    }
}

private fun trainingResultClass() {
    CoroutineScope(Dispatchers.IO).launch {

        val runCatching = runCatching {
            simplifyCallback("my")
        }

        //region 同步回调写法

        //ex. 1 单个步骤
        runCatching.onFailure {
            //失败

        }.onSuccess {
            //成功

        }

        //ex. 2 单个步骤
        runCatching.getOrNull()?.run {
            //成功
        } ?: run {
            //失败
        }


        //ex. 3 多个步骤
        val fold = runCatching.fold({
            //成功返回数据
            1
        }, {
            //失败 协程取消异常需要额外处理 直接抛出
            null
        })

        val second = fold?.runCatching {
            simplifyCallback(this.toString())
        }

        val secondResult = second?.fold({
            //成功
            1
        }, {
            //失败
            null
        })
        //endregion

        //region 数据类型转换
        runCatching.map {

        }

        runCatching.mapCatching { }

        runCatching.recover { }

        runCatching.recoverCatching { }
        //endregion

        //region 其他方法
        val orDefault = runCatching.getOrDefault(Any())

        val orElse = runCatching.getOrElse {
            println(it)
            Any()
        }

        runCatching.getOrThrow()
        //endregion

    }
}

//简化回调
private suspend fun simplifyCallback(info: String): String {
    return suspendCancellableCoroutine { continuation ->
        callbackDelay(info) {
            continuation.resumeWithException(Exception(info))
        }
    }
}

//延时后回调出去
private fun callbackDelay(info: String, callback: (String) -> Unit) {
    CoroutineScope(Dispatchers.Default).launch {
        delay(1000)
        callback(info)
    }
}