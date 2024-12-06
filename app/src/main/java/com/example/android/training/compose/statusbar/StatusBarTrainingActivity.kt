package com.example.android.training.compose.statusbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalDensity
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class StatusBarTrainingActivity:ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            //设置状态栏，可以在Theme里面设置
            val systemUiController = rememberSystemUiController()
            SideEffect {
                systemUiController.statusBarDarkContentEnabled = false
            }

            //状态栏高度
            val statusBarHeight = WindowInsets.statusBars.getTop(LocalDensity.current)

            SideEffect {

            }

        }

    }
}