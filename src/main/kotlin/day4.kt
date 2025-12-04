import utils.Coordinate
import utils.readInputLines

/** [https://adventofcode.com/2025/day/4] */
class Forklifts : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val map =
            readInputLines("4.txt")
                .flatMapIndexed { y, line -> line.mapIndexed { x, c -> Coordinate(x, y) to c } }
                .toMap()
                .toMutableMap()
                .withDefault { '.' }

        fun removeAvailable(): Int =
            map.filterValues { it == '@' }
                .filterKeys { coordinate -> coordinate.adjacent8().count { map.getValue(it) == '@' } < 4 }
                .keys
                .onEach(map::remove)
                .size

        return if (part2) {
            generateSequence(::removeAvailable).takeWhile { it > 0 }.sum()
        } else {
            removeAvailable()
        }
    }
}

fun main() {
    print(Forklifts().run(part2 = true))
}
