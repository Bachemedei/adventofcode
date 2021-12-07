package week1

import readInput
import kotlin.math.absoluteValue

fun main() {
    fun part1(input: List<String>): Int {
        val currentPositions = fromStringToInt(input)
        val desiredPosition = getMedian(currentPositions)

        return currentPositions.fold(0) { totalFuelUsed, currentPosition ->
            totalFuelUsed + getDistance(currentPosition, desiredPosition)
        }
    }

    fun part2(input: List<String>): Int {
        val currentPositions = fromStringToInt(input)

        val fuelConsumptionOptOne = currentPositions.fold(0) { totalFuelUsed, currentPosition ->
                totalFuelUsed + getFuelUsed(currentPosition, getMeanFloor(currentPositions)) }

        val fuelConsumptionOptTwo = currentPositions.fold(0) { totalFuelUsed, currentPosition ->
            totalFuelUsed + getFuelUsed(currentPosition, getMeanCeiling(currentPositions))
        }

        return when {
            fuelConsumptionOptOne < fuelConsumptionOptTwo -> fuelConsumptionOptOne
            else -> fuelConsumptionOptTwo
        }
    }

    fun run() {
        val testInput = readInput("src/week1", "Day07_test")
        println("Part 1 test " + if (part1(testInput) == 37) "passed" else "failed")
        println("Part 2 test " + if (part2(testInput) == 168) "passed" else "failed")

        val input = readInput("src/week1", "Day07")
        println("Part 1 answer: ${part1(input)}")
        println("Part 2 answer: ${part2(input)}")
    }

    run()
}

fun fromStringToInt(input: List<String>): List<Int> {
    return input[0].split(",").map { it.toInt() }.sorted()
}

fun getMedian(numbers: List<Int>): Int {
    return numbers[numbers.size.floorDiv(2)]
}

fun getMeanFloor(numbers: List<Int>): Int {
    return numbers.sum().floorDiv(numbers.size)
}

fun getMeanCeiling(numbers: List<Int>): Int {
    return getMeanFloor(numbers) + 1
}

fun getDistance(currentPosition: Int, newPosition: Int): Int {
    return (currentPosition - newPosition).absoluteValue
}

fun getFuelUsed(currentPosition: Int, newPosition: Int): Int {
    val distanceToMove = getDistance(currentPosition, newPosition)
    return calculateArithmeticSum(distanceToMove)
}

fun calculateArithmeticSum(n: Int): Int {
    return (n * (n + 1) / 2)
}
