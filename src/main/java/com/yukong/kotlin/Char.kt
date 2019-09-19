package com.yukong.kotlin

/**
 * 字符运算学习
 * @author yukong
 * @date 2019-09-11 15:11
 */
fun main(){
    // Char不能当成数字
    // val ch: Char = 2
    println(decimalDigitValue('7'))
    println(decimalDigitValue('a'))
}
fun decimalDigitValue(c: Char): Int {
    if(c !in '0'..'9') {
        throw IllegalAccessException("$c is out of range")
    }
    return c.toInt() - '0'.toInt()
}