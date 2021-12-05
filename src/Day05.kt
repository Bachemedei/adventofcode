fun main() {
    fun part1(input: List<String>): Int {
        val coordinateList = input
            .map { Coordinates(it.split(" -> ")) }
            .filter { it.pointOne.x == it.pointTwo.x || it.pointOne.y == it.pointTwo.y }
        val grid = MutableList(1000) {MutableList(1000) {0} }
        val allLines = coordinateList.map { it.getLine() }.flatten()
        allLines.forEach { grid[it.y][it.x] += 1 }
        var matches = 0
        grid.forEach { it.forEach { point ->
            if (point > 1) matches++ }
        }
        return matches
    }

    fun part2(input: List<String>): Int {
        val coordinateList = input
            .map { Coordinates(it.split(" -> ")) }
        val grid = MutableList(1000) {MutableList(1000) {0} }
        val allLines = coordinateList.map { it.getLine() }.flatten()
        allLines.forEach { grid[it.y][it.x] += 1 }
        var matches = 0
        grid.forEach { it.forEach { point ->
            if (point > 1) matches++ }
        }
        return matches
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    println(part1(testInput) == 5)
    println(part2(testInput) == 12)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

data class Coordinates private constructor(
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
    fun getLine(): List<Coordinate> {
        val list = mutableListOf<Coordinate>()
        when {
            pointOne.x == pointTwo.x -> {
                var smallerY = if (pointOne.y < pointTwo.y) pointOne.y else pointTwo.y
                var largerY = if (pointOne.y > pointTwo.y) pointOne.y else pointTwo.y
                var i = smallerY
                while (i <= largerY) {
                    list.add(Coordinate(pointOne.x, i))
                    i++
                }
            }
            pointOne.y == pointTwo.y -> {
                var smallerX = if (pointOne.x < pointTwo.x) pointOne.x else pointTwo.x
                var largerX = if (pointOne.x > pointTwo.x) pointOne.x else pointTwo.x
                var i = smallerX
                while (i <= largerX) {
                    list.add(Coordinate(i, pointOne.y))
                    i++
                }
            }
            else -> {
                when {
                    pointOne.x < pointTwo.x && pointOne.y < pointTwo.y -> {
                        var x = pointOne.x
                        var y = pointOne.y
                        while (x <= pointTwo.x) {
                            list.add(Coordinate(x, y))
                            x++
                            y++
                        }
                    }
                    pointOne.x > pointTwo.x && pointOne.y > pointTwo.y -> {
                        var x = pointTwo.x
                        var y = pointTwo.y
                        while (x <= pointOne.x) {
                            list.add(Coordinate(x, y))
                            x++
                            y++
                        }
                    }
                    pointOne.x > pointTwo.x && pointOne.y < pointTwo.y -> {
                        var x = pointTwo.x
                        var y = pointTwo.y
                        while (x <= pointOne.x) {
                            list.add(Coordinate(x, y))
                            x++
                            y--
                        }
                    }
                    pointOne.x < pointTwo.x && pointOne.y > pointTwo.y -> {
                        var x = pointOne.x
                        var y = pointOne.y
                        while (x <= pointTwo.x) {
                            list.add(Coordinate(x, y))
                            x++
                            y--
                        }
                    }
                }
            }
        }
        return list
    }
}

data class Coordinate(val x: Int, val y: Int)
