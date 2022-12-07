import java.io.File

val totalsList: MutableList<Int> = mutableListOf()

fun main() {
    val lines = File("input.txt").readLines()

    val root = TreeNode("/")
    var currentTreeNode = root
    var index = 0

    while (index < lines.size) {
        val chunks = lines[index].split(" ")
        if (chunks[0] == "$") {
            if (chunks[1] == "cd") {
                // command
                if (chunks[2] == "/") {
                    currentTreeNode = root
                } else if (chunks[2] == "..") {
                    currentTreeNode = currentTreeNode.parent!!
                } else {
                    val newNode = TreeNode(chunks[2])
                    currentTreeNode.addChild(newNode)
                    currentTreeNode = newNode
                }
                index++
            } else {
                // list
                index = readFilesReturningNextIndex(currentTreeNode, lines, index + 1)
            }
        }
    }

    val totalFilesize = root.getDirSize()
    val totalAtMost100k = totalsList.filter { it <= 100000 }.sum()

    println("Part 1: $totalAtMost100k")

    val freeSpace = 70000000 - totalFilesize
    val neededSpace = 30000000 - freeSpace
    val minimumSizeToFree = totalsList.filter { it >= neededSpace }.min()

    println("Part 2: $minimumSizeToFree")
}


fun readFilesReturningNextIndex(node: TreeNode, lines: List<String>, startIndex: Int): Int {
    var index = startIndex
    var isCommand = false
    var totalFileSize = 0
    while (!isCommand && index < lines.size) {
        val chunks = lines[index].split(" ")
        if (chunks[0] == "$") {
            isCommand = true
        } else {
            if (chunks[0] != "dir") {
                totalFileSize += chunks[0].toInt()
            }
            index++
        }
    }
    node.size = totalFileSize
    return index
}

class TreeNode(name: String) {
    var name = name
    var size = 0
    var parent: TreeNode? = null

    var children: MutableList<TreeNode> = mutableListOf()

    fun addChild(node: TreeNode) {
        children.add(node)
        node.parent = this
    }

    fun getDirSize(): Int {
        return getDirSize(this)
    }

    private fun getDirSize(node: TreeNode): Int {
        val sum = node.size +
                node.children.sumOf { getDirSize(it) }
        totalsList.add(sum)
        return sum
    }
}
