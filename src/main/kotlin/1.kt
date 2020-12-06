import org.apache.commons.io.IOUtils

fun main() {
    val lines = IOUtils.readLines(IOUtils::class.java.getResourceAsStream("/1.txt"), "UTF-8")
    lines.forEach {
        val a = it
        lines.forEach {
            val b = it
            lines.forEach {
                val c = it
                if (Integer.valueOf(a) + Integer.valueOf(b) + Integer.valueOf(c) == 2020) {
                    System.out.println("-")
                    System.out.println(Integer.valueOf(a))
                    System.out.println(Integer.valueOf(b))
                    System.out.println(Integer.valueOf(c))
                    System.out.println(Integer.valueOf(a) * Integer.valueOf(b) * Integer.valueOf(c))
                }
            }
        }
    }
}


