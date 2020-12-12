import org.apache.commons.io.IOUtils
import java.io.StringReader

val day = Thread.currentThread().stackTrace[2].fileName!!.split(".")[0]

fun header(part: Int) {
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

fun solve(solveFn: (List<String>) -> Long, input: List<String> = getInputFile()) {
    println(solveFn(input))
    println()
}

fun getInputFile(): List<String> {
    return IOUtils.readLines(IOUtils::class.java.getResourceAsStream("/$day.txt"), "UTF-8")
}
