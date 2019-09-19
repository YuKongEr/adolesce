package com.yukong.kotlin

/**
 * 数组使用
 * @author yukong
 * @date 2019-09-11 15:16
 */
fun main() {
    // 使用Array定义数组

    // 使用库函数arrayOf定义一个数组
    val array1 = arrayOf(1,2,3)
    // 使用库函数arrayOfNulls定义一个指定大小且所有元素为空的数组
    val array2 = arrayOfNulls<Int>(3)

    // 还有一种就是通过指定数组大小以及一个函数参数的Array构造函数，用作参数的函数能够返回给
    // 给定索引的每个元素初始值
    val asc = Array(5){i -> (i * i).toString()}
    asc.forEach { print("$it ") }
    print(asc[0])
    print(asc.get(0))

    // 原生类型数组
    val x = intArrayOf(1,2,3)
    val y = longArrayOf(1,2,3)

    var a= IntArray(1)
}