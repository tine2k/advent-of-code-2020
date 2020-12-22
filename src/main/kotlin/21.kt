data class Food(val alg: MutableList<String>, val ing: MutableList<String>)

fun main() {
    val testInput = "mxmxvkd kfcds sqjhc nhms (contains dairy, fish)\n" +
            "trh fvjkl sbzzf mxmxvkd (contains dairy)\n" +
            "sqjhc fvjkl (contains soy)\n" +
            "sqjhc mxmxvkd sbzzf (contains fish)"

    fun parseData(lines: List<String>): List<Food> {
        val foods = mutableListOf<Food>()
        lines.forEach {
            val tokens = it.split(" (")
            val alg = tokens[1].substring("contains ".length, tokens[1].length - 1).split(", ").toMutableList()
            val ing = tokens[0].split(" ").toMutableList()
            foods.add(Food(alg, ing))
        }
        return foods.toList()
    }

    fun solve(lines: List<String>): Pair<Long, Map<String,String>> {
        val foods = parseData(lines)
        val algs = foods.flatMap { it.alg }.toSet()
        var changed: Boolean
        val badIngs = mutableMapOf<String, String>()
        do {
            changed = false
            algs.forEach { alg ->
                val foodWithAlg = foods.filter { food -> food.alg.contains(alg) }
                val allIng = foodWithAlg.flatMap { it.ing }.toSet()
                    .map { ing -> Pair(ing, foodWithAlg.count { it.ing.contains(ing) }) }
                    .filter { pair -> pair.second == foodWithAlg.size }
                if (allIng.size == 1) {
                    foods.forEach { food ->
                        food.ing.remove(allIng.first().first)
                        food.alg.remove(alg)
                    }
                    badIngs[alg] = allIng.first().first
                    changed = true
                }
            }
        } while(changed)

        return Pair(foods.map { it.ing.size }.sum().toLong(), badIngs)
    }

    fun solve1(lines: List<String>): Long {
        return solve(lines).first
    }

    fun solve2(lines: List<String>): String {
        return solve(lines).second
            .toSortedMap()
            .map { it.value }
            .joinToString(",")
    }

    header(1)
    test(::solve1, testInput, 5)
    solve(::solve1)

    header(2)
    testString(::solve2, testInput, "mxmxvkd,sqjhc,fvjkl")
    solveString(::solve2)
}
