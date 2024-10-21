package com.example.android.training.compose.common

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.android.ui.theme.AndroidTheme

class CommonActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidTheme {
                Screen()
            }
        }
    }
}

@Composable
private fun Screen(modifier: Modifier = Modifier) {

    PullToRefreshTrainingScreen()
}