package com.yukong.kotlin

/**
 * 学习kotlin中的整形
 * @author yukong
 * @date 2019-09-11 14:49
 */

fun main(args: Array<String>) {
    val one = 1 // 不可变int变量
    val treeBillion = 30000000000 // 不可变long变量
    var oneLong = 1L // 可变long变量
    var oneByte: Byte = 1; // 可变byte变量

    println("class = ${one.javaClass.name}, value = $one")
    println("class = ${treeBillion.javaClass.name}, value = $treeBillion")
    println("class = ${oneLong.javaClass.name}, value = $oneLong")
    println("class = ${oneByte.javaClass.name}, value = $oneByte")

    var pi = 3.14 //double
    val eFloat = 2.71f //float
    fun printDouble(d: Double){print(d)}

    printDouble(pi)
    // printDouble(eFloat) // 报错 因为kotlin不支持类型向上转换

    // ========== 显示类型转换 =============
    // toByte
    // toShort
    // toInt
    // toLong
    // toFloat
    // toDouble
    // toChar

    printDouble(eFloat.toDouble()) //使用显示的类型转换即可


    val oneBillion = 1_000_000_000 // kotlin支持字面值下划线


}
