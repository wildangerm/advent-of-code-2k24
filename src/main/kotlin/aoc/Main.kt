package aoc

import aoc.days.Day1

fun main(args: Array<String>) {

    val days = arrayListOf<BaseDay>()

     days.add(Day1())
//    days.add(Day2())
//    days.add(Day3())
//    days.add(Day9("input_9_test2.txt"))

    days.forEach{
        it.part1()
        it.part2()
    }
}