package com.example.core.utils

import java.util.Random

object RandomUtils {

    private val mRandom by lazy { Random() }

    /**
     * 通过概率判断
     * 传入30则有30%的概率返回true
     *
     * @param prob 概率
     * */
    @JvmStatic
    fun isTrueFromProb(prob: Int): Boolean {
        val randomNum = mRandom.nextInt(100)
        return prob > randomNum
    }


}