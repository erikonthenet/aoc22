import java.io.File

fun main() {
    val lines = File("input.txt").readLines()
    val emptyLine = getEmptyLine(lines)

    // part 1
    var stacks = getStacks(lines, emptyLine)

    for (index in emptyLine + 1 until lines.size) {
        val chunks = lines[index].split(' ')
        val numberToMove = chunks[1].toInt()
        val fromStack = chunks[3].single()
        val toStack = chunks[5].single()
        for (i in 1..numberToMove) {
            val crateToMove = stacks[fromStack]?.removeLast()
            if (crateToMove != null) {
                stacks[toStack]?.addLast(crateToMove)
            }
        }
    }
    println("=== Part 1 ===")
    stacks.forEach { (t, u) ->
        print(u.last())
    }
    println()

    // part 2
    stacks = getStacks(lines, emptyLine)

    for (index in emptyLine + 1 until lines.size) {
        val chunks = lines[index].split(' ')
        val numberToMove = chunks[1].toInt()
        val fromStack = chunks[3].single()
        val toStack = chunks[5].single()
        val cratesToMove = ArrayDeque<Char>()

        for (i in 1..numberToMove) {
            stacks[fromStack]?.removeLast()?.let { cratesToMove.addFirst(it) }
        }
        stacks[toStack]?.addAll(cratesToMove)
    }

    println("=== Part 2 ===")
    stacks.forEach { (t, u) ->
        print(u.last())
    }
}

fun getStacks(lines: List<String>, emptyLine: Int): Map<Char, ArrayDeque<Char>> {
    val map = mutableMapOf<Char, ArrayDeque<Char>>()
    for (index in 0 until lines[emptyLine - 1].length) {
        val currentChar = lines[emptyLine - 1][index]
        if (currentChar != ' ') {
            map[currentChar] = ArrayDeque()

            for (index2 in emptyLine - 2 downTo 0) {
                val crateChar = lines[index2][index]
                if (crateChar != ' ') {
                    map[currentChar]?.addLast(crateChar)
                }
            }
        }
    }
    return map
}

fun getEmptyLine(lines: List<String>): Int {
    for ((index, line) in lines.withIndex()) {
        if (line == "") {
            return index
        }
    }
    return -1
}
