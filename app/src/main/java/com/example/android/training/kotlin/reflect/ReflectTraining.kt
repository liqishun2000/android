package com.example.android.training.kotlin.reflect

import com.example.core.utils.KClassUtils

fun main() {

    val typeList = KClassUtils.getSealedClassInstances(Type::class)
    println(typeList)
    typeList.forEach {
        println(it.type)
    }
}

sealed class Type(val type: Int)

data object Cat:Type(1)

data object Dog:Type(2)

data object Cattle:Type(3)

data object Sheep:Type(4)