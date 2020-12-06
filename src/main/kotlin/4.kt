import org.apache.commons.lang3.math.NumberUtils
import java.util.regex.Pattern

fun main() {
    val testInput = "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd\n" +
            "byr:1937 iyr:2017 cid:147 hgt:183cm\n" +
            "\n" +
            "iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884\n" +
            "hcl:#cfa07d byr:1929\n" +
            "\n" +
            "hcl:#ae17e1 iyr:2013\n" +
            "eyr:2024\n" +
            "ecl:brn pid:760753108 byr:1931\n" +
            "hgt:179cm\n" +
            "\n" +
            "hcl:#cfa07d eyr:2025 pid:166559648\n" +
            "iyr:2011 ecl:brn hgt:59in"
    val testResult = 2

    //    byr (Birth Year) - four digits; at least 1920 and at most 2002.
    fun validateByr(token: String): Boolean {
        return NumberUtils.isCreatable(token) && (NumberUtils.toInt(token) in 1920..2002)
    }

    //    iyr (Issue Year) - four digits; at least 2010 and at most 2020.
    fun validateIyr(token: String): Boolean {
        return NumberUtils.isCreatable(token) && (NumberUtils.toInt(token) in 2010..2020)
    }

    //    eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
    fun validateEyr(token: String): Boolean {
        return NumberUtils.isCreatable(token) && (NumberUtils.toInt(token) in 2020..2030)
    }

    //    hgt (Height) - a number followed by either cm or in:
    //    If cm, the number must be at least 150 and at most 193.
    //    If in, the number must be at least 59 and at most 76.
    fun validateHgt(token: String): Boolean {
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
        return setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(token)
    }

    //    pid (Passport ID) - a nine-digit number, including leading zeroes.
    fun validatePid(token: String): Boolean {
        return Pattern.compile("\\w{9}").matcher(token).matches()
    }

    fun solve(lines: List<String>, checkPassport: (MutableMap<String, String>) -> Boolean): Long {
        var isValid: Long = 0
        var passport: MutableMap<String, String> = mutableMapOf()
        val reqTokens = setOf("byr",
                "iyr",
                "eyr",
                "hgt",
                "hcl",
                "ecl",
                "pid")

        lines.forEachIndexed { i, line ->
            if (line.isNotBlank()) {
                line.split(" ").forEach { token ->
                    val tokens = token.split(":")
                    passport[tokens[0]] = tokens[1]
                }
            }

            if (line.isBlank() || i == lines.size - 1) {
                if (passport.keys.containsAll(reqTokens) && checkPassport(passport)) {
                    isValid++
                }
                passport = mutableMapOf()
            }
        }
        return isValid
    }

    fun solve1(lines: List<String>): Long {
        return solve(lines) { true };
    }

    fun solve2(lines: List<String>): Long {
        return solve(lines) { pp -> validateByr(pp["byr"]!!) &&
                validateIyr(pp["iyr"]!!) &&
                validateEyr(pp["eyr"]!!) &&
                validateHgt(pp["hgt"]!!) &&
                validateHcl(pp["hcl"]!!) &&
                validateEcl(pp["ecl"]!!) &&
                validatePid(pp["pid"]!!)}
    }

    solveAndTest(4, ::solve1, "One", testInput, testResult)
    solveAndTest(4, ::solve2, "Two")
}




