data class Circle(val data: MutableMap<Int,Int>, val min: Int, val max: Int) {

    fun remove3ElementsAfter(element: Int): List<Int> {
        val nextElement1 = getElementAfter(element)
        val nextElement2 = getElementAfter(nextElement1)
        val nextElement3 = getElementAfter(nextElement2)
        val newTarget = getElementAfter(nextElement3)
        data.remove(nextElement1)
        data.remove(nextElement2)
        data.remove(nextElement3)
        if (element + 1 == newTarget) {
            data.remove(element)
        } else {
            data[element] = newTarget
        }
        return listOf(nextElement1, nextElement2, nextElement3)
    }

    fun insertElementsAfterElement(element: Int, newElements: List<Int>) {
        assert(newElements.size == 3)
        val nextElement = getElementAfter(element)
        data[element] = newElements[0]
        data[newElements[0]] = newElements[1]
        data[newElements[1]] = newElements[2]
        data[newElements[2]] = nextElement
    }

    fun getElementAfter(element: Int): Int {
        return data[element] ?: element + 1
    }

    fun getNextLowerElement(element: Int, lastRemoved: List<Int>): Int {
        var nextLower = element
        do {
            nextLower -= 1
            if (nextLower < min) {
                nextLower = max
            }
        } while(lastRemoved.contains(nextLower))
        return nextLower
    }
}

fun main() {
    val testInput = "389125467"

    fun solve(circle: Circle, count: Int, start: Int): Circle {
        var currentCup = start
        for (i in 0 until count) {
            val moveCups = circle.remove3ElementsAfter(currentCup)
            circle.insertElementsAfterElement(circle.getNextLowerElement(currentCup, moveCups), moveCups)
            currentCup = circle.getElementAfter(currentCup)
        }
        return circle
    }

    fun initData(lines: List<String>) : Pair<MutableMap<Int,Int>, Int> {
        val initDigits = lines[0].toCharArray().map { it.toString().toInt() }
        val data = mutableMapOf<Int,Int>()
        initDigits.forEachIndexed { i, d ->
            if (i == initDigits.size - 1) {
                data[d] = initDigits[0]
            } else {
                data[d] = initDigits[i + 1]
            }
        }
        return Pair(data, initDigits[0])
    }

    fun solve1(lines: List<String>): Long {
        val initData = initData(lines)
        val data = initData(lines).first
        val cups = solve(Circle(data, data.keys.minOrNull()!!, data.keys.maxOrNull()!!), 100, initData.second)

        var rv = ""
        var nextElement = cups.getElementAfter(1)
        while (nextElement != 1) {
            rv += nextElement
            nextElement = cups.getElementAfter(nextElement)
        }
        return rv.toLong()
    }

    fun solve2(lines: List<String>): Long {
        val initData = initData(lines)
        val data = initData.first
        data[lines[0].last().toString().toInt()] = data.keys.maxOrNull()!! + 1
        data[1_000_000] = initData.second

        val newCups = solve(Circle(data, data.keys.minOrNull()!!, 1_000_000), 10_000_000, initData.second)

        return newCups.getElementAfter(1) * newCups.getElementAfter(newCups.getElementAfter(1)).toLong()
    }

    header(1)
    test(::solve1, testInput, 67384529)
    solve(::solve1)

    header(2)
    test(::solve2, testInput, 149245887792)
    solve(::solve2)
}
