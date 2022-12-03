import java.io.File

fun main() {
    var lines = File("input.txt").readLines()

    // part 1
    var totalPrioritiesPart1 = 0
    for (line in lines) {
        var left = line.substring(0, line.length / 2)
        var right = line.substring(line.length /2)

        totalPrioritiesPart1 += getPriority(getDuplicateItem(left, right))
    }

    println("=== Part 1 ===")
    println("Total priorities $totalPrioritiesPart1")

    var totalPrioritiesPart2 = 0
    for(i in 1..lines.size) {
        if(i % 3 == 0) {
            totalPrioritiesPart2 += getPriority(getDuplicateItem(lines.get(i-3), lines.get(i-2), lines.get(i-1)))
        }
    }

    println("=== Part 2 ===")
    println("Total priorities $totalPrioritiesPart2")
}

fun getDuplicateItem(left: String, right: String): Char {
    for (i in 0..left.length-1) {
        var currentChar = left.get(i)
        if (right.contains(currentChar)) {
            return currentChar
        }
    }
    return ' '
}

fun getDuplicateItem(first: String, second: String, third: String): Char {
    for (i in 0..first.length-1) {
        var currentChar = first.get(i)
        if (second.contains(currentChar) && third.contains(currentChar)) {
            return currentChar
        }
    }
    return ' '
}

fun getPriority(c: Char): Int {
    var i = c.code
    if (i <= 90) {
        return i - 38
    } else {
        return i - 96
    }
}