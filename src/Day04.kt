fun main() {
    val callInput = readInput("Day04-Calls")
    val boardInput = readInput("Day04-Boards")

    val calls = parseCalls(callInput)
    val boards = parseBoards(boardInput)

//    println("Part 1")
//    calls.forEachIndexed { index, number ->
//        boards.forEachIndexed { boardIndex, board ->
//            println("Call #$index $number. Board $boardIndex ${board.numbers}")
//            if (board.checkNumber(number)) {
//                println("  Answer: ${board.answer()}")
//                board.numbers.forEach { println("  $it") }
//                return
//            }
//        }
//    }

    println("\n\nPart 2")

    var lastItem = false
    calls.forEachIndexed { index, number ->
        boards.forEachIndexed { boardIndex, board ->
            //println("Call #$index $number. Board $boardIndex ${board.numbers}")
            lastItem = boards.filter { !it.hasWonAlready }.size == 1
            if (board.checkNumber(number)) {
                if (lastItem) println("  Answer: ${board.answer()}")
            }
        }
    }
}

fun parseCalls(input: List<String>): List<Int> {
    return input[0].split(",").map { Integer.parseInt(it) }
}

fun parseBoards(input: List<String>): MutableList<Board> {
    val boards = mutableListOf<Board>()
    var currentBoard = mutableListOf<List<Int>>()

    input.forEach { line ->
        if (line.isEmpty()) {
            val board = Board(currentBoard)
            boards.add(board)
            currentBoard = mutableListOf()
        } else {
            val sanitizedLine = line
                .replace(Regex("""^ """), "")
                .replace(Regex(""" +"""), ",")

            currentBoard.add(
                sanitizedLine
                    .split(",")
                    .map { Integer.parseInt(it) }
                    .toList()
            )
        }
    }

    val board = Board(currentBoard)
    boards.add(board)

    return boards
}

data class Board(val numbers: List<List<Int>>) {
    private val calledNumbers = mutableListOf<Int>()
    private var lastNumberCalled: Int = -1

    var hasWonAlready = false

    fun checkNumber(newNumber: Int): Boolean {
        lastNumberCalled = newNumber
        calledNumbers.add(newNumber)

        // Check lines
        numbers.forEach { line ->
            if (calledNumbers.containsAll(line)) {
                hasWonAlready = true
                return true
            }
        }

        // Check columns
        (0 until numbers[0].size).forEach { columnIndex ->
            var winner = true
            numbers.forEach { line ->
                if (!calledNumbers.contains(line[columnIndex])) {
                    winner = false
                }
            }

            if (winner) {
                hasWonAlready = true
                return true
            }
        }

        return false
    }

    fun answer(): Int {
        println("  Called Numbers: $calledNumbers")
        val remaining = numbers.flatten().filter { !calledNumbers.contains(it) }.sum()
        println("  Remaining Numbers: $remaining")
        println("  Last number called: $lastNumberCalled")
        return remaining * lastNumberCalled
    }
}