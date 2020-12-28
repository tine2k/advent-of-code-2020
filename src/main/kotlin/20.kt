import org.apache.commons.lang3.StringUtils
import kotlin.math.sqrt

data class Tile(val number: Int, var content: List<String>) {

    var sides: List<String>
    private val sideLength: Int = content[0].length

    init {
        assert(sideLength > 0)
        assert(content.count { it.length != sideLength } == 0)
        assert(content.size == sideLength)
        sides = calcSides(content)
    }

    fun rotate(times: Int) {
        for (i in 0 until times) rotate()
    }

    private fun rotate() {
        val newContent = modifyContent { l, c -> Pair(sideLength - 1 - c, l) }
        val newSides = calcSides(newContent)
        assert(newSides == listOf(sides[3], sides[0], sides[1], sides[2]))
        content = newContent
        sides = newSides
    }

    fun flipV() {
        val newContent = modifyContent { l, c -> Pair(sideLength - 1 - l, c) }
        val newSides = calcSides(newContent)
        assert(newSides == listOf(sides[2].reversed(), sides[1].reversed(), sides[0].reversed(), sides[3].reversed()))
        content = newContent
        sides = newSides
    }

    fun flipH() {
        val newContent = modifyContent { l, c -> Pair(l, sideLength - 1 - c) }
        val newSides = calcSides(newContent)
        assert(newSides == listOf(sides[0].reversed(), sides[3].reversed(), sides[2].reversed(), sides[1].reversed()))
        content = newContent
        sides = newSides
    }

    private fun modifyContent(indexModify: (l: Int, c: Int) -> Pair<Int, Int>): MutableList<String> {
        val newContent = MutableList(sideLength) { StringUtils.repeat("x", sideLength) }
        newContent.forEachIndexed { l, line ->
            val newLine = line.toCharArray()
            newContent[l].forEachIndexed { c, _ ->
                val newIndices = indexModify(l, c)
                newLine[c] = content[newIndices.first][newIndices.second]
            }
            newContent[l] = String(newLine)
        }
        assert(newContent.count { it.contains("x") } == 0)
        return newContent
    }

    private fun calcSides(newContent: List<String>): List<String> {
        return listOf(newContent[0],
            getRightSide(newContent),
            newContent[sideLength - 1].reversed(),
            getLeftSide(newContent))
    }

    private fun getRightSide(newContent: List<String>): String {
        return newContent.map { it[sideLength - 1] }.joinToString("")
    }

    private fun getLeftSide(newContent: List<String>): String {
        return newContent.map { it[0] }.reversed().joinToString("")
    }
}

fun main() {
    val testInput = "Tile 2311:\n" +
            "..##.#..#.\n" +
            "##..#.....\n" +
            "#...##..#.\n" +
            "####.#...#\n" +
            "##.##.###.\n" +
            "##...#.###\n" +
            ".#.#.#..##\n" +
            "..#....#..\n" +
            "###...#.#.\n" +
            "..###..###\n" +
            "\n" +
            "Tile 1951:\n" +
            "#.##...##.\n" +
            "#.####...#\n" +
            ".....#..##\n" +
            "#...######\n" +
            ".##.#....#\n" +
            ".###.#####\n" +
            "###.##.##.\n" +
            ".###....#.\n" +
            "..#.#..#.#\n" +
            "#...##.#..\n" +
            "\n" +
            "Tile 1171:\n" +
            "####...##.\n" +
            "#..##.#..#\n" +
            "##.#..#.#.\n" +
            ".###.####.\n" +
            "..###.####\n" +
            ".##....##.\n" +
            ".#...####.\n" +
            "#.##.####.\n" +
            "####..#...\n" +
            ".....##...\n" +
            "\n" +
            "Tile 1427:\n" +
            "###.##.#..\n" +
            ".#..#.##..\n" +
            ".#.##.#..#\n" +
            "#.#.#.##.#\n" +
            "....#...##\n" +
            "...##..##.\n" +
            "...#.#####\n" +
            ".#.####.#.\n" +
            "..#..###.#\n" +
            "..##.#..#.\n" +
            "\n" +
            "Tile 1489:\n" +
            "##.#.#....\n" +
            "..##...#..\n" +
            ".##..##...\n" +
            "..#...#...\n" +
            "#####...#.\n" +
            "#..#.#.#.#\n" +
            "...#.#.#..\n" +
            "##.#...##.\n" +
            "..##.##.##\n" +
            "###.##.#..\n" +
            "\n" +
            "Tile 2473:\n" +
            "#....####.\n" +
            "#..#.##...\n" +
            "#.##..#...\n" +
            "######.#.#\n" +
            ".#...#.#.#\n" +
            ".#########\n" +
            ".###.#..#.\n" +
            "########.#\n" +
            "##...##.#.\n" +
            "..###.#.#.\n" +
            "\n" +
            "Tile 2971:\n" +
            "..#.#....#\n" +
            "#...###...\n" +
            "#.#.###...\n" +
            "##.##..#..\n" +
            ".#####..##\n" +
            ".#..####.#\n" +
            "#..#.#..#.\n" +
            "..####.###\n" +
            "..#.#.###.\n" +
            "...#.#.#.#\n" +
            "\n" +
            "Tile 2729:\n" +
            "...#.#.#.#\n" +
            "####.#....\n" +
            "..#.#.....\n" +
            "....#..#.#\n" +
            ".##..##.#.\n" +
            ".#.####...\n" +
            "####.#.#..\n" +
            "##.####...\n" +
            "##..#.##..\n" +
            "#.##...##.\n" +
            "\n" +
            "Tile 3079:\n" +
            "#.#.#####.\n" +
            ".#..######\n" +
            "..#.......\n" +
            "######....\n" +
            "####.#..#.\n" +
            ".#...#.##.\n" +
            "#.#####.##\n" +
            "..#.###...\n" +
            "..#.......\n" +
            "..#.###..."

    fun parse(lines: List<String>): List<Tile> {
        val tiles = mutableListOf<Tile>()
        var tileNumber = 0
        var tileIndex = -1
        var content = mutableListOf<String>()
        for (line in lines) {
            if (line.isEmpty()) continue
            if (line.startsWith("Tile ")) {
                tileNumber = line.split(" ")[1].split(":")[0].toInt()
                tileIndex = 0
                content = mutableListOf<String>()
            } else {
                content.add(line)
                if (tileIndex == 9) {
                    assert(content.size == 10)
                    tiles.add(Tile(tileNumber, content))
                }
                tileIndex += 1
            }
        }
        return tiles
    }

    fun getEdgeTiles(tile: Tile, allTiles: Collection<Tile>): List<String> {
        return tile.sides.filter { side -> allTiles.flatMap { it.sides }.none { it == side || it == side.reversed() } }
    }

    fun findTilesWithSide(side: String, allTiles: Collection<Tile>): Tile {
        return allTiles.single { tile -> tile.sides.any { it == side || it == side.reversed() } }
    }

    fun solve1(lines: List<String>): Long {
        val tiles = parse(lines)
        return tiles.filter { getEdgeTiles(it, tiles.filter { v -> v != it }).size == 2 }
            .map { it.number.toLong() }
            .reduce { a, b -> a * b }
    }

    fun matchesSides(tile: Tile, i1: Int, i2: Int, sides: Set<String>): Boolean {
        return sides == setOf(tile.sides[i1], tile.sides[i2]) ||
                sides == setOf(tile.sides[i1].reversed(), tile.sides[i2]) ||
                sides == setOf(tile.sides[i1], tile.sides[i2].reversed())
    }

    fun compareAnyDirection(v1: String, v2: String): Boolean {
        return v1 == v2 || v1.reversed() == v2
    }

    fun findTileWithLeftSide(side: String, tiles: List<Tile>): Tile {
        val tileWithSide = findTilesWithSide(side, tiles)
        when {
            compareAnyDirection(side, tileWithSide.sides[0]) -> tileWithSide.rotate(3)
            compareAnyDirection(side, tileWithSide.sides[1]) -> tileWithSide.rotate(2)
            compareAnyDirection(side, tileWithSide.sides[2]) -> tileWithSide.rotate(1)
        }
        if (side == tileWithSide.sides[3]) {
            tileWithSide.flipV()
            assert(side != tileWithSide.sides[3])
        }
        return tileWithSide
    }

    fun findTileWithTopSide(side: String, tiles: List<Tile>): Tile {
        val tileWithSide = findTilesWithSide(side, tiles)
        when {
            compareAnyDirection(side, tileWithSide.sides[1]) -> tileWithSide.rotate(3)
            compareAnyDirection(side, tileWithSide.sides[2]) -> tileWithSide.rotate(2)
            compareAnyDirection(side, tileWithSide.sides[3]) -> tileWithSide.rotate(1)
        }
        if (side == tileWithSide.sides[0]) {
            tileWithSide.flipH()
            assert(side != tileWithSide.sides[0])
        }
        return tileWithSide
    }

    fun p(i: Int, j: Int): Pair<Int, Int> {
        return Pair(i, j)
    }

    fun findNessie(tile: Tile): Int {
        val nessieBody = listOf(p(18, 0), p(0, 1), p(5, 1), p(6, 1), p(11, 1),
            p(12, 1), p(17, 1), p(18, 1), p(19, 1), p(1, 2), p(4, 2),
            p(7, 2), p(10, 2), p(13, 2), p(16, 2))
        var nessieCount = 0
        for (i in 0..tile.content[0].length - 19) {
            for (j in 0..tile.content.size - 3) {
                if (nessieBody.all { tile.content[j + it.second][i + it.first] == '#' }) {
                    nessieCount++
                }
            }
        }
        return nessieCount
    }

    fun modifyAndFindNessie(tile: Tile): Int {
        var nessieCount = 0
        for (i in 0 until 11) {
            nessieCount = findNessie(tile)
            if (nessieCount > 0) {
                return nessieCount
            }
            tile.rotate(1)
            if (i == 4) {
                tile.flipH()
            } else if (i ==8 ) {
                tile.flipV()
            }
        }
        return nessieCount
    }

    fun solve2(lines: List<String>): Long {
        val tiles = parse(lines)

        // find first tile
        val topLeftTile = tiles
            .map { Pair(it, getEdgeTiles(it, tiles.filter { v -> v != it })) }
            .first { it.second.size == 2 }

        when {
            matchesSides(topLeftTile.first, 0, 1, topLeftTile.second.toSet()) -> topLeftTile.first.rotate(3)
            matchesSides(topLeftTile.first, 1, 2, topLeftTile.second.toSet()) -> topLeftTile.first.rotate(2)
            matchesSides(topLeftTile.first, 2, 3, topLeftTile.second.toSet()) -> topLeftTile.first.rotate(1)
        }
        val remainingTiles = tiles.filter { it != topLeftTile.first }.toMutableList()

        val size = sqrt(tiles.size.toDouble()).toInt()
        val puzzle: MutableList<MutableList<Tile?>> = MutableList(size) { MutableList(size) { null } }
        puzzle[0][0] = topLeftTile.first

        // match other tiles
        for (y in 0 until puzzle.size) {
            if (y > 0) {
                puzzle[0][y] = findTileWithTopSide(puzzle[0][y - 1]!!.sides[2], remainingTiles)
                remainingTiles.remove(puzzle[0][y])
            }
            for (x in 1 until puzzle.size) {
                puzzle[x][y] = findTileWithLeftSide(puzzle[x - 1][y]!!.sides[1], remainingTiles)
                remainingTiles.remove(puzzle[x][y])
            }
        }

        // combine and remove borders
        val bigPicture = MutableList(size * 8) { "" }
        for (y in 0 until puzzle.size) {
            for (x in 0 until puzzle.size) {
                for (i in 1 until 9) {
                    bigPicture[(y * 8) + i - 1] += puzzle[x][y]!!.content[i].substring(1..8)
                }
            }
        }
        assert(bigPicture.count { it.length != size * 8 } == 0)
        val bigTile = Tile(0, bigPicture)

        // search for nessie
        val nessieCount = modifyAndFindNessie(bigTile)

        return bigPicture.map { it.count { c -> c == '#' } }.sum() - (nessieCount * 15).toLong()
    }

    header(1)
    test(::solve1, testInput, 20899048083289L)
    solve(::solve1)

    header(2)
    test(::solve2, testInput, 273)
    solve(::solve2)
}
