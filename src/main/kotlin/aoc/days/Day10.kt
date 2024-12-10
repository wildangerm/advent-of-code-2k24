package aoc.days

import aoc.BaseDay

class Day10(inputFile: String = "input_10.txt") : BaseDay(inputFile) {

    override fun part1(): Any {
        val map = Map(inputList)

        return println(map.findTrailheadScores())
    }

    override fun part2(): Any {
        val map = Map(inputList)

        return println(map.findTrailheadScores(true))
    }


    class Map(input: List<String>) {
        private val map = mutableListOf<List<Int>>()
        private var maxY = 0
        private var maxX = 0

        init {
            input.forEach { line ->
                val row = line.toCharArray().map { it.digitToInt() }
                map.add(row)
            }
            maxY = map.size - 1
            maxX = map[0].size - 1
        }

        fun findTrailheadScores(scoreBasedOnDistinctTrails: Boolean = false): Int {
            val trailheads = findTrailheads()
            val trailheadScores = mutableListOf<Int>()

            trailheads.forEach { trailhead ->
                val reachedTops = mutableListOf<Position>()

                recursiveDfs(trailhead, reachedTops)
                trailheadScores += if (scoreBasedOnDistinctTrails) {
                    reachedTops.size
                } else {
                    reachedTops.toHashSet().size
                }
            }

            return trailheadScores.sum()
        }

        private fun recursiveDfs(currentPosition: Position, reachedTops: MutableList<Position>) {
            val possibleNextPositions = getPossibleNextPositions(currentPosition)
            possibleNextPositions.forEach { possibleNextPos ->
                if (map[possibleNextPos.y][possibleNextPos.x] == 9) {
                    reachedTops += possibleNextPos
                } else {
                    recursiveDfs(possibleNextPos, reachedTops)
                }
            }
        }

        private fun getPossibleNextPositions(currPos: Position): List<Position> {
            return Orientation.values().mapNotNull { orientation ->
                val newY = currPos.y + orientation.step.first
                val newX = currPos.x + orientation.step.second
                if (newY in 0..maxY && newX in 0..maxX && map[newY][newX] == map[currPos.y][currPos.x] + 1) {
                    Position(newY, newX)
                } else {
                    null
                }
            }
        }

        private fun findTrailheads(): List<Position> {
            val trailheads = mutableListOf<Position>()
            for (y in map.indices) {
                for (x in map[y].indices) {
                    if (map[y][x] == 0) {
                        trailheads += Position(y, x)
                    }
                }
            }
            return trailheads
        }
    }

    class Position(y: Int, x: Int) {
        private val coordinates: Pair<Int, Int> = Pair(y, x)
        val y: Int get() = coordinates.first
        val x: Int get() = coordinates.second

        override fun equals(other: Any?): Boolean {
            if (this === other)
                return true
            if (other !is Position)
                return false
            return coordinates == other.coordinates
        }
        override fun hashCode(): Int {
            return coordinates.hashCode()
        }
    }

    enum class Orientation(val step: Pair<Int, Int>) {
        UP(Pair(-1, 0)), DOWN(Pair(1, 0)), LEFT(Pair(0, -1)), RIGHT(Pair(0, 1))
    }
}
