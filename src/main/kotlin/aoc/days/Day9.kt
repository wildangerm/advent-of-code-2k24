package aoc.days

import aoc.BaseDay
import kotlin.math.max
import kotlin.math.min

class Day9(inputFile: String = "input_9.txt") : BaseDay(inputFile) {

    override fun part1(): Any {
        val disk = readInputToDisk()

        var i = 0
        var j = disk.size - 1

        while (i < j) {
            if (disk[i] != -1 || disk[j] == -1) {
                if (disk[i] != -1) {
                    i++
                }
                if (disk[j] == -1) {
                    j--
                }
            } else {
                disk[i] = disk[j]!!
                disk[j] = -1
            }
        }

        var sum = 0L
        disk.forEach { (k, v) ->
            if (v != -1) {
                sum += k * v
            }
        }

        return println(sum)
    }

    override fun part2(): Any {
        val files = mutableListOf<File>()
        var freeSpaces = mutableListOf<File>()
        readFilesAndFreeSpaces(files, freeSpaces)
        files.sortByDescending { it.id }


        for (file in files) {
            freeSpaces = freeSpaces.filter { it.rangeSize != 0 }.toMutableList()
            val freeSpaceIterator = freeSpaces.listIterator()

            while (freeSpaceIterator.hasNext()) {
                val freeSpace = freeSpaceIterator.next()
                if (freeSpace.indexes.first < file.indexes.first) {
                    if (freeSpace.rangeSize == file.rangeSize && freeSpace.indexes.first > 0) {
                        val temp = file.indexes
                        file.indexes = freeSpace.indexes
                        freeSpace.indexes = temp

                        mergeFreeSpaceIfNeeded(freeSpaces, freeSpace, freeSpaceIterator, false)
                        break
                    } else if (freeSpace.rangeSize > file.rangeSize && freeSpace.indexes.first > 0) {
                        val tempFreeSpace = File(-1, file.indexes)
                        file.indexes = freeSpace.indexes.first until freeSpace.indexes.first + file.rangeSize
                        freeSpace.indexes = (freeSpace.indexes.first + file.rangeSize)..freeSpace.indexes.last

                        mergeFreeSpaceIfNeeded(freeSpaces, tempFreeSpace, freeSpaceIterator, true)
                        break
                    }
                }
            }
        }

        val sum = files.sumOf { file -> file.indexes.sumOf { file.id * it.toLong() } }

        return println(sum)
    }

    private fun mergeFreeSpaceIfNeeded(
        freeSpaces: MutableList<File>,
        tempFreeSpace: File,
        freeSpaceIterator: MutableListIterator<File>,
        newFreeSpace: Boolean
    ) {
        val freeSpacesToMergeWith = freeSpaces
            .filter {
                it.indexes.last + 1 == tempFreeSpace.indexes.first ||
                        it.indexes.first == tempFreeSpace.indexes.last + 1
            }
        if (freeSpacesToMergeWith.isEmpty()) {
            if (newFreeSpace) {
                freeSpaceIterator.add(tempFreeSpace)
            }
        } else {
            val newFirst =
                min(freeSpacesToMergeWith.minOf { it.indexes.first }, tempFreeSpace.indexes.first)
            val newLast = max(freeSpacesToMergeWith.maxOf { it.indexes.last }, tempFreeSpace.indexes.last)
            tempFreeSpace.indexes = newFirst..newLast
            if (newFreeSpace) {
                freeSpaceIterator.add(tempFreeSpace)
            }
            freeSpacesToMergeWith.forEach {
                it.indexes = -2..-2
            }
        }
    }

    private fun readFilesAndFreeSpaces(files: MutableList<File>, freeSpaces: MutableList<File>) {
        var diskIndex = 0
        var fileID = 0

        inputList.first().forEachIndexed { index, c ->
            if (c.digitToInt() != 0) {
                val fileRange = diskIndex..diskIndex + max(c.digitToInt() - 1, 0)

                if (index % 2 == 0) {
                    files.add(File(fileID, fileRange))
                    fileID++
                } else {
                    freeSpaces.add(File(-1, fileRange))
                }
                diskIndex += c.digitToInt()
            }
        }
    }

    private fun readInputToDisk(): MutableMap<Int, Int> {
        val disk = mutableMapOf<Int, Int>()
        var diskIndex = 0
        var fileID = 0

        inputList.first().forEachIndexed { index, c ->
            if (index % 2 == 0) {
                repeat(c.digitToInt()) {
                    disk[diskIndex] = fileID
                    diskIndex++
                }
                fileID++
            } else {
                repeat(c.digitToInt()) {
                    disk[diskIndex] = -1
                    diskIndex++
                }
            }
        }
        return disk
    }

    class File(val id: Int, intRange: IntRange) {
        var indexes = intRange
            set(value) {
                field = value
                rangeSize = if (value.first == -2) {
                    0
                } else {
                    value.last - value.first + 1
                }
            }
        var rangeSize = indexes.last - indexes.first + 1
            private set
    }

}
