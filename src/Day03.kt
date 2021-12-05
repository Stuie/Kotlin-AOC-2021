fun main() {

    fun part1(input: List<String>): Int {
        val gamma = mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)

        input.forEach { line ->
            line.toCharArray().forEachIndexed { innerIndex, bit ->
                gamma[innerIndex] += if (bit == '1') 1 else -1
            }
        }

        println(gamma.map { if (it > 0) 1 else 0 })

        return 0
    }

    fun mostCommonBit(
        input: List<CharArray>,
        position: Int,
        tieBreaker: Char
    ): Char {
        var zeroCount = 0
        var oneCount = 0

        input.forEach { line ->
            if (line[position] == '1') oneCount++ else zeroCount++
        }

        //println("Most common bit. Position: $position. Zeroes: $zeroCount. Ones: $oneCount")
        return if (zeroCount > oneCount) {
            '0'
        } else if (oneCount > zeroCount) {
            '1'
        } else {
            //println("Most common bit decided by tiebreaker: $tieBreaker")
            tieBreaker
        }
    }

    fun leastCommonBit(
        input: List<CharArray>,
        position: Int,
        tieBreaker: Char
    ): Char {
        var zeroCount = 0
        var oneCount = 0

        input.forEach { line ->
            if (line[position] == '1') oneCount++ else zeroCount++
        }

        //println("Least common bit. Position: $position. Zeroes: $zeroCount. Ones: $oneCount")
        return if (zeroCount > oneCount) {
            '1'
        } else if (oneCount > zeroCount) {
            '0'
        } else {
            //println("Least common bit decided by tiebreaker: $tieBreaker")
            tieBreaker
        }
    }

    fun filter(
        input: List<CharArray>,
        position: Int,
        wantedBit: Char,
    ): List<CharArray> {
        val output = mutableListOf<CharArray>()

        //println("Filter input size: ${input.size}")

        input.forEach { line ->
            if (line[position] == wantedBit) {
                //println("  Keeping ${line.concatToString()}. Wanted bit: $wantedBit. Position: $position.")
                output.add(line)
            } else {
                //println("  Discarding ${line.concatToString()}. Wanted bit: $wantedBit. Position: $position.")
            }
        }

        //println("Filter output size: ${output.size}")

        return output
    }

    fun calculateOxygen(input: List<CharArray>, position: Int = 0): Int {
        val mostCommonBit = mostCommonBit(input, position, tieBreaker = '1')
        val filteredInput = filter(
            input,
            position,
            mostCommonBit,
        )

        when (filteredInput.size) {
            0 -> {
                // Uh-oh
                println("Filtered all the items")
            }
            1 -> {
                // Hooray!
                println("Oxygen ${filteredInput[0].concatToString()}")
            }
            else -> {
                calculateOxygen(filteredInput, position+1)
            }
        }

        return 0
    }

    fun calculateCo2(input: List<CharArray>, position: Int = 0): Int {
        val leastCommonBit = leastCommonBit(input, position, tieBreaker = '0')
        val filteredInput = filter(
            input,
            position,
            leastCommonBit,
        )

        when (filteredInput.size) {
            0 -> {
                // Uh-oh
                println("Filtered all the items")
            }
            1 -> {
                // Hooray!
                println("Co2 ${filteredInput[0].concatToString()}")
            }
            else -> {
                calculateCo2(filteredInput, position+1)
            }
        }

        return 0
    }

    fun part2(input: List<String>): Int {
        val sanitizedInput = input.map { it.toCharArray() }

        calculateOxygen(sanitizedInput)
        calculateCo2(sanitizedInput)

        return 0
    }

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}