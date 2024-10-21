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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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


    MainView(
        modifier = modifier,
        onClickOperate = {
            context.fFindActivity {
                vm.click(it)
            }
        },
    )
}

@Composable
private fun MainView(
    modifier: Modifier = Modifier,
    onClickOperate: () -> Unit = {},
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Button(onClick = onClickOperate) {
            Text(text = "点击")
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