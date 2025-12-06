import utils.readInputLines

/** [https://adventofcode.com/2025/day/6] */
class Spaces : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val input = readInputLines("6.txt")
        val columnIndices = input.maxBy { it.length }.indices
        val problems =
            if (part2) {
                val columns =
                    columnIndices.map { row ->
                        input.mapNotNull { it.getOrNull(row) }.joinToString("").replace(" ", "")
                    }

                columns.fold(mutableListOf(mutableListOf<String>())) { groups, column ->
                    when {
                        column.isEmpty() -> groups.add(mutableListOf())
                        else -> groups.last().add(column)
                    }
                    groups
                }
            } else {
                val processedInput = input.map { row -> row.trim().split("\\s+".toRegex()) }
                columnIndices.map { row -> processedInput.mapNotNull { it.getOrNull(row) } }.filter { it.isNotEmpty() }
            }

        return problems.sumOf { problem ->
            val operator = if (part2) problem.first().last().toString() else problem.last()
            problem
                .mapNotNull { it.filter(Char::isDigit).toLongOrNull() }
                .reduce { a, b -> if (operator == "+") a + b else a * b }
        }
    }
}

fun main() {
    print(Spaces().run(part2 = true))
}
