import java.util.concurrent.ConcurrentSkipListMap
import utils.readInputLines

/** [https://adventofcode.com/2025/day/11] */
class Cables : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val cables =
            readInputLines("11.txt").associate {
                val from = it.substringBefore(":")
                val to = it.substringAfter(": ").split(" ")
                from to to.toSet()
            }

        val cache = ConcurrentSkipListMap<Pair<String, String>, Int>(compareBy({ it.first }, { it.second }))
        fun findPaths(start: String, end: String): Int =
            cache.computeIfAbsent(start to end) {
                if (start == end) {
                    1
                } else {
                    cables[start]?.sumOf { next -> findPaths(next, end) } ?: 0
                }
            }

        val path = if (part2) listOf("svr", "fft", "dac", "out") else listOf("you", "out")
        return path.zipWithNext().fold(1L) { acc, (start, end) -> acc * findPaths(start, end) }
    }
}

fun main() {
    print(Cables().run(part2 = true))
}
