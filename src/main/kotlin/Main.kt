package org.carlos

import java.nio.charset.Charset
import kotlin.math.abs


fun main() {
    val textFile = object {}::class.java.classLoader.getResource("input1.txt")?.readText(Charset.defaultCharset())
    val mapWithValues = textFile?.split("\n")?.map { it.split(Regex("\\D+")) }?.associateBy ({ it[0].toInt() }, { it[1].toInt() }).orEmpty()
    val sortedValues  = mapWithValues.values.sorted()
    print(mapWithValues.keys.sorted().mapIndexed { index, it ->  abs(it - sortedValues[index]) }.sum())
}