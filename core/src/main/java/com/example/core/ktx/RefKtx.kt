package com.example.core.ktx

import kotlin.reflect.KProperty

class Ref<T>(var value:T)

@Suppress("NOTHING_TO_INLINE")
inline operator fun <T> Ref<T>.getValue(thisObj: Any?, property: KProperty<*>): T = value

@Suppress("NOTHING_TO_INLINE")
inline operator fun <T> Ref<T>.setValue(thisObj: Any?, property: KProperty<*>, value: T) {
    this.value = value
}

@Suppress("NOTHING_TO_INLINE")
inline fun <T> T.boxed() = Ref(this)