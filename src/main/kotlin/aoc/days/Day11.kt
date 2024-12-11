package aoc.days

import aoc.BaseDay

class Day11(inputFile: String = "input_11.txt") : BaseDay(inputFile) {

    override fun part1(): Any {
        val stones = inputList.first().split(' ').map { it.toLong() }

        var newStones = stones.toMutableList()
        repeat(25) {
            newStones = blink(newStones)
        }

        return println(newStones.size)
    }

    override fun part2(): Any {
        val stones = inputList.first().split(' ').map { it.toLong() }
        val cache = (1..75).associateWith { mutableMapOf<Long, Long>() }

        val stoneCount = stones.sumOf { countStones(it, cache, 75) }
        return println(stoneCount)
    }

    private fun blink(stones: List<Long>): MutableList<Long> {
        val newStones = mutableListOf<Long>()
        stones.forEach { stone ->
            when {
                stone == 0L -> newStones.add(1L)
                "$stone".length % 2 == 0 -> {
                    val stoneString = stone.toString()
                    val size = stoneString.length
                    val first = stoneString.take(size / 2).toLong()
                    val second = stoneString.takeLast(size / 2).toLong()

                    newStones += first
                    newStones += second
                }

                else -> newStones += stone * 2024
            }
        }

        return newStones
    }

    private fun countStones(stone: Long, cache: Map<Int, MutableMap<Long, Long>>, repetition: Int): Long {
        return if (repetition == 0) {
            1
        } else {
            val iterationsCache = cache[repetition]!!
            iterationsCache.getOrPut(stone) {
                when {
                    stone == 0L -> countStones(1, cache, repetition - 1)
                    "$stone".length % 2 == 0 -> {
                        val stoneString = stone.toString()
                        val size = stoneString.length
                        val first = stoneString.take(size / 2).toLong()
                        val second = stoneString.takeLast(size / 2).toLong()

                        countStones(first, cache, repetition - 1) + countStones(second, cache, repetition - 1)
                    }

                    else -> countStones(stone * 2024, cache, repetition - 1)
                }
            }
        }
    }

}
