fun main() {
    val testInput = "Player 1:\n" +
            "9\n" +
            "2\n" +
            "6\n" +
            "3\n" +
            "1\n" +
            "\n" +
            "Player 2:\n" +
            "5\n" +
            "8\n" +
            "4\n" +
            "7\n" +
            "10"

    fun parseInput(lines: List<String>): Pair<MutableList<Int>, MutableList<Int>> {
        val p1 = mutableListOf<Int>()
        val p2 = mutableListOf<Int>()
        var currentStack = p1
        lines.forEach {
            when (it) {
                "Player 1:" -> currentStack = p1
                "Player 2:" -> currentStack = p2
                else -> if (it != "") currentStack.add(it.toInt())
            }
        }
        return Pair(p1, p2)
    }

    fun solve1(lines: List<String>): Long {
        val decks = parseInput(lines)
        while (decks.first.isNotEmpty() && decks.second.isNotEmpty()) {
            val c1 = decks.first.removeAt(0)
            val c2 = decks.second.removeAt(0)
            when {
                c1 > c2 -> {
                    decks.first.add(c1)
                    decks.first.add(c2)
                }
                c1 < c2 -> {
                    decks.second.add(c2)
                    decks.second.add(c1)
                }
                else -> {
                    assert(false)
                }
            }
        }
        return (decks.first + decks.second).reversed().mapIndexed { i, v -> (i + 1) * v }.sum().toLong()
    }

    fun solve2(lines: List<String>): Long {
        return 1337
    }

    header(1)
    test(::solve1, testInput, 306)
    solve(::solve1)

    header(2)
    test(::solve2, testInput, 1337)
    solve(::solve2)
}
