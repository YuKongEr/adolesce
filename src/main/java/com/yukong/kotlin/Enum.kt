package com.yukong.kotlin

import java.util.function.BinaryOperator
import java.util.function.IntBinaryOperator

/**
 *
 * @author yukong
 * @date 2019-09-12 14:49
 */

/*fun main() {
    val cc : StringBuilder.() -> Unit = {
        this.append("better")
        this.append("wolrd")
    }

    println(myBuildString(cc))
}*/

fun myBuildString(action: StringBuilder.() -> Unit) : String {
    val sb = StringBuilder()
    sb.action()
    return sb.toString()
}





enum class IntArith : BinaryOperator<Int>, IntBinaryOperator {

    PLUS {
        override fun apply(t: Int, u: Int): Int {
            return t + u
        }
    },
    TIMES {
        override fun apply(t: Int, u: Int): Int {
            return  t * u
        }
    }
    ;

    override fun applyAsInt(t: Int, u: Int) = apply(t, u)

}

fun main() {
    var a = 13
    var b = 31
    for (f in IntArith.values()) {
        println("$f($a, $b) = ${f.apply(a, b)}")
    }

    IntArith.valueOf("PLUS").apply(::println)
}