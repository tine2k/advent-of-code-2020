fun main() {

    fun performOperation(left: Long, operator: Char, right: Long): Long {
        return if (operator == '+') {
            left + right
        } else {
            left * right
        }
    }

    fun calculate(iter: Iterator<Char>): Long {
        var sum = 0L
        var operator = '+'
        while (iter.hasNext()) {
            val token = iter.next()
            when {
                token == '*' -> operator = '*'
                token == '+' -> operator = '+'
                token.isDigit() -> sum = performOperation(sum, operator, token.toString().toLong())
                token == '(' -> sum = performOperation(sum, operator, calculate(iter))
                token == ')' -> return sum
            }
        }
        return sum
    }

    fun solve1(lines: List<String>): Long {
        return lines.map { calculate(it.iterator()) }.sum()
    }

    fun solve2(lines: List<String>): Long {
        return 1337
    }

    header(1)
    test(::solve1, "1 + 2 + 3", 6)
    test(::solve1, "1 + 2 * 3", 9)
    test(::solve1, "2 * 3 + (4 * 5)", 26)
    test(::solve1, "5 + (8 * 3 + 9 + 3 * 4 * 3)", 437)
    test(::solve1, "5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))", 12240)
    test(::solve1, "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2", 13632)

    solve(::solve1)

    header(2)
    test(::solve2, "1 + (2 * 3) + (4 * (5 + 6))", 51)
    test(::solve2, "2 * 3 + (4 * 5)", 46)
    test(::solve2, "5 + (8 * 3 + 9 + 3 * 4 * 3)", 1445)
    test(::solve2, "5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))", 669060)
    test(::solve2, "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2", 23340)
    solve(::solve2)
}

