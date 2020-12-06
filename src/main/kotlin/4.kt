import org.apache.commons.io.IOUtils
import org.apache.commons.lang3.math.NumberUtils
import java.util.regex.Pattern

fun main() {
    val lines = IOUtils.readLines(IOUtils::class.java.getResourceAsStream("/4.txt"), "UTF-8")

    var isValid: Long = 1
    var passport: MutableMap<String, String> = mutableMapOf()
    val reqTokens = setOf("byr",
            "iyr",
            "eyr",
            "hgt",
            "hcl",
            "ecl",
            "pid")

    lines.forEach { line ->
        if (line.isNotBlank()) {
            line.split(" ").forEach { token ->
                val tokens = token.split(":")
                passport[tokens[0]] = tokens[1]
            }
        }

        if (line.isBlank()) {
            if (passport.keys.containsAll(reqTokens) &&
                    validateByr(passport["byr"]!!) &&
                    validateIyr(passport["iyr"]!!) &&
                    validateEyr(passport["eyr"]!!) &&
                    validateHgt(passport["hgt"]!!) &&
                    validateHcl(passport["hcl"]!!) &&
                    validateEcl(passport["ecl"]!!) &&
                    validatePid(passport["pid"]!!)
                    ) {
                isValid++
            }
            passport = mutableMapOf()
        }
    }
    println(isValid)
}

//    byr (Birth Year) - four digits; at least 1920 and at most 2002.
fun validateByr(token: String): Boolean {
    return NumberUtils.isNumber(token) && (NumberUtils.toInt(token) in 1920..2002)
}

//    iyr (Issue Year) - four digits; at least 2010 and at most 2020.
fun validateIyr(token: String): Boolean {
    return NumberUtils.isNumber(token) && (NumberUtils.toInt(token) in 2010..2020)
}

//    eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
fun validateEyr(token: String): Boolean {
    return NumberUtils.isNumber(token) && (NumberUtils.toInt(token) in 2020..2030)
}

//    hgt (Height) - a number followed by either cm or in:
//    If cm, the number must be at least 150 and at most 193.
//    If in, the number must be at least 59 and at most 76.
fun validateHgt(token: String): Boolean {
    println(token)
    val pattern = Pattern.compile("(\\w*)(cm|in)")
    val matcher = pattern.matcher(token)
    if (matcher.matches()) {
        val value = Integer.valueOf(matcher.group(1))
        return if (matcher.group(2) == "cm") {
            value in 150..193
        } else {
            value in 59..76
        }
    }
    return false
}

//    hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
fun validateHcl(token: String): Boolean {
    return Pattern.compile("#[0-9,a-f]{6}").matcher(token).matches()
}

//    ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
fun validateEcl(token: String): Boolean {
    return setOf("amb","blu","brn","gry","grn","hzl","oth").contains(token)
}

//    pid (Passport ID) - a nine-digit number, including leading zeroes.
fun validatePid(token: String): Boolean {
    return Pattern.compile("\\w{9}").matcher(token).matches()
}



