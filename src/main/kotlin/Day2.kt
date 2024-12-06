package org.carlos

import java.nio.charset.Charset
import kotlin.math.abs


fun main() {
    val textFile = object {}::class.java.classLoader.getResource("day2.txt")?.readText(Charset.defaultCharset())
    val listWithNumbers = textFile?.split("\n")?.map { it.split(Regex("\\D+")).map { it -> it.toInt() } }.orEmpty()
    day2Part1(listWithNumbers)
    day2Part2(listWithNumbers)
}

fun day2Part1(listWithNumbers: List<List<Int>>) {
    println(listWithNumbers.map(::isSafe).count { it })
}

fun day2Part2(listWithNumbers: List<List<Int>>) {
    println(listWithNumbers.map(::isSafeWithDampener).count { it })
}

fun isSafe(numbers: List<Int>): Boolean {
    val direction = majorDirection(numbers)
    return !numbers.zipWithNext { a, b -> getDirection(a, b) == direction && abs(a - b) >= 1 && abs(a - b) <= 3 }
        .any { !it }
}

fun isSafeWithDampener(numbers: List<Int>): Boolean {
    return isSafe(numbers)
        .or(numbers.mapIndexed { index, it -> isSafe(numbers.filterIndexed { i, _ -> i != index }) }
            .filter { it }.count() >= 1)
}

enum class Direction {
    ASC, DESC
}

fun getDirection(a: Int, b: Int): Direction {
    return if (a - b > 0) Direction.ASC else Direction.DESC
}



fun majorDirection(numbers: List<Int>): Direction {
    return numbers.zipWithNext { a, b -> getDirection(a, b) }.groupingBy { it }.eachCount().maxBy { it.value }.key
}