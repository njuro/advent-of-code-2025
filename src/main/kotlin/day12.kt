import kotlin.collections.flatMapIndexed
import utils.Coordinate
import utils.readInputBlock

/** [https://adventofcode.com/2025/day/12] */
class Day12 : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val chunks = readInputBlock("12.txt").trim().split("\n\n")
        val shapes =
            chunks
                .dropLast(1)
                .map(String::lines)
                .associate { lines ->
                    val index = lines.first().substringBefore(':').toInt()
                    val shape =
                        lines
                            .drop(1)
                            .flatMapIndexed { y, line -> line.mapIndexed { x, c -> Coordinate(x, y) to c } }
                            .toMap()
                    index to shape
                }
                .mapValues { (_, shape) -> TODO() }

        data class Region(val width: Int, val height: Int, val requirements: List<IndexedValue<Int>>) {
            val map: MutableMap<Coordinate, Char> =
                (0 until height)
                    .flatMap { y -> (0 until width).map { x -> Coordinate(x, y) to '.' } }
                    .toMap()
                    .toMutableMap()
        }
        val regions =
            chunks.last().lines().map {
                val (width, height) = it.substringBefore(": ").split('x').map(String::toInt)
                val requirements = it.substringAfter(": ").split(' ').map(String::toInt).withIndex().toList()
                Region(width, height, requirements)
            }

        return regions
    }
}

fun main() {
    print(Day12().run(part2 = false))
}
