import org.apache.commons.io.IOUtils
import java.util.regex.Pattern

fun main() {
    val lines = IOUtils.readLines(IOUtils::class.java.getResourceAsStream("/2.txt"), "UTF-8")

    var validCount = 0
    val pattern = Pattern.compile("(\\d+)-(\\d+)\\s(\\w):\\s(.*)")
    lines.forEach {
        System.out.println(it)
        val m = pattern.matcher(it)
        m.matches()
        val min = Integer.valueOf(m.group(1))
        val max = Integer.valueOf(m.group(2))
        val char = m.group(3)
        val value = m.group(4)

        if (value.substring(min - 1, min).equals(char) xor value.substring(max - 1, max).equals(char)) {
            validCount++
        }
    }

    System.out.println(validCount)
}


