import org.apache.commons.io.IOUtils

fun main() {
    val lines = IOUtils.readLines(IOUtils::class.java.getResourceAsStream("/5.txt"), "UTF-8")

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
    System.out.println(highestId)

    takenSeats.forEachIndexed { i, _ ->
        if (i > 7 && i < 8 * 126 && !takenSeats[i] && takenSeats[i + 1] && takenSeats[i - 1]) {
            System.out.println("this is my seat: $i")
        }
    }
}

fun changeRange(rowRange: IntRange, forward: Boolean): IntRange {
    val rowBound = (rowRange.last + 1 - rowRange.first) / 2;
    return if (forward) {
        rowRange.first..(rowRange.last - rowBound)
    } else {
        (rowRange.first + rowBound)..rowRange.last
    }
}


