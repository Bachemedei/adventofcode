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

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
