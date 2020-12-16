data class TicketData(
    val rules: MutableMap<String, List<IntRange>> = mutableMapOf(),
    val myTicket: MutableList<Int> = mutableListOf(),
    val otherTickets: MutableList<List<Int>> = mutableListOf()
)

fun main() {
    val testInput = "class: 1-3 or 5-7\n" +
            "row: 6-11 or 33-44\n" +
            "seat: 13-40 or 45-50\n" +
            "\n" +
            "your ticket:\n" +
            "7,1,14\n" +
            "\n" +
            "nearby tickets:\n" +
            "7,3,47\n" +
            "40,4,50\n" +
            "55,2,20\n" +
            "38,6,12"

    fun parseInput(lines: List<String>): TicketData {
        val data = TicketData()
        for (i in lines.indices) {
            if (lines[i].isEmpty()) continue
            val tokens = lines[i].split(":")
            if (tokens.size > 1) {
                if (tokens[0] != "your ticket" && tokens[0] != "nearby tickets") {
                    data.rules[tokens[0]] = tokens[1].trim().split(" or ").map {
                        val rangeTokens = it.split("-")
                        IntRange(rangeTokens[0].toInt(), rangeTokens[1].toInt())
                    }
                }
            } else {
                val ticketTokens = tokens[0].split(",").map { it.trim().toInt() }
                if (lines[i - 1] == "your ticket:") {
                    data.myTicket.addAll(ticketTokens)
                } else {
                    data.otherTickets.add(ticketTokens)
                }
            }
        }
        return data
    }

    fun solve1(lines: List<String>): Long {
        val data = parseInput(lines)
        return data.otherTickets.map { ticket ->
            ticket.filter { number ->
                !data.rules.any { rule ->
                    rule.value[0].contains(number) || rule.value[1].contains(number)
                }
            }.sum()
        }.sum().toLong()
    }

    fun matchField(ranges: List<IntRange>, tickets: List<List<Int>>, i: Int): Boolean {
        return tickets.all { ranges[0].contains(it[i]) || ranges[1].contains(it[i]) }
    }

    fun solve2(lines: List<String>): Long {
        val data = parseInput(lines)
        val validTickets = data.otherTickets.filter { ticket ->
            ticket.all { number ->
                data.rules.any { rule ->
                    rule.value[0].contains(number) || rule.value[1].contains(number)
                }
            }
        }

        val matchesFields = mutableMapOf<String, MutableList<Int>>()
        data.rules.forEach { (f, ranges) ->
            val matches = mutableListOf<Int>()
            for (i in data.myTicket.indices) {
                if (matchField(ranges, validTickets, i)) {
                    matches.add(i)
                }
            }
            matchesFields[f] = matches
        }

        val fixedFields = mutableMapOf<String, Int>()
        while (matchesFields.isNotEmpty()) {
            val iterator = matchesFields.iterator()
            while (iterator.hasNext()) {
                val match = iterator.next()
                if (match.value.size == 1) {
                    fixedFields[match.key] = match.value[0]
                    iterator.remove()
                    matchesFields.values.forEach {
                        it.remove(fixedFields[match.key])
                    }
                }
            }
        }

        return fixedFields.mapValues { (_, i) -> data.myTicket[i].toLong() }
            .filterKeys { it.startsWith("departure") }
            .values
            .reduce { a, b -> a * b }
            .toLong()
    }

    header(1)
    test(::solve1, testInput, 71)
    solve(::solve1)

    header(2)
    solve(::solve2)
}
