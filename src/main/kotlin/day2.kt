import utils.readInputBlock

/** [https://adventofcode.com/2025/day/2] */
class Ids : AdventOfCodeTask {
    override fun run(part2: Boolean): Any =
        readInputBlock("2.txt").trim().split(",").sumOf { sequence ->
            val (start, end) = sequence.split("-").map { it.toLong() }
            (start..end)
                .filter {
                    val str = it.toString()
                    if (part2) {
                        (1..str.length / 2).any { range -> str.take(range).repeat(str.length / range) == str }
                    } else {
                        str.length % 2 == 0 && str.take(str.length / 2) == str.substring(str.length / 2)
                    }
                }
                .sum()
        }
}

fun main() {
    print(Ids().run(part2 = true))
}
