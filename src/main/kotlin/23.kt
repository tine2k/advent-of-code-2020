data class Circle(val ranges: MutableList<IntRange>) {

    private val min: Int = ranges.map { it.first }.minOrNull()!!
    private val max: Int = ranges.map { it.last }.maxOrNull()!!

    fun remove3ElementsAfter(element: Int): List<Int> {
        return listOf(
            removeElementAfter(element),
            removeElementAfter(element),
            removeElementAfter(element)
        )
    }

    private fun removeElementAfter(element: Int): Int {
        val range = ranges.single { e -> e.contains(element) }
        val index = ranges.indexOf(range)
        if (range.last == element) {
            var nextIndex = index + 1
            if (index + 1 == ranges.size) {
                nextIndex = 0
            }
            val newRange = ranges[nextIndex]
            if (newRange.count() == 1) {
                ranges.remove(newRange)
            } else {
                ranges[nextIndex] = newRange.first + 1..newRange.last
            }
            return newRange.first
        } else {
            ranges[index] = range.first..element
            if (element + 1 < range.last) {
                ranges.add(index + 1, element + 2..range.last)
            }
            return element + 1
        }
    }

    fun insertElementsAfterElement(element: Int, newElements: List<Int>) {
        assert(newElements.size == 3)
        insertAfterElement(element, newElements[0])
        insertAfterElement(newElements[0], newElements[1])
        insertAfterElement(newElements[1], newElements[2])
    }

    private fun insertAfterElement(element: Int, newElement: Int) {
        val range = ranges.single { e -> e.contains(element) }
        val index = ranges.indexOf(range)
        if (range.last == element) {
            if (newElement == element + 1) {
                ranges[index] = range.first..newElement
            } else {
                ranges.add(index + 1, newElement..newElement)
            }
        } else {
            ranges[index] = range.first..element
            ranges.add(index + 1, newElement..newElement)
            ranges.add(index + 2, element + 1..range.last)
        }
    }

    fun getElementAfter(element: Int): Int {
        val range = ranges.single { e -> e.contains(element) }
        return if (range.last == element) {
            if (ranges.last() == range) {
                ranges[0].first
            } else {
                ranges[ranges.indexOf(range) + 1].first
            }
        } else {
            element + 1
        }
    }

    private fun hasElement(element: Int): Boolean {
        return ranges.any { e -> e.contains(element) }
    }

    fun getNextLowerElement(element: Int): Int {
        var newElement = element - 1
        if (newElement < min) {
            newElement = max
        }
        return if (hasElement(newElement)) {
            newElement
        } else {
            getNextLowerElement(newElement)
        }
    }
}

fun main() {
    val testInput = "389125467"

    fun solve(circle: Circle, count: Int): Circle {
        var currentCup = circle.ranges.first().first
        var counter = 0
        var t1 = System.currentTimeMillis()
        for (i in 0 until count) {
            counter++
            if (counter % 1_000 == 0) {
                println("" + counter + " in " + (System.currentTimeMillis() - t1) + " with size " + circle.ranges.size)
                t1 = System.currentTimeMillis()
            }

            val moveCups = circle.remove3ElementsAfter(currentCup)

            circle.insertElementsAfterElement(circle.getNextLowerElement(currentCup), moveCups)
            currentCup = circle.getElementAfter(currentCup)
        }
        return circle
    }

    fun solve1(lines: List<String>): Long {
        val cups =
            solve(Circle(lines[0].toCharArray().map { it.toString().toInt() }.map { it..it }.toMutableList()), 100)

        var rv = ""
        var nextElement = cups.getElementAfter(1)
        while (nextElement != 1) {
            rv += nextElement
            nextElement = cups.getElementAfter(nextElement)
        }
        return rv.toLong()
    }

    fun solve2(lines: List<String>): Long {
        val initDigits = lines[0].toCharArray()
            .map { it.toString().toInt() }
        val cups = initDigits
            .map { it..it }
            .toMutableList()
        cups.add(initDigits.maxOrNull()!!+1..1_000_000)

        val newCups = solve(Circle(cups), 10_000_000)

        return newCups.getElementAfter(1) * newCups.getElementAfter(newCups.getElementAfter(1)).toLong()
    }

    header(1)
    test(::solve1, testInput, 67384529)
    solve(::solve1)

    header(2)
    test(::solve2, testInput, 149245887792)
    solve(::solve2)
}
