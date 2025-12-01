import utils.readInputLines

/** [https://adventofcode.com/2025/day/1] */
class Lock : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        return readInputLines("1.txt").runningFold(listOf(50)) { previous, instruction ->
            val direction = instruction.first()
            val amount = instruction.drop(1).toInt()
            List(amount) { 0 }.runningFold(previous.last()) { current, _ ->
                val next = if (direction == 'R') current + 1 else current - 1
                if (next > 99) 0 else if (next < 0) 99 else next
            }.drop(1)
        }.sumOf { sequence -> if (part2) sequence.count { it == 0 } else if (sequence.last() == 0) 1 else 0 }
    }
}

fun main() {
    print(Lock().run(part2 = true))
}
