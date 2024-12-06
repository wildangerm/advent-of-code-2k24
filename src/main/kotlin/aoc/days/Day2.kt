package aoc.days

import aoc.BaseDay

class Day2(inputFile: String = "input_2.txt") : BaseDay(inputFile) {

    override fun part1(): Any {
        val reports = inputList

        var safeCount = 0

        reports.forEach { reportString ->
            val levels = reportString.split(' ').map { it.toInt() }

            val pair = checkIfLevelsAreSafe(levels)
            val ascending = pair.first
            val descending = pair.second

            if ((ascending && !descending) || (!ascending && descending)) {
                safeCount++
            }
        }

        return println(safeCount)
    }

    override fun part2(): Any {
        val reports = inputList

        var safeCount = 0

        reports.forEach { reportString ->
            val levels = reportString.split(' ').map { it.toInt() }

            val pair = checkIfLevelsAreSafe(levels)
            val ascending = pair.first
            val descending = pair.second

            if ((ascending && !descending) || (!ascending && descending)) {
                safeCount++
            } else {
                if (checkWithProblemDampener(levels)) {
                    safeCount++
                }
            }
        }

        return println(safeCount)
    }

    private fun checkWithProblemDampener(levels: List<Int>): Boolean {
        val subLists = levels.indices.map { index -> levels.filterIndexed { i, _ -> i != index } }

        for (subList in subLists) {
            val result = checkIfLevelsAreSafe(subList)
            val ascending = result.first
            val descending = result.second

            if ((ascending && !descending) || (!ascending && descending)) {
                return true
            }
        }

        return false
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun checkIfLevelsAreSafe(levels: List<Int>): Pair<Boolean, Boolean> {
        var descending = false
        var ascending = false
        for (i in 1..<levels.size) {
            val level1 = levels[i - 1]
            val level2 = levels[i]

            val diff = level1 - level2
            if (diff in 1..3) {
                descending = true
            } else if (diff in -3..-1) {
                ascending = true
            } else {
                ascending = false
                descending = false
                break;
            }
        }
        return Pair(ascending, descending)
    }

}