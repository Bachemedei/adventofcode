fun main() {
    fun part1(input: List<String>): Int {

        return 0
    }

    fun part2(input: List<String>): Int {

        return 0
    }

    val testInput = readInput("src/week2", "Day13_test")
    println("Part 1 test " + if (part1(testInput) == 0) "passed" else "failed")
    println("Part 2 test " + if (part2(testInput) == 0) "passed" else "failed")

    val input = readInput("src/week2", "Day13")
    println("Part 1 answer: ${part1(input)}")
    println("Part 2 answer: ${part2(input)}")
}
