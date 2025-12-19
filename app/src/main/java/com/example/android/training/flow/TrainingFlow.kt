package com.example.android.training.flow

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * 冷流是一个在没有收集者时不会产生任何数据的流,
 * 在观察者（收集者）订阅（或收集）时，流才开始发射数据。每次新的收集者订阅时，都会重新执行流的代码.
 * 每个收集者都获得自己的数据流，互不影响。即使多次收集，流的逻辑会独立执行，因此每个收集者都会收到独立的数据.
 *
 * 热流是一个在生成数据时始终处于活动状态的流。即使没有消费者（收集者），流依然可能继续发射数据.
 * 数据会在流中持续发射，一旦有收集者加入，流立即将当前状态发送给他们，而不是重新执行流的逻辑.
 * 所有的消费者共享同一个数据流，收集者接收的是相同的数据，比如事件或状态更新.
 *
 *  StateFlow有set/get方法，必须有一个初值 SharedFlow没有初值，可以保留历史数据
 * */

private fun main() {


    trainingSharedFlow()
}


//region channelFlow 创建支持背压的流，如果collect未处理完，那么发送方将挂起。且其支持在不同协程发送
private suspend fun trainingChannelFlow(){
    channelFlow {
        launch {
            for (i in 0 until 10) {
                delay(10)
                send("launch i:$i")
            }
        }
        send("1")
        launch {
            for (i in 0 until 10) {
                send("launch2:$i")
            }
        }
        send("over")
    }.collect {
        delay(1000)
        println(it)
    }
}
//endregion

//region callbackFlow 多次回调转流，例：事件回调
private fun trainingCallbackFlow() = callbackFlow {
//    val callback = object : Callback {
//        override fun onNext(value: String) {
    trySend(Unit)
//        }
//        override fun onComplete() {
//            close()
//        }
//    }
//    registerCallback(callback)
    awaitClose {
//        unregisterCallback(callback)
    }
}

//endregion

//region flow代码简单实现
private fun testCollect() {
    testScope(
        {
            onSuccess(1)
        },
        object : MyScope {
            override fun onSuccess(i: Int) {
                println(i)
            }
        },
    )
}

private interface MyScope {

    fun onSuccess(i: Int)
}

private fun testScope(block: MyScope.() -> Unit, scope: MyScope) {
    scope.block()
}
//endregion

//region snapShotFlow,collect后的代码不会执行，里面死循环。snapShotFlow只能观察State对象。
//endregion

//region 冷流flow 每次collect都会重新执行flow代码 互相独立 同一个协程内，第一个collect执行完毕才会执行后面的代码
private fun trainingFlow() {

    runBlocking {
        println("启动 runBlocking")

        val flow = flow<Int> {
            println("开始执行流")

            for (i in 0 until 3) {
                println("发射流$i")
                emit(i)
                delay(1000)
            }
        }


        println("挂起 runBlocking 准备启动 coroutineScope")
        coroutineScope {
            println("启动 coroutineScope")
            launch {
                flow.collect { i ->
                    println("collect: 接收流$i")
                }

            }

            flow.collect { i ->
                println("collect2: 接收流$i")
            }

            flow.collect { i ->
                println("collect3: 接收流$i")
            }


        }

        println("结束 runBlocking")
    }
}
//endregion

//region 热流StateFlow 所有消费者都是同一个数据流。StateFlow 必须有一个初始状态 collect后会一直挂起当前协程。立即接受数据
private fun trainingStateFlow() {
    runBlocking {
        println("启动 runBlocking")

        val stateFlow = MutableStateFlow(0)

        launch {
            for (i in 0 until 3) {
                delay(1000)
                println("更新状态")
                stateFlow.value++
            }
        }


        println("挂起 runBlocking 准备启动 coroutineScope")
        coroutineScope {
            println("启动 coroutineScope")

            launch {
                stateFlow.collect { i ->
                    println("collect1: 接收流$i")
                }
            }

            launch {
                stateFlow.collect { i ->
                    println("collect2: 接收流$i")
                }
            }

            launch {
                stateFlow.collect { i ->
                    println("collect3: 接收流$i")
                }
            }

        }

        println("结束 runBlocking")
    }

}
//endregion

//region SharedFlow 所有消费者都是同一个数据流。SharedFlow collect后会一直挂起当前协程。默认不保存数据，则没有数据
private fun trainingSharedFlow() {
    runBlocking {
        println("启动 runBlocking")

        val sharedFlow = MutableSharedFlow<Int>()

        launch {
            for (i in 0 until 3) {
                delay(1000)
                println("更新状态")
                sharedFlow.emit(i + 1)
            }
        }


        println("挂起 runBlocking 准备启动 coroutineScope")
        coroutineScope {
            println("启动 coroutineScope")

            launch {
                sharedFlow.collect { i ->
                    println("collect1: 接收流$i")
                }
            }

            launch {
                sharedFlow.collect { i ->
                    println("collect2: 接收流$i")
                }
            }

            launch {
                sharedFlow.collect { i ->
                    println("collect3: 接收流$i")
                }
            }

        }

        println("结束 runBlocking")
    }
}
//endregion


