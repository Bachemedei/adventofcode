package week1

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var count = 0
        val numberInputs = mutableListOf<Int>()
        input.forEach { numberInputs.add(it.toInt()) }
        numberInputs.forEachIndexed { index, it -> if (index > 0 && it > numberInputs[index - 1]) count += 1 }
        return count
    }

    fun part2(input: List<String>): Int {
        var count = 0
        val numberInputs = mutableListOf<Int>()
        input.forEach { numberInputs.add(it.toInt()) }
        numberInputs.forEachIndexed { index, _ ->
            if (index <= (numberInputs.lastIndex - 3)) {
                val windowOne = numberInputs[index] + numberInputs[index + 1] + numberInputs[index + 2]
                val windowTwo = numberInputs[index + 1] + numberInputs[index + 2] + numberInputs[index + 3]
                if (windowTwo > windowOne) count += 1
            }
        }
        return count
    }

    val input = readInput("src/week1", "Day01")
    println("Part 1 answer: ${part1(input)}")
    println("Part 2 answer: ${part2(input)}")
}
