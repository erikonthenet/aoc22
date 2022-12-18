import java.io.File
import kotlin.math.abs

fun main() {
    val trackY = 2000000
    val lines = File("input.txt").readLines()
    val grid = parseFileToGrid(lines, trackY)
    //grid.drawGrid()

    println("Part 1: " + grid.getNoBeaconsAtY(trackY))

    // val pointNotInGrid = grid.getCoordinatesNotInGrid(trackMin, trackMax)
    // println("Part 2: " + pointNotInGrid)
    // println("Frequency: " + (4000000*pointNotInGrid.x + pointNotInGrid.y))
}

fun parseFileToGrid(lines: List<String>, trackY: Int): Grid {
    val grid = Grid()

    lines.forEachIndexed { index, line ->
        val coordinates = line.replace("Sensor at x=", "").replace(": closest beacon is at x=", ", ").replace("y=", "")
            .split(", ",).map {it.toInt()}
        // println(coordinates)
        val sensor = Point(coordinates[0], coordinates[1])
        val beacon = Point(coordinates[2], coordinates[3])
        if (beacon.y == trackY) {
            grid.addPoint(beacon, Content.BEACON)
        }
        // grid.addPoint(sensor, Content.SENSOR)
        
        val distance = getManhattanDistance(sensor, beacon)
        // println(distance)
        getPointsFromSensorDistance(sensor, distance, trackY)
            .filter {!grid.contains(it)}
            .forEach {grid.addPoint(it, Content.NO_BEACON)}
    }
    return grid
}

fun getManhattanDistance(a: Point, b: Point): Int {
    return abs(a.x - b.x) + abs(a.y - b.y)
}

fun getPointsFromSensorDistance(p: Point, maxDistance: Int, trackY: Int): List<Point> {
    val points = mutableListOf<Point>()
    for (deltaY in -maxDistance..maxDistance) {
        if (p.y + deltaY == trackY) {
            for (deltaX in 0..maxDistance-abs(deltaY)) {
                points.add(Point(p.x+deltaX, p.y+deltaY))
                points.add(Point(p.x-deltaX, p.y+deltaY))
            }
        }
    }

    return points
}

data class Point(val x: Int, val y: Int) {
    companion object {
        fun of(s: String): Point {
            val xy = s.split(",").map { it.toInt() }
            return Point(xy[0], xy[1])
        }
    }
}
class Grid {
    var minX = Int.MAX_VALUE
    var minY = Int.MAX_VALUE
    var maxX = 0
    var maxY = 0
    private val data = mutableMapOf<Point, Content>()

    fun addPoint(p: Point, content: Content) {
        minX = Integer.min(p.x, minX)
        minY = Integer.min(p.y, minY)
        maxX = Integer.max(p.x, maxX)
        maxY = Integer.max(p.y, maxY)
        data[p] = content
    }

    fun contains(x: Int, y: Int): Boolean {
        return this.contains(Point(x, y))
    }

    fun contains(p: Point): Boolean {
        return data.contains(p)
    }

    fun getNoBeaconsAtY(y: Int): Int {
        var count = 0
        for (x in minX..maxX) {
            val point = Point(x, y)
            if (data[point] == Content.NO_BEACON) {
                count ++
            }
        }
        return count
    }

    fun drawGrid() {
        for (y in minY..maxY) {
            for (x in minX..maxX) {
                val point = Point(x, y)
                if (data.contains(point)) {
                    print(data[point]?.char)
                } else {
                    print('.')
                }
            }
            println()
        }
    }

    fun getCoordinatesNotInGrid(trackMin: Point, trackMax: Point): Point {
        for(x in trackMin.x..trackMax.x) {
            for(y in trackMin.y..trackMax.y) {
                val p = Point(x,y)
                if(!data.contains(p)) {
                    return p
                }
            }
        }
        return Point(-1,-1)
    }

    override fun toString(): String {
        return "Grid(minX=$minX, minY=$minY, maxX=$maxX, maxY=$maxY, data=$data)"
    }
}

enum class Content(val char: Char) {
    SENSOR('S'),
    BEACON('B'),
    NO_BEACON('#')
}