fun main() {
    fun part1(input: List<String>): Pair<Int, Long> {
        val lines = input.toMutableList()
        var part1 = 0
        val part2results = mutableListOf<Long>()

        for (line in input) {
            val characters = line.toMutableList()
            val open = ArrayDeque<Char>()
            val close = ArrayDeque<Char>()

            var syntaxError: Char? = null

            while (characters.isNotEmpty()) {
                val character = characters.removeFirst()
                if (isOpeningCharacter(character)) {
                    open.addLast(character)
                    close.addFirst(getMatchingCharacter(character))
                } else {
                    val expectedMatch = open.last()
                    if (expectedMatch == getMatchingCharacter(character)) {
                        open.removeLast()
                        close.removeFirst()
                    } else {
                        part1 += getIllegalCharacterScore(character)
                        lines.remove(line)
                        syntaxError = character
                    }
                }
                if (syntaxError != null) break
            }
            if (lines.contains(line)) {
                part2results.add(getAutoCompleteScore(close))
            }
        }

        return part1 to part2results.sorted()[part2results.size.floorDiv(2)]
    }

    fun run() {
        val testInput = readInput("src/week2", "Day10_test")
        val testAnswer = part1(testInput)
        println("Part 1 test " + if (testAnswer.first == 26397) "passed" else "failed")
        println("Part 2 test " + if (testAnswer.second == 288957L) "passed" else "failed")

        val input = readInput("src/week2", "Day10")
        val answer = part1(input)
        println("Part 1 answer: ${answer.first}")
        println("Part 2 answer: ${answer.second}")
    }
    run()
}

fun isOpeningCharacter(character: Char): Boolean {
    return when (character) {
        '(',
        '[',
        '{',
        '<' -> true
        else -> false
    }
}

fun getMatchingCharacter(character: Char): Char {
    return when (character) {
        '(' -> ')'
        '[' -> ']'
        '{' -> '}'
        '<' -> '>'
        ')' -> '('
        ']' -> '['
        '}' -> '{'
        '>' -> '<'
        else -> ' '
    }
}

fun getIllegalCharacterScore(character: Char): Int {
    return when (character) {
        ')' -> 3
        ']' -> 57
        '}' -> 1197
        '>' -> 25137
        else -> 0
    }
}

fun getAutoCompleteScore(characters: List<Char>): Long {
    var runningScore = 0L
    for (char in characters) {
        val characterScore = getMissingCharacterScore(char)
        val newScore = (runningScore * 5L) + characterScore
        runningScore = newScore
    }
    return runningScore
}

fun getMissingCharacterScore(character: Char): Long {
    return when (character) {
        ')' -> 1L
        ']' -> 2L
        '}' -> 3L
        '>' -> 4L
        else -> 0L
    }
}
