import java.io.File

val gridWidth = 8
val gridHeight = 5

fun main() {
    val lines = File("input2.txt").readLines()
    val grid = mutableListOf<MutableList<Position>>()

    var startPosition = Position(0,0,'S')
    var endPosition = Position(0,5,'E')

    for(r in 0 until lines.size) {
        val row = mutableListOf<Position>()
        for(c in 0 until lines[r].length) {
            val currentPosition = Position(r, c, lines[r][c])
            row.add(currentPosition)

            when(currentPosition.char) {
                'S' -> { startPosition = currentPosition }
                'E' -> { endPosition = currentPosition }
            }
        }
        grid.add(row)
    }

    val terrain = Terrain(grid, startPosition, endPosition)

    println(startPosition)
    println(endPosition)

    

    //val routes = mutableListOf<Int>()

    //determineNextStep(0,0,grid,mutableListOf<Position>(),routes)

    //routes.sortDescending()
    //println("Kortste route ${routes.size} van =" + routes.min())
}

fun previousStep(currentPosition: Position, terrain: Terrain) {
    val previousSteps = mutableListOf<Position>()

    if (currentPosition.r > 0)
}

/* 
fun determineNextStep(posX: Int, posY: Int, grid: List<String>, visitedPositions: MutableList<Position>, routes: MutableList<Int>) {
    val currentPosition = Position(posX, posY, grid[posY][posX])
    visitedPositions.add(currentPosition)
    //println("Determine next step for current position $currentPosition")


    if (currentPosition.char == 'E') {
        routes.add(visitedPositions.size)

        if (visitedPositions.size == 36) {
            println("Route met lengte 36")
            visitedPositions.forEach {
                println (it)
            }
        }
        return
    }

    if (posX > 0) {
        // look left
        val nextPosition = Position(posX-1, posY, grid[posY][posX-1])
        if (goNext(currentPosition, nextPosition, visitedPositions)) {
            //println("go left")
            determineNextStep(nextPosition.x, nextPosition.y, grid, visitedPositions.toMutableList(), routes)

        }
    }
    if (posX < gridWidth -1) {
        // look right
        val nextPosition = Position(posX+1, posY, grid[posY][posX+1])
        if (goNext(currentPosition, nextPosition, visitedPositions)) {
            //println("go right")
            determineNextStep(nextPosition.x, nextPosition.y, grid, visitedPositions.toMutableList(), routes)
        }
    }

    if (posY > 0) {
        // look up
        val nextPosition = Position(posX, posY-1, grid[posY-1][posX])
        if (goNext(currentPosition, nextPosition, visitedPositions)) {
            //println("go up")
            determineNextStep(nextPosition.x, nextPosition.y, grid, visitedPositions.toMutableList(), routes)
        }
    }

    if (posY < gridHeight -1) {
        // look down
        val nextPosition = Position(posX, posY+1, grid[posY+1][posX])
        if (goNext(currentPosition, nextPosition, visitedPositions)) {
            //println("go down")
            determineNextStep(nextPosition.x, nextPosition.y, grid, visitedPositions.toMutableList(), routes)
        }
    }
}
*/

fun goNext(currentPosition: Position, nextPosition: Position, visitedPositions: List<Position>): Boolean {
    //println("cur: $currentPosition - next: $nextPosition")
    if (visitedPositions.contains(nextPosition)) {
        return false
    }
    return (nextPosition.char != 'S' && currentPosition.char.code <= nextPosition.char.code) || nextPosition.char == 'E' || currentPosition.char == 'S'
}

class Terrain(
    var grid: MutableList<MutableList<Position>>,
    var startPosition: Position,
    var endPosition: Position) {
}

data class Position(val r: Int, val c: Int, val char: Char) {
    fun getHeight(): Int {
        return when(this.char) {
            'S' -> 1
            'E' -> 26
            else -> this.char.code - 96
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }
        if (other is Position) {
            return this.r == other.r && this.c == other.c
        }
        return false
    }

    override fun toString(): String {
        return "Position($r, $c, $char(${getHeight()}))"
    }
}