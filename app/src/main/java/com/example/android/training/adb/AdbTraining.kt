package com.example.android.training.adb

/**
 * 传文件
 * adb push {filePath} /storage/emulated/0/fileName
 * 拿文件 可以拿整个目录，或者一个文件 如果名字含有空格，需要用""
 * adb pull /storage/emulated/0/DCIM/Screenshots C:\Users\fisrt\AndroidStudioProjects\temp
 * 列出文件列表
 * adb shell ls /storage/emulated/0/
 * */