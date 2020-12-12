fun main() {
    val day = 5
    val testInput = "BFFFBBFRRR"

    fun changeRange(rowRange: IntRange, forward: Boolean): IntRange {
        val rowBound = (rowRange.last + 1 - rowRange.first) / 2;
        return if (forward) {
            rowRange.first..(rowRange.last - rowBound)
        } else {
            (rowRange.first + rowBound)..rowRange.last
        }
    }

    fun solve(lines: List<String>): Pair<Int, Int> {
        var highestId = 0
        val takenSeats: MutableList<Boolean> = MutableList(8 * 128) { false }
        lines.forEach {
            var rowRange = 0..127
            var rowSeat = 0..7
            it.toCharArray().forEach { c ->
                if (c == 'F' || c == 'B') {
                    rowRange = changeRange(rowRange, c == 'F')
                }
                if (c == 'L' || c == 'R') {
                    rowSeat = changeRange(rowSeat, c == 'L')
                }
            }
            assert(rowRange.first == rowRange.last)
            assert(rowSeat.first == rowSeat.last)

            val seatId = rowRange.first * 8 + rowSeat.first
            highestId = Integer.max(seatId, highestId)

            takenSeats[seatId] = true
        }

        takenSeats.forEachIndexed { i, _ ->
            if (i > 7 && i < 8 * 126 && !takenSeats[i] && takenSeats[i + 1] && takenSeats[i - 1]) {
                return Pair(highestId, i)
            }
        }

        return Pair(highestId, -1)
    }

    fun solve1(lines: List<String>): Long {
        return solve(lines).first.toLong()
    }

    fun solve2(lines: List<String>): Long {
        return solve(lines).second.toLong()
    }

    header(day, 1)
    test(::solve1, testInput, 567)
    solve(day, ::solve1)

    header(day, 2)
    solve(day, ::solve2)
}


