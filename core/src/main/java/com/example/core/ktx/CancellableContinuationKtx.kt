package com.example.core.ktx

import kotlinx.coroutines.CancellableContinuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

fun CancellableContinuation<Unit>.activeResume() {
    this.activeResume(Unit)
}

fun <T> CancellableContinuation<T>.activeResume(data: T) {
    if (this.isActive) {
        this.resume(data)
    }
}

fun <T> CancellableContinuation<T>.activeResumeWithException(e: Throwable = Exception()) {
    if (this.isActive) {
        this.resumeWithException(e)
    }
}