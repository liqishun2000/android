package com.example.android.training.compose.animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(backgroundColor = 0xffffffff, showBackground = true)
@Composable
private fun Preview() {
    Test()
}

@Composable
private fun Test(modifier: Modifier = Modifier) {

    var isVisible by remember { mutableStateOf(false) }

    val density = LocalDensity.current

    val animateState by animateDpAsState(
        targetValue = if (isVisible) 0.dp else (-300).dp,
        label = "animateStateTest",
    )

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Button(
            modifier = Modifier.align(Alignment.Center),
            onClick = { isVisible = !isVisible },
        ) {
            Text(text = if (isVisible) "Hide" else "Show")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = 200.dp)
                .fillMaxWidth()
                .background(Color.Gray)
                .height(200.dp),
        ) {


            Text(
                text = "Hello, I am visible!",
                modifier = Modifier
                    .padding(16.dp)
//                    .offset(x = animateState)
                    .graphicsLayer(translationX = with(density){animateState.toPx()})
            )

        }


    }
}

//region animate*AsState + graphicsLayer动画
@Composable
private fun TrainingCustomAnimateAsState(modifier: Modifier = Modifier) {

    var isVisible by remember { mutableStateOf(false) }

    val density = LocalDensity.current

    val animateState by animateDpAsState(
        targetValue = if (isVisible) 0.dp else (-300).dp,
        label = "animateStateTest",
    )

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Button(
            modifier = Modifier.align(Alignment.Center),
            onClick = { isVisible = !isVisible },
        ) {
            Text(text = if (isVisible) "Hide" else "Show")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = 200.dp)
                .fillMaxWidth()
                .background(Color.Gray)
                .height(200.dp),
        ) {


            Text(
                text = "Hello, I am visible!",
                modifier = Modifier
                    .padding(16.dp)
//                    .offset(x = animateState)
                    .graphicsLayer(translationX = with(density){animateState.toPx()})
            )

        }


    }
}
//endregion

//region Animatable() + graphicsLayer动画
@Composable
private fun TrainingCustomAnimatable(modifier: Modifier = Modifier) {

    var isVisible by remember { mutableStateOf(false) }

    val offset = remember {
        Animatable(-300f)
    }

    LaunchedEffect(isVisible) {
        if (isVisible) {
            offset.animateTo(0f)
        } else {
            offset.animateTo(-300f)
        }

    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Button(
            modifier = Modifier.align(Alignment.Center),
            onClick = { isVisible = !isVisible },
        ) {
            Text(text = if (isVisible) "Hide" else "Show")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = 200.dp)
                .fillMaxWidth()
                .background(Color.Gray)
                .height(200.dp),
        ) {


            Text(
                text = "Hello, I am visible!",
                modifier = Modifier
                    .padding(16.dp)
                    .graphicsLayer(translationX = offset.value)

            )

        }


    }
}
//endregion

//region AnimatedVisibility 自定义动画
@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun TrainingCustomAnimatedVisibility(modifier: Modifier = Modifier) {

    var isVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Button(
            modifier = Modifier.align(Alignment.Center),
            onClick = { isVisible = !isVisible },
        ) {
            Text(text = if (isVisible) "Hide" else "Show")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = 200.dp)
                .fillMaxWidth()
                .background(Color.Gray)
                .height(200.dp),
        ) {
            AnimatedVisibility(
                visible = isVisible,
                enter = EnterTransition.None, // 可以自定义进入动画
                exit = ExitTransition.None // 可以自定义退出动画
            ) {
                Text(
                    text = "Hello, I am visible!",
                    modifier = Modifier
                        .padding(16.dp)
                        .animateEnterExit(
                            enter = fadeIn(animationSpec = tween(durationMillis = 500)),
                            exit = fadeOut(animationSpec = tween(durationMillis = 500))
                        )
                )
            }

        }


    }
}
//endregion

