import java.util.regex.Pattern

data class BagCount(val bag: String, val count: Int)

fun main() {
    val day = 7

    val testInput = "light red bags contain 1 bright white bag, 2 muted yellow bags.\n" +
            "dark orange bags contain 3 bright white bags, 4 muted yellow bags.\n" +
            "bright white bags contain 1 shiny gold bag.\n" +
            "muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.\n" +
            "shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.\n" +
            "dark olive bags contain 3 faded blue bags, 4 dotted black bags.\n" +
            "vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.\n" +
            "faded blue bags contain no other bags.\n" +
            "dotted black bags contain no other bags."

    val testInput2 = "shiny gold bags contain 2 dark red bags.\n" +
            "dark red bags contain 2 dark orange bags.\n" +
            "dark orange bags contain 2 dark yellow bags.\n" +
            "dark yellow bags contain 2 dark green bags.\n" +
            "dark green bags contain 2 dark blue bags.\n" +
            "dark blue bags contain 2 dark violet bags.\n" +
            "dark violet bags contain no other bags."

    fun findBag(rel: Map<String, List<BagCount>>, result: MutableSet<String>, targets: List<String>): MutableSet<String> {
        val newTargets = mutableListOf<String>()
        rel.forEach { e ->
            targets.forEach { t ->
                if (e.value.count { it.bag == t } > 0) {
                    result.add(e.key)
                    newTargets.add(e.key)
                }
            }
        }
        if (newTargets.isNotEmpty()) {
            findBag(rel, result, newTargets);
        }
        return result
    }

    fun findContainingBagCount(rel: Map<String, List<BagCount>>, bag: String): Int {
        var stackCounter = 1
        rel[bag]?.forEach { e ->
            stackCounter += e.count * findContainingBagCount(rel, e.bag)
        }
        return stackCounter
    }

    fun getModel(lines: List<String>): Map<String, List<BagCount>> {
        val p1 = Pattern.compile("([a-z\\s]*) bags contain (.*)\\.")
        val p2 = Pattern.compile("(\\d*) ([a-z\\s]*) bags?")
        val rel = mutableMapOf<String, List<BagCount>>()
        lines.forEach { line ->
            val matcher = p1.matcher(line)
            assert(matcher.matches())
            val source = matcher.group(1)
            val matcherTargets = p2.matcher(matcher.group(2))
            val targets = mutableListOf<BagCount>()
            while (matcherTargets.find()) {
                val amount = matcherTargets.group(1)
                if (amount.isNotBlank()) {
                    val target = matcherTargets.group(2)
                    targets.add(BagCount(target, Integer.valueOf(amount)))
                }
            }
            rel[source] = targets
        }
        return rel
    }

    fun solve1(lines: List<String>): Long {
        return findBag(getModel(lines), mutableSetOf(), listOf("shiny gold")).size.toLong()
    }

    fun solve2(lines: List<String>): Long {
        return findContainingBagCount(getModel(lines), "shiny gold").toLong() - 1
    }

    header(day, 1)
    test(::solve1, testInput, 4)
    solve(day, ::solve1)

    header(day, 2)
    test(::solve2, testInput2, 126)
    solve(day, ::solve2)
}
