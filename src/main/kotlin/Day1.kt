package org.carlos

import java.nio.charset.Charset
import kotlin.math.abs


fun main() {
    val textFile = object {}::class.java.classLoader.getResource("input1.txt")?.readText(Charset.defaultCharset())
    val mapWithValues = textFile?.split("\n")?.map { it.split(Regex("\\D+")) }?.associateBy ({ it[0].toInt() }, { it[1].toInt() }).orEmpty()
    part1(mapWithValues)
    part2(mapWithValues)
}

fun part1(mapWithValues: Map<Int, Int>) {
    val sortedValues  = mapWithValues.values.sorted()
    println(mapWithValues.keys.sorted().mapIndexed { index, it ->  abs(it - sortedValues[index]) }.sum())
}

fun part2(mapWithValues: Map<Int, Int>) {
    val mapWithCounter = mutableMapOf<Int, Int>();
    mapWithValues.values.forEach { mapWithCounter.compute(it) { _, v -> v?.plus(1) ?: 1 } }
    println(mapWithValues.keys.sumOf { it * (mapWithCounter[it] ?: 0) })
}