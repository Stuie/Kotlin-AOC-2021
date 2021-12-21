//const val D7_INPUT_FILE = "Day07-Test"
const val D7_INPUT_FILE = "Day07"

fun main() {
    val rawInput = readInput(D7_INPUT_FILE)

    val input = parseInputPart1(rawInput)

    println(part1(input.sorted()))
    println(part2(input.sorted()))
}

private fun part1(input: List<Int>): Int {
    val maxPosition = input.last()
    val minPosition = input.first()
    var lowestFuel = Integer.MAX_VALUE

    for (targetPosition in minPosition..maxPosition) {
        var fuelUsedForTarget = 0
        input.forEach { crabPosition ->
            fuelUsedForTarget += if (targetPosition > crabPosition) {
                targetPosition - crabPosition
            } else {
                crabPosition - targetPosition
            }
        }

        if (fuelUsedForTarget < lowestFuel) lowestFuel = fuelUsedForTarget
    }

    return lowestFuel
}

private fun part2(input: List<Int>): Int {
    val maxPosition = input.last()
    val minPosition = input.first()
    var lowestFuel = Integer.MAX_VALUE

    for (targetPosition in minPosition..maxPosition) {
        var fuelUsedForTarget = 0
        input.forEach { crabPosition ->
            val positionsMoved = if (targetPosition > crabPosition) {
                targetPosition - crabPosition
            } else {
                crabPosition - targetPosition
            }
            // 1 = 1
            // 2 = 3
            // 3 = 6
            // 4 = 10
            // 5 = 15

            var previousCost = 1
            for (step in 1..positionsMoved) {
                fuelUsedForTarget += previousCost
                previousCost++
            }
        }

        if (fuelUsedForTarget < lowestFuel) lowestFuel = fuelUsedForTarget
    }

    return lowestFuel
}

private fun parseInputPart1(lines: List<String>): List<Int> {
    return lines[0].split(',').map { Integer.parseInt(it) }
}