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
        numberInputs.forEachIndexed { index, it ->
            if (index <= (numberInputs.lastIndex - 3)) {
                val windowOne = numberInputs[index] + numberInputs[index + 1] + numberInputs[index + 2]
                val windowTwo = numberInputs[index + 1] + numberInputs[index + 2] + numberInputs[index + 3]
                if (windowTwo > windowOne) count += 1
            }
        }
        return count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}