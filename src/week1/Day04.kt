package week1

import readInput

fun main() {
    fun part1(input: List<String>) {
        val numbers = input[0].split(",").map { it.toInt() }
        val boardsInput = input.subList(2, input.lastIndex)
        val boards = mutableListOf<Board>()
        var i = 0
        var ii = 1
        while (i <= boardsInput.size - 5) {
            val boardSubList = boardsInput.subList(i, i + 5)
            val board = Board(ii)
            board.addInput(boardSubList)
            board.checkForBingo(numbers)
            boards += board
            i += 6
            ii ++
        }
        var winner: Board? = null
        var loser: Board? = null
        boards.forEach {
            if (it.winningPosition != -1) {
                when {
                    winner == null || it.winningPosition < winner!!.winningPosition -> winner = it
                    loser == null || it.winningPosition > loser!!.winningPosition -> loser = it
                }
            }
        }
        if (winner == null || loser == null) return

        val winningScore = winner!!.getWinningScore(numbers)
        val losingScore = loser!!.getWinningScore(numbers)
        println("part 1: $winningScore")
        println("part 2: $losingScore")
    }

    val input = readInput("src/week1", "Day04")
    part1(input)
}

data class Board(val id: Int) {
    private val rows = mutableListOf<Row>()
    private val columns = mutableListOf<Column>()
    var winningPosition: Int = -1

    fun getWinningScore(numbers: List<Int>): Int {
        if (winningPosition == -1) return -1

        val sublist = numbers.subList(0, winningPosition + 1)
        val remainingNumbers = rows.map { it.rowNumbers.filterNot { number -> sublist.contains(number) } }
        var totalSum = 0
        remainingNumbers.forEach { totalSum += it.sum() }
        return totalSum * numbers[winningPosition]
    }

    fun checkForBingo(numbers: List<Int>) {
        rows.forEach {
            val position = it.checkForNumbers(numbers)
            updatePositionIfWon(position)
        }
        columns.forEach {
            val position = it.checkForNumbers(numbers)
            updatePositionIfWon(position)
        }
    }

    private fun updatePositionIfWon(newPosition: Int?) {
        if (newPosition == null) return
        when {
            winningPosition == -1 -> {
                println("$newPosition, $id")
                winningPosition = newPosition
            }
            newPosition < winningPosition -> {
                println("$newPosition, $id")
                winningPosition = newPosition
            }
        }
    }

    fun addInput(input: List<String>) {
        input.forEach{ addRow(it) }
        addColumn(input)
    }

    private fun addRow(input: String) {
        var i = 0
        val rowList = mutableListOf<Int>()
        while (i < input.length - 1) {
            rowList += getNumber(input[i], input[i+1])
            i += 3
        }
        rows += Row(rowList)
    }

    private fun addColumn(input: List<String>) {
        var i = 0
        while (i < input[0].length - 1) {
            val columnList = mutableListOf<Int>()
            columnList += input.map { getNumber(it[i], it[i + 1]) }
            columns += Column(columnList)
            i += 3
        }
    }

    private fun getNumber(inputOne: Char, inputTwo: Char): Int {
        val number =
            if (inputOne.isWhitespace()) inputTwo.toString()
            else inputOne.toString() + inputTwo.toString()
        return number.toInt()
    }
}

sealed class BoardNumbers(private val numbers: List<Int>) {
    fun checkForNumbers(winningNumbers: List<Int>): Int {
        var found = 0
        var position = 0
        numbers.forEach {
            if (winningNumbers.contains(it)) {
                found++
                val winningIndex = winningNumbers.indexOf(it)
                if (winningIndex > position) position = winningIndex
            }
        }
        return if (found == numbers.size) position else -1
    }
}

data class Row(val rowNumbers: List<Int>): BoardNumbers(rowNumbers)
data class Column(val columnNumbers: List<Int>): BoardNumbers(columnNumbers)
