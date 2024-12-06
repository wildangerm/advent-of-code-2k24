package aoc.days

import aoc.BaseDay

class Day5(inputFile: String = "input_5.txt") : BaseDay(inputFile) {

    override fun part1(): Any {
        val (rules, pagesList) = mapInputToRulesAndPagesList()
        val (correctlyOrderedPageListIndexes, _) = getCorrectlyOrderedPageListIndexes(pagesList, rules)

        val result = pagesList
            .filterIndexed { index, _ -> correctlyOrderedPageListIndexes.contains(index) }
            .sumOf { getMiddleNumOfList(it) }

        return println(result)
    }

    override fun part2(): Any {
        val (rules, pagesList) = mapInputToRulesAndPagesList()
        val (correctlyOrderedPageListIndexes, failingRules) = getCorrectlyOrderedPageListIndexes(pagesList, rules)
        val incorrectlyOrderedPageListIndexes =
            pagesList.filterIndexed { index, _ -> !correctlyOrderedPageListIndexes.contains(index) }

        incorrectlyOrderedPageListIndexes.forEachIndexed { index, pageList ->
            pageList.sortWith(comparator(failingRules[index]))
        }

        val result = incorrectlyOrderedPageListIndexes
            .sumOf { getMiddleNumOfList(it) }

        return println(result)
    }

    fun <T> comparator(ordering: Set<Pair<T, T>>) = Comparator<T> { o1, o2 ->
        when {
            (o1 to o2) in ordering -> -1
            (o2 to o1) in ordering -> 1
            else -> 0
        }
    }

    private fun mapInputToRulesAndPagesList(): Pair<ArrayList<Pair<Int, Int>>, ArrayList<ArrayList<Int>>> {
        val rules = ArrayList<Pair<Int, Int>>()
        val pagesList = ArrayList<ArrayList<Int>>()

        inputList.forEach { row ->
            if (row.contains('|')) {
                val split = row.split('|')
                val before = split[0].toInt()
                val number = split[1].toInt()

                rules.add(Pair(before, number))
            } else if (row.contains(',')) {
                pagesList.add(ArrayList(row.split(',').map { it.toInt() }))
            }
        }
        return Pair(rules, pagesList)
    }

    private fun getCorrectlyOrderedPageListIndexes(
        pagesList: ArrayList<ArrayList<Int>>,
        rules: ArrayList<Pair<Int, Int>>
    ): Pair<ArrayList<Int>, ArrayList<HashSet<Pair<Int, Int>>>> {
        val correctlyOrderedPageListIndexes = ArrayList<Int>()
        val failingRuleLists = ArrayList<HashSet<Pair<Int, Int>>>()

        pagesList.forEachIndexed { index, pageList ->
            val seenPageNumbers = mutableSetOf<Int>()
            var correctlyOrderedSoFar = true
            val failingRules = hashSetOf<Pair<Int, Int>>()

            pageList.forEach { pageNumber ->
                rules.filter { rule -> rule.second == pageNumber && pageList.contains(rule.first) }.forEach { rule ->
                    if (seenPageNumbers.contains(rule.first)) {
                        // helyes a rulenak az ordering
                    } else {
                        correctlyOrderedSoFar = false
                        failingRules += rule
                    }
                }
                seenPageNumbers += pageNumber
            }
            if (failingRules.isNotEmpty()) {
                failingRuleLists.add(failingRules)
            }

            if (correctlyOrderedSoFar) {
                correctlyOrderedPageListIndexes += index
            }
        }
        return Pair(correctlyOrderedPageListIndexes, failingRuleLists)
    }

    private fun getMiddleNumOfList(list: List<Int>): Int {
        val middleIndex = (list.size - 1) / 2
        return list[middleIndex]
    }
}
