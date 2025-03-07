package com.example.core.utils

import kotlin.reflect.KClass

object KClassUtils {

    fun <T : Any> getSealedClassInstances(sealedClass: KClass<T>): List<T> {
        return sealedClass.sealedSubclasses
            .mapNotNull { subclass ->
                when {
                    subclass.objectInstance != null -> subclass.objectInstance // 单例对象
                    subclass.constructors.any { it.parameters.isEmpty() } -> {
                        subclass.constructors.first { it.parameters.isEmpty() }.call()
                    }
                    else -> null // 无法实例化有参构造的类
                }
            }
    }

    fun <T : Any> getSealedSubclasses(sealedClass: KClass<T>): List<KClass<out T>> {
        return sealedClass.sealedSubclasses
    }
}