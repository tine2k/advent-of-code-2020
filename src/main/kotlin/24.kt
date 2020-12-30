import java.util.regex.Pattern

fun main() {
    val testInput = "sesenwnenenewseeswwswswwnenewsewsw\n" +
            "neeenesenwnwwswnenewnwwsewnenwseswesw\n" +
            "seswneswswsenwwnwse\n" +
            "nwnwneseeswswnenewneswwnewseswneseene\n" +
            "swweswneswnenwsewnwneneseenw\n" +
            "eesenwseswswnenwswnwnwsewwnwsene\n" +
            "sewnenenenesenwsewnenwwwse\n" +
            "wenwwweseeeweswwwnwwe\n" +
            "wsweesenenewnwwnwsenewsenwwsesesenwne\n" +
            "neeswseenwwswnwswswnw\n" +
            "nenwswwsewswnenenewsenwsenwnesesenew\n" +
            "enewnwewneswsewnwswenweswnenwsenwsw\n" +
            "sweneswneswneneenwnewenewwneswswnese\n" +
            "swwesenesewenwneswnwwneseswwne\n" +
            "enesenwswwswneneswsenwnewswseenwsese\n" +
            "wnwnesenesenenwwnenwsewesewsesesew\n" +
            "nenewswnwewswnenesenwnesewesw\n" +
            "eneswnwswnwsenenwnwnwwseeswneewsenese\n" +
            "neswnwewnwnwseenwseesewsenwsweewe\n" +
            "wseweeenwnesenwwwswnew\n"

    val pattern = Pattern.compile("(se|sw|e|ne|nw|w)")

    fun parseData(lines: List<String>): List<List<String>> {
        return lines.map {
            val m = pattern.matcher(it)
            val directions = mutableListOf<String>()
            while (m.find()) {
                directions.add(m.group())
            }
            directions
        }
    }

    fun getNeighbor(tile: Pair<Int, Int>, direction: String): Pair<Int, Int> {
        return when (direction) {
            "se" -> Pair(tile.first + 1, tile.second + 1)
            "sw" -> Pair(tile.first, tile.second + 1)
            "e" -> Pair(tile.first + 1, tile.second)
            "ne" -> Pair(tile.first, tile.second - 1)
            "nw" -> Pair(tile.first - 1, tile.second - 1)
            "w" -> Pair(tile.first - 1, tile.second)
            else -> throw IllegalArgumentException()
        }
    }

    fun processWays(ways: List<List<String>>): List<Pair<Int, Int>> {
        val floor = mutableListOf<Pair<Int, Int>>()
        ways.forEach { way ->
            var element = Pair(0, 0)
            way.forEach { element = getNeighbor(element, it) }
            if (floor.contains(element)) {
                floor.remove(element)
            } else {
                floor.add(element)
            }
        }
        return floor
    }

    fun solve1(lines: List<String>): Long {
        return processWays(parseData(lines)).size.toLong()
    }

    fun getNeighbors(tile: Pair<Int, Int>): List<Pair<Int, Int>> {
        return listOf(
            getNeighbor(tile, "se"),
            getNeighbor(tile, "sw"),
            getNeighbor(tile, "e"),
            getNeighbor(tile, "ne"),
            getNeighbor(tile, "nw"),
            getNeighbor(tile, "w")
        )
    }

    fun getNeighborCount(tile: Pair<Int, Int>, tiles: List<Pair<Int, Int>>): Int {
        return getNeighbors(tile).count { tiles.contains(it) }
    }

    fun solve2(lines: List<String>): Long {
        var floor = processWays(parseData(lines))
        for (i in 0 until 100) {
            val newFloor = floor.filter { getNeighborCount(it, floor) in 1..2 }
            val newTiles = floor.flatMap { getNeighbors(it) }
                .distinct()
                .filter { !floor.contains(it) }
                .filter { getNeighborCount(it, floor) == 2 }
            floor = newFloor + newTiles
        }

        return floor.size.toLong()
    }

    header(1)
    test(::solve1, testInput, 10)
    solve(::solve1)

    header(2)
    test(::solve2, testInput, 2208)
    solve(::solve2)
}
