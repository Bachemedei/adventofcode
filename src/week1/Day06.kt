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
        val schoolSize = school.getSchoolSize()
        return schoolSize
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
    private val fish: HashMap<Int, Long> = HashMap(8)

    fun addInitialFish(fishInput: List<Int>) {
        fishInput.forEach {
            val currentCount = fish[it]
            fish[it] = (currentCount ?: 0).plus(1)
        }
    }

    fun procreate() {
        val reproduced = fish[0] ?: 0
        fish[0] = fish[1] ?: 0
        fish[1] = fish[2] ?: 0
        fish[2] = fish[3] ?: 0
        fish[3] = fish[4] ?: 0
        fish[4] = fish[5] ?: 0
        fish[5] = fish[6] ?: 0
        fish[6] = (fish[7] ?: 0) + reproduced
        fish[7] = fish[8] ?: 0
        fish[8] = reproduced
    }

    fun getSchoolSize(): Long {
        return fish.values.sum()
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