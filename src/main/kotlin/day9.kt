import kotlin.math.max
import kotlin.math.min
import utils.Coordinate
import utils.readInputLines

/** [https://adventofcode.com/2025/day/9] */
class Rectangles : AdventOfCodeTask {

    override fun run(part2: Boolean): Any {
        val corners =
            readInputLines("9.txt").map {
                val (x, y) = it.split(",").map(String::toInt)
                Coordinate(x, y)
            }

        fun IntRange.overlaps(other: IntRange): Boolean = max(first, other.first) <= min(last, other.last)
        fun IntRange.size(): Int = last - first + 1

        data class Rectangle(val c1: Coordinate, val c2: Coordinate) {
            val xRange = min(c1.x, c2.x)..max(c1.x, c2.x)
            val yRange = min(c1.y, c2.y)..max(c1.y, c2.y)

            fun inner(): Rectangle =
                Rectangle(Coordinate(xRange.first + 1, yRange.first + 1), Coordinate(xRange.last - 1, yRange.last - 1))

            fun overlaps(other: Rectangle): Boolean = xRange.overlaps(other.xRange) && yRange.overlaps(other.yRange)

            fun area(): Long = xRange.size().toLong() * yRange.size()
        }

        val polygon = (corners + corners.first()).zipWithNext().map { (c1, c2) -> Rectangle(c1, c2) }
        val rectangles = corners.flatMapIndexed { offset, c1 -> corners.drop(offset).map { c2 -> Rectangle(c1, c2) } }

        return rectangles
            .filter { rectangle ->
                if (part2) {
                    val inner = rectangle.inner()
                    polygon.none { border -> inner.overlaps(border) }
                } else {
                    true
                }
            }
            .maxOf(Rectangle::area)
    }
}

fun main() {
    print(Rectangles().run(part2 = true))
}
