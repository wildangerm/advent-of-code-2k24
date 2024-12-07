package aoc.days

import aoc.BaseDay

class Day6(inputFile: String = "input_6.txt") : BaseDay(inputFile) {

    override fun part1(): Any {
        val map = Map(inputList)
        return println(map.traceRoute().map { it.toPair() }.toHashSet().size)
    }

    override fun part2(): Any {
        val map = Map(inputList)
        val positionsVisited = map.traceRoute()

        return println(map.traceRoute2(positionsVisited))
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

        fun traceRoute(): MutableList<Triple<Int, Int, Orientation>> {
            var (y, x) = findGuardStartingPosition()
            var orientation = Orientation.UP
            val positionsVisited = mutableListOf(Triple(y, x, orientation))

            while (y in 0..maxY && x in 0..maxX) {
                // trying to step
                val newPos = Triple(y, x, orientation).add(orientation.step)

                // step or turn
                if (newPos.first in 0..maxY && newPos.second in 0..maxX) {
                    if (map[newPos.first][newPos.second] != '#') {
                        y = newPos.first
                        x = newPos.second
                        positionsVisited += newPos
                    } else {
                        orientation = turn90Right(orientation)
                    }
                } else {
                    break
                }
            }

            return positionsVisited
        }

        fun traceRoute2(positionsVisitedInRoute: MutableList<Triple<Int, Int, Orientation>>): Int {
            val (y, x) = positionsVisitedInRoute.removeAt(0)
            val startingOrientation = Orientation.UP
            var countOfLoops = 0


            positionsVisitedInRoute.map { it.toPair() }.toHashSet().forEach { position ->
                val map2 = map.map { it.toMutableList() }.toMutableList()
                map2[position.first][position.second] = '#'
                var newY = y
                var newX = x
                var orientation = startingOrientation

                val positionsVisited = hashSetOf(Triple(y, x, orientation))

                while (newY in 0..maxY && newX in 0..maxX) {
                    // trying to step
                    val newPos = Triple(newY, newX, orientation).add(orientation.step)

                    // step or turn
                    if (newPos.first in 0..maxY && newPos.second in 0..maxX) {
                        if (map2[newPos.first][newPos.second] != '#') {
                            newY = newPos.first
                            newX = newPos.second

                            // In a loop, if we are at the same place, same orientation as sometime before
                            if (positionsVisited.contains(newPos)) {
                                countOfLoops++
                                break
                            }
                            positionsVisited.add(newPos)
                        } else {
                            orientation = turn90Right(orientation)
                        }
                    } else {
                        break
                    }
                }
            }

            return countOfLoops
        }

        private fun turn90Right(orientation: Orientation): Orientation {
            return when (orientation) {
                Orientation.UP -> Orientation.RIGHT
                Orientation.RIGHT -> Orientation.DOWN
                Orientation.DOWN -> Orientation.LEFT
                Orientation.LEFT -> Orientation.UP
            }
        }

        private fun findGuardStartingPosition(): Pair<Int, Int> {
            for (y in map.indices) {
                for (x in map[y].indices) {
                    if (map[y][x] == '^') {
                        return Pair(y, x)
                    }
                }
            }
            return Pair(-1, -1)
        }

        private fun Triple<Int, Int, Orientation>.add(other: Pair<Int, Int>): Triple<Int, Int, Orientation> {
            return Triple(this.first + other.first, this.second + other.second, this.third)
        }
    }

    companion object {
        fun <A, B, C> Triple<A, B, C>.toPair(): Pair<A, B> { return Pair(this.first, this.second) }
    }

    enum class Orientation(val step: Pair<Int, Int>) {
        UP(Pair(-1, 0)),
        DOWN(Pair(1, 0)),
        LEFT(Pair(0, -1)),
        RIGHT(Pair(0, 1))
    }
}
