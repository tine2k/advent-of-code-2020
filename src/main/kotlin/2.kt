import org.apache.commons.lang3.StringUtils
import java.util.regex.Pattern

fun main() {
    val testInput = "1-3 a: abcde\n" +
            "1-3 b: cdefg\n" +
            "2-9 c: ccccccccc"
    val testResult = 2

    fun solve1(lines: List<String>): Long {
        var validCount = 0
        val pattern = Pattern.compile("(\\d+)-(\\d+)\\s(\\w):\\s(.*)")
        lines.forEach {
            val m = pattern.matcher(it)
            m.matches()
            val min = Integer.valueOf(m.group(1))
            val max = Integer.valueOf(m.group(2))
            val char = m.group(3)
            val value = m.group(4)

            val count = StringUtils.countMatches(value, char);
            if (count in min..max) {
                validCount++
            }
        }

        return validCount.toLong()
    }

    fun solve2(lines: List<String>): Long {
        var validCount = 0L
        val pattern = Pattern.compile("(\\d+)-(\\d+)\\s(\\w):\\s(.*)")
        lines.forEach {
            val m = pattern.matcher(it)
            m.matches()
            val min = Integer.valueOf(m.group(1))
            val max = Integer.valueOf(m.group(2))
            val char = m.group(3)
            val value = m.group(4)

            if ((value.substring(min - 1, min) == char) xor (value.substring(max - 1, max) == char)) {
                validCount++
            }
        }

        return validCount
    }

    solveAndTest(2, ::solve1, "One", testInput, testResult)
    solveAndTest(2, ::solve2, "Two")
}


