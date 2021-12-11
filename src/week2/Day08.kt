package week2

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        return input
            .map { it.split(" | ")[1].split(" ") }
            .flatten()
            .filterNot { it.length == 5 || it.length == 6 }
            .size
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { determineNumber(it) }
    }


    val testInput = readInput("src/week2", "Day08_test")
    println("Part 1 test " + if (part1(testInput) == 26) "passed" else "failed")
    println("Part 2 test " + if (part2(testInput) == 61229) "passed" else "failed")

    val input = readInput("src/week2", "Day08")
    println("Part 1 answer: ${part1(input)}")
    println("Part 2 answer: ${part2(input)}")
}

fun determineNumber(input: String): Int {
    val inputSplit = input.split(" | ")
    val sequence = inputSplit[0].split(" ")
    val output = inputSplit[1].split(" ")

    val twoThreeFive = sequence.filter { it.length == 5 }
    val zeroSixNine = sequence.filter { it.length == 6 }


    val one = sequence.filter { it.length == 2 }[0].toSet()
    val four = sequence.filter { it.length == 4 }[0].toSet()
    val seven = sequence.filter { it.length == 3 }[0].toSet()
    val eight = sequence.filter { it.length == 7 }[0].toSet()


    val six = zeroSixNine.filter { it.toList().intersect(one).size == 1 }[0].toSet()
    val nine = zeroSixNine.filter { it.toList().intersect(four).size == 4 }[0].toSet()
    val zero = zeroSixNine.filterNot { it.toSet() == six || it.toSet() ==  nine}[0].toSet()


    val three = twoThreeFive.filter { it.toSet().intersect(seven).size == 3 }[0].toSet()
    val five = twoThreeFive.filter { (it.toSet().intersect(four).size == 3) && it.toSet() != three }[0].toSet()
    val two = twoThreeFive.filterNot { it.toSet() == three || it.toSet() == five }[0].toSet()

    var sum = output.map {
        when (it.length) {
            2 -> 1
            4 -> 4
            3 -> 7
            7 -> 8
            5 -> when (it.toSet()) {
                two -> 2
                three -> 3
                five -> 5
                else -> -1
            }
            6 -> when (it.toSet()) {
                zero -> 0
                six -> 6
                nine -> 9
                else -> -1
            }
            else -> -1
        }
    }
    return sum.joinToString("").toInt()
}
