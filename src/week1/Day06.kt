package week1

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var school = School(getFish(input))
        var i = 0
        while (i < 80) {
           school = school.oneDayLater()
            i++
        }
        return school.getSchoolSize()
    }

    fun part2(input: List<String>): Long {
        val fish = input[0].split(",").map { it.toInt() }
        val school = School2()
        school.addInitialFish(fish)
        var i = 0
        while (i < 256) {
            school.procreate()
            i++
        }

        return school.getSchoolSize()
    }

    fun run() {
        val part1Test = part1(readInput("src/week1", "Day06_test"))
        val part2Test = part2(readInput("src/week1", "Day06_test"))

        println("Part 1 test " + if (part1Test == 5934) "passed" else "failed")
        println("Part 2 test " + if (part2Test == 26984457539) "passed" else "failed")

        println("Part 1 answer: ${part1(readInput("src/week1", "Day06"))}")
        println("Part 2 answer: ${part2(readInput("src/week1", "Day06"))}")
    }

    run()
}

fun getFish(input: List<String>): List<Fish> {
    return input[0].split(",").map { Fish(it.toInt()) }
}

class School2 {
    private val fishies: HashMap<Int, Long> = HashMap(8)

    init {
        var i = 0
        while (i < 9) {
            fishies[i] = 0
            i ++
        }
    }

    fun addInitialFish(fishInput: List<Int>) {
        fishInput.forEach {
            val currentCount = fishies[it]
            fishies[it] = (currentCount ?: 0).plus(1)
        }
    }

    fun procreate() {
        val reproduced = fishies[0] ?: 0
        for (fish in fishies) {
            when (fish.key) {
                6 -> fishies[fish.key] = (fishies[7] ?: 0) + reproduced
                8 -> fishies[fish.key] = reproduced
                else -> fishies[fish.key] = fishies[fish.key + 1] ?: 0
            }
        }
    }

    fun getSchoolSize(): Long {
        return fishies.values.sum()
    }
}


data class School(val fish: List<Fish>) {
    fun oneDayLater(): School {
        val newFish = fish.map {
            val newGuy = it.getOlder()
            newGuy
        }.flatten()
        return School(newFish)
    }

    fun getSchoolSize(): Int {
        return fish.size
    }
}

data class Fish(var timer: Int = 8) {
    fun getOlder(): List<Fish> {
        return when (timer) {
            0 -> {
                timer = 6
                val newGuy = procreate()
                listOf(newGuy, this)
            }
            else -> {
                timer --
                listOf(this)
            }
        }
    }

    private fun procreate(): Fish {
        return Fish()
    }
}


