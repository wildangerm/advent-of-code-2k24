package aoc.days

import aoc.BaseDay
import kotlin.math.abs

class Day1(inputFile: String = "input_1.txt") : BaseDay(inputFile) {
    @OptIn(ExperimentalStdlibApi::class)
    override fun part1(): Any {
        val (left, right) = getLists()

        left.sort()
        right.sort()

        var sum = 0
        for (i in 0..< left.size){
            sum += abs(left[i] - right[i])
        }

        return println(sum)
    }


    override fun part2(): Any {
        val (left, right) = getLists()

        val rightMap = HashMap<Int, Int>()

        for (element in right) {
            rightMap[element] = rightMap.getOrDefault(element, 0) + 1
        }

        val sum = left.sumOf {
            it * rightMap.getOrDefault(it, 0)
        }

        return println(sum)
    }

    private fun getLists(): Pair<MutableList<Int>, MutableList<Int>> {
        val left = mutableListOf<Int>()
        val right = mutableListOf<Int>()

        inputList.forEach {
            val row = it.split("   ")
            left += row[0].toInt()
            right += row[1].toInt()
        }
        return Pair(left, right)
    }
}
