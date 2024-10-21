package com.example.android.training.coroutine


private fun main() {

    val childrenSecond = ChildrenSecond()
    println(childrenSecond.key == ChildrenFirst)


}

private interface Parent{

    public interface Key<K:Children>

    public interface Children:Parent{

        val key:Key<*>
    }

}

private abstract interface ChildrenFirst:Parent.Children{

    companion object Key:Parent.Key<ChildrenFirst>

}

private abstract class ChildrenFirstSupport:ChildrenFirst{

    override val key = ChildrenFirst
}

private class ChildrenSecond:ChildrenFirst,ChildrenFirstSupport(){

}