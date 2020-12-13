import kotlin.math.abs

fun main() {
    val testInput = "F10\n" +
            "N3\n" +
            "F7\n" +
            "R90\n" +
            "F11"

    val directions = listOf("E", "S", "W", "N")

    fun getEffectiveCommand(command: String, direction: String): String {
        return if (command == "F") {
            direction
        } else {
            command
        }
    }

    fun turnRight(direction: String, distance: Int): String {
        return directions[(directions.indexOf(direction) + (distance / 90)) % directions.size]
    }

    fun turnLeft(direction: String, distance: Int): String {
        return directions[(directions.indexOf(direction) + directions.size - (distance / 90)) % directions.size]
    }

    fun solve1(lines: List<String>): Long {
        var h = 0
        var v = 0
        var direction = "E"
        lines.forEach {
            val command = it.substring(0..0)
            val distance = it.substring(1).toInt()
            when (getEffectiveCommand(command, direction)) {
                "N" -> v -= distance
                "E" -> h += distance
                "S" -> v += distance
                "W" -> h -= distance
                "R" -> direction = turnRight(direction, distance)
                "L" -> direction = turnLeft(direction, distance)
            }
        }
        return (abs(h) + abs(v)).toLong()
    }

    fun rotate(w: Pair<Int, Int>, direction: String): Pair<Int, Int> {
        return if (direction == "R") {
            Pair(w.second * -1, w.first)
        } else {
            Pair(w.second, w.first * -1)
        }
    }

    fun solve2(lines: List<String>): Long {
        var h = 0
        var v = 0
        var wh = 10
        var wv = -1
        lines.forEach {
            val command = it.substring(0..0)
            val distance = it.substring(1).toInt()
            when (command) {
                "N" -> wv -= distance
                "E" -> wh += distance
                "S" -> wv += distance
                "W" -> wh -= distance
                "L", "R" -> {
                    var coords = Pair(wh, wv)
                    for (i in 0 until distance / 90) {
                        coords = rotate(coords, command)
                    }
                    wh = coords.first
                    wv = coords.second
                }
                "F" -> {
                    h += distance * wh
                    v += distance * wv
                }
            }
        }
        return (abs(h) + abs(v)).toLong()
    }

    header(1)
    test(::solve1, testInput, 25)
    solve(::solve1)

    header(2)
    test(::solve2, testInput, 286)
    solve(::solve2)
}
