import com.google.ortools.Loader
import com.google.ortools.linearsolver.MPSolver
import java.util.LinkedList
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
                Loader.loadNativeLibraries()
                val solver = MPSolver.createSolver("CBC")
                val variables = switches.indices.map { solver.makeIntVar(0.0, Double.POSITIVE_INFINITY, "$it") }

                expected.forEachIndexed { index, value ->
                    solver.makeConstraint(value.toDouble(), value.toDouble()).apply {
                        switches
                            .withIndex()
                            .filter { (_, toggles) -> index in toggles }
                            .forEach { (switchIndex, _) -> setCoefficient(variables[switchIndex], 1.0) }
                    }
                }

                solver.objective().apply {
                    variables.forEach { setCoefficient(it, 1.0) }
                    setMinimization()
                }
                solver.solve()

                variables.sumOf { it.solutionValue() }.toInt()
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
