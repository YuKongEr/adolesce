package com.yukong.kotlin

/**
 *
 * @author yukong
 * @date 2019-09-15 07:39
 */

fun foo(bar: Int = 0, baz: Int = 1, qux: () -> Unit) {
    println("Int = $bar, baz = $baz")
    qux()
}

// 省略花括号 最后一个表达式作为返回值
fun double(x: Int) : Int = x * 2

// 类型自动推导
fun double1(x: Int) = x * 2

/**
 * 可变参数
 */
fun <T> asList(vararg ts: T): List<T> {
    val result = ArrayList<T>()
    for (t in ts) {
        result.add(t)
    }
    return result
}

/**
 * 中缀函数
 * 1、必须是成员函数或者扩展函数
 * 2、只能接收一个参数
 * 3、参数不得接收可变参数，默认参数
 */
infix fun Int.plus(x: Int): Int = this.toInt() + x

/**
 * 局部函数
 */
fun dfs() {
    val list = ArrayList<String>()

    fun <T>dfs(println: (T) -> Unit) {
        // 局部函数可以访问外部函数的值 也就是闭包内的引用
        list.add("xxx")
        println(list)
    }

    dfs<String>{ println()}
}

class Book(val name: String)


fun main() {
    // 参数内指定lambda表达式
    foo(bar = 2, baz = 3, qux = { println("hello")});

    // 如果最后一个参数lambda表达式 可以在括号外传入
    foo(bar = 2, baz = 3){ println("xxx")};

    val a = arrayOf(1,2,3)
    // *伸展操作符
    val list = asList(-1, 0, *a, 4)

    println(3 plus  2 )

    dfs()

    val times = listOf(1, 2, 3, 4)
    val sum = times.fold(0){
        acc: Int, i: Int ->
        print("acc = $acc, i = $i")
        val result = acc + i
        println("result = $result")
        result
    }
    println("sum = $sum")

    val getBook = ::Book
    val book = getBook("effective java")
    println(book)

    val bookNames = listOf(
            Book("Thinking in Java"),
            Book("Effective Java")
    ).map(Book::name)

    println(bookNames)

}