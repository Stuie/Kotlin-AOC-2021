const val D6_INPUT_FILE = "Day06"

fun main() {
    val rawInput = readInput(D6_INPUT_FILE)

    val input = parseInputPart1(rawInput)

    println(bucketedSimulation(input.toMutableList(), 80))
    println(bucketedSimulation(input.toMutableList(), 256))
}

fun naiveSimulation(input: MutableList<Int>, days: Int): Int {
    for (i in 1..days) {
        var numToAdd = 0
        input.forEachIndexed { index, num ->
            if (num == 0) {
                input[index] = 6
                numToAdd++
            } else {
                input[index]--
            }
        }
        while (numToAdd > 0) {
            input.add(8)
            numToAdd--
        }
    }

    return input.size
}

fun bucketedSimulation(input: List<Int>, days: Int): Long {
    val fish = mutableMapOf(
        0 to 0L,
        1 to 0L,
        2 to 0L,
        3 to 0L,
        4 to 0L,
        5 to 0L,
        6 to 0L,
        7 to 0L,
        8 to 0L,
    )

    input.forEach {
        fish[it] = fish[it]!! + 1
    }

    for (i in 1..days) {
        var numToAdd = 0L
        for (key in 0..8) {
            if (key == 0) {
                numToAdd = fish[0]!!
                fish[0] = fish[1]!!
            } else if (key < 8) {
                fish[key] = fish[key+1]!!
            } else {
                fish[key] = numToAdd
            }
        }
        fish[6] = fish[6]!! + numToAdd
    }

    return fish.values.sum()
}

private fun parseInputPart1(lines: List<String>): List<Int> {
    return lines[0].split(',').map { Integer.parseInt(it) }
}