import org.apache.commons.io.IOUtils

fun main() {
    val lines = IOUtils.readLines(IOUtils::class.java.getResourceAsStream("/3.txt"), "UTF-8")

    var result: Long = 1
    var right = intArrayOf(1, 3, 5, 7, 1)

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

    System.out.println(result)
}


