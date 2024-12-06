package aoc.days

import aoc.BaseDay

class Day3(inputFile: String = "input_3.txt") : BaseDay(inputFile) {

    private val mulRegex: Regex = """mul\((\d{1,3}),(\d{1,3})\)""".toRegex()
    private val doRegex: Regex = """do\(\)""".toRegex()
    private val dontRegex: Regex = """don't\(\)""".toRegex()

    override fun part1(): Any {

        val result = inputList.sumOf { memoryLine ->
            val matched = mulRegex.findAll(memoryLine)

            matched.sumOf {
                val (first, second) = it.destructured
                first.toInt() * second.toInt()
            }
        }

        return println(result)
    }

    override fun part2(): Any {
        val fullMemory = inputList.joinToString()

        val mulMatched = mulRegex.findAll(fullMemory)
        val doMatched = doRegex.findAll(fullMemory)
        val dontMatched = dontRegex.findAll(fullMemory)

        val doMatchStarts = doMatched.map { it.range.first }
        val dontMatchStarts = dontMatched.map { it.range.first }

        val result = mulMatched.sumOf { match ->
            val matchStart = match.range.first
            val closestBeforeDoMatchDiff =
                doMatchStarts.map { matchStart - it }.filter { it > 0 }.sorted().firstOrNull()
            val closestBeforeDontMatchDiff =
                dontMatchStarts.map { matchStart - it }.filter { it > 0 }.sorted().firstOrNull()

            if (closestBeforeDoMatchDiff == null && closestBeforeDontMatchDiff == null ||
                (closestBeforeDoMatchDiff != null && closestBeforeDontMatchDiff != null && closestBeforeDoMatchDiff < closestBeforeDontMatchDiff)
            ) {
                val (first, second) = match.destructured
                first.toInt() * second.toInt()
            } else {
                0
            }
        }

        return println(result)
    }

}