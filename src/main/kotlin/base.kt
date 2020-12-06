import org.apache.commons.io.IOUtils
import java.io.StringReader

fun solveAndTest(day: Int, solveFn: (List<String>) -> Long, part: String, testInput: String? = null, testResult: Int? = null) {

    println("--- Day $day Part $part ---");
    if (testInput != null && testResult != null) {
        val result = solveFn(IOUtils.readLines(StringReader(testInput)))
        if (result != testResult.toLong()) {
            println("Result should be $testResult but was $result")
        } else {
            println("Test OK")
        }
    }
    println(solveFn(IOUtils.readLines(IOUtils::class.java.getResourceAsStream("/$day.txt"), "UTF-8")))
    println()
}
