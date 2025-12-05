import utils.readInputBlock

/** [https://adventofcode.com/2025/day/5] */
class Ranges : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val (rawRanges, rawIds) = readInputBlock("5.txt").split("\n\n").map { it.trim().lines() }
        val ranges =
            rawRanges.map {
                val (start, end) = it.split("-").map(String::toLong)
                start..end
            }
        val ids = rawIds.map(String::toLong)

        return if (part2) {
            ranges
                .fold(mutableListOf<LongRange>()) { dedupedRanges, range ->
                    dedupedRanges.apply {
                        val adjustedStart = find { range.first in it }?.last?.plus(1) ?: range.first
                        val adjustedEnd = find { range.last in it }?.first?.minus(1) ?: range.last
                        if (adjustedStart <= adjustedEnd) {
                            removeAll { it.first >= adjustedStart && it.last <= adjustedEnd }
                            add(adjustedStart..adjustedEnd)
                        }
                    }
                }
                .sumOf { it.last - it.first + 1 }
        } else {
            ids.count { id -> ranges.any { id in it } }
        }
    }
}

fun main() {
    print(Ranges().run(part2 = true))
}
