import org.apache.commons.lang3.StringUtils
import java.util.regex.Pattern

fun main() {
    val testInput = "1-3 a: abcde\n" +
            "1-3 b: cdefg\n" +
            "2-9 c: ccccccccc"
    val testResult = 2

    fun solve(lines: List<String>, isValid: (min: Int, max: Int, char: String, value: String) -> Boolean): Long {
        var validCount = 0
        val pattern = Pattern.compile("(\\d+)-(\\d+)\\s(\\w):\\s(.*)")
        lines.forEach {
            val m = pattern.matcher(it)
            m.matches()
            val min = Integer.valueOf(m.group(1))
            val max = Integer.valueOf(m.group(2))
            val char = m.group(3)
            val value = m.group(4)

            if (isValid(min, max, char, value)) {
                validCount++
            }
        }

        return validCount.toLong()
    }

    fun solve1(lines: List<String>): Long {
        return solve(lines) { min, max, char, value ->
            val count = StringUtils.countMatches(value, char)
            count in min..max
        }
    }

    fun solve2(lines: List<String>): Long {
        return solve(lines) { min, max, char, value ->
            (value.substring(min - 1, min) == char) xor (value.substring(max - 1, max) == char)
        }
    }

    solveAndTest(2, ::solve1, "One", testInput, testResult)
    solveAndTest(2, ::solve2, "Two")
}


