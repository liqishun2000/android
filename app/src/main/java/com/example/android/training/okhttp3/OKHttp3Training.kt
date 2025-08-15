package com.example.android.training.okhttp3

import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException

/**
 *  okhttp内部有一个Dispatch管理一个线程池
 *  用enqueue方法调用默认线程池发起网络请求
 *  用execute方法则用当前线程发起网络请求
 * */


//region 简单使用
private fun sendPostRequestAsync(callback:(String?)-> Unit){
    val okHttpClient = OkHttpClient().newBuilder()
        .connectTimeout(5, java.util.concurrent.TimeUnit.SECONDS)
        .readTimeout(5,java.util.concurrent.TimeUnit.SECONDS)
        .writeTimeout(5,java.util.concurrent.TimeUnit.SECONDS)
        .build()

    //fun1
    val requestBody = "json".toRequestBody("application/json;charset=utf-8".toMediaType())
    //fun2
    val formBody = FormBody.Builder()
        .add("name", "value")
        .build()

    val request = Request.Builder()
        .url("")
        .addHeader("","")
        .header("","")
//        .post(requestBody)
        .post(formBody)
        .build()

    okHttpClient.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {

        }

        override fun onResponse(call: Call, response: Response) {
        }
    })
}

private fun sendGetRequest(): String?{
    val okHttpClient = OkHttpClient()
    val request = Request.Builder()
        .url("")
        .get()
        .build()
    return runCatching {
        okHttpClient.newCall(request).execute()
    }.fold(
        onSuccess = {
            if(it.isSuccessful) it.body?.string() else null
        },
        onFailure = {
            null
        }
    )
}

private fun sendGetRequestAsync(callback:(String?)-> Unit){
    val okHttpClient = OkHttpClient()
    val request = Request.Builder()
        .url("")
        .get()
        .build()
    okHttpClient.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            callback(null)
        }

        override fun onResponse(call: Call, response: Response) {
            callback(response.body?.string())
        }
    })
}
//endregion