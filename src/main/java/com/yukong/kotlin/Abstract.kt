package com.yukong.kotlin

/**
 *
 * @author yukong
 * @date 2019-09-12 09:23
 */

open class Pg {
    open fun draw() {}
}

abstract class Pq : Pg() {
    abstract override fun draw()
}

class Operator {

    companion object {
        @JvmField
        val NAME: String = "NAME"
    }


}

class Address {
    var name: String = "Holmes, Sherlock"
    var street: String = "Baker"
    var city: String = "London"
    var state: String? = null
    var zip:String = "123456"
}


fun main() {
    println(Operator.NAME)
    val address = Address()

    address.city = "xx"


}