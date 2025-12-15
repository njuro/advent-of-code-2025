import java.util.LinkedList
import kotlin.math.roundToInt
import org.ojalgo.optimisation.ExpressionsBasedModel
import utils.readInputLines

/** [https://adventofcode.com/2025/day/10] */
class Switches : AdventOfCodeTask {

    override fun run(part2: Boolean): Any =
        readInputLines("10.txt").sumOf { line ->
            val chunks = line.split(" ")
            val expected =
                if (part2) {
                    chunks.last().substringAfter('{').substringBefore('}').split(",").map { it.toInt() }
                } else {
                    chunks.first().substringAfter('[').substringBefore(']').map { if (it == '#') 1 else 0 }
                }
            val switches =
                chunks.drop(1).dropLast(1).map { switch ->
                    switch.substringAfter('(').substringBefore(')').split(",").map { it.toInt() }.toSet()
                }
            if (part2) {
                val model = ExpressionsBasedModel()
                val variables =
                    switches.indices.map {
                        model.addVariable("$it").lower(0.0).upper(expected.max()).integer(true).weight(1.0)
                    }

                expected.forEachIndexed { index, value ->
                    model.addExpression("$index").level(value).apply {
                        switches
                            .withIndex()
                            .filter { (_, toggles) -> index in toggles }
                            .forEach { (switchIndex, _) -> set(variables[switchIndex], 1.0) }
                    }
                }

                model.minimise()

                variables.sumOf { it.value.toDouble().roundToInt() }
            } else {
                val initial = MutableList(expected.size) { 0 }
                val queue = LinkedList(switches.map { switch -> Triple(initial, switch, 1) })
                val seen = mutableSetOf<MutableList<Int>>()
                while (queue.isNotEmpty()) {
                    val (current, switch, presses) = queue.removeFirst()
                    seen += current
                    val next =
                        current.toMutableList().apply { switch.forEach { index -> set(index, get(index) xor 1) } }
                    if (next in seen) {
                        continue
                    }
                    if (next == expected) {
                        return@sumOf presses
                    }
                    switches.map { switch -> Triple(next, switch, presses + 1) }.toCollection(queue)
                }

                error("No solution found")
            }
        }
}

fun main() {
    print(Switches().run(part2 = true))
}
