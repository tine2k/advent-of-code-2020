fun main() {
    val step1 = "L.LL.LL.LL\n" +
            "LLLLLLL.LL\n" +
            "L.L.L..L..\n" +
            "LLLL.LL.LL\n" +
            "L.LL.LL.LL\n" +
            "L.LLLLL.LL\n" +
            "..L.L.....\n" +
            "LLLLLLLLLL\n" +
            "L.LLLLLL.L\n" +
            "L.LLLLL.LL"

    fun convertToArray(lines: List<String>): List<CharArray> {
        return lines.map { it.toCharArray() }
    }

    fun getSeat(input: List<CharArray>, lineNo: Int, charNo: Int): Char {
        return if (lineNo >= 0 && lineNo < input.size && charNo >= 0 && charNo < input[lineNo].size) {
            input[lineNo][charNo]
        } else {
            'X'
        }
    }

    fun getAdjacentSeat(input: List<CharArray>, lineNo: Int, charNo: Int, diffLine: Int, diffChar: Int): Char {
        return getSeat(input, lineNo + diffLine, charNo + diffChar)
    }

    fun getVisibleSeat(input: List<CharArray>, lineNo: Int, charNo: Int, diffLine: Int, diffChar: Int): Char {
        var visibleSeat = '.'
        var i = 1
        do {
            visibleSeat = getSeat(input, lineNo + (diffLine * i), charNo + (diffChar * i))
            i++
        } while(visibleSeat == '.')
        return visibleSeat
    }

    fun getOccupiedSeatCount(input: List<CharArray>, lineNo: Int, charNo: Int,
                                     seatCountFn: (input: List<CharArray>, lineNo: Int, charNo: Int, diffLine: Int, diffChar: Int) -> Char): Int {
        var count = 0
        if (seatCountFn(input, lineNo, charNo, -1, -1) == '#') count++
        if (seatCountFn(input, lineNo, charNo, -1, +0) == '#') count++
        if (seatCountFn(input, lineNo, charNo, -1, +1) == '#') count++
        if (seatCountFn(input, lineNo, charNo, +0, +1) == '#') count++
        if (seatCountFn(input, lineNo, charNo, +0, -1) == '#') count++
        if (seatCountFn(input, lineNo, charNo, +1, -1) == '#') count++
        if (seatCountFn(input, lineNo, charNo, +1, +0) == '#') count++
        if (seatCountFn(input, lineNo, charNo, +1, +1) == '#') count++
        return count
    }

    fun getOccupiedAdjacentSeatCount(input: List<CharArray>, lineNo: Int, charNo: Int): Int {
        return getOccupiedSeatCount(input, lineNo, charNo, ::getAdjacentSeat)
    }

    fun getOccupiedVisibleSeatCount(input: List<CharArray>, lineNo: Int, charNo: Int): Int {
        return getOccupiedSeatCount(input, lineNo, charNo, ::getVisibleSeat)
    }

    fun processStep(input: List<CharArray>, minSeatCount: Int, seatCountFn: (input: List<CharArray>, lineNo: Int, charNo: Int) -> Int): List<CharArray> {
        val output = mutableListOf<CharArray>()
        input.forEachIndexed { lineNo, line ->
            output.add(CharArray(line.size))
            line.forEachIndexed { charNo, char ->
                val occupiedAdjacentSeatCount = seatCountFn(input, lineNo, charNo)
                if (char == 'L' && occupiedAdjacentSeatCount == 0) {
                    output[lineNo][charNo] = '#'
                } else if (char == '#' && occupiedAdjacentSeatCount >= minSeatCount) {
                    output[lineNo][charNo] = 'L'
                } else {
                    output[lineNo][charNo] = char
                }
            }
        }
        return output
    }

    fun convertToString(input: List<CharArray>): String {
        return input.joinToString("\n", "", "", -1, "") { String(it) }
    }

    fun solve(lines: List<String>, minSeatCount: Int, seatCountFn: (input: List<CharArray>, lineNo: Int, charNo: Int) -> Int): Long {
        var inputStep = convertToArray(lines)
        while (true) {
            val step = processStep(inputStep, minSeatCount, seatCountFn)
            if (convertToString(step) == convertToString(inputStep)) {
                break
            } else {
                inputStep = step
            }
        }

        return inputStep.sumOf { it.count { it == '#' } }.toLong()
    }

    fun solve1(lines: List<String>): Long {
        return solve(lines, 4, ::getOccupiedAdjacentSeatCount)
    }

    fun solve2(lines: List<String>): Long {
        return solve(lines, 5, ::getOccupiedVisibleSeatCount)
    }

    header(1)
    test(::solve1, step1, 37)
    solve(::solve1)

    header(2)
    test(::solve2, step1, 26)
    solve(::solve2)
}
