import java.io.File

fun main() {
    val lines = File("input.txt").readLines()

    val head = Position("H", 1, 5)
    val tail1 = Position("1", 1, 5)
    val tail2 = Position("2", 1, 5)
    val tail3 = Position("3", 1, 5)
    val tail4 = Position("4", 1, 5)
    val tail5 = Position("5", 1, 5)
    val tail6 = Position("6", 1, 5)
    val tail7 = Position("7", 1, 5)
    val tail8 = Position("8", 1, 5)
    val tail9 = Position("9", 1, 5)

    val uniqueTailPositions = mutableSetOf<String>()
    val uniqueTail9Positions = mutableSetOf<String>()

    for (line in lines) {
        val direction = line.split(" ")
//        println("$line : ")
        for (i in 1..direction[1].toInt()) {
            head.move(direction[0])
            tail1.follow(head)
            tail2.follow(tail1)
            tail3.follow(tail2)
            tail4.follow(tail3)
            tail5.follow(tail4)
            tail6.follow(tail5)
            tail7.follow(tail6)
            tail8.follow(tail7)
            tail9.follow(tail8)
//            println("$head - $tail1 - $tail2 - $tail3 - $tail4 - $tail5 - $tail6 - $tail7 - $tail8 - $tail9")
            uniqueTailPositions.add(tail1.toString())
            uniqueTail9Positions.add(tail9.toString())
        }
    }
    println("=== Part 1 ===")
    println("Unique tail positions: ${uniqueTailPositions.size}")
    println("=== Part 2 ===")
    println("Unique tail9 positions: ${uniqueTail9Positions.size}")
}

data class Position(val id: String, var x: Int, var y: Int) {
    fun move(direction: String) {
        when (direction) {
            "U" -> y--
            "D" -> y++
            "L" -> x--
            "R" -> x++
        }
    }

    fun follow(head: Position) {
        when (head.x) {
            this.x + 2 -> {
                this.x++
                when (head.y) {
                    this.y + 2 -> this.y++
                    this.y - 2 -> this.y--
                    else -> this.y = head.y
                }
            }

            this.x - 2 -> {
                this.x--
                when (head.y) {
                    this.y + 2 -> this.y++
                    this.y - 2 -> this.y--
                    else -> this.y = head.y
                }
            }

            else -> when (head.y) {
                this.y + 2 -> {
                    this.x = head.x
                    this.y++
                }

                this.y - 2 -> {
                    this.x = head.x
                    this.y--
                }
            }

        }
    }

    override fun toString(): String {
        return "Position$id($x, $y)"
    }
}
