package week1

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val sums = mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
        val gammaList = mutableListOf<Int>()
        val epsilonList = mutableListOf<Int>()
        input.forEach { it.forEachIndexed { index, value -> sums[index] += Character.getNumericValue(value) } }
        sums.forEach {
            if (it > (input.size / 2)) {
                gammaList.add(1)
                epsilonList.add(0)
            } else {
                gammaList.add(0)
                epsilonList.add(1)
            }
        }
        val gamma = gammaList.joinToString("").toInt(2)
        val epsilon = epsilonList.joinToString("").toInt(2)
        return gamma * epsilon
    }

    fun part2(input: List<String>): Int {
        val o2 = Measurement(input)
        val co2 = Measurement(input)

        var i = 0
        while ( i < input[0].length) {
            o2.list.forEach { o2.incrementBits(charToBit(it[i])) }
            co2.list.forEach { co2.incrementBits(charToBit(it[i])) }

            o2.removeLessCommonBitsAtIndex(i)
            co2.removeMostCommonBitsAtIndex(i)
            i++
        }
        return o2.getDecimalMeasurement() * co2.getDecimalMeasurement()
    }

    val input = readInput("src/week1", "Day03")
    println("Part 1 answer: ${part1(input)}")
    println("Part 2 answer: ${part2(input)}")
}

data class Measurement constructor(
    var list: List<String>,
    var ones: Int = 0,
    var zeros: Int = 0,
    ) {
    private fun getMostCommonBit(): Bits {
        return if (ones >= zeros) Bits.One else Bits.Zero
    }

    private fun getLeastCommonBit(): Bits {
        return if (zeros <= ones) Bits.Zero else Bits.One
    }

    fun getDecimalMeasurement(): Int {
        return list.joinToString("").toInt(2)
    }

    fun incrementBits(bit: Bits) {
        when (bit) {
            Bits.One -> ones++
            Bits.Zero -> zeros++
        }
    }

    fun removeLessCommonBitsAtIndex(index: Int) {
        val newList =
            if (getMostCommonBit() == Bits.One) {
                if (list.size > 1) list.filterNot { charToBit(it[index]) == Bits.Zero } else list
            } else {
                if (list.size > 1) list.filterNot { charToBit(it[index]) == Bits.One } else list
            }
        update(newList)
    }

    fun removeMostCommonBitsAtIndex(index: Int) {
        val newList =
            if (getLeastCommonBit() == Bits.Zero) {
                if (list.size > 1) list.filterNot { charToBit(it[index]) == Bits.One } else list
            } else {
                if (list.size > 1) list.filterNot { charToBit(it[index]) == Bits.Zero } else list
            }
        update(newList)
    }

    private fun update(inputList: List<String>) {
        list = inputList
        ones = 0
        zeros = 0
    }

}

fun charToBit(input: Char): Bits {
    val int = Character.getNumericValue(input)
    return if (int == 1) Bits.One else Bits.Zero
}

enum class Bits {
    One,
    Zero,
}
