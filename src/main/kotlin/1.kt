fun main() {
    val day = 1
    val testInput = "1721\n" +
            "979\n" +
            "366\n" +
            "299\n" +
            "675\n" +
            "1456\n"

    fun solve1(lines: List<String>): Long {
        lines.forEach { a ->
            lines.forEach { b ->
                if (Integer.valueOf(a) + Integer.valueOf(b) == 2020) {
                    return Integer.valueOf(a).toLong() * Integer.valueOf(b).toLong()
                }
            }
        }
        return -1
    }

    fun solve2(lines: List<String>): Long {
        lines.forEach { a ->
            lines.forEach { b ->
                lines.forEach { c ->
                    if (Integer.valueOf(a) + Integer.valueOf(b) + Integer.valueOf(c) == 2020) {
                        return Integer.valueOf(a).toLong() * Integer.valueOf(b).toLong() * Integer.valueOf(c).toLong()
                    }
                }
            }
        }
        return -1
    }

    header(day, 1)
    test(::solve1, testInput, 514579)
    solve(day, ::solve1)

    header(day, 2)
    solve(day, ::solve2)
}
