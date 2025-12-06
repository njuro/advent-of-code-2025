import utils.readInputLines

/** [https://adventofcode.com/2025/day/6] */
class Spaces : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val pattern = Regex("([*+]\\s*)")
        val grid = readInputLines("6.txt")
        val operators = pattern.findAll(grid.last()).map { it.value }.map { it.first() to it.length - 1 }.toList()

        var offset = 0
        return operators.sumOf { (operator, size) ->
            val numbers = grid.dropLast(1).map { row -> row.drop(offset).let { if (size > 0) it.take(size) else it } }
            offset += size + 1
            if (part2) {
                val newSize = if (size == 0) numbers.maxOf { it.length } else size
                val newNumbers =
                    (0 until newSize).map { idx ->
                        numbers.mapNotNull { it.getOrNull(idx).takeUnless { it == ' ' } }.joinToString("")
                    }
                newNumbers.map { it.trim().toLong() }.reduce { acc, l -> if (operator == '+') acc + l else acc * l }
            } else {
                numbers.map { it.trim().toLong() }.reduce { acc, l -> if (operator == '+') acc + l else acc * l }
            }
        }

        //        return grid.first().indices.sumOf { column ->
        //            val rawNumbers = grid.indices.toList().dropLast(1).map { row -> grid[row][column] }
        //            val operation: ((Long, Long) -> Long) = if (grid[grid.lastIndex][column] == "*") Long::times else
        // Long::plus
        //            val numbers =
        //                if (part2) {
        //                    emptyList()
        //                } else {
        //                    rawNumbers.map(String::toLong)
        //                }
        //            numbers.reduce(operation)
        //        }
    }
}

fun main() {
    print(Spaces().run(part2 = true))
}
