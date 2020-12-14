import org.apache.commons.lang3.StringUtils
import java.lang.Long.parseUnsignedLong
import java.lang.Long.toBinaryString

fun main() {
    val testInput = "mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X\n" +
            "mem[8] = 11\n" +
            "mem[7] = 101\n" +
            "mem[8] = 0"

    fun solve1(lines: List<String>): Long {
        val memory = mutableMapOf<Int, Long>()
        var mask = ""
        lines.forEach {
            val tokens = it.split(" = ")
            val command = tokens[0]
            val value = tokens[1]

            if (command.startsWith("mem")) {
                val newValue = StringUtils.leftPad(toBinaryString(value.toLong()), mask.length, "0").toCharArray()
                for (i in newValue.size - 1 downTo 0) {
                    if (mask[i] != 'X') {
                        newValue[i] = mask[i]
                    }
                }
                memory[command.split("[", "]")[1].toInt()] = parseUnsignedLong(String(newValue), 2)
            } else if (command == "mask") {
                mask = value
            }
        }
        return memory.values.sum()
    }

    fun solve2(lines: List<String>): Long {
        return 1337
    }

    header(1)
    test(::solve1, testInput, 165)
    solve(::solve1)

    header(2)
    solve(::solve2)
}
