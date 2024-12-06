package org.carlos

import java.nio.charset.Charset



fun main() {
    val textFile = object {}::class.java.classLoader.getResource("day3.txt")?.readText(Charset.defaultCharset()).orEmpty()
    // Part 1
    calculateMul(textFile)

    //Part 2
    //(?s) ignores new lines in regex
    val trimmedText = Regex("(?s)don\'t\\(\\).*?do\\(\\)").replace(textFile) { "" }
    calculateMul(trimmedText)
}

fun calculateMul(text: String) {
    val regex = Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)")
    regex.findAll(text.toString()).map { it.destructured.toList() }.map { it[0].toInt()  * it[1].toInt() }.sum().let(::println)
}
