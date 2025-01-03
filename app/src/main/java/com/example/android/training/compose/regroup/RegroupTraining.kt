package com.example.android.training.compose.regroup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android.ktx.log
import com.example.android.training.compose.regroup.vm.RegroupVM

@Composable
fun RegroupScreen(
    modifier: Modifier = Modifier,
    vm: RegroupVM = viewModel()
) {
    val state by vm.stateFlow.collectAsStateWithLifecycle()

    Start(
        progress = { state.intValue },
        str = state.str,
        updateProgress = { vm.addIntValue() },
        modifier = modifier,
    )
}

@Preview
@Composable
private fun Preview() {

    Start(
        progress = { 1 },
        updateProgress = { }
    )
}

@Composable
private fun Start(
    progress: ()->Int,
    updateProgress: () -> Unit,
    modifier: Modifier = Modifier,
    str:String = "",
) {
    log("Start regroup")

    RegroupTraining(
        progress = progress,
        updateProgress = updateProgress,
        modifier = modifier,
    )
}

@Composable
private fun RegroupTraining(
    progress: ()->Int,
    updateProgress: () -> Unit,
    modifier: Modifier = Modifier,
) {
    log("RegroupTraining regroup")

    Box(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
    ) {
        Button(
            onClick = { updateProgress() },
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Text(
                text = "click"
            )
        }

        Second(
            modifier = Modifier.align(Alignment.Center),
            progress = progress
        )
    }

}

@Composable
private fun Second(
    modifier: Modifier = Modifier,
    progress: () -> Int,
) {
    log("second regroup")

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        contentAlignment = Alignment.Center
    ) {
        Final(
            modifier = Modifier,
            progress = progress
        )

    }
}

@Composable
private fun Final(
    modifier: Modifier = Modifier,
    progress: () -> Int,
) {
    log("Final regroup")

    Text(
        modifier = modifier,
        text = progress().toString(),
        color = Color.Black,
        fontSize = 20.sp
    )
}