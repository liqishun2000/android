package com.example.android.training.okhttp3

/**
 *  okhttp内部有一个Dispatch管理一个线程池
 *  用enqueue方法调用默认线程池发起网络请求
 *  用execute方法则用当前线程发起网络请求
 * */