import org.apache.commons.io.IOUtils

fun main() {
    val lines = IOUtils.readLines(IOUtils::class.java.getResourceAsStream("/1.txt"), "UTF-8")
    lines.forEach { a ->
        lines.forEach { b ->
            lines.forEach { c ->
                if (Integer.valueOf(a) + Integer.valueOf(b) + Integer.valueOf(c) == 2020) {
                    println(Integer.valueOf(a) * Integer.valueOf(b) * Integer.valueOf(c))
                }
            }
        }
    }
}
