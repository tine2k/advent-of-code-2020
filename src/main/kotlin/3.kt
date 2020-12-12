fun main() {
    val testInput = "..##.......\n" +
            "#...#...#..\n" +
            ".#....#..#.\n" +
            "..#.#...#.#\n" +
            ".#...##..#.\n" +
            "..#.##.....\n" +
            ".#.#.#....#\n" +
            ".#........#\n" +
            "#.##...#...\n" +
            "#...##....#\n" +
            ".#..#...#.#"

    fun solve(lines: List<String>, right: IntArray): Long {
        var result: Long = 1

        right.forEachIndexed { index, rvalue ->
            var trees = 0;
            var i = 0
            lines.forEachIndexed { lineIndex, line ->
                if (index != 4 || lineIndex % 2 == 0) {
                    if (i >= line.length) {
                        i -= line.length;
                    }
                    if (line.substring(i, i + 1) == "#") {
                        trees++
                    }
                    i += rvalue
                }
            }
            result *= trees;
        }

        return result
    }

    fun solve1(lines: List<String>): Long {
        return solve(lines, intArrayOf(3))
    }

    fun solve2(lines: List<String>): Long {
        return solve(lines, intArrayOf(1, 3, 5, 7, 1))
    }

    header(1)
    test(::solve1, testInput, 7)
    solve(::solve1)

    header(2)
    solve(::solve2)
}


