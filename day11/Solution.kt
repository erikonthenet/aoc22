import java.io.File

fun main() {
    val lines = File("input.txt").readLines()

    val monkeys = parseInput(lines)
    val rounds = 10000

    val divider = {it / 3}
    //val divider = monkeys.values.map{it.divider}.reduce(Int::times)

    for (round in 1..rounds) {
        monkeys.forEach{id, monkey ->
            while(monkey.items.size > 0) {
                var item = monkey.items.removeAt(0)
                item = worryOperation(item, monkey.operationOp, monkey.operationVal)
                //item = worryCooldown(item, divider)
                item = item % divider
                if (item % monkey.divider == 0L) {
                    monkeys[monkey.throwToIfTrue]?.items?.add(item)
                } else {
                    monkeys[monkey.throwToIfFalse]?.items?.add(item)
                }
                monkey.addInspectedItem()
            }
        }
    }

    monkeys.forEach { k, v ->
        println(v)
    }

    val part1 = monkeys.values
        .map{it.itemsInspected}
        .toMutableList()
    part1.sortDescending()
    println(part1[0] * part1[1])
}

fun worryOperation(item: Long, operationOp: String, operationVal: String): Long {
    var value: Long
    if (operationVal == "old") {
        value = item
    } else {
        value = operationVal.toLong()
    }

    if (operationOp == "*") {
        return item * value
    } else {
        return item + value
    }
}

fun worryCooldown(item: Long, divider: Int): Long {
    return Math.floor(item.toDouble()/divider).toLong()
}

fun parseInput(lines: List<String>): Map<Int, Monkey> {
    var i = 0
    val monkeys = mutableMapOf<Int, Monkey>()

    while(i < lines.size) {
        // line 1
        if (lines[i].startsWith("Monkey")) {
            val id = lines[i++][7].digitToInt()
            val items = lines[i++].substring(18).split(", ").map{it.toLong()}
            val operation = lines[i++].substring(23).split(" ")
            val divider = lines[i++].substring(21).toInt()
            val throwToIfTrue = lines[i++][29].digitToInt()
            val throwToIfFalse = lines[i++][30].digitToInt()
            monkeys.put(id, Monkey(id, items, operation[0], operation[1], divider, throwToIfTrue, throwToIfFalse))
        }
        i++
    }
    return monkeys
}

class Monkey(
    id: Int,
    items: List<Long>,
    operationOp: String,
    operationVal: String,
    divider: Int,
    throwToIfTrue: Int,
    throwToIfFalse: Int
) {
    val id = id
    val items = items.toMutableList()
    val operationOp = operationOp
    val operationVal = operationVal
    val divider = divider
    val throwToIfTrue = throwToIfTrue
    val throwToIfFalse = throwToIfFalse

    var itemsInspected: Long = 0

    fun addInspectedItem() {
        this.itemsInspected++
    }

    override fun toString(): String {
        return """Monkey($id)
        items: $items
        items inspected: $itemsInspected"""
    }
}