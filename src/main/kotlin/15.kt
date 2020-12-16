fun main() {

    fun solve(lines: List<String>, number: Int): Long {
        val spoken = lines[0].split(",").map { it.toInt() }.toMutableList()
        val count = mutableMapOf<Int, Int>()
        spoken.forEach {
            count[it] = 1
        }
        val lastIndexOf = mutableMapOf<Int, Int>()
        spoken.forEachIndexed { i, n ->
            lastIndexOf[n] = i + 1
        }

        var lastSpoken = spoken[spoken.size - 1]
        for (i in spoken.size until number) {
            var newValue = 0
            if (count[lastSpoken] != 1) {
                newValue = i - lastIndexOf[lastSpoken]!!
            }
            count[newValue] = (count[newValue] ?: 0) + 1
            lastIndexOf[lastSpoken] = i
            lastSpoken = newValue
        }
        return lastSpoken.toLong()
    }

    fun solve1(lines: List<String>): Long {
        return solve(lines, 2020)
    }

    fun solve2(lines: List<String>): Long {
        return solve(lines, 30000000)
    }

    header(1)
    test(::solve1, "0,3,6", 436)
    test(::solve1, "1,3,2", 1)
    test(::solve1, "2,1,3", 10)
    test(::solve1, "1,2,3", 27)
    test(::solve1, "2,3,1", 78)
    test(::solve1, "3,2,1", 438)
    test(::solve1, "3,1,2", 1836)
    solve(::solve1)

    header(2)
    test(::solve2, "0,3,6", 175594)
    test(::solve2, "1,3,2", 2578)
    test(::solve2, "2,1,3", 3544142)
    test(::solve2, "1,2,3", 261214)
    test(::solve2, "2,3,1", 6895259)
    test(::solve2, "3,2,1", 18)
    test(::solve2, "3,1,2", 362)
    solve(::solve2)
}
