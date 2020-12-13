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
    val step2 = "#.##.##.##\n" +
            "#######.##\n" +
            "#.#.#..#..\n" +
            "####.##.##\n" +
            "#.##.##.##\n" +
            "#.#####.##\n" +
            "..#.#.....\n" +
            "##########\n" +
            "#.######.#\n" +
            "#.#####.##"
    val step3 = "#.LL.L#.##\n" +
            "#LLLLLL.L#\n" +
            "L.L.L..L..\n" +
            "#LLL.LL.L#\n" +
            "#.LL.LL.LL\n" +
            "#.LLLL#.##\n" +
            "..L.L.....\n" +
            "#LLLLLLLL#\n" +
            "#.LLLLLL.L\n" +
            "#.#LLLL.##"
    val step4 = "#.##.L#.##\n" +
            "#L###LL.L#\n" +
            "L.#.#..#..\n" +
            "#L##.##.L#\n" +
            "#.##.LL.LL\n" +
            "#.###L#.##\n" +
            "..#.#.....\n" +
            "#L######L#\n" +
            "#.LL###L.L\n" +
            "#.#L###.##"
    val step5 = "#.#L.L#.##\n" +
            "#LLL#LL.L#\n" +
            "L.L.L..#..\n" +
            "#LLL.##.L#\n" +
            "#.LL.LL.LL\n" +
            "#.LL#L#.##\n" +
            "..L.L.....\n" +
            "#L#LLLL#L#\n" +
            "#.LLLLLL.L\n" +
            "#.#L#L#.##"
    val step6 = "#.#L.L#.##\n" +
            "#LLL#LL.L#\n" +
            "L.#.L..#..\n" +
            "#L##.##.L#\n" +
            "#.#L.LL.LL\n" +
            "#.#L#L#.##\n" +
            "..L.L.....\n" +
            "#L#L##L#L#\n" +
            "#.LLLLLL.L\n" +
            "#.#L#L#.##"

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

    fun getOccupiedAdjacentSeatCount(input: List<CharArray>, lineNo: Int, charNo: Int): Int {
        var count = 0;
        if (getSeat(input, lineNo - 1, charNo - 1) == '#') count++
        if (getSeat(input, lineNo - 1, charNo + 0) == '#') count++
        if (getSeat(input, lineNo - 1, charNo + 1) == '#') count++
        if (getSeat(input, lineNo + 0, charNo + 1) == '#') count++
        if (getSeat(input, lineNo + 0, charNo - 1) == '#') count++
        if (getSeat(input, lineNo + 1, charNo - 1) == '#') count++
        if (getSeat(input, lineNo + 1, charNo + 0) == '#') count++
        if (getSeat(input, lineNo + 1, charNo + 1) == '#') count++
        return count;
    }

    fun processStep(input: List<CharArray>): List<CharArray> {
        val output = mutableListOf<CharArray>()
        input.forEachIndexed { lineNo, line ->
            output.add(CharArray(line.size))
            line.forEachIndexed { charNo, char ->
                val occupiedAdjacentSeatCount = getOccupiedAdjacentSeatCount(input, lineNo, charNo)
                if (char == 'L' && occupiedAdjacentSeatCount == 0) {
                    output[lineNo][charNo] = '#'
                } else if (char == '#' && occupiedAdjacentSeatCount >= 4) {
                    output[lineNo][charNo] = 'L'
                } else {
                    output[lineNo][charNo] = char
                }
            }
        }
        return output
    }

    fun convertToString(input: List<CharArray>): String {
        return input.joinToString("\n","","", -1, "") { String(it) }
    }

    fun solve1(lines: List<String>): Long {
        var inputStep = convertToArray(lines)
        while(true) {
            val step = processStep(inputStep)
            if (convertToString(step) == convertToString(inputStep)) {
                break
            } else {
                inputStep = step
            }
        }

        return inputStep.sumOf { it.count { it == '#' } }.toLong()
    }

    fun solve2(lines: List<String>): Long {
        return 1337
    }

    header(1)
    test(::solve1, step1, 37)
    solve(::solve1)

    header(2)
    solve(::solve2)
}
