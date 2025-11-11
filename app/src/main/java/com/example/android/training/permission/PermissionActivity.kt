package com.example.android.training.permission

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.android.ui.theme.AndroidTheme
import com.example.core.utils.commonLog

/**
 * android 13开始读取存储需要额外添加权限，例如READ_MEDIA_AUDIO等
 * android 10需要添加android:requestLegacyExternalStorage="true"
 * 才能正常按读取文件。但是可以统一使用对应的api获取对应的文件
 *
 * */
class PermissionActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "World",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->

            if (result) {
                commonLog("权限被授予")
            } else {
                commonLog("权限被拒绝")
            }
        }.also {
            it.launch(android.Manifest.permission.ACTIVITY_RECOGNITION)
        }
    }
}

@Composable
private fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun GreetingPreview() {
    AndroidTheme {
        Greeting("Android")
    }
}