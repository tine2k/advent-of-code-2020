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

    fun resolve1(parent: Op): Op {
        return if (parent.ops.isNotEmpty()) {
            var value = 0L
            parent.ops.forEach { op ->
                val newOp = resolve1(op)
                value = performOperation(value, newOp.operator, newOp.value)
            }
            Op(parent.operator, value)
        } else {
            parent
        }
    }

    fun resolve2(parent: Op): Op {
        return if (parent.ops.isNotEmpty()) {
            if (parent.ops.size == 1) {
                return parent.ops.first()
            }

            var currentOps = mutableListOf<Op>()
            val allOps = currentOps
            for (i in 1 until parent.ops.size) {
                if (parent.ops[i - 1].operator == '*' && parent.ops[i].operator == '+' && currentOps.size > 0) {
                    val newList = mutableListOf<Op>()
                    currentOps.add(Op(parent.ops[i - 1].operator, 0, newList))
                    currentOps = newList
                }
                currentOps.add(parent.ops[i - 1])
            }
            currentOps.add(parent.ops.last())

            var value = -1L
            allOps.forEach { op ->
                val newOp = resolve2(op)
                value = if (value == -1L) {
                    newOp.value
                } else {
                    performOperation(value, newOp.operator, newOp.value)
                }
            }
            Op(parent.operator, value)
        } else {
            parent
        }
    }

    fun parse1(iter: Iterator<Char>, operations: MutableList<Op> = mutableListOf()): MutableList<Op> {
        var operator = '+'
        while (iter.hasNext()) {
            val token = iter.next()
            when {
                token == '*' -> operator = '*'
                token == '+' -> operator = '+'
                token.isDigit() -> operations.add(Op(operator, token.toString().toLong()))
                token == '(' -> operations.add(Op(operator, -1, parse1(iter, mutableListOf())))
                token == ')' -> return operations
            }
        }
        return operations
    }

    fun parseData(s: String): Op {
        return Op('+', 0, parse1(s.replace(" ", "").iterator()))
    }

    fun solve1(lines: List<String>): Long {
        return lines.map { resolve1(parseData(it)).value }.sum()
    }

    fun solve2(lines: List<String>): Long {
        return lines.map { resolve2(parseData(it)).value }.sum()
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
    test(::solve2, "8 * 3 + 9 + 3 * 4 * 3 + 1", 1920)
    test(::solve2, "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2", 23340)
    test(::solve2, "1 * 2 + 2", 4)
    test(::solve2, "1 + 2 * 2", 6)
    test(::solve2, "1 + 6 + 6 * 2 + 5 * 2", 182)
    test(::solve2, "3 + (8 + 6 * 4)", (14 * 4) + 3)
    test(::solve2, "7 * 3 * 3 + 9 * 3", 252 * 3)
    test(::solve2, "1 * 2 + 3", 5)
    test(::solve2, "1 * 2 + 3 + 4", 9)
    test(::solve2, "1 * 2 + 3 + 4 * 1", 9)
    test(::solve2, "1 * 2 + 3 * 2 + 2 + 2", 30)
    test(::solve2, "1 + (2 * 3) + (4 * (5 + 6))", 51)
    test(::solve2, "2 * 3 + (4 * 5)", 46)
    test(::solve2, "5 + (8 * 3 + 9 + 3 * 4 * 3)", 1445)
    test(::solve2, "5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))", 669060)
    test(::solve2, "1 * (6 * 7) + 4 * 2", ((6 * 7) + 4) * 2)
    solve(::solve2)
}

