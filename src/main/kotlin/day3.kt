import utils.readInputLines

/** [https://adventofcode.com/2025/day/3] */
class Elevators : AdventOfCodeTask {
    override fun run(part2: Boolean): Any =
        readInputLines("3.txt").sumOf { line ->
            val digits = line.map { it.digitToInt() }
            val batteryCount = if (part2) 12 else 2
            val (_, results) =
                (batteryCount downTo 1).fold(digits to emptyList<Int>()) { (remaining, results), offset ->
                    val newResult = remaining.dropLast(offset - 1).max()
                    val newRemaining = remaining.subList(remaining.indexOf(newResult) + 1, remaining.size)
                    newRemaining to results + newResult
                }

            results.joinToString("").toLong()
        }
}

fun main() {
    print(Elevators().run(part2 = true))
}
