import java.lang.IllegalStateException

fun main() {
    val testInput = "5764801\n17807724"

    fun performOperation(subjectNumber: Long, loopSize: Long): Long {
        var newInput = 1L
        for (i in 0 until loopSize) {
            newInput = (newInput * subjectNumber) % 20201227
        }
        return newInput
    }

    fun findLoopSize(publicKey: Long): Long {
        var newInput = 1L
        for (i in 1 until 10_000_000) {
            newInput = (newInput * 7L) % 20201227
            if (newInput == publicKey) {
                return i.toLong()
            }
        }
        throw IllegalStateException("no solution found")
    }

    fun solve1(lines: List<String>): Long {
        val pkKey = lines[0].toLong()
        val pkDoor = lines[1].toLong()
        val lsKey = findLoopSize(pkKey)
        return performOperation(pkDoor, lsKey)
    }

    header(1)
    test(::solve1, testInput, 14897079)
    solve(::solve1)
}
