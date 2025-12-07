import java.util.concurrent.ConcurrentSkipListMap
import utils.Coordinate
import utils.readInputLines

/** [https://adventofcode.com/2025/day/7] */
class Beams : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val map =
            readInputLines("7.txt")
                .flatMapIndexed { y, line -> line.mapIndexed { x, c -> Coordinate(x, y) to c } }
                .toMap()

        val cache = ConcurrentSkipListMap<Coordinate, Long>()
        fun move(current: Coordinate): Long =
            if (!part2 && current in cache) {
                1L
            } else {
                cache.computeIfAbsent(current) {
                    if (current !in map) {
                        return@computeIfAbsent 1
                    }
                    if (map[current] == '^') {
                        move(current.left()) + move(current.right())
                    } else {
                        move(current.down(offset = true))
                    }
                }
            }

        return move(map.entries.first { it.value == 'S' }.key).let { if (part2) it else it - 1 }
    }
}

fun main() {
    print(Beams().run(part2 = false))
}
