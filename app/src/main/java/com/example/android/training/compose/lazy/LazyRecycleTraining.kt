package com.example.android.training.compose.lazy

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.android.R
import com.example.core.ktx.log

/**
 *
 * LazyColumn pin后取消回收
 * */
@Preview
@Composable
private fun Preview() {
    LazyRecycleTraining()
}

@Composable
private fun LazyRecycleTraining(modifier: Modifier = Modifier) {

    LazyColumn(modifier = Modifier.fillMaxSize()) {

        item {
            pin()

            Image(
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
                painter = painterResource(R.drawable.head_portrait),
                contentDescription = null
            )

            DisposableEffect(Unit) {
                onDispose {
                    log("dispose:head")
                }
            }
        }

        items(100) { index ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "text:$index"
                )
            }

            if(index%10 == 0){
                pin()
            }

            DisposableEffect(Unit) {
                onDispose {
                    log("dispose:$index")
                }
            }
        }

    }
}