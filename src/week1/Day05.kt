package week1

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val coordinateList = input
            .map { Coordinates(it.split(" -> ")) }
            .filter { it.pointOne.x == it.pointTwo.x || it.pointOne.y == it.pointTwo.y }
        val grid = GridMap()
        coordinateList.forEach { it.drawLine(grid) }
        return grid.countCrossingLines()
    }

    fun part2(input: List<String>): Int {
        val coordinateList = input
            .map { Coordinates(it.split(" -> ")) }
        val grid = GridMap()
        coordinateList.forEach { it.drawLine(grid) }
        return grid.countCrossingLines()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("src/week1", "Day05_test")
    println("Part 1 test " + if (part1(testInput) == 5) "passed" else "failed")
    println("Part 2 test " + if (part2(testInput) == 12) "passed" else "failed")

    val input = readInput("src/week1", "Day05")
    println("Part 1 answer: ${part1(input)}")
    println("Part 2 answer: ${part2(input)}")
}

data class Coordinates constructor(
    val pointOne: Coordinate,
    val pointTwo: Coordinate
) {
    constructor(input: List<String>) : this(
        pointOne = Coordinate(
            input[0].split(",")[0].toInt(),
            input[0].split(",")[1].toInt(),
        ),
        pointTwo = Coordinate(
            input[1].split(",")[0].toInt(),
            input[1].split(",")[1].toInt(),
        ),
    )

    fun drawLine(grid: GridMap) {
        when {
            pointOne.y == pointTwo.y -> {
                val smallerY = if (pointOne.y < pointTwo.y) pointOne.y else pointTwo.y
                val largerY = if (pointOne.y > pointTwo.y) pointOne.y else pointTwo.y
                var i = smallerY
                while (i <= largerY) {
                    grid.addPoint(Coordinate(pointOne.x, i))
                    i++
                }
            }
            pointOne.x == pointTwo.x -> {
                val smallerX = if (pointOne.x < pointTwo.x) pointOne.x else pointTwo.x
                val largerX = if (pointOne.x > pointTwo.x) pointOne.x else pointTwo.x
                var i = smallerX
                while (i <= largerX) {
                    grid.addPoint(Coordinate(i, pointOne.y))
                    i++
                }
            }
            else -> {
                when {
                    pointOne.x < pointTwo.x && pointOne.y < pointTwo.y -> {
                        var x = pointOne.x
                        var y = pointOne.y
                        while (x <= pointTwo.x) {
                            grid.addPoint(Coordinate(x, y))
                            x++
                            y++
                        }
                    }
                    pointOne.x > pointTwo.x && pointOne.y > pointTwo.y -> {
                        var x = pointTwo.x
                        var y = pointTwo.y
                        while (x <= pointOne.x) {
                            grid.addPoint(Coordinate(x, y))
                            x++
                            y++
                        }
                    }
                    pointOne.x > pointTwo.x && pointOne.y < pointTwo.y -> {
                        var x = pointTwo.x
                        var y = pointTwo.y
                        while (x <= pointOne.x) {
                            grid.addPoint(Coordinate(x, y))
                            x++
                            y--
                        }
                    }
                    pointOne.x < pointTwo.x && pointOne.y > pointTwo.y -> {
                        var x = pointOne.x
                        var y = pointOne.y
                        while (x <= pointTwo.x) {
                            grid.addPoint(Coordinate(x, y))
                            x++
                            y--
                        }
                    }
                }
            }
        }
    }
}

data class GridMap(val grid: MutableList<MutableList<Int>> = MutableList(1000) {MutableList(1000) {0} }) {
    fun addPoint(point: Coordinate) {
        grid[point.y][point.x] += 1
    }

    fun countCrossingLines(): Int {
        var crossingLines = 0
        grid.forEach { it.forEach { point ->
            if (point > 1) crossingLines++ }
        }
        return crossingLines
    }
}
data class Coordinate(val x: Int, val y: Int)
