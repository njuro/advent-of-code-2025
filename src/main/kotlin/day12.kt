import utils.readInputBlock

/** [https://adventofcode.com/2025/day/12] */
class Shapes : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val chunks = readInputBlock("12.txt").trim().split("\n\n")
        val shapes = chunks.dropLast(1).map { chunk -> chunk.count { it == '#' } }

        return chunks.last().lines().count { region ->
            val (width, height) = region.substringBefore(": ").split('x').map(String::toInt)
            val requirements = region.substringAfter(": ").split(' ').map(String::toInt)
            val requiredArea = shapes.zip(requirements).sumOf { (shape, amount) -> shape * amount }
            requiredArea <= width * height
        }
    }
}

fun main() {
    print(Shapes().run(part2 = true))
}
