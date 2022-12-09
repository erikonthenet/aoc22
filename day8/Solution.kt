import java.io.File

fun main() {
    val grid = File("input.txt").readLines()

    var treesVisible = 0;
    var maxScenicScore = 0;
    for (i in 0 until grid.size) {
        for (j in 0 until grid[i].length) {
            if (isVisible(grid, i, j)) {
                treesVisible++
            }
            val scenicScore = calculateScenicScore(grid, i, j)
            if (scenicScore > maxScenicScore) {
                maxScenicScore = scenicScore
            }
        }
    }

    println("=== Part 1 ===")
    println("Trees visible from edge: $treesVisible")

    println("=== Part 2 ===")
    println("Max scenic score: $maxScenicScore")
}

fun isVisible(grid: List<String>, vIndex: Int, hIndex: Int): Boolean {
    if (vIndex == 0 || vIndex == grid.size - 1) {
        return true
    }
    if (hIndex == 0 || hIndex == grid[vIndex].length - 1) {
        return true;
    }

    val treeHeight = grid[vIndex][hIndex].digitToInt()

    // check to top
    var higherTreesToTop = 0
    for (i in vIndex - 1 downTo 0) {
        if (grid[i][hIndex].digitToInt() >= treeHeight) {
            higherTreesToTop++
        }
    }
    if (higherTreesToTop == 0) {
        return true;
    }

    // check to bottom
    val allTreesToBottom = mutableListOf<Int>()
    for (i in vIndex + 1 until grid.size) {
        allTreesToBottom.add(grid[i][hIndex].digitToInt())
    }
    if (allTreesToBottom.filter { it >= treeHeight }.isEmpty()) {
        return true;
    }

    // check to left
    val allTreesToLeft = mutableListOf<Int>()
    for (i in hIndex - 1 downTo 0) {
        allTreesToLeft.add(grid[vIndex][i].digitToInt())
    }
    if (allTreesToLeft.filter { it >= treeHeight }.isEmpty()) {
        return true;
    }

    // check to right
    val allTreesToRight = mutableListOf<Int>()
    for (i in hIndex + 1 until grid[vIndex].length) {
        allTreesToRight.add(grid[vIndex][i].digitToInt())
    }
    if (allTreesToRight.filter { it >= treeHeight }.isEmpty()) {
        return true;
    }

    return false
}

fun calculateScenicScore(grid: List<String>, vIndex: Int, hIndex: Int): Int {
    if (vIndex == 0 || vIndex == grid.size - 1) {
        return 0
    }
    if (hIndex == 0 || hIndex == grid[vIndex].length - 1) {
        return 0;
    }

    val treeHeight = grid[vIndex][hIndex].digitToInt()

    // check to top
    var scoreToTop = 0
    for (i in vIndex - 1 downTo 0) {
        if (grid[i][hIndex].digitToInt() >= treeHeight) {
            scoreToTop++
            break
        }
        scoreToTop++
    }

    // check to bottom
    var scoreToBottom = 0
    for (i in vIndex + 1 until grid.size) {
        if (grid[i][hIndex].digitToInt() >= treeHeight) {
            scoreToBottom++
            break
        }
        scoreToBottom++
    }

    // check to left
    var scoreToLeft = 0
    for (i in hIndex - 1 downTo 0) {
        if (grid[vIndex][i].digitToInt() >= treeHeight) {
            scoreToLeft++
            break
        }
        scoreToLeft++
    }

    // check to right
    var scoreToRight = 0
    for (i in hIndex + 1 until grid[vIndex].length) {
        if (grid[vIndex][i].digitToInt() >= treeHeight) {
            scoreToRight++
            break
        }
        scoreToRight++
    }

    return scoreToTop * scoreToBottom * scoreToLeft * scoreToRight
}
