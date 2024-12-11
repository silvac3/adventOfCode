package org.carlos

import java.nio.charset.Charset

val unaryMatrixMovements = listOf(
    Pair(0, 1),
    Pair(0, -1),
    Pair(1, 0),
    Pair(-1, 0),
    Pair(1, 1),
    Pair(1, -1),
    Pair(-1, -1),
    Pair(-1, 1),
)

val diagonalMatrixMovements = listOf(
    Pair(1, 1),
    Pair(1, -1),
    Pair(-1, -1),
    Pair(-1, 1),
)

fun main() {
    val word = "XMAS"


    val txt = object {}::class.java.classLoader.getResource("day4.txt")?.readText(Charset.defaultCharset()).orEmpty()

    // Part 1
    val matrix = txt.lines().map { it.toCharArray().toList() }
    val wordsSum = (0..matrix[0].size - 1).flatMap { i ->
        (0..matrix.size - 1).filter { matrix[i][it] == word[0] }.map { j -> findNextPositionWithWordInMatrix(i, j, matrix, word[1], unaryMatrixMovements)
                    .filter { (x, y) -> findNextPositionWithWordInMatrix(2*x, 2*y, Pair(i, j), matrix, word[2]) }
                    .count { (x, y) -> findNextPositionWithWordInMatrix(3*x, 3*y, Pair(i, j), matrix, word[3]) }
        }
    }.sum()
    println(wordsSum)
    // Part 2
    val wordsSum2 = (0..matrix[0].size - 1).flatMap { i ->
        (0..matrix.size - 1).filter {  matrix[i][it] == 'A' }.filter { j ->
            println("i: $i, j: $j")
            findNextPositionWithWordInMatrix(i, j, matrix, 'M', diagonalMatrixMovements)
            .filter { (x, y) -> findNextPositionWithWordInMatrix(-x, -y, Pair(i, j), matrix, 'S') }.size == 2
        }
    }.count()
    println(wordsSum2)
}

fun findNextPositionWithWordInMatrix(i: Int, j: Int, position: Pair<Int, Int>,  matrix: List<List<Char>>, c: Char): Boolean {
    val xx = i + position.first
    val yy = j + position.second
    //println("xx: $xx, yy: $yy c: $c, position: $position")
    return xx >= 0 && xx < matrix.size && yy >= 0 && yy < matrix[0].size && matrix[xx][yy] == c
}


fun findNextPositionWithWordInMatrix(i: Int, j: Int, matrix: List<List<Char>>, char: Char, movementList: List<Pair<Int, Int>>): List<Pair<Int, Int>> {
    return movementList.filter { (x, y) ->
        val xx = i + x
        val yy = j + y
        xx >= 0 && xx < matrix.size && yy >= 0 && yy < matrix[0].size && matrix[xx][yy] == char
    }
}
//None of the bellow is used
fun findAllPossibleLines(text: String, word: String) {

    val matrix = text.lines().map { it.toCharArray().toList() }
    text.lines().sumOf { findTextBothWays(it, word) }
    (0..matrix[0].size - 1).map { i -> matrix.map { it[i] }.joinToString("") }.sumOf { findTextBothWays(it, word) }
    getDiagonals(matrix).sumOf { findTextBothWays(it, word) }
}

fun findTextBothWays(text: String, word: String): Int {
    val regex = Regex(word)
    val count = regex.findAll(text).count()
    val reversed = Regex(word.reversed())
    val countReversed = reversed.findAll(text).count()
    return count + countReversed
}

fun getDiagonals(matrix: List<List<Char>>): List<String> {
    val diagonals = mutableListOf<String>()
    for (i in 0..matrix.size - 1) {
        val diagonal = mutableListOf<Char>()
        for (j in 0..i) {
            diagonal.add(matrix[j][i - j])
        }
        diagonals.add(diagonal.joinToString(""))
    }
    for (i in 1..matrix.size - 1) {
        val diagonal = mutableListOf<Char>()
        for (j in 0..matrix.size - 1 - i) {
            diagonal.add(matrix[i + j][matrix.size - 1 - j])
        }
        diagonals.add(diagonal.joinToString(""))
    }
    return diagonals
}

const val INPUT_TEXT_4 = "MMMSXXMASM\n" +
        "MSAMXMSMSA\n" +
        "AMXSXMAAMM\n" +
        "MSAMASMSMX\n" +
        "XMASAMXAMM\n" +
        "XXAMMXXAMA\n" +
        "SMSMSASXSS\n" +
        "SAXAMASAAA\n" +
        "MAMMMXMMMM\n" +
        "MXMXAXMASX"