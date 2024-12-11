package org.carlos

import java.nio.charset.Charset



fun main() {
    val txt = object {}::class.java.classLoader.getResource("day5.txt")?.readText(Charset.defaultCharset()).orEmpty()

    // Part 1
    val textSplit = txt.split("\n\n")
    val rulesMap = textSplit[0].lines().map { it.split("|") }.groupBy ({ it[0].toInt()}, {it[1].toInt() }).mapValues { it.value.toSet() }
    val updates = textSplit[1].lines().map { it.split(",").map { it.toInt() } }
    println(updates.filter { validate(rulesMap, it) }.sumOf { it[it.size / 2] })

    // Part 2
    val incorrectUpdates = updates.filter { !validate(rulesMap, it) }
    println(incorrectUpdates.map { orderUpdate(rulesMap, it) }.sumOf { it[it.size / 2] })

}

fun validate(rulesMap: Map<Int, Set<Int>>, update: List<Int>) : Boolean {
    return update.zipWithNext { a, b -> rulesMap[a]?.contains(b) ?: false }.all { it }
}


fun orderUpdate(rulesMap: Map<Int, Set<Int>>, update: List<Int>) : List<Int> {
    val comparator = Comparator { i1: Int, i2: Int ->
        return@Comparator if (rulesMap[i1]?.contains(i2) == true) -1 else 1
    }
    return update.sortedWith(comparator)
}

const val INPUT_TEXT_5 = "47|53\n" +
        "97|13\n" +
        "97|61\n" +
        "97|47\n" +
        "75|29\n" +
        "61|13\n" +
        "75|53\n" +
        "29|13\n" +
        "97|29\n" +
        "53|29\n" +
        "61|53\n" +
        "97|53\n" +
        "61|29\n" +
        "47|13\n" +
        "75|47\n" +
        "97|75\n" +
        "47|61\n" +
        "75|61\n" +
        "47|29\n" +
        "75|13\n" +
        "53|13\n" +
        "\n" +
        "75,47,61,53,29\n" +
        "97,61,53,29,13\n" +
        "75,29,13\n" +
        "75,97,47,61,53\n" +
        "61,13,29\n" +
        "97,13,75,29,47"