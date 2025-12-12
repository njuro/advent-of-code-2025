import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import utils.Coordinate
import utils.readInputLines

/** [https://adventofcode.com/2025/day/9] */
class Day9 : AdventOfCodeTask {

    override fun run(part2: Boolean): Any {
        val corners =
            readInputLines("9.txt").map {
                val (x, y) = it.split(",").map(String::toInt)
                Coordinate(x, y)
            }

        data class Edge(val c1: Coordinate, val c2: Coordinate) {
            val minX = min(c1.x, c2.x)
            val maxX = max(c1.x, c2.x)
            val minY = min(c1.y, c2.y)
            val maxY = max(c1.y, c2.y)
        }

        val polygon = (corners + corners.first()).zipWithNext().map { (c1, c2) -> Edge(c1, c2) }.toSet()
        val rectangles = corners.flatMapIndexed { offset, c1 -> corners.drop(offset).map { c2 -> Edge(c1, c2) } }

        fun Edge.hasLineInside(line: Edge): Boolean =
            if (line.c1.x == line.c2.x && line.c1.x in (minX + 1 until maxX)) {
                line.minY <= minY && line.maxY > minY || line.maxY >= maxY && line.minY < maxY
            } else if (line.c1.y == line.c2.y && line.c1.y in (minY + 1 until maxY)) {
                line.minX <= minX && line.maxX > maxX || line.maxX >= maxX && line.minX < maxX
            } else {
                false
            }

        fun Edge.isInsidePolygon(): Boolean {

            val centerPoint = Coordinate(minX + 1, minY + 1)

            return polygon.any { centerPoint.x in it.minX..it.maxX && it.maxY < centerPoint.y } &&
                polygon.any { centerPoint.x in it.minX..it.maxX && it.minY > centerPoint.y } &&
                polygon.any { centerPoint.y in it.minY..it.maxY && it.maxX < centerPoint.x } &&
                polygon.any { centerPoint.y in it.minY..it.maxY && it.minX > centerPoint.x }
        }

        val (valid, invalid) =
            rectangles.partition { rectangle ->
                if (part2) {
                    polygon.none { border -> rectangle.hasLineInside(border) } && rectangle.isInsidePolygon()
                } else {
                    true
                }
            }

        var counter = 0
        return valid
            .map { (c1, c2) -> Edge(c1, c2) to (abs(c1.x - c2.x) + 1).toLong() * (abs(c1.y - c2.y) + 1) }
            .sortedByDescending { it.second }
            .first { (edge, area) ->
                println("NOPE $area")
                (edge.minX..edge.maxX).all { x ->
                    (edge.minY..edge.maxY).all { y ->
                        val centerPoint = Coordinate(x, y)
                        polygon.any { centerPoint.x in it.minX..it.maxX && it.maxY <= centerPoint.y } &&
                            polygon.any { centerPoint.x in it.minX..it.maxX && it.minY >= centerPoint.y } &&
                            polygon.any { centerPoint.y in it.minY..it.maxY && it.maxX <= centerPoint.x } &&
                            polygon.any { centerPoint.y in it.minY..it.maxY && it.minX >= centerPoint.x }
                    }
                }
            }
            .second
    }
}

fun main() {
    print(Day9().run(part2 = true))
}
