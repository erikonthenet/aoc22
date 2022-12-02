import java.io.File

fun main() {
    var elfTotalCalories = mutableListOf<Int>()
    var currentElfCalories = 0;

    File("input.txt").forEachLine {
        if (it == "") {
            elfTotalCalories.add(currentElfCalories)
            currentElfCalories = 0
        } else {
            currentElfCalories += it.toInt()
        }
    }
    elfTotalCalories.add(currentElfCalories)
    elfTotalCalories.sortDescending()

    println("Aantal elven: ${elfTotalCalories.size}")
    println("Max: " + elfTotalCalories.first())
    println("Min: " + elfTotalCalories.last())

    println("Top 3 heeft ${elfTotalCalories.take(3).sum()}")
}
