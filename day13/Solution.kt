import java.io.File
import java.util.concurrent.atomic.AtomicInteger

fun main() {
    val lines = File("input.txt").readLines()

    // Part 1
    val pairs = getPairs(lines)
    val part1 = pairs.mapIndexed { index, pair ->
        if (Item(pair.first) > Item(pair.second)) {
            index + 1
        } else {
            0
        }
    }.sum()
    println("Part 1: $part1")

    // Part 2
    val div1 = Item(readList("[[2]]", AtomicInteger(1)));
    val div2 = Item(readList("[[6]]", AtomicInteger(1)))

    val part2 = lines.filter { it != "" }
        .map { Item(readList(it, AtomicInteger(1))) }
        .toMutableList()
    part2.add(div1)
    part2.add(div2)
    part2.sortDescending()
    println("Part 2: ${(part2.indexOf(div1) + 1) * (part2.indexOf(div2) + 1)}")
}

fun getPairs(lines: List<String>): List<Pair<List<Item>, List<Item>>> {
    val pairs = mutableListOf<Pair<List<Item>, List<Item>>>()
    var i = 0
    while (i < lines.size) {
        pairs.add(
            Pair(
                readList(lines[i++], AtomicInteger(1)),
                readList(lines[i++], AtomicInteger(1))
            )
        )
        i++
    }
    return pairs
}

fun readList(line: String, index: AtomicInteger): List<Item> {
    val list = mutableListOf<Item>()
    while (index.get() < line.length) {
        when (line[index.get()]) {
            '[' -> {
                index.getAndIncrement()
                list.add(Item(readList(line, index)))
            }

            ']' -> {
                index.getAndIncrement()
                return list
            }

            ',' ->
                index.getAndIncrement()

            else ->
                list.add(readNumber(line, index))
        }
    }
    return list
}

fun readNumber(line: String, index: AtomicInteger): Item {
    var number = ""

    while (line[index.get()].isDigit()) {
        number += line[index.getAndIncrement()]
    }
    return Item(number.toInt())
}

data class Item(val value: Any) : Comparable<Any> {
    override fun compareTo(other: Any): Int {
        if (other !is Item) {
            throw IllegalStateException("Only compare Item")
        }

        if (this.value is Int && other.value is Int) {
            return other.value.compareTo(this.value)
        } else {
            val firstList = when (this.value) {
                is List<*> -> this.value as List<Item>
                else -> listOf(this)
            }
            val secondList = when (other.value) {
                is List<*> -> other.value as List<Item>
                else -> listOf(other)
            }

            val comparisonResult = compareToList(firstList, secondList)

            if (comparisonResult != 0) {
                return comparisonResult
            }
        }
        return 0
    }

    fun compareToList(firstList: List<Item>, secondList: List<Item>): Int {
        var i = 0
        while (i < firstList.size || i < secondList.size) {
            if (i == firstList.size) {
                return 1 // left runs out of items
            }
            if (i == secondList.size) {
                return -1 // right runs out of items
            }

            val comparisonResult = firstList[i].compareTo(secondList[i])
            if (comparisonResult != 0) {
                return comparisonResult
            }
            
            i++
        }
        return 0
    }
}
