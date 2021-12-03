fun main() {
    fun part1(input: List<String>): Int {
        var x = 0
        var y = 0

        input.forEach { line ->
            val (direction, amount) = line.split(" ")

            when (direction) {
                "forward" -> {
                    x += Integer.parseInt(amount)
                }
                "down" -> {
                    y += Integer.parseInt(amount)
                }
                "up" -> {
                    y -= Integer.parseInt(amount)
                }
            }
        }

        return x * y
    }

    fun part2(input: List<String>): Int {
        var x = 0
        var y = 0
        var aim = 0

        input.forEach { line ->
            val (direction, amount) = line.split(" ")

            when (direction) {
                "forward" -> {
                    x += Integer.parseInt(amount)
                    y += Integer.parseInt(amount) * aim
                }
                "down" -> {
                    aim += Integer.parseInt(amount)
                }
                "up" -> {
                    aim -= Integer.parseInt(amount)
                }
            }
        }

        return x * y
    }

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}