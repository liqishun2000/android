package com.example.android.training.kotlin.lambda

/**
 * https://book.kotlincn.net/text/lambdas.html
 * 闭包
 * Lambda 表达式或者匿名函数（以及局部函数和对象表达式） 可以访问其闭包 ，其中包含在外部作用域中声明的变量。
 * 在 lambda 表达式中可以修改闭包中捕获的变量：
 * var sum = 0
 * ints.filter { it > 0 }.forEach {
 *     sum += it
 * }
 * print(sum)
 * */