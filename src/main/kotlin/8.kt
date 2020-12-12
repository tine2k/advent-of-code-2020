data class Operation(val i: Int, val cmd: String, val param: Int)
data class ExitCode(val code: Int, val value: Long)

fun main() {
    val testInput = "nop +0\n" +
            "acc +1\n" +
            "jmp +4\n" +
            "acc +3\n" +
            "jmp -3\n" +
            "acc -99\n" +
            "acc +1\n" +
            "jmp -4\n" +
            "acc +6"

    fun getOperations(lines: List<String>): List<Operation> {
        val operations = mutableListOf<Operation>()
        lines.forEachIndexed { i, line ->
            val lineSplit = line.split(" ")
            operations.add(Operation(i, lineSplit[0], Integer.valueOf(lineSplit[1])))
        }
        return operations;
    }

    fun runProgram(operations: List<Operation>, mutateIdx: Int): ExitCode {
        var acc = 0L
        var idx = 0
        val history = mutableListOf<Operation>()
        while(true) {
            if (idx >= operations.size) {
                return ExitCode(0, acc)
            }
            val operation = operations[idx]
            if (history.contains(operation)) {
                return ExitCode(1, acc)
            }

            // mutate
            var cmd = operation.cmd
            if (operation.i == mutateIdx) {
                when(cmd) {
                    "nop" -> cmd = "jmp"
                    "jmp" -> cmd = "nop"
                }
            }

            when (cmd) {
                "acc" -> {
                    acc += operation.param
                    idx++
                }
                "jmp" -> idx += operation.param
                else -> idx++
            }
            history.add(operation)
        }
    }

    fun solve1(lines: List<String>): Long {
        return runProgram(getOperations(lines), -1).value
    }

    fun solve2(lines: List<String>): Long {
        val operations = getOperations(lines)
        operations.forEachIndexed { i, o ->
            val exitCode = runProgram(operations, i)
            if (exitCode.code == 0) {
                return exitCode.value
            }
        }
        return -1
    }

    header(1)
    test(::solve1, testInput, 5)
    solve(::solve1)

    header(2)
    test(::solve2, testInput, 8)
    solve(::solve2)
}
