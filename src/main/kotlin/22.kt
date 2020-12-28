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

    val testInput2 = "Player 1:\n" +
            "43\n" +
            "19\n" +
            "\n" +
            "Player 2:\n" +
            "2\n" +
            "29\n" +
            "14"

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

    fun calcWinnerScore(decks: List<Int>): Long {
        return decks.reversed().mapIndexed { i, v -> (i + 1).toLong() * v.toLong() }.sum().toLong()
    }

    fun solve1(lines: List<String>): Long {
        val decks = parseInput(lines)
        while (decks.first.isNotEmpty() && decks.second.isNotEmpty()) {
            val c1 = decks.first.removeAt(0)
            val c2 = decks.second.removeAt(0)
            when {
                c1 > c2 -> decks.first.addAll(listOf(c1, c2))
                c1 < c2 -> decks.second.addAll(listOf(c2, c1))
                else -> assert(false)
            }
        }
        return calcWinnerScore(decks.first + decks.second)
    }

    fun playRecursiveCombat(decks: Pair<MutableList<Int>, MutableList<Int>>): Boolean {
        val history = mutableListOf<Long>()
        while (decks.first.isNotEmpty() && decks.second.isNotEmpty()) {
            val currentWinnerScore = calcWinnerScore(decks.first) * calcWinnerScore(decks.second)
            if (history.contains(currentWinnerScore)) {
                return true
            }
            history.add(currentWinnerScore)

            val c1 = decks.first.removeAt(0)
            val c2 = decks.second.removeAt(0)
            when {
                decks.first.size >= c1 && decks.second.size >= c2 -> {
                    if (playRecursiveCombat(Pair(decks.first.subList(0, c1).toMutableList(), decks.second.subList(0, c2).toMutableList()))) {
                        decks.first.addAll(listOf(c1, c2))
                    } else {
                        decks.second.addAll(listOf(c2, c1))
                    }
                }
                c1 > c2 -> decks.first.addAll(listOf(c1, c2))
                c1 < c2 -> decks.second.addAll(listOf(c2, c1))
                else -> assert(false)
            }
        }
        return decks.second.isEmpty()
    }

    fun solve2(lines: List<String>): Long {
        val decks = parseInput(lines)
        val p1Winner = playRecursiveCombat(decks)
        return calcWinnerScore(if (p1Winner) decks.first else decks.second)
    }

    header(1)
    test(::solve1, testInput, 306)
    solve(::solve1)

    header(2)
    test(::solve2, testInput, 291)
    test(::solve2, testInput2, 105)
    solve(::solve2)
}
