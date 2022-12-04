import java.io.File

fun main() {
    var lines = File("input.txt").readLines()

    var totalInRange = 0
    var totalOverlap = 0
    for(line in lines) {
        var pair = line.split(",")
        var elf1 = pair[0].split("-").map {it.toInt()}
        var elf2 = pair[1].split("-").map {it.toInt()}
        if (isElfInRangeOtherElf(elf1, elf2) || isElfInRangeOtherElf(elf2, elf1)) {
            totalInRange++
        }
        if (hasElfOverlapWithOtherElf(elf1, elf2)) {
            totalOverlap++
        }
    }
    println("=== Part 1 ===")
    println("Total in range $totalInRange")

    println("=== Part 2 ===")
    println("Total overlap $totalOverlap")
}

fun isElfInRangeOtherElf(elf1: List<Int>, elf2: List<Int>): Boolean {
    for(section in elf1[0]..elf1[1]) {
        if (section !in elf2[0]..elf2[1]) {
            return false
        }
    }
    return true
}

fun hasElfOverlapWithOtherElf(elf1: List<Int>, elf2: List<Int>): Boolean {
    for(section in elf1[0]..elf1[1]) {
        if (section in elf2[0]..elf2[1]) {
            return true
        }
    }
    return false
}

fun isInRange(section: Int, rangeStart: Int, rangeEnd: Int): Boolean {
    return section in rangeStart..rangeEnd
}