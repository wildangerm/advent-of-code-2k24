package aoc.days

import aoc.BaseDay

class Day8(inputFile: String = "input_8.txt") : BaseDay(inputFile) {

    override fun part1(): Any {
        val map = Map(inputList)

        return println(map.findAntiNodes1().toHashSet().size)
    }

    override fun part2(): Any {
        val map = Map(inputList)

        return println(map.findAntiNodes2().toHashSet().size)
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

        fun findAntiNodes1(): MutableList<Pair<Int, Int>> {
            val antennas = collectAntennas()
            val antiNodes = mutableListOf<Pair<Int, Int>>()

            antennas.forEach { (_, antennaLocations) ->
                val pairings = getPairings(antennaLocations)

                pairings.forEach { pairing ->
                    val (a, b) = pairing
                    val diff = b - a
                    val antiNode = b + diff

                    if (isWithinBounds(antiNode)) {
                        antiNodes += antiNode
                    }
                }
            }

            return antiNodes
        }

        fun findAntiNodes2(): MutableList<Pair<Int, Int>> {
            val antennas = collectAntennas()
            val antiNodes = mutableListOf<Pair<Int, Int>>()

            antennas.forEach { (_, antennaLocations) ->
                val pairings = getPairings(antennaLocations)
                if (antennaLocations.size >= 2) {
                    antiNodes.addAll(antennaLocations)
                }

                pairings.forEach { pairing ->
                    val (a, b) = pairing
                    val diff = b - a

                    var antiNode = b + diff
                    while (isWithinBounds(antiNode)) {
                        antiNodes += antiNode
                        antiNode += diff
                    }
                }
            }

            return antiNodes
        }

        private fun isWithinBounds(antiNode: Pair<Int, Int>): Boolean {
            return antiNode.first in 0..maxY && antiNode.second in 0..maxX
        }

        private fun getPairings(antennaLocations: MutableList<Pair<Int, Int>>): MutableSet<Pair<Pair<Int, Int>, Pair<Int, Int>>> {
            val pairings = mutableSetOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
            for (i in antennaLocations.indices) {
                for (j in antennaLocations.indices) {
                    if (i != j) {
                        pairings.add(Pair(antennaLocations[i], antennaLocations[j]))
                    }
                }
            }

            return pairings
        }

        private fun collectAntennas(): MutableMap<Char, MutableList<Pair<Int, Int>>> {
            val antennas = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()

            map.forEachIndexed { y, row ->
                row.forEachIndexed { x, char ->
                    if (char != '.') {
                        if (antennas.containsKey(char)) {
                            antennas[char]!!.add(y to x)
                        } else {
                            antennas[char] = mutableListOf(y to x)
                        }
                    }
                }
            }

            return antennas
        }

        private operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> {
            return Pair(this.first + other.first, this.second + other.second)
        }

        private operator fun Pair<Int, Int>.minus(other: Pair<Int, Int>): Pair<Int, Int> {
            return Pair(this.first - other.first, this.second - other.second)
        }

    }

}
