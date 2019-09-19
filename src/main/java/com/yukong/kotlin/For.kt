package com.yukong.kotlin

/**
 *
 * @author yukong
 * @date 2019-09-11 16:11
 */
fun main() {
    val its = intArrayOf(1,2,3)

    for (it in its) {
        print(it)
    }
    println()

    for(idx in its.indices) {
        print(its[idx])
    }
    println()
    for((index, value) in its.withIndex()) {
        print(index + value)
    }
    println()
    for (idx in 0..10) {
        print(idx)
    }


}