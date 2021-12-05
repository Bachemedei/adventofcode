package week1

import readInput

fun main() {
    fun part1(input: List<String>): Int {

        return 0
    }

    fun part2(input: List<String>): Int {

        return 0
    }

    val testInput = readInput("src/week1", "Day07_test")
    println("Part 1 test " + if (part1(testInput) == 0) "passed" else "failed")
    println("Part 2 test " + if (part2(testInput) == 0) "passed" else "failed")

    val input = readInput("src/week1", "Day07")
    println("Part 1 answer: ${part1(input)}")
    println("Part 2 answer: ${part2(input)}")
}
