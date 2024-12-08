package aoc

import aoc.days.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class Tester {

    @ParameterizedTest
    @MethodSource("generator")
    fun testDays(input: BaseDay, part1Result: Any?, part2Result: Any?){
        testParts(input, part1Result, part2Result)
    }

    private fun testParts(day: BaseDay, part1Result: Any?, part2Result: Any?) {
        println(day::class.simpleName)

        print("\tpart1: ")
        part1Result
            ?.let { assertEquals(it, day.part1()) }
            ?: day.part1()

        print("\tpart2: ")
        part2Result
            ?.let { assertEquals(it, day.part2()) }
            ?: day.part2()

        println()
    }

    companion object {
        @JvmStatic
        private fun generator() : Stream<Arguments> {
            return Stream.of(
//                Arguments.of(Day1(), 1970720, 17191599),
//                Arguments.of(Day1(getTestFileName(1)), 11, 31),
//                Arguments.of(Day2(), 526, 566),
//                Arguments.of(Day2(getTestFileName(2)), 2, 4),
//                Arguments.of(Day3(), 173517243, 100450138),
//                Arguments.of(Day3(getTestFileName(3)), 161, 48),
//                Arguments.of(Day4(), 2536, 1875)
//                Arguments.of(Day4(getTestFileName(4)), 18, 9)
//                Arguments.of(Day5(), 6612, 4944)
//                Arguments.of(Day5(getTestFileName(5)), 143, 123)
//                  Arguments.of(Day6(), 4982, 1663)
//                  Arguments.of(Day6(getTestFileName(6)), 41, 6)
//                  Arguments.of(Day7(), 12839601725877, 149956401519484)
//                  Arguments.of(Day7(getTestFileName(7)), 3749L, 11387L)
                  Arguments.of(Day8(), 273, 1017)
//                  Arguments.of(Day8(getTestFileName(8)), 14, 34)
//                  Arguments.of(Day9(), null, null)
//                  Arguments.of(Day9(getTestFileName(9)), 13, 1)
//                  Arguments.of(Day10(), null, null)
//                  Arguments.of(Day10(getTestFileName(10)), 13140, null)
//                      Arguments.of(Day11(), null, null)
//                  Arguments.of(Day11(getTestFileName(11)), 10605L, 2713310158L)
//                Arguments.of(Day12(), null, null)
//                Arguments.of(Day12(getTestFileName(12)), 31, null)

            )

        }

        private fun getTestFileName(num: Int): String {
            return "input_${num}_test.txt"
        }
    }
}