package com.example.android.training.compose.lottie

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

/** 参数 */
const val ANIME_SCAN_IMAGES = "anime_scan/images"
const val ANIME_SCAN_JSON = "anime_scan/data.json"

@Composable
fun CustomLottie(
    asset: String,
    modifier: Modifier = Modifier,
    imageAssetsFolder: String = "",
) {
    val compositionResult =
        rememberLottieComposition(LottieCompositionSpec.Asset(asset),imageAssetsFolder)
    val progress by animateLottieCompositionAsState(
        composition = compositionResult.value,
        isPlaying = true,
        iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        composition = compositionResult.value,
        progress = { progress },
        modifier = modifier,
    )
}