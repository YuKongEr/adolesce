package com.yukong.kotlin

import javafx.scene.Parent
import org.springframework.beans.factory.annotation.Autowired

/**
 * 类与对象
 * @author yukong
 * @date 2019-09-11 17:34
 */
class InitOrderDemo(name: String) {
    val firstProperty = "First property: $name".also (::println)

    init {
        println("First initializer block that prints $name")
    }

    val secondProperty = "Second property: ${name.length}".also(::println)

    init {
        println("Second initializer block that prints ${name.length}")

    }
}

/**
 * 主构造函数属性省略写法
 */
class Person(val firstName: String, val lastName:String, var age: Int) {

}

/**
 * 次构造函数
 */
open class People{

    var children: MutableList<People> = mutableListOf<People>()

    open val name: String = "people"

    constructor(parent: People) {
        parent.children.add(this)
    }

    constructor() {

    }

    open fun say(word: String) {
        println("say $word")
    }

    fun eat(food: String) {
        println("eat $food")
    }

    fun printName() {
        print(name)
    }

}

class Man : People {

    override var name: String = "man"

    constructor(): super()

    constructor(parent: People): super(parent)

    override fun say(word: String) {
        super.say(word)
    }
}

// 派生类初始化顺序

open class Base(val name: String) {
    init {
        println("Init Base")
    }

    open val size: Int =
            name.length.also(::println)

    fun po() {}
}

class Derived(name: String, val lastName: String): Base(name.capitalize().also{ println("Argument for Base: $it") }) {
    init {
        println("Init Derived")
    }

    override val size: Int =
            (super.size + lastName.length).also { println("Init size in Dervied: $it") }

    var word: String = "word"
        get() = field
        set(value) { field = value + "word"}

    open fun say() {}

    inner class Filler {
        fun fill() {}
        fun drawAndFill() {
            super@Derived.toString()

        }
    }
}

open class Rectangle {
    open fun draw() {
        println("rectangle draw")
    }
}

interface Polygon {
    fun draw() {
        println("polygon draw")
    }
}

class Square() : Rectangle(), Polygon {


    override fun draw() {
        super<Rectangle>.draw()
        super<Polygon>.draw()

    }
}

fun main() {
    val demo: InitOrderDemo = InitOrderDemo("yukong")
    var people = People();
    people.printName()

    people = Man()

    people.printName()

    println("======= 派生类初始化顺序 ========")
    val d = Derived("hello", "world")

}
