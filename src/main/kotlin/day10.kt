import java.lang.reflect.Array.set
import java.util.LinkedList
import utils.readInputLines

/** [https://adventofcode.com/2025/day/10] */
class Day10 : AdventOfCodeTask {
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
                chunks
                    .drop(1)
                    .dropLast(1)
                    .map { switch -> switch.substringAfter('(').substringBefore(')').split(",").map { it.toInt() } }
                    .toSet()
            val initial = MutableList(expected.size) { 0 }
            val queue = LinkedList(switches.map { switch -> Triple(initial, switch, 1) })
            val seen = mutableSetOf<MutableList<Int>>()
            loop@ while (queue.isNotEmpty()) {
                val (current, switch, presses) = queue.removeFirst()
                if (!part2) {
                    seen += current
                }
                val next =
                    current.toMutableList().apply {
                        switch.forEach { index ->
                            val oldValue = get(index)
                            val newValue = if (part2) oldValue + 1 else oldValue xor 1
                            set(index, newValue)
                        }
                    }
                if (!part2 && next in seen) {
                    continue
                }
                if (next == expected) {
                    return@sumOf presses
                }
                switches.map { switch -> Triple(next, switch, presses + 1) }.toCollection(queue)
            }
            -1
        }
}

fun main() {
    print(Day10().run(part2 = false))
}
