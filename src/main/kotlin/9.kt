import org.apache.commons.io.IOUtils
import java.io.StringReader

fun main() {
    val testInput = "35\n" +
            "20\n" +
            "15\n" +
            "25\n" +
            "47\n" +
            "40\n" +
            "62\n" +
            "55\n" +
            "65\n" +
            "95\n" +
            "102\n" +
            "117\n" +
            "150\n" +
            "182\n" +
            "127\n" +
            "219\n" +
            "299\n" +
            "277\n" +
            "309\n" +
            "576"
    val testResult = 127

    fun findNumber(number: Long, numbers: List<Long>): Boolean {
        numbers.forEach { i ->
            numbers.forEach { j ->
                if (i != j && i + j == number) {
                    return true
                }
            }
        }
        return false
    }

    fun findWeakNumber(preamble: Int, numbers: List<Long>): Long {
        for (i in preamble until numbers.size) {
            if (!findNumber(numbers[i], numbers.subList(i - preamble, i))) {
                return numbers[i]
            }
        }
        return -1
    }

    fun solve1(lines: List<String>): Long {
        val preamble = if (lines.size == IOUtils.readLines(StringReader(testInput)).size) 5 else 25
        val numbers = lines.map { it.toLong() }
        return findWeakNumber(preamble, numbers)
    }

    fun solve2(lines: List<String>): Long {
        val preamble = if (lines.size == IOUtils.readLines(StringReader(testInput)).size) 5 else 25
        val numbers = lines.map { it.toLong() }
        val weakNumber = findWeakNumber(preamble, numbers)

        numbers.forEachIndexed { i, _ ->
            for (j in i until numbers.size - 1) {
                val contiguousSet = numbers.subList(i, j)
                val sum = contiguousSet.sum()
                if (sum == weakNumber) {
                    return contiguousSet.minOrNull()!! + contiguousSet.maxOrNull()!!
                } else if (sum > weakNumber) {
                    break
                }
            }
        }
        return -1
    }

    solveAndTest(9, ::solve1, "One", testInput, testResult)
    solveAndTest(9, ::solve2, "Two", testInput, 62)
}
