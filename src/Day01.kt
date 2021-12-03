fun main() {
    fun part1(input: List<String>): Int {
        var increaseCount = 0
        var previous = 0
        input.forEachIndexed { index, item ->
            if (index == 0) return@forEachIndexed
            val current = Integer.parseInt(item)

            if (current > previous) {
                increaseCount++
            }

            previous = current
        }

        return increaseCount
    }

    fun part2(input: List<String>): Int {
        var increaseCount = 0
        var previousSum = 0

        input.forEachIndexed { index, item ->
            if (index == 0) return@forEachIndexed
            if (input.size < index + 3) return@forEachIndexed

            val first = Integer.parseInt(item)
            val second = Integer.parseInt(input[index+1])
            val third = Integer.parseInt(input[index+2])

            val currentSum = first + second + third

            if (currentSum > previousSum) increaseCount++

            previousSum = currentSum
        }

        return increaseCount
    }

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
