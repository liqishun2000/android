package com.example.android.training.dependence

/**
 * 移除重复依赖，可以把所有的相关依赖都移除：
 * configurations.configureEach {
 *     exclude group: 'com.github.bumptech.glide', module: 'gifdecoder'
 * }
 * 然后在指定添加：
 * implementation 'com.github.bumptech.glide:gifdecoder:5.0.5'
 * */