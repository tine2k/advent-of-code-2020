data class Rule(val rule: List<List<Int>>? = null, val charCompare: Char? = null)
data class Data(val rules: Map<Int, Rule>, val messages: List<String>)

fun main() {
    val testInput = "0: 4 1 5\n" +
            "1: 2 3 | 3 2\n" +
            "2: 4 4 | 5 5\n" +
            "3: 4 5 | 5 4\n" +
            "4: \"a\"\n" +
            "5: \"b\"\n" +
            "\n" +
            "ababbb\n" +
            "bababa\n" +
            "abbbab\n" +
            "aaabbb\n" +
            "aaaabbb"

    fun parseData(lines: List<String>): Data {
        val iterator = lines.iterator()
        val rules = mutableMapOf<Int, Rule>()
        val messages = mutableListOf<String>()
        while (iterator.hasNext()) {
            val line = iterator.next();
            if (line.isEmpty()) {
                break;
            }
            val tokens = line.split(":").map { it.trim() }
            when {
                tokens[1].startsWith("\"") -> rules[tokens[0].toInt()] =
                    Rule(charCompare = tokens[1].substring(1, 2)[0])
                tokens[1].contains("|") -> {
                    rules[tokens[0].toInt()] = Rule(rule = tokens[1].split("|")
                        .map { it.trim() }
                        .map { it.split(" ").map { s -> s.toInt() } })
                }
                else -> rules[tokens[0].toInt()] = Rule(rule = listOf(tokens[1].split(" ").map { it.toInt() }))
            }
        }

        while (iterator.hasNext()) {
            messages.add(iterator.next())
        }

        return Data(rules, messages);
    }

    fun refRuleMatches(
        input: String, refRules: List<Int>, allRules: Map<Int, Rule>,
        ruleMatcher: (input: String, r: Rule, allRules: Map<Int, Rule>) -> Int,
    ): Int {
        var index = 0
        for (refRule in refRules) {
            val ruleMatches = ruleMatcher.invoke(input.substring(index), allRules[refRule]!!, allRules)
            if (ruleMatches < 0) {
                return -1
            } else {
                index += ruleMatches
            }
        }
        return index
    }

    fun ruleMatches(input: String, r: Rule, allRules: Map<Int, Rule>): Int {
        return if (r.charCompare != null) {
            if (input.startsWith(r.charCompare)) 1 else -1
        } else {
            return r.rule!!.map { refRuleMatches(input, it, allRules, ::ruleMatches) }.firstOrNull { it >= 0 } ?: -1
        }
    }

    fun messageMatches(message: String, allRules: Map<Int, Rule>): Boolean {
        return ruleMatches(message, allRules[0]!!, allRules) == message.length
    }

    fun solve1(lines: List<String>): Long {
        val data = parseData(lines)
        return data.messages.count { messageMatches(it, data.rules) }.toLong()
    }

    fun solve2(lines: List<String>): Long {
        return 1337
    }

    header(1)
    test(::solve1, testInput, 2)
    solve(::solve1)

    header(2)
    test(::solve2, testInput, 1337)
    solve(::solve2)
}
