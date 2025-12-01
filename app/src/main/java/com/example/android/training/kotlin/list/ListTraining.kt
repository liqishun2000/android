package com.example.android.training.kotlin.list

fun main() {

    val list = listOf(1, 2, 3, 4, 5)

    val lists = listOf(listOf(1, 2, 3), listOf(4, 5))

    list.any { it>2 }
    list.all { it>2 }
    list.none { it>2 }

    //分组
    val groupBy = list.groupBy { it % 2 == 0 }
    //分割，符合条件的一组，不符合的一组
    val partition = list.partition { it > 2 }
    //降维用的
    val flatMap = lists.flatMap { it }

    val pairs = list zip lists

    list.getOrElse(1){ it }
}

/** 两个列表索引跟随 */
private fun indexFollow(){
    val list1 = listOf("1","3","2")
    val list2 = listOf("1","2","3")

    val valueToIndex = list1.withIndex().associate { it.value to it.index }
    val sortedBy = list2.sortedBy { valueToIndex[it] }

}
