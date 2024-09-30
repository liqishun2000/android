package com.example.android.training.compose.animation

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.StartOffsetType
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween

private fun animationSpecChildType(){


    var tween = tween<Float>(durationMillis = 1000, delayMillis = 0, easing = FastOutSlowInEasing)

    val keyframeSpec = keyframes {
        durationMillis = 1000
        0f at 0 using LinearEasing
        0.5f at 500 using FastOutSlowInEasing
        1f at 1000 using FastOutLinearInEasing
    }


    var repeatable = repeatable<Float>(
        iterations = 5,
        animation = tween(delayMillis = 1000),
        repeatMode = RepeatMode.Restart,
        initialStartOffset = StartOffset(offsetMillis = 1000, offsetType = StartOffsetType.Delay)
    )


    var infiniteRepeatable = infiniteRepeatable<Float>(
        animation = tween(durationMillis = 1000),
        repeatMode = RepeatMode.Restart,
    )
}