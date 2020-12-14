fun main() {
    val testInput = "939\n" +
            "7,13,x,x,59,x,31,19"

    fun solve1(lines: List<String>): Long {
        val time = lines[0].toLong()
        var lowestWait = Long.MAX_VALUE
        var busId = -1
        lines[1].split(",")
            .filter { it != "x" }
            .map { it.toInt() }
            .forEach { b ->
                val wait = b - (time % b)
                if (wait < lowestWait) {
                    busId = b
                    lowestWait = wait
                }
            }
        return busId * lowestWait
    }

    // gratefully borrow from https://rosettacode.org/wiki/Chinese_remainder_theorem#Kotlin ❤️
    fun multInv(a: Long, b: Long): Long {
        if (b == 1L) return 1
        var aa = a
        var bb = b
        var x0 = 0L
        var x1 = 1L
        while (aa > 1) {
            val q = aa / bb
            var t = bb
            bb = aa % bb
            aa = t
            t = x0
            x0 = x1 - q * x0
            x1 = t
        }
        if (x1 < 0) x1 += b
        return x1
    }

    fun chineseRemainder(n: LongArray, a: LongArray): Long {
        val prod = n.fold(1L) { acc, i -> acc * i }
        var sum = 0L
        for (i in n.indices) {
            val p = prod / n[i]
            sum += a[i] * multInv(p, n[i]) * p
        }
        return sum % prod
    }

    fun solve2(lines: List<String>): Long {
        val data = mutableListOf<Pair<Int, Int>>()
        lines[1].split(",")
            .forEachIndexed { i, t ->
                if (t != "x") {
                    data.add(Pair(t.toInt(), i))
                }
            }

        val n = data.map { it.first.toLong() }.toLongArray()
        val a = data.map { (it.first - it.second).toLong() }.toLongArray()
        return chineseRemainder(n, a)
    }

    header(1)
    test(::solve1, testInput, 295)
    solve(::solve1)

    header(2)
    test(::solve2, "\n17,x,13,19", 3417)
    test(::solve2, "\n67,7,59,61", 754018)
    test(::solve2, "\n67,x,7,59,61", 779210)
    test(::solve2, "\n67,7,x,59,61", 1261476)
    test(::solve2, testInput, 1068781)
    test(::solve2, "\n1789,37,47,1889", 1202161486)
    solve(::solve2)
}
