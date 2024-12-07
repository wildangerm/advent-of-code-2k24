package aoc.days

import aoc.BaseDay

class Day7(inputFile: String = "input_7.txt") : BaseDay(inputFile) {

    override fun part1(): Any {
        val equations = mapInputToEquations(inputList)

        val result = equations.mapNotNull { eq ->
            eq.test()
        }.sum()

        return println(result)
    }

    override fun part2(): Any {
        val equations = mapInputToEquations(inputList)

        val result = equations.mapNotNull { eq ->
            eq.test(true)
        }.sum()

        return println(result)
    }

    private fun mapInputToEquations(inputList: ArrayList<String>): ArrayList<Equation> {
        val equations = ArrayList<Equation>()

        inputList.forEach { line ->
            val split1 = line.split(':')
            val testValue = split1[0].toLong()
            val numbers = split1[1].trim().split(' ').map { it.toLong() }.toMutableList()
            equations += Equation(testValue, numbers)
        }
        return equations
    }

    class Equation(private val testValue: Long, private val numbers: MutableList<Long>) {
        private val operatorPlaces = numbers.size - 1

        fun test(isConcatInPlay: Boolean = false): Long? {
            val possibleOperations = Operator.values().toMutableList()
            if (!isConcatInPlay) {
                possibleOperations.remove(Operator.CONCAT)
            }
            val operatorPermutations = permutations(possibleOperations, operatorPlaces.toDouble())

            for (operatorPermutation in operatorPermutations) {
                var result = numbers.first()

                for (number in numbers.filterIndexed { i, _ -> i != 0 }) {
                    val currentOperator = operatorPermutation.removeAt(0)
                    when (currentOperator) {
                        Operator.ADD -> result += number
                        Operator.MULTIPLY -> result *= number
                        Operator.CONCAT -> {
                            if (isConcatInPlay) {
                                result = "$result$number".toLong()
                            } else error("Concat is not in play")
                        }
                    }
                    if (result > testValue) {
                        break
                    }
                }

                if (testValue == result) {
                    return testValue
                }
            }

            return null
        }

        private fun <T> permutations(list: List<T>, size: Double): List<MutableList<T>> {
            if (size == 1.0) return list.map { mutableListOf(it) }
            val result = mutableListOf<MutableList<T>>()
            for (item in list) {
                for (perm in permutations(list, size - 1.0)) {
                    result.add((mutableListOf(item) + perm).toMutableList())
                }
            }
            return result
        }
    }

    enum class Operator {
        ADD,
        MULTIPLY,
        CONCAT
    }

}
