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

    val testInput2 = "42: 9 14 | 10 1\n" +
            "9: 14 27 | 1 26\n" +
            "10: 23 14 | 28 1\n" +
            "1: \"a\"\n" +
            "11: 42 31\n" +
            "5: 1 14 | 15 1\n" +
            "19: 14 1 | 14 14\n" +
            "12: 24 14 | 19 1\n" +
            "16: 15 1 | 14 14\n" +
            "31: 14 17 | 1 13\n" +
            "6: 14 14 | 1 14\n" +
            "2: 1 24 | 14 4\n" +
            "0: 8 11\n" +
            "13: 14 3 | 1 12\n" +
            "15: 1 | 14\n" +
            "17: 14 2 | 1 7\n" +
            "23: 25 1 | 22 14\n" +
            "28: 16 1\n" +
            "4: 1 1\n" +
            "20: 14 14 | 1 15\n" +
            "3: 5 14 | 16 1\n" +
            "27: 1 6 | 14 18\n" +
            "14: \"b\"\n" +
            "21: 14 1 | 1 14\n" +
            "25: 1 1 | 1 14\n" +
            "22: 14 14\n" +
            "8: 42\n" +
            "26: 14 22 | 1 20\n" +
            "18: 15 15\n" +
            "7: 14 5 | 1 21\n" +
            "24: 14 1\n" +
            "\n" +
            "abbbbbabbbaaaababbaabbbbabababbbabbbbbbabaaaa\n" +
            "bbabbbbaabaabba\n" +
            "babbbbaabbbbbabbbbbbaabaaabaaa\n" +
            "aaabbbbbbaaaabaababaabababbabaaabbababababaaa\n" +
            "bbbbbbbaaaabbbbaaabbabaaa\n" +
            "bbbababbbbaaaaaaaabbababaaababaabab\n" +
            "ababaaaaaabaaab\n" +
            "ababaaaaabbbaba\n" +
            "baabbaaaabbaaaababbaababb\n" +
            "abbbbabbbbaaaababbbbbbaaaababb\n" +
            "aaaaabbaabaaaaababaa\n" +
            "aaaabbaaaabbaaa\n" +
            "aaaabbaabbaaaaaaabbbabbbaaabbaabaaa\n" +
            "babaaabbbaaabaababbaabababaaab\n" +
            "aabbbbbaabbbaaaaaabbbbbababaaaaabbaaabba"

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
        assert(refRules.isNotEmpty())
        var index = 0
        for (refRule in refRules) {
            val ruleMatches = ruleMatcher.invoke(input.substring(index), allRules[refRule]!!, allRules)
            if (ruleMatches < 0) {
                return -1
            } else {
                index += ruleMatches
            }
        }
        assert(index > 0)
        return index
    }

    fun ruleMatches(input: String, r: Rule, allRules: Map<Int, Rule>): Int {
        return if (r.charCompare != null) {
            if (input.startsWith(r.charCompare)) 1 else -1
        } else {
            return r.rule!!
                .map { refRuleMatches(input, it, allRules, ::ruleMatches) }
                .firstOrNull { it >= 0 }
                ?: -1
        }
    }

    fun messageMatches(message: String, allRules: Map<Int, Rule>): Boolean {
        val ruleMatches = ruleMatches(message, allRules[0]!!, allRules)
        return ruleMatches == message.length
    }

    fun solve1(lines: List<String>): Long {
        val data = parseData(lines)
        return data.messages.count { messageMatches(it, data.rules) }.toLong()
    }

    fun solve2(lines: List<String>): Long {
        val data = parseData(lines)
        val newRules = data.rules.toMutableMap()
        newRules[8] = Rule(listOf(listOf(42), listOf(42, 8)))
        newRules[11] = Rule(listOf(listOf(42, 31), listOf(42, 11, 31)))
        val newData = Data(newRules, data.messages)

        return newData.messages.count { messageMatches(it, newData.rules) }.toLong()
    }

    header(1)
    test(::solve1, testInput, 2)
    solve(::solve1)

    header(2)
    test(::solve1, testInput2, 3) //solve1 on purpose
    test(::solve2, testInput2, 12)
    solve(::solve2)
}
