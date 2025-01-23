package com.example.android.training.compose.nested


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.example.core.ktx.log
import kotlin.math.absoluteValue

@Preview
@Composable
private fun Preview() {
    NestTest()
}

@Composable
fun NestTest(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current

    val screenHeight = LocalConfiguration.current.screenHeightDp
    var nestedScrollHeight = remember {
        0
    }

    var topHeightPx by remember {
        mutableFloatStateOf(0f)
    }


    LaunchedEffect(Unit) {
//        log("topHeightPx:${topHeightPx}")
//        snapshotFlow { topHeightPx }
//            .collect{
//                if(it.absoluteValue>850){
//                    return@collect
//                }
//                scrollState.scrollTo(it.absoluteValue.toInt())
//            }
//        scrollState.animateScrollTo(topHeightPx.toInt())
    }
    LaunchedEffect(topHeightPx) {

                if(topHeightPx.absoluteValue>850){
                    return@LaunchedEffect
                }
                scrollState.scrollTo(topHeightPx.absoluteValue.toInt())
//        scrollState.animateScrollTo(topHeightPx.toInt())
    }


    val connection = remember {
        object : NestedScrollConnection {
            /**
             * 预先劫持滑动事件，消费后再交由子布局。
             * available：当前可用的滑动事件偏移量
             * source：滑动事件的类型
             * 返回值：当前组件消费的滑动事件偏移量，如果不想消费可返回Offset.Zero
             */
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (source == NestedScrollSource.UserInput) {  // 判断是滑动事件
                    val dH = nestedScrollHeight - topHeightPx.absoluteValue  // 向上滑动过程中，还差多少达到最小高度
                    log("available:${available}")
                    if (available.y < 0 && dH > 0) { // 向上滑动

                        if (available.y.absoluteValue < dH) {  // 如果当前可用的滑动距离全部消费都不足以达到最小高度，就将当前可用距离全部消费掉
                            topHeightPx += available.y
                            log("dH:${dH} topHeightPx:$topHeightPx")
                            return Offset(x = 0f, y = available.y)
                        } else {  // 如果当前可用的滑动距离足够达到最小高度，就只消费掉需要的距离。剩余的给到子组件。
                            topHeightPx += dH
                            log("dH:${dH} topHeightPx:$topHeightPx")
                            return Offset(x = 0f, y = dH)
                        }
                        return Offset(x = 0f, y = available.y)
                    }
                    return Offset.Zero
                } else {  // 如果不是滑动事件，就不消费。
                    return Offset.Zero
                }
            }

            /**
             * 获取子布局处理后的滑动事件
             * consumed：之前消费的所有滑动事件偏移量
             * available：当前剩下还可用的滑动事件偏移量
             * source：滑动事件的类型
             * 返回值：当前组件消费的滑动事件偏移量，如果不想消费可返回 Offset.Zero ，则剩下偏移量会继续交由当前布局的父布局进行处理
             */
            override fun onPostScroll(
                consumed: Offset, available: Offset, source: NestedScrollSource
            ): Offset {
                // 子组件处理后的剩余的滑动距离，此处不需要消费了，直接不消费。
                if (source == NestedScrollSource.UserInput) {  // 判断是滑动事件
                    log("available:${available}")
                    val dH = -topHeightPx  // 向下滑动过程中，还差多少达到最大高度
                    if (available.y > 0 && dH > 0) { // 向下滑动

                        if (available.y < dH) {  // 如果当前可用的滑动距离全部消费都不足以达到最大高度，就将当前可用距离全部消费掉
                            topHeightPx += available.y
                            log("dH:${dH} topHeightPx:$topHeightPx")
                            return Offset(x = 0f, y = available.y)
                        } else {  // 如果当前可用的滑动距离足够达到最大高度，就只消费掉需要的距离。剩余的给到子组件。
                            topHeightPx += dH
                            log("dH:${dH} topHeightPx:$topHeightPx")
                            return Offset(x = 0f, y = dH)
                        }
                    }
                    return Offset.Zero
                } else {  // 如果不是滑动事件，就不消费。
                    return Offset.Zero
                }
            }

            override suspend fun onPreFling(available: Velocity): Velocity {
                return super.onPreFling(available)
            }

            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                return super.onPostFling(consumed, available)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .nestedScroll(connection)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(360f / 300)
                .background(Color.Red)
                .onSizeChanged {
                    nestedScrollHeight = it.height
                    log("nestedScrollHeight:$nestedScrollHeight")
                }

        )

        Button(onClick = {
            topHeightPx -= 100
        }) {
            Text(
                text = "Click"
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight.dp*1.1f)
                .navigationBarsPadding()
        ) {


            items(count = 300) { index ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Gray)
                        .height(30.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        color = Color.Black,
                        lineHeight = 1.em,
                        text = index.toString(),
                        modifier = Modifier
                    )
                }

            }
        }

    }
}

