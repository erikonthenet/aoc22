import java.io.File

fun main() {
    val lines = File("input.txt").readLines()
    val startPosition = findPosition('S', lines)
    val endPosition = findPosition('E', lines)

    println("=== Part 1 ===")
    println(
        determinePathLength(
            startPosition,
            lines,
            isPossibleRoute = { currentPosition, nextPosition -> nextPosition.getHeight() - currentPosition.getHeight() <= 1 },
            isFinish = { position -> position == endPosition }
        )
    )
    println("=== Part 2 ===")
    println(
        determinePathLength(
            endPosition,
            lines,
            isPossibleRoute = { currentPosition, nextPosition -> currentPosition.getHeight() - nextPosition.getHeight() <= 1 },
            isFinish = { position -> position.getHeight() == 1 }
        )
    )
}

fun determinePathLength(
    startAt: Position,
    grid: List<String>,
    isPossibleRoute: (Position, Position) -> Boolean,
    isFinish: (Position) -> Boolean
): Int {
    val positions = ArrayDeque<Position>()
    positions.addLast(startAt)
    val visitedPositions = mutableSetOf<Position>()

    while (positions.isNotEmpty()) {
        val currentPosition = positions.removeFirst()

        if (visitedPositions.contains(currentPosition)) {
            continue
        }

        if (isFinish(currentPosition)) {
            return currentPosition.path
        }

        listOf(0 to 1, -1 to 0, 0 to -1, 1 to 0)
            .filter { isInGrid(currentPosition.x + it.first, currentPosition.y + it.second, grid) }
            .map {
                Position(
                    currentPosition.x + it.first,
                    currentPosition.y + it.second,
                    grid[currentPosition.y + it.second][currentPosition.x + it.first],
                    currentPosition.path + 1
                )
            }
            .filter { isPossibleRoute(currentPosition, it) }
            .filter { !visitedPositions.contains(it) }
            .forEach { positions.add(it) }

        visitedPositions.add(currentPosition)
    }
    return -1
}

fun isInGrid(x: Int, y: Int, grid: List<String>): Boolean {
    val result = x >= 0 && x < grid[0].length
            && y >= 0 && y < grid.size
    return result
}

fun findPosition(charToFind: Char, grid: List<String>): Position {
    grid.mapIndexed { row, line ->
        line.mapIndexed { col, char ->
            if (char == charToFind) {
                return Position(col, row, char)
            }
        }
    }
    throw IllegalStateException("Char $charToFind not in grid")
}

data class Position(val x: Int, val y: Int, val char: Char, val path: Int = 0) {
    fun getHeight(): Int {
        return when (this.char) {
            'S' -> 1
            'E' -> 26
            else -> this.char.code - 96
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other != null && other is Position) {
            return this.x == other.x && this.y == other.y
        }
        return false
    }
}
