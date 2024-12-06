package aoc.days

import aoc.BaseDay

class Day4(inputFile: String = "input_4.txt") : BaseDay(inputFile) {

    override fun part1(): Any {
        val map = Map(inputList)
        val result = map.getCountOfXmas()
        return println(result)
    }

    override fun part2(): Any {
        val map = Map(inputList)
        val result = map.getCountOfMASX()
        return println(result)
    }

    class Map(input: List<String>) {
        private val map = mutableListOf<List<Char>>()
        private var maxY = 0
        private var maxX = 0

        init {
            input.forEach { line ->
                val row = line.toCharArray().map { it }
                map.add(row)
            }
            maxY = map.size - 1
            maxX = map[0].size - 1
        }

        fun getCountOfXmas(): Int {
            val xmas = "XMAS"
            var sum = 0

            for (y in 0..maxY) {
                for (x in 0..maxX) {
                    var currentString = ""

                    val char = map[y][x]
                    if (char == 'X') {
                        currentString += char

                        val nbIx = listOf(-1, 0, 1)

                        for (i in nbIx) {
                            for (j in nbIx) {
                                if (!bothZero(i, j)) {
                                    var newY = y + i;
                                    var newX = x + j;

                                    if (newY in 0..maxY && newX in 0..maxX) {
                                        val neighbor = map[newY][newX]

                                        if (neighbor == 'M') {
                                            val direction = Pair(i, j)
                                            var stepCount = 2
                                            var stepped = true
                                            while (stepped && stepCount < 4) {
                                                stepped = step(newY, newX, direction, xmas[stepCount])
                                                if (stepped) {
                                                    newY += direction.first
                                                    newX += direction.second
                                                    stepCount++
                                                    if (stepCount == 4) {
                                                        sum++
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            return sum
        }

        fun getCountOfMASX(): Int {
            var sum = 0

            for (y in 0..maxY) {
                for (x in 0..maxX) {
                    val char = map[y][x]
                    if (char == 'A') {
                        val diagonals = listOf(-1 to -1, 1 to 1, 1 to -1, -1 to 1)

                        var mDiagonalCount = 0
                        var sDiagonalCount = 0
                        val charsInDiagonal = "SM"

                        diagonals.forEach {
                            val newY = y + it.first
                            val newX = x + it.second

                            if (newY in 0..maxY && newX in 0..maxX) {
                                val diagonalChar = map[newY][newX]

                                if (charsInDiagonal.contains(diagonalChar)) {
                                    when (diagonalChar) {
                                        'S' -> sDiagonalCount++
                                        'M' -> mDiagonalCount++
                                    }
                                }
                            }
                        }

                        if (mDiagonalCount == 2 && sDiagonalCount == 2 && oneOppositeDiagonalIsNotTheSame(y, x)) {
                            sum++
                        }
                    }
                }
            }

            return sum
        }

        private fun oneOppositeDiagonalIsNotTheSame(y: Int, x: Int): Boolean {
            val newY1 = y + 1
            val newX1 = x + 1
            val diagonalChar1 = map[newY1][newX1]

            val newY2 = y - 1
            val newX2 = x - 1
            val diagonalChar2 = map[newY2][newX2]

            return diagonalChar1 != diagonalChar2
        }


        private fun step(y: Int, x: Int, direction: Pair<Int, Int>, nextXmasChar: Char): Boolean {
            val newY = y + direction.first;
            val newX = x + direction.second;

            if (newY in 0..maxY && newX in 0..maxX) {
                val neighbor = map[newY][newX]
                if (neighbor == nextXmasChar) {
                    return true
                }
            }
            return false
        }

        private fun bothZero(i: Int, j: Int): Boolean {
            return i == 0 && j == 0
        }
    }
}

