fun main() {
    val testInput = "abc\n" +
            "\n" +
            "a\n" +
            "b\n" +
            "c\n" +
            "\n" +
            "ab\n" +
            "ac\n" +
            "\n" +
            "a\n" +
            "a\n" +
            "a\n" +
            "a\n" +
            "\n" +
            "b"

    fun solve1(lines: List<String>): Long {
        var rv =  0
        var answers = mutableSetOf<Char>()
        lines.forEachIndexed { i, it ->
            answers.addAll(it.toCharArray().asIterable())

            if (it.isBlank() || i == lines.size - 1) {
                rv += answers.size
                answers = mutableSetOf()
            }
        }
        return rv.toLong()
    }

    fun solve2(lines: List<String>): Long {
        var rv =  0
        var people = 0
        var answers = mutableMapOf<Char, Int>()
        lines.forEachIndexed { i, it ->
            it.toCharArray().asIterable().forEach { c ->
                answers.putIfAbsent(c, 0);
                answers[c] = answers[c]!! + 1
            }
            if (it.isNotBlank()) {
                people++
            }

            if (it.isBlank() || i == lines.size - 1) {
                rv += answers.filter { it.value == people }.size
                answers = mutableMapOf()
                people = 0
            }
        }
        return rv.toLong()
    }

    header(1)
    test(::solve1, testInput, 11)
    solve(::solve1)

    header(2)
    test(::solve2, testInput, 6)
    solve(::solve2)
}

