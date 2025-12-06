import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AdventOfCodeTasksTest {

    @Test
    fun day01() {
        runTaskTest(Lock(), 1007, 5820)
    }

    @Test
    fun day02() {
        runTaskTest(Ids(), 40055209690, 50857215650)
    }

    @Test
    fun day03() {
        runTaskTest(Elevators(), 17694L, 175659236361660)
    }

    @Test
    fun day04() {
        runTaskTest(Forklifts(), 1547, 8948)
    }

    @Test
    fun day05() {
        runTaskTest(Ranges(), 848, 334714395325710)
    }

    @Test
    fun day06() {
        runTaskTest(Spaces(), 4364617236318, 9077004354241)
    }

    private fun runTaskTest(task: AdventOfCodeTask, part1Result: Any, part2Result: Any) {
        assertEquals(part1Result, task.run())
        assertEquals(part2Result, task.run(part2 = true))
    }
}
