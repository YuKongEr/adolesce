package com.yukong.kotlin

/**
 * 比较运算
 * @author yukong
 * @date 2019-09-11 15:04
 */
fun main(){

    // 相等性比较
    val a = 1
    val b = 1
    println(a == b)
    val c: Int = 2
    val d: Long = 2
    println(c.toLong() == d )

    // 区间检测
    println(2 in 1..5)
    println(2 !in 1..5)

}