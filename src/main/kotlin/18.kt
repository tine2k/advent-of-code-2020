data class Op(val operator: Char, val value: Long, val ops: List<Op> = emptyList()) {
    override fun toString(): String {
        return if (ops.isNotEmpty()) {
            operator + ops.toString()
        } else {
            "" + operator + value
        }
    }
}

fun main() {

    fun performOperation(left: Long, operator: Char, right: Long): Long {
        return if (operator == '+') {
            left + right
        } else {
            left * right
        }
    }

    fun calculate1(iter: Iterator<Char>): Long {
        var sum = 0L
        var operator = '+'
        while (iter.hasNext()) {
            val token = iter.next()
            when {
                token == '*' -> operator = '*'
                token == '+' -> operator = '+'
                token.isDigit() -> sum = performOperation(sum, operator, token.toString().toLong())
                token == '(' -> sum = performOperation(sum, operator, calculate1(iter))
                token == ')' -> return sum
            }
        }
        return sum
    }

    fun solve1(lines: List<String>): Long {
        return lines.map { calculate1(it.replace(" ", "").iterator()) }.sum()
    }

    fun parse(iter: Iterator<Char>, operations: MutableList<Op>): MutableList<Op> {
        var operator = '+'
        while (iter.hasNext()) {
            val token = iter.next()
            when {
                token == '*' -> operator = '*'
                token == '+' -> operator = '+'
                token.isDigit() -> operations.add(Op(operator, token.toString().toLong()))
                token == '(' -> operations.add(Op(operator, -1, parse(iter, mutableListOf())))
                token == ')' -> return operations
            }
        }

        return operations
    }

    fun calculate2(input: String): Long {

        println(input)
        println(parse(input.iterator(), mutableListOf()))

        System.exit(1)

        return -1
    }

    fun solve2(lines: List<String>): Long {
        return lines.map { calculate2(it.replace(" ", "")) }.sum()
    }

    header(2)
//    test(::solve2, "1 * 2 + 2", 4)
//    test(::solve2, "1 + 2 * 2", 6)
//    test(::solve2, "1 + 6 + 6 * 2 + 5 * 2", 182)
//    test(::solve2, "3 + (8 + 6 * 4)", (14*4)+3)
//    test(::solve2, "7 * 3 * 3 + 9 * 3", 252 * 3)
//    test(::solve2, "1 * 2 + 3", 5)
//    test(::solve2, "1 * 2 + 3 + 4", 9)
//    test(::solve2, "1 * 2 + 3 + 4 * 1", 9)
//    test(::solve2, "1 * 2 + 3 * 2 + 2 + 2", 30)
    test(::solve2, "1 + (2 * 3) + (4 * (5 + 6))", 51)
//    test(::solve2, "2 * 3 + (4 * 5)", 46)
//    test(::solve2, "5 + (8 * 3 + 9 + 3 * 4 * 3)", 1445)
//    test(::solve2, "5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))", 669060)
//    test(::solve2, "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2", 23340)
//    test(::solve2, "1 * (6 * 7) + 4 * 2", ((6 * 7) + 4) * 2)
    solve(::solve2)

    header(1)
    test(::solve1, "1 + 2 + 3", 6)
    test(::solve1, "1 + 2 * 3", 9)
    test(::solve1, "2 * 3 + (4 * 5)", 26)
    test(::solve1, "5 + (8 * 3 + 9 + 3 * 4 * 3)", 437)
    test(::solve1, "5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))", 12240)
    test(::solve1, "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2", 13632)

    solve(::solve1)
}

