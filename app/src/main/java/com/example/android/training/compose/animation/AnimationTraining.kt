package com.example.android.training.compose.animation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

/**
 *
 *
 * Crossfade() 切换控件动画
 * */

@Preview(backgroundColor = 0xffffffff, showBackground = true)
@Composable
private fun Preview() {
//    Test()
//    TrainingCustomAnimatedContent()
    TrainingCustomTransition()
}

@Composable
private fun Test(modifier: Modifier = Modifier) {
    val density = LocalDensity.current

    val infiniteTransition = rememberInfiniteTransition("infinite")
    val animateFloat = infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1_000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,
        ), label = "infiniteFloat"
    )

    val animateScale = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1_000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,
        ), label = "infiniteFloat"
    )






    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Button(
            modifier = Modifier.align(Alignment.Center),
            onClick = { },
        ) {
            Text(text = "text")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = 200.dp)
                .fillMaxWidth()
                .background(Color.Gray)
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {

            Box(
                modifier = Modifier
                    .size(50.dp)
                    .graphicsLayer {
                        scaleX = animateFloat.value
                        scaleY = animateFloat.value
                        alpha = animateScale.value
                    }
                    .background(Color.Red)
            ) {
                Text(
                    text = "Content"
                )

            }


        }


    }
}

//基于价值的动画

//region rememberInfiniteTransition 永久循环动画
@Composable
fun InfiniteAnimationTraining(modifier: Modifier = Modifier) {
    var isVisible by remember { mutableStateOf(false) }

    val density = LocalDensity.current

    val infiniteTransition = rememberInfiniteTransition(label = "")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),  // 1秒完成一次动画
            repeatMode = RepeatMode.Reverse  // 反向重复，使动画来回进行
        ), label = ""
    )

    val animateState by animateFloatAsState(
        targetValue = if (isVisible) 0.8f else 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        ),
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
            contentAlignment = Alignment.Center
        ) {


            Box(
                modifier = Modifier
                    .size(200.dp)
                    .scale(scale)
                    .background(Color.Green)
            ) {
                Box(
                    Modifier
                        .size(50.dp)
                        .background(Color.Red)
                )

                Text(
                    text = "Hello, I am visible!",
                    modifier = Modifier
                        .padding(16.dp)
                )
            }


        }


    }
}
//endregion

//region animate*AsState + graphicsLayer动画 (内部也是Animatable实现)
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
                    .graphicsLayer(translationX = with(density) { animateState.toPx() })
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
            offset.snapTo(-300f)
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

//region Transition 并发动画
@Composable
private fun TrainingCustomTransitionEffect(modifier: Modifier = Modifier) {
    //使用协程 API (Animatable#animateTo() 或 animate) 或 Transition API 实现并发动画
    //在协程上下文中使用多个启动函数，系统会同时启动动画：
    //e.g.:
    //LaunchedEffect("animationKey") {
    //    launch {
    //        alphaAnimation.animateTo(1f)
    //    }
    //    launch {
    //        yAnimation.animateTo(100f)
    //    }
    //}

    var restart by remember { mutableStateOf(false) }

    val scaleAnim = remember { Animatable(1f) }
    val alphaAnim = remember { Animatable(1f) }

    LaunchedEffect(restart) {
        if(restart){
            launch {
                scaleAnim.animateTo(1f, animationSpec = tween(durationMillis = 2_000))
            }

            launch {
                alphaAnim.animateTo(1f, animationSpec = tween(durationMillis = 2_000))
            }
        }else{
            scaleAnim.snapTo(0.5f)
            alphaAnim.snapTo(0.1f)
        }
    }


    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Button(
            modifier = Modifier.align(Alignment.Center),
            onClick = { restart = !restart },
        ) {
            Text(text = "restart")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = 200.dp)
                .fillMaxWidth()
                .background(Color.Gray)
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {

            Box(
                modifier = Modifier
                    .size(50.dp)
                    .graphicsLayer {
                        scaleX = scaleAnim.value
                        scaleY = scaleAnim.value
                        alpha = alphaAnim.value
                    }
                    .background(Color.Red)
            ) {
                Text(
                    text = "Content"
                )

            }


        }


    }
}

@Composable
private fun TrainingCustomTransition(modifier: Modifier = Modifier) {
    //使用协程 API (Animatable#animateTo() 或 animate) 或 Transition API 实现并发动画

    var restart by remember { mutableStateOf(false) }

    val updateTransition = updateTransition(restart, label = "transition")
    val alphaAnim by updateTransition.animateFloat(transitionSpec = {tween(durationMillis = 1000, easing = LinearEasing)}, label = "float") { state->
        if(state){
            1f
        }else{
            0f
        }
    }
    val scaleAnim by updateTransition.animateFloat(transitionSpec = {tween(durationMillis = 1000, easing = LinearEasing)}, label = "scale") { state ->
        if(state){
            1f
        }else{
            0.5f
        }
    }



    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Button(
            modifier = Modifier.align(Alignment.Center),
            onClick = { restart = !restart },
        ) {
            Text(text = "restart")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = 200.dp)
                .fillMaxWidth()
                .background(Color.Gray)
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {

            Box(
                modifier = Modifier
                    .size(50.dp)
                    .graphicsLayer {
                        scaleX = scaleAnim
                        scaleY = scaleAnim
                        alpha = alphaAnim
                    }
                    .background(Color.Red)
            ) {
                Text(
                    text = "Content"
                )

            }


        }


    }
}
//endregion

//动画修饰符

//region AnimatedVisibility 自定义动画 控制显隐
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

//region AnimatedContent 控制组件切换
@Composable
private fun TrainingCustomAnimatedContent(modifier: Modifier = Modifier) {

    var tab by remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Button(
            modifier = Modifier.align(Alignment.Center),
            onClick = { tab = (tab + 1) % 3 },
        ) {
            Text(text = tab.toString())
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = 200.dp)
                .fillMaxWidth()
                .background(Color.Gray)
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            AnimatedContent(
                modifier = Modifier.fillMaxWidth(),
                targetState = tab,
                label = "Tab",
                transitionSpec = {
                    (
                            slideInHorizontally(
                                //0为中点
                                initialOffsetX = { fullWidth -> fullWidth / 2 }
                            ) + scaleIn() + fadeIn()
                            ).togetherWith(
                            slideOutHorizontally(
                                targetOffsetX = { fullWidth -> -fullWidth / 2 }
                            ) + scaleOut() + fadeOut()
                        )

                }
            ) { targetState ->

                when (targetState) {
                    0 -> Text(
                        text = targetState.toString(),
                        modifier = Modifier
                            .requiredSize(50.dp)
                            .background(Color.Red)
                    )

                    1 -> Text(
                        text = targetState.toString(),
                        modifier = Modifier
                            .requiredSize(50.dp)
                            .background(Color.Red)
                    )

                    2 -> Text(
                        text = targetState.toString(),
                        modifier = Modifier
                            .requiredSize(50.dp)
                            .background(Color.Red)
                    )
                }
            }

        }


    }
}
//endregion

//region animateContentSize() 控制组件大小调整
//endregion




