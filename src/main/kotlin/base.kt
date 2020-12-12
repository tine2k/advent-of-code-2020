import org.apache.commons.io.IOUtils
import java.io.StringReader
import java.lang.IllegalArgumentException

fun solveAndTest(day: Int?, solveFn: (List<String>) -> Long, part: String, testInput: String? = null, testResult: Int? = null) {

    println("--- Day $day Part $part ---");
    if (testInput != null && testResult != null) {
        val result = solveFn(IOUtils.readLines(StringReader(testInput)))
        if (result != testResult.toLong()) {
            println("Test-Result should be $testResult but was $result")
        } else {
            println("Test OK")
        }
    }

    if (day != null) {
        println(solveFn(getInputFile(day)))
        println()
    }
}

fun header(day: Int, part: Int) {
    if (day < 0) {
        throw IllegalArgumentException("Set correct day!")
    }
    println("--- Day $day Part $part ---");
}

fun test(solveFn: (List<String>) -> Long, testInput: String, testResult: Int) {
    val result = solveFn(IOUtils.readLines(StringReader(testInput)))
    if (result != testResult.toLong()) {
        println("Test FAILED: Result should be $testResult but was $result")
    } else {
        println("Test OK")
    }
}

fun solve(day: Int, solveFn: (List<String>) -> Long, input: List<String> = getInputFile(day)) {
    println(solveFn(input))
    println()
}

fun getInputFile(day: Int): List<String> {
    return IOUtils.readLines(IOUtils::class.java.getResourceAsStream("/$day.txt"), "UTF-8")
}
