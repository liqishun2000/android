package com.example.android.training.flow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random
import kotlin.random.nextInt

private fun main() = runBlocking {

    trainingDistinctUntilChanged()
}

//region DistinctUntilChanged 与上一个值不一样才发送
private suspend fun trainingDistinctUntilChanged(){
    flowOf(1,1,2,3)
        .distinctUntilChanged()
        .collect{
            println(it)
        }
}
//endregion

//region debounce 防抖，只在指定时间内没有新值时发射
@OptIn(FlowPreview::class)
private suspend fun trainingDebounce(){
    flowOf(1,1,2,3)
        .debounce(1000L)
        .collect{
            println(it)
        }
}
//endregion

//region zip 将两个流按照顺序组合成对。如果两个流的发射速率不同，它会等待两个流都发射了一个值，然后将这两个值组合。直到其中一个流结束，整个组合流就会结束
@OptIn(ExperimentalCoroutinesApi::class)
private suspend fun trainingZip(){
    val flow1 = List(20){ it }.asFlow().flatMapConcat { flow {
        delay(Random.nextInt(1000..2000).toLong())
        emit(it)
    } }
    val flow2 = List(20){ -it }.asFlow().flatMapConcat { flow {
        emit(it)
    } }

    flow1.zip(flow2){ a,b-> a to b }
        .collect {
            println(it)
        }
}
//endregion

//region merge 将多个流合并成一个流，所有流发射的值都会按照实际发射的时间顺序合并到一个流中。注意，merge不会改变流中的值，只是将多个流混合成一个
@OptIn(ExperimentalCoroutinesApi::class)
private suspend fun trainingMerge(){
    val flow1 = List(5){ it }.asFlow().flatMapMerge { flow {
        delay(Random.nextInt(1000..2000).toLong())
        emit(it)
    } }
    val flow2 = List(2){ -it }.asFlow().flatMapConcat { flow {
        emit(it)
    } }

    merge(flow1,flow2)
        .collect {
            println(it)
        }
}
//endregion

//region combine 组合多个流，当任一流发射时重新计算
//endregion

//region flatMapMerge 并发展开流 flatMapConcat 顺序展开流
/** flatMapConcat顺序收集，等待前一个收集完成才收集后一个
 * flatMapMerge 并发收集，所有流一起收集后合并值发送
 * */
@OptIn(ExperimentalCoroutinesApi::class)
private suspend fun trainingFlatMapMerge(){
    List(20){ it }.asFlow()
        .flatMapMerge {
            flow {
                println("start value:$it thread:${Thread.currentThread().name}")
                delay(Random.nextInt(1000..2000).toLong())
                println("value:$it thread:${Thread.currentThread().name}")
                emit(it)
                delay(10)
                emit(-it)
            }
        }.flowOn(Dispatchers.Default)
        .collect {
            println("value:$it thread:${Thread.currentThread().name} ")
        }
}
//endregion
