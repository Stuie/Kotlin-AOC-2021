//const val SIZE = 10
//const val INPUT_FILE = "Day05-Test"
const val SIZE = 1000
const val INPUT_FILE = "Day05"

fun main() {
    val rawInput = readInput(INPUT_FILE)

    val input = parseInputPart1(rawInput)
    val inputPart2 = parseInputPart2(rawInput)

    println(part1(input))
    println(part2(inputPart2))
}

private fun parseInputPart1(input: List<String>): List<IntArray> {
    val output = buildOutput()

    input.forEach { rawLine ->
        val (start, end) = rawLine.replace(" -> ", "-").split('-')
        var (startX, startY) = start.split(',').map { Integer.parseInt(it) }
        var (endX, endY) = end.split(',').map { Integer.parseInt(it) }

        // Remove diagonal lines
        if (startX != endX && startY != endY) return@forEach

        //println("startX: $startX, endX: $endX, startY: $startY, endY: $endY")

        if (startX > endX) {
            val oldStartX = startX
            startX = endX
            endX = oldStartX
        }

        if (startY > endY) {
            val oldStartY = startY
            startY = endY
            endY = oldStartY
        }

        println("startX: $startX, endX: $endX, startY: $startY, endY: $endY")

        if (startX == endX) {
            // This is a vertical line
            for (i in startY..endY) {
                output[i][startX]++
            }
        } else {
            // This is a horizontal line
            for (i in startX..endX) {
                output[startY][i]++
            }
        }
    }

    return output
}

private fun part1(input: List<IntArray>): Int {
    println("PART 1")

//    input.forEach {
//        println(it.contentToString())
//    }

    return getAnswer(input)
}

private fun getAnswer(input: List<IntArray>): Int {
    var sum = 0
    input.forEach { line ->
        line.forEach { position ->
            if (position > 1) sum++
        }
    }
    return sum
}

private fun parseInputPart2(input: List<String>): List<IntArray> {
    val output = buildOutput()

    input.forEach { rawLine ->
        println("Line: $rawLine")
        val (start, end) = rawLine.replace(" -> ", "-").split('-')
        var (startX, startY) = start.split(',').map { Integer.parseInt(it) }
        var (endX, endY) = end.split(',').map { Integer.parseInt(it) }

        if (startX == endX) {
            // This is a vertical line
            if (startY > endY) {
                val oldStartY = startY
                startY = endY
                endY = oldStartY
            }

            for (i in startY..endY) {
                println("i: $i, startY: $startY, endY: $endY, startX: $startX")
                output[i][startX]++
            }
        } else if (startY == endY) {
            // This is a horizontal line
            if (startX > endX) {
                val oldStartX = startX
                startX = endX
                endX = oldStartX
            }

            for (i in startX..endX) {
                println("i: $i, startY: $startY, endY: $endY, startY: $startY")
                output[startY][i]++
            }
        } else {
            // Diagonal line
            var currentX = startX
            var currentY = startY

            if (startX > endX) {
                if (startY > endY) {
                    while (currentX >= endX && currentY >= endY) {
                        println("A) currentX: $currentX, startX: $startX, endX: $endX, currentY: $currentY, startY: $startY, endY: $endY")
                        println("  Before: $currentX,$currentY - " + output[currentX][currentY])
                        output[currentY][currentX]++
                        println("  After: $currentX,$currentY - " + output[currentX][currentY])
                        currentX--
                        currentY--
                    }
                } else {
                    while (currentX >= endX && currentY <= endY) {
                        println("B) currentX: $currentX, startX: $startX, endX: $endX, currentY: $currentY, startY: $startY, endY: $endY")
                        output[currentY][currentX]++
                        currentX--
                        currentY++
                    }
                }
            } else {
                if (startY > endY) {
                    while (currentX <= endX && currentY >= endY) {
                        println("C) currentX: $currentX, startX: $startX, endX: $endX, currentY: $currentY, startY: $startY, endY: $endY")
                        output[currentY][currentX]++
                        currentX++
                        currentY--
                    }
                } else {
                    while (currentX <= endX && currentY <= endY) {
                        println("D) currentX: $currentX, startX: $startX, endX: $endX, currentY: $currentY, startY: $startY, endY: $endY")
                        output[currentY][currentX]++
                        currentX++
                        currentY++
                    }
                }
            }
        }
    }

    return output
}

private fun part2(input: List<IntArray>): Int {

    println("\n\nPART 2")

    input.forEach {
        println(it.contentToString())
    }

    return getAnswer(input)
}

private fun buildOutput(): MutableList<IntArray> {
    val output = mutableListOf<IntArray>()

    for (i in 0 until SIZE) {
        val row = IntArray(SIZE)
        for (j in 0 until SIZE) {
            row[j] = 0
        }
        output.add(row)
    }
    return output
}