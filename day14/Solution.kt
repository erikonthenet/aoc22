import java.io.File
import java.lang.Integer.max
import java.lang.Integer.min

val START = Point(500, 0)

fun main() {
    val lines = File("input.txt").readLines()
    val grid = parseFileToGrid(lines)

    var sandUnits = 0
    while (dropUnitOfSand(grid)) {
        sandUnits++
    }

    grid.drawGrid()
    println("Part 1: $sandUnits")

    // Part 2
    // Add long horizontal line
    Line(Point(grid.minX - 200, grid.maxY + 2), Point(grid.maxX + 200, grid.maxY + 2))
        .getPoints()
        .forEach { grid.addPoint(it, Content.ROCK) }

    // continue to drop sand
    while (dropUnitOfSand(grid, true)) {
        sandUnits++
    }
    grid.drawGrid()
    println("Part 2: $sandUnits")
}

fun dropUnitOfSand(grid: Grid, part2: Boolean = false): Boolean {
    var x = START.x
    var y = START.y

    if (part2 && grid.contains(x, y)) {
        return false
    }

    while (true) {
        if (!grid.contains(x, y + 1)) {
            y++
        } else {
            if (!grid.contains(x - 1, y + 1)) {
                x--
                y++
            } else if (!grid.contains(x + 1, y + 1)) {
                x++
                y++
            } else {
                grid.addPoint(Point(x, y), Content.SAND)
                return true
            }
        }

        if (!part2 && y >= grid.maxY) {
            return false
        }
    }
}

fun parseFileToGrid(lines: List<String>): Grid {
    val grid = Grid()

    lines.forEachIndexed { index, line ->
        val coordinates = line.split(" -> ")
        for (i in 0 until coordinates.size - 1) {
            Line(Point.of(coordinates[i]), Point.of(coordinates[i + 1]))
                .getPoints()
                .forEach { grid.addPoint(it, Content.ROCK) }
        }
    }
    return grid
}

data class Point(val x: Int, val y: Int) {
    companion object {
        fun of(s: String): Point {
            val xy = s.split(",").map { it.toInt() }
            return Point(xy[0], xy[1])
        }
    }
}

data class Line(val start: Point, val end: Point) {
    fun getPoints(): List<Point> {
        val points = mutableListOf<Point>()
        if (start.x == end.x) {
            for (y in min(start.y, end.y)..max(start.y, end.y)) {
                points.add(Point(start.x, y))
            }
        } else {
            for (x in min(start.x, end.x)..max(start.x, end.x)) {
                points.add(Point(x, start.y))
            }
        }
        return points
    }
}

class Grid {
    var minX = Int.MAX_VALUE
    var minY = Int.MAX_VALUE
    var maxX = 0
    var maxY = 0
    private val data = mutableMapOf<Point, Content>()

    fun addPoint(p: Point, content: Content) {
        minX = min(p.x, minX)
        minY = min(p.y, minY)
        maxX = max(p.x, maxX)
        maxY = max(p.y, maxY)
        data[p] = content
    }

    fun contains(x: Int, y: Int): Boolean {
        return data.contains(Point(x, y))
    }

    fun drawGrid() {
        for (y in minY..maxY) {
            for (x in minX..maxX) {
                val point = Point(x, y)
                if (data.contains(point)) {
                    print(data[point]?.char)
                } else {
                    print(' ')
                }
            }
            println()
        }
    }

    override fun toString(): String {
        return "Grid(minX=$minX, minY=$minY, maxX=$maxX, maxY=$maxY, data=$data)"
    }
}

enum class Content(val char: Char) {
    ROCK('#'),
    SAND('o')
}
