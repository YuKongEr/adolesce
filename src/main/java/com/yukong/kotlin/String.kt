package com.yukong.kotlin

/**
 * 字符串学习
 * @author yukong
 * @date 2019-09-11 15:29
 */
fun main() {
    val str = "abc"
    for(c in str) {
        println(c )
    }
    var index = 0;
    while (index < str.length) {
        print(str[index++])
    }
}