import kotlin.collections.flatMapIndexed
import kotlin.collections.plus
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
                        lines.drop(1).flatMapIndexed { y, line ->
                            line.mapIndexedNotNull { x, c -> Coordinate(x, y).takeIf { c == '#' } }
                        }
                    index to shape.toSet()
                }
                .mapValues { (_, shape) -> generateVariants(shape) }

        data class Region(val width: Int, val height: Int, val requirements: List<IndexedValue<Int>>) {
            fun isValid(): Boolean = true // TODO
        }

        val regions =
            chunks.last().lines().map { region ->
                val (width, height) = region.substringBefore(": ").split('x').map(String::toInt)
                val requirements = region.substringAfter(": ").split(' ').map(String::toInt).withIndex().toList()
                Region(width, height, requirements)
            }

        return regions.count(Region::isValid)
    }

    private fun Set<Coordinate>.rotate90(): Set<Coordinate> =
        map { coordinate -> Coordinate(maxOf { it.y } - coordinate.y, coordinate.x) }.toSet()

    private fun Set<Coordinate>.flipHorizontal(): Set<Coordinate> =
        map { coordinate -> Coordinate(maxOf { it.x } - coordinate.x, coordinate.y) }.toSet()

    private fun generateVariants(shape: Set<Coordinate>): Set<Set<Coordinate>> {
        val rotations = generateSequence(shape) { it.rotate90() }.take(4).toSet()
        val flippedRotations = generateSequence(shape.flipHorizontal()) { it.rotate90() }.take(4).toSet()

        return (rotations + flippedRotations)
    }
}

fun main() {
    print(Day12().run(part2 = false))
}
