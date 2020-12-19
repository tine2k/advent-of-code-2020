import com.google.common.collect.Collections2
import com.google.common.collect.ImmutableSet


fun main() {
    val testInput = ".#.\n" +
            "..#\n" +
            "###"

    fun getNeighbor(center: List<Int>, index: Int, direction: Int): List<Int> {
        val arrayList = ArrayList(center)
        arrayList[index] += direction
        return arrayList
    }

    fun getNeighbors(center: List<Int>, index: Int): Set<List<Int>> {
        return if (index < center.size) {
            getNeighbors(getNeighbor(center, index, -1), index + 1)
                .union(getNeighbors(getNeighbor(center, index, 0), index + 1))
                .union(getNeighbors(getNeighbor(center, index, +1), index + 1))
        } else {
            setOf(center);
        }
    }

    fun getActiveNeighbourCount(activeCubes: MutableSet<List<Int>>, center: List<Int>): Int {
        val neighborHood = getNeighbors(center, 0).toMutableSet()
        neighborHood.remove(center)
        return neighborHood.filter { activeCubes.contains(it) }.count()
    }

    fun runRound(activeCubes: MutableSet<List<Int>>): MutableSet<List<Int>> {
        val newCubes = mutableSetOf<List<Int>>()
        activeCubes
            .flatMap { getNeighbors(it, 0) }
            .forEach {
                if (activeCubes.contains(it)) {
                    if (getActiveNeighbourCount(activeCubes, it) in 2..3) {
                        newCubes.add(it)
                    }
                } else {
                    if (getActiveNeighbourCount(activeCubes, it) == 3) {
                        newCubes.add(it)
                    }
                }
            }
        return newCubes
    }

    fun solve1(lines: List<String>): Long {
        val activeCubes = mutableSetOf<List<Int>>()
        lines.forEachIndexed { i, it ->
            it.toCharArray().forEachIndexed { j, char ->
                if (char == '#') activeCubes.add(listOf(0, i, j))
            }
        }

        var currentCubes = activeCubes
        for (i in 0..5) {
            currentCubes = runRound(currentCubes)
        }

        return currentCubes.size.toLong()
    }

    fun solve2(lines: List<String>): Long {
        return 1337
    }

    header(1)
    test(::solve1, testInput, 112)
    solve(::solve1)

    header(2)
    solve(::solve2)
}
