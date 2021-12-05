package week1

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var forward = 0
        var depth = 0

        fun incrementValues(direction: Char, distance: Int) {
            when {
                direction == 'f' -> forward += distance
                direction == 'd' -> depth += distance
                direction == 'u' -> depth -= distance
            }
        }

        input.forEach { incrementValues(it[0], it.split(" ")[1].toInt()) }
        return forward * depth
    }

    fun part2(input: List<String>): Int {
        var forward = 0
        var depth = 0
        var aim = 0

        fun incrementValues(direction: Char, distance: Int) {
            when {
                direction == 'f' -> {
                    forward += distance
                    depth += distance * aim
                }
                direction == 'd' -> aim += distance
                direction == 'u' -> aim -= distance
            }
        }

        input.forEach { incrementValues(it[0], it.split(" ")[1].toInt()) }
        return forward * depth
    }

    val input = readInput("src/week1", "Day02")
    println("Part 1 answer: ${part1(input)}")
    println("Part 2 answer: ${part2(input)}")
}
