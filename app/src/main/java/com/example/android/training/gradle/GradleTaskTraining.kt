package com.example.android.training.gradle

/**
 * 构建gradle任务，继承DefaultTask，必须open
 * @TaskAction任务入口
 * @get:Input 输入
 * @get:InputDirectory 输入目录
 * @get:PathSensitive 关心的路径(绝对路径、相对路径)
 *
 *
 * import path.类名
 * tasks.register("任务名", 类名) {
 *     group = "任务分组"
 *     description = "鼠标悬停描述"
 *     encryptionKey = "输入string类型参数"
 *     sourceDir = file("${project.projectDir}/src")
 *     fileExtensions = listOf("kt", "java", "xml")
 * }
 *
 * */