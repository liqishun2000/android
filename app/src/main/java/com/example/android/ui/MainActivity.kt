package com.example.android.ui

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android.ui.theme.AndroidTheme
import com.example.android.ui.viewmodel.MainVM
import com.sd.lib.ctx.fFindActivity


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Composable
private fun MainScreen(
    modifier: Modifier = Modifier,
    vm: MainVM = viewModel()
) {
    val state by vm.stateFlow.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        context.fFindActivity {
            vm.observe(it)
        }
    }

    MainView(
        modifier = modifier,
        onClickOperate = {
            context.fFindActivity {
                vm.click(it)
            }
        },
        onClickAdd = {
            context.fFindActivity {
                vm.clickAdd(it)
            }
        },
        onClickDelete = {
            context.fFindActivity {
                vm.clickDelete(it)
            }
        },
        onClickUpdate = {
            context.fFindActivity {
                vm.clickUpdate(it)
            }
        },
        onClickQuery = {
            context.fFindActivity {
                vm.clickQuery(it)
            }
        }
    )
}

@Composable
private fun MainView(
    modifier: Modifier = Modifier,
    onClickOperate: () -> Unit = {},
    onClickAdd: () -> Unit = {},
    onClickDelete: () -> Unit = {},
    onClickUpdate: () -> Unit = {},
    onClickQuery: () -> Unit = {},
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        val density = LocalDensity.current
        val context = LocalContext.current

        Button(onClick = onClickOperate) {
            Box {
                Text(
                    text = "点击",
                    color = Color.Red,
                    style = LocalTextStyle.current.copy(
                        drawStyle = Stroke(
                            width = with(density) { 2.dp.toPx() },
                            join = StrokeJoin.Round
                        ),
                        shadow = Shadow(
                            color = Color.Red,
                            blurRadius = with(density) { 1.dp.toPx() }
                        )
                    ),
                )
                Text(text = "点击")
            }
        }

        Box(
            modifier = Modifier
                .background(Color.Gray)
                .padding(20.dp)
        ) {
            Text(text = "content")
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(Color.LightGray)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                Button(onClick = onClickAdd) {
                    Text(text = "add")
                }

                Button(onClick = onClickDelete) {
                    Text(text = "delete")
                }

                Button(onClick = onClickUpdate) {
                    Text(text = "update")
                }

                Button(onClick = onClickQuery) {
                    Text(text = "query")
                }

            }

        }

    }

}

@Preview(showBackground = true)
@Composable
private fun GreetingPreview() {
    AndroidTheme {
        MainView()
    }
}