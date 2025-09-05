package com.example.android.training.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/**
 * 大文件需使用： @Multipart
 * */
private interface RequestInterface{

    @GET("users/{id}")
    suspend fun getUser(@Path("id") userId:Int): User

    @GET("users")
    suspend fun getUser2(@Query("id") userId:Int): User

    @POST("users")
    suspend fun postUser(@Body user: User): User

}

private fun simpleUse(){

    val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply { this.level = HttpLoggingInterceptor.Level.BODY })
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(RequestInterface::class.java)


}

private data class User(
    val id: Int,
    val name: String,
    val email: String
)

private data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)