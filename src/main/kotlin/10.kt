fun main() {
    val testInput = "28\n" +
            "33\n" +
            "18\n" +
            "42\n" +
            "31\n" +
            "14\n" +
            "46\n" +
            "20\n" +
            "48\n" +
            "47\n" +
            "24\n" +
            "23\n" +
            "49\n" +
            "45\n" +
            "19\n" +
            "38\n" +
            "39\n" +
            "11\n" +
            "1\n" +
            "32\n" +
            "25\n" +
            "35\n" +
            "8\n" +
            "17\n" +
            "7\n" +
            "9\n" +
            "4\n" +
            "2\n" +
            "34\n" +
            "10\n" +
            "3"

    fun getAdapters(lines: List<String>): MutableList<Int> {
        val adapters = lines.map { it.toInt() }.sorted().toMutableList()
        adapters.add(0, 0)
        adapters.add(adapters.maxOrNull()!! + 3)
        return adapters
    }

    fun solve1(lines: List<String>): Long {
        val adapters = getAdapters(lines)

        var ones = 0
        var threes = 0
        for (i in 0 until adapters.size - 1) {
            if (adapters[i] + 1 == adapters[i + 1]) {
                ones++
            } else if (adapters[i] + 3 == adapters[i + 1]) {
                threes++
            }
        }
        return (ones * threes).toLong()
    }

    fun solve2(lines: List<String>): Long {
        val adapters = getAdapters(lines)
        val diff = mutableListOf<Int>()
        diff.add(1)
        adapters.forEachIndexed { i, x ->
            if (i < adapters.size - 1) {
                diff.add(adapters[i + 1] - x)
            }
        }

        val combinations = MutableList<Long>(diff.size) { 0 }
        diff.forEachIndexed { i, n ->
            if (i == 0) {
                combinations[i] = 1
            } else {
                combinations[i] = combinations[i - 1]
                if (n == 1 && diff[i - 1] == 1 && i >= 2) {
                    combinations[i] += combinations[i - 2]
                    if (diff[i - 2] == 1 && i >= 3) {
                        combinations[i] += combinations[i - 3]
                    }
                }
            }
        }

        return combinations.last()
    }

    header(1)
    test(::solve1, testInput, 220)
    solve(::solve1)

    header(2)
    test(::solve2, "16\n10\n15\n5\n1\n11\n7\n19\n6\n12\n4\n", 8)
    test(::solve2, "1\n4\n5\n6\n7\n", 4)
    test(::solve2, "3\n4\n5\n6", 4)
    test(::solve2, "1\n2\n3", 4)
    test(::solve2, "1\n3\n6", 1)
    test(::solve2, "1\n2\n3\n4", 7)
    test(::solve2, "1\n2\n3\n4\n5", 13)
    solve(::solve2)
}
