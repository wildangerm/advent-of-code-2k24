package aoc

abstract class BaseDay(inputFile: String) {

    protected var inputList = ArrayList<String>()

    abstract fun part1(): Any
    abstract fun part2(): Any

    init {
        readFile(inputFile)
    }

    protected open fun readFile(inputFile: String) {
        inputList = ArrayList(getInput(inputFile))
    }

    private fun getInput(fileName: String): List<String> {
        return javaClass.getResourceAsStream("/$fileName")!!.bufferedReader().readLines()
    }

    protected fun <T> println(result: T): T {
        kotlin.io.println(result)
        return result
    }
}