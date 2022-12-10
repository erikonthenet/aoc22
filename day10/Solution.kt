import java.io.File

fun main() {
    val lines = File("input.txt").readLines()
    val signalStrengths = mutableListOf<Int>()
    var pixels = mutableListOf<Char>()

    var cycle = 0
    var x = 1
    var instructionPosition = 0
    
    var valueToAdd = 0
    var isBusy = false
    
    while(cycle < 220 || instructionPosition < lines.size) {
        cycle++

        // determine signal strength
        if ((cycle-20) % 40 == 0) {
             signalStrengths.add(cycle * x);
        }
        // draw pixel
        if (spriteIsOnCurrentPixel(x, pixels.size%40)) {
            pixels.add('#')
        } else {
            pixels.add(' ')
        }
        // process instruction
        if (instructionPosition < lines.size || isBusy) {
            if (lines[instructionPosition] == "noop" || isBusy) {
                if (isBusy) {
                    x += valueToAdd
                    isBusy = false
                }
                instructionPosition++
            } else {
                valueToAdd = lines[instructionPosition].split(" ")[1].toInt()
                isBusy = true
            }
        }
    }

    println("=== Part 1 ===")
    println("Signal strength: " + signalStrengths.sumOf {it}) //14540

    println("=== Part 2 ===")
    for(i in 0 until pixels.size) {
        print(pixels[i])
        if ((i+1)%40 == 0) {
            println()
        }
    }
}

fun spriteIsOnCurrentPixel(x: Int, currentPixel: Int): Boolean {
    return currentPixel >= x-1 && currentPixel <= x+1
}
