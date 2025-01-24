package com.example.android.training.compose.modifier

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.android.R

@Preview
@Composable
private fun Preview() {
    PointInputTraining()
}

@Composable
fun PointInputTraining(modifier: Modifier = Modifier) {
    var scale by remember { mutableFloatStateOf(1f) }
    var translationX by remember { mutableFloatStateOf(0f) }
    var translationY by remember { mutableFloatStateOf(0f) }
    var imageRotation by remember { mutableFloatStateOf(1f) }
    val transformOrigin by remember { mutableStateOf(TransformOrigin(0.5f,0.5f)) }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .pointerInput(Unit){
                    detectTransformGestures{centroid,pan,zoom,rotation->
                        translationX += pan.x
                        translationY += pan.y
                        scale *= zoom
                        imageRotation += rotation
                    }
                }.graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    this.translationX = translationX
                    this.translationY = translationY
                    rotationZ = imageRotation
                    this.transformOrigin = transformOrigin
                },
            painter = painterResource(R.drawable.head_portrait),
            contentDescription = null
        )

    }

}