package com.example.android.training.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking

private fun main() = runBlocking {

    trainingDistinctUntilChanged()
}

//region DistinctUntilChanged
private suspend fun trainingDistinctUntilChanged(){
    flowOf(1,1,2,3)
        .distinctUntilChanged()
        .collect{
            println(it)
        }
}
//endregion