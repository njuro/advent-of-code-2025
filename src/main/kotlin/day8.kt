import java.util.PriorityQueue
import kotlin.math.pow
import kotlin.math.sqrt
import utils.readInputLines

/** [https://adventofcode.com/2025/day/8] */
class Boxes : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {

        data class Box(val x: Int, val y: Int, val z: Int) {
            fun distanceTo(other: Box): Double =
                sqrt(
                    (x.toDouble() - other.x).pow(2.0) +
                        (y.toDouble() - other.y).pow(2.0) +
                        (z.toDouble() - other.z).pow(2.0)
                )
        }

        val boxes =
            readInputLines("8.txt").map {
                val (x, y, z) = it.split(",").map(String::toInt)
                Box(x, y, z)
            }

        val closest =
            boxes.flatMapIndexedTo(
                PriorityQueue { (a1, a2), (b1, b2) -> a1.distanceTo(a2).compareTo(b1.distanceTo(b2)) }
            ) { offset, box ->
                boxes.drop(offset + 1).map { it to box }
            }

        val circuits = mutableListOf<MutableSet<Box>>()
        repeat(if (part2) closest.size else 1000) {
            val (box1, box2) = closest.poll()
            val circuit1 = circuits.find { box1 in it }
            val circuit2 = circuits.find { box2 in it }
            when {
                circuit1 == null && circuit2 == null -> circuits += mutableSetOf(box1, box2)
                circuit1 != null && circuit2 == null -> circuit1 += box2
                circuit1 == null && circuit2 != null -> circuit2 += box1
                circuit1 != null && circuit2 != null && circuit1 != circuit2 ->
                    circuit1.addAll(circuit2).also { circuits.remove(circuit2) }
            }
            if (part2 && circuits.singleOrNull()?.size == boxes.size) {
                return box1.x * box2.x
            }
        }

        return circuits.sortedByDescending { it.size }.take(3).fold(1) { acc, box -> acc * box.size }
    }
}

fun main() {
    print(Boxes().run(part2 = true))
}
