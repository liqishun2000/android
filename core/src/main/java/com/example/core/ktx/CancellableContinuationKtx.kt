package com.sc.qrbar.ktx

import kotlinx.coroutines.CancellableContinuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

fun CancellableContinuation<Unit>.activeResume() {
    this.resume(Unit)
}

fun <T> CancellableContinuation<T>.activeResume(data: T) {
    if (this.isActive) {
        this.resume(data)
    }
}

fun <T> CancellableContinuation<T>.activeResumeWithException(e: Exception = Exception()) {
    if (this.isActive) {
        this.resumeWithException(e)
    }
}