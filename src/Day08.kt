private const val D8_INPUT_FILE = "Day08-Test"
//private const val D8_INPUT_FILE = "Day08"

private const val EMPTY = '0'

fun main() {
    val rawInput = readInput(D8_INPUT_FILE)

    val input = parseInput(rawInput)

    println(part1(input))
    println(part2Recursive(input))
}

private fun parseInput(parserInput: List<String>): List<Pair<List<String>, List<String>>> {
    val parserOutput = mutableListOf<Pair<List<String>, List<String>>>()
    parserInput.forEach { line ->
        val (input, output) = line.split('|').map { it.trim() }
        val inputs = input.split(' ')
        val outputs = output.split(' ')

        parserOutput.add(Pair(inputs, outputs))
    }

    return parserOutput
}

private fun part1(input: List<Pair<List<String>, List<String>>>): Int {
    var count = 0

    input.forEach { entry ->
        entry.second.forEach { output ->
            if (output.length == 2 || output.length == 3 || output.length == 4 || output.length == 7) count++
        }
    }

    return count
}

private fun part2Parse(
    input: Pair<List<String>, List<String>>,
    display: Display,
): Long {
    var outputSum = 0L

    // The signals potentially change every line. Need to figure out everything at this level of looping.
    val potentialA = mutableListOf<Char>()
    val potentialB = mutableListOf<Char>()
    val potentialC = mutableListOf<Char>()
    val potentialD = mutableListOf<Char>()
    val potentialE = mutableListOf<Char>()
    val potentialF = mutableListOf<Char>()
    val potentialG = mutableListOf<Char>()

    input.first.forEach { signal ->
        val sortedSignal = signal.toCharArray().sorted()

        when (sortedSignal.size) {
            2 -> {
                if (display.displays1.isEmpty()) {
                    // This is a 1
                    potentialB.addAll(sortedSignal)
                    potentialC.addAll(sortedSignal)

                    display.displays1.addAll(sortedSignal)
                }

                if (display.segmentA != EMPTY && display.segmentC != EMPTY) {
                    display.segmentB = sortedSignal.minus(display.segmentC).first()
                }
            }
            3 -> {
                if (display.displays7.isEmpty()) {
                    // This is a 7
                    potentialA.addAll(sortedSignal)
                    potentialB.addAll(sortedSignal)
                    potentialC.addAll(sortedSignal)

                    display.displays7.addAll(sortedSignal)

                    if (display.displays1.isNotEmpty()) {
                        display.segmentA = display.displays7.minus(display.displays1.toSet()).first()
                    }
                }
            }
            4 -> {
                if (display.displays4.isEmpty()) {
                    // This is a 4
                    potentialB.addAll(sortedSignal)
                    potentialC.addAll(sortedSignal)
                    potentialF.addAll(sortedSignal)
                    potentialG.addAll(sortedSignal)

                    display.displays4.addAll(sortedSignal)
                }
            }
            5 -> {
                // This is a 2 or a 3 or a 5

                // If all the segments are present in a 7, it's a 3
                if (display.displays3.isEmpty() &&
                    display.displays7.isNotEmpty()
                ) {
                    if (sortedSignal.containsAll(display.displays7)) {
                        display.displays3.addAll(sortedSignal)
                    }
                }

                // If we know display3, and Segment B, and segment B is present, then it's a 2
                if (display.displays2.isEmpty() &&
                    display.displays3.isNotEmpty() &&
                    display.segmentB != EMPTY &&
                    sortedSignal.contains(display.segmentB)
                ) {
                    display.displays2.addAll(sortedSignal) // TODO Figure out if this is incorrect
                }

                // If we know display3, and segment C, and segment C is missing then it's a 2
                if (display.displays2.isEmpty() &&
                        display.displays3.isNotEmpty() &&
                        display.segmentC != EMPTY &&
                        !sortedSignal.contains(display.segmentC)
                ) {
                    display.displays2.addAll(sortedSignal) // TODO Or maybe it's this one
                }

                // If we know segment B and segment B is not present, then it's a 5
                if (display.displays5.isEmpty() &&
                    display.segmentB != EMPTY &&
                    !sortedSignal.contains(display.segmentB)
                ) {
                    display.displays5.addAll(sortedSignal)
                }

                // Fallback logic that depends on knowing the other possibilities
                if (display.displays2.isEmpty() &&
                    display.displays3.isNotEmpty() &&
                    display.displays5.isNotEmpty()
                ) {
                    display.displays2.addAll(sortedSignal) // TODO Or it could be this one, because the 5 is being done wrong
                }

                if (display.displays3.isEmpty() &&
                    display.displays2.isNotEmpty() &&
                    display.displays5.isNotEmpty()
                ) {
                    display.displays3.addAll(sortedSignal)
                }

                if (display.displays5.isEmpty() &&
                    display.displays3.isNotEmpty() &&
                    display.displays2.isNotEmpty()
                ) {
                    display.displays5.addAll(sortedSignal)
                }
            }
            6 -> {
                // This is a 6 or a 9 or a 0
                if (display.displays6.isEmpty() &&
                    display.displays1.isNotEmpty()
                ) {
                    // We can figure out if this is a 6
                    display.displays1.forEach { d1Char ->
                        if (!sortedSignal.contains(d1Char)) {
                            display.displays6.addAll(sortedSignal)
                            display.segmentC = d1Char
                        }
                    }
                }

                if (display.displays0.isEmpty() &&
                    display.segmentG != EMPTY
                ) {
                    display.displays0.addAll(sortedSignal)
                }

                // Fallback logic that depends on knowing the other possibilities
                if (display.displays0.isEmpty() &&
                    display.displays6.isNotEmpty() &&
                    display.displays9.isNotEmpty()
                ) {
                    display.displays0.addAll(sortedSignal)
                }

                if (display.displays6.isEmpty() &&
                    display.displays0.isNotEmpty() &&
                    display.displays9.isNotEmpty()
                ) {
                    display.displays6.addAll(sortedSignal)
                }

                if (display.displays9.isEmpty() &&
                    display.displays0.isNotEmpty() &&
                    display.displays6.isNotEmpty()
                ) {
                    display.displays9.addAll(sortedSignal)
                }
            }
            7 -> {
                if (display.displays8.isEmpty()) {
                    // This is an 8, no point adding everything, it'll only complicate things.
                    display.displays8.addAll(sortedSignal)
                }
            }
        }

        // If a segment is present in the 2, the 5, and the 4, it's segment G
        if (display.segmentG == EMPTY &&
            display.displays2.isNotEmpty() &&
            display.displays4.isNotEmpty() &&
            display.displays5.isNotEmpty()
        ) {
            display.segmentG = display.displays2.toSet()
                .intersect(display.displays4.toSet())
                .intersect(display.displays5.toSet())
                .first()
        }
    }

    return outputSum
}

private fun part2Recursive(
    input: List<Pair<List<String>, List<String>>>,
): Long {
    var totalSum = 0L
    var display = Display()

    input.forEach { line ->
        var i = 0
        while (!display.complete() && i < 10) {
            // Do the parsing logic.
            part2Parse(line, display)
            i++
        }

        println(display)
        totalSum += calculateOutput(line.second, display)
        display = Display()
    }

    return totalSum
}

private fun calculateOutput(input: List<String>, display: Display): Long {
    var outputString = ""

    input.forEach { outputItem ->
        val sortedOutput = outputItem.toCharArray().sorted()

        if (sortedOutput.containsAll(display.displays0) && display.displays0.containsAll(sortedOutput)) {
            outputString = outputString.plus(0)
        } else if (sortedOutput.containsAll(display.displays1) && display.displays1.containsAll(sortedOutput)) {
            outputString = outputString.plus(1)
        } else if (sortedOutput.containsAll(display.displays2) && display.displays2.containsAll(sortedOutput)) {
            outputString = outputString.plus(2)
        } else if (sortedOutput.containsAll(display.displays3) && display.displays3.containsAll(sortedOutput)) {
            outputString = outputString.plus(3)
        } else if (sortedOutput.containsAll(display.displays4) && display.displays4.containsAll(sortedOutput)) {
            outputString = outputString.plus(4)
        } else if (sortedOutput.containsAll(display.displays5) && display.displays5.containsAll(sortedOutput)) {
            outputString = outputString.plus(5) // TODO Figure out why this is missing sometimes
        } else if (sortedOutput.containsAll(display.displays6) && display.displays6.containsAll(sortedOutput)) {
            outputString = outputString.plus(6)
        } else if (sortedOutput.containsAll(display.displays7) && display.displays7.containsAll(sortedOutput)) {
            outputString = outputString.plus(7)
        } else if (sortedOutput.containsAll(display.displays8) && display.displays8.containsAll(sortedOutput)) {
            outputString = outputString.plus(8)
        } else if (sortedOutput.containsAll(display.displays9) && display.displays9.containsAll(sortedOutput)) {
            outputString = outputString.plus(9) // TODO Figure out why this isn't working
        }
    }

    println("$outputString for $input")

    return outputString.toLong()
}

data class Display(
    val displays0: MutableList<Char> = mutableListOf(),
    var displays1: MutableList<Char> = mutableListOf(),
    var displays2: MutableList<Char> = mutableListOf(),
    var displays3: MutableList<Char> = mutableListOf(),
    var displays4: MutableList<Char> = mutableListOf(),
    var displays5: MutableList<Char> = mutableListOf(),
    var displays6: MutableList<Char> = mutableListOf(),
    var displays7: MutableList<Char> = mutableListOf(),
    var displays8: MutableList<Char> = mutableListOf(),
    var displays9: MutableList<Char> = mutableListOf(),
    var segmentA: Char = EMPTY,
    var segmentB: Char = EMPTY,
    var segmentC: Char = EMPTY,
    var segmentD: Char = EMPTY,
    var segmentE: Char = EMPTY,
    var segmentF: Char = EMPTY,
    var segmentG: Char = EMPTY,
) {
    fun complete(): Boolean {
        return displays0.isNotEmpty() &&
                displays1.isNotEmpty() &&
                displays2.isNotEmpty() &&
                displays3.isNotEmpty() &&
                displays4.isNotEmpty() &&
                displays5.isNotEmpty() &&
                displays6.isNotEmpty() &&
                displays7.isNotEmpty() &&
                displays8.isNotEmpty() &&
                displays9.isNotEmpty()
    }
}