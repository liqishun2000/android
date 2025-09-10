package com.example.android.training.gradle

/**
 * 依赖排除groovy:
 * implementation ('com.example:library:1.0') {
 *     exclude group: 'unwanted.group', module: 'unwanted-module'
 * }
 *
 * kotlin dsl:
 * implementation("com.example:library:1.0") {
 *     exclude(group = "unwanted.group", module = "unwanted-module")
 * }
 * */