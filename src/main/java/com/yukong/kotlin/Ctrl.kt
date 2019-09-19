package com.yukong.kotlin

/**
 *
 * @author yukong
 * @date 2019-09-11 15:35
 */
fun main() {
    // kotlin中 if是一个表达式 它会返回一个值
    val a = 1
    val b = 2
    var max: Int
    // 传统
    if (a > b) {
        max = a
    } else {
        max = b
    }
    // 使用kotlin中的if表达式
    max = if(a > b) a else b

    // if的内容可以代码块  但是最后的一个表达式作为该块的返回值
    max = if(a > b) {
        print(a)
        a
    } else {
        print(b)
        b
    }


    // ======== when的用法 ========

    when (a) {
        0, 1 -> print("0 . 1")
        in 1..10 -> print("in 1..10")
        is Int -> print("is String")
        else -> print("else")
    }

}