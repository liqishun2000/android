package com.example.android.ktx

import kotlinx.coroutines.CancellationException

inline fun <R> runCatchingIgnore(block: () -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (e: Throwable) {
        if (e is CancellationException) throw e
        Result.failure(e)
    }
}

inline fun <T, R> T.runCatchingIgnore(block: T.() -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (e: Throwable) {
        if (e is CancellationException) throw e
        Result.failure(e)
    }
}