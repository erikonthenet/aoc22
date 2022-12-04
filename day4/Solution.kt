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
    var rangeElf1Start = elf1[0]
    var rangeElf1End = elf1[1]
    var rangeElf2Start = elf2[0]
    var rangeElf2End = elf2[1]
    for(section in rangeElf1Start..rangeElf1End) {
        if (!isInRange(section, rangeElf2Start, rangeElf2End)) {
            return false
        }
    }
    return true
}

fun hasElfOverlapWithOtherElf(elf1: List<Int>, elf2: List<Int>): Boolean {
    var rangeElf1Start = elf1[0]
    var rangeElf1End = elf1[1]
    var rangeElf2Start = elf2[0]
    var rangeElf2End = elf2[1]
    for(section in rangeElf1Start..rangeElf1End) {
        // println("$i in range $rangeElf2Start .. $rangeElf2End = ${i.toInt() in rangeElf2Start..rangeElf2End}")
        if (isInRange(section, rangeElf2Start, rangeElf2End)) {
            return true
        }
    }
    return false
}

fun isInRange(section: Int, rangeStart: Int, rangeEnd: Int): Boolean {
    return section in rangeStart..rangeEnd
}