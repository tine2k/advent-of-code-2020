import org.apache.commons.lang3.StringUtils
import java.lang.Long.parseUnsignedLong
import java.lang.Long.toBinaryString
import java.util.*

fun main() {
    val testInput = "mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X\n" +
            "mem[8] = 11\n" +
            "mem[7] = 101\n" +
            "mem[8] = 0"

    val testInput2 = "mask = 000000000000000000000000000000X1001X\n" +
            "mem[42] = 100\n" +
            "mask = 00000000000000000000000000000000X0XX\n" +
            "mem[26] = 1"

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

    fun getCombinations(initial: CharArray): Set<String> {
        val stack: Stack<CharArray> = Stack()
        val combinations = mutableSetOf<String>()
        stack.push(initial)

        while(!stack.empty()) {
            val mask = stack.pop()

            val xSpot = mask.indexOf('X')
            if (xSpot == -1) {
                combinations.add(String(mask))
            } else {
                val m1 = mask.clone()
                m1[xSpot] = '0'
                combinations.addAll(getCombinations(m1))

                val m2 = mask.clone()
                m2[xSpot] = '1'
                combinations.addAll(getCombinations(m2))
            }
        }

        return combinations;
    }

    fun solve2(lines: List<String>): Long {
        val memory = mutableMapOf<Long, Long>()
        var mask = ""
        lines.forEach {
            val tokens = it.split(" = ")
            val command = tokens[0]
            val value = tokens[1]

            if (command.startsWith("mem")) {
                val address = command.split("[", "]")[1].toLong()
                val newAddress = StringUtils.leftPad(toBinaryString(address), mask.length, "0").toCharArray()
                for (i in newAddress.size - 1 downTo 0) {
                    if (mask[i] != '0') {
                        newAddress[i] = mask[i]
                    }
                }
                getCombinations(newAddress).forEach { comb ->
                    memory[parseUnsignedLong(comb, 2)] = value.toLong()
                }
            } else if (command == "mask") {
                mask = value
            }
        }
        return memory.values.sum()
    }

    header(1)
    test(::solve1, testInput, 165)
    solve(::solve1)

    header(2)
    test(::solve2, testInput2, 208)
    solve(::solve2)
}
