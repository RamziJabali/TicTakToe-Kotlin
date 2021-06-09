import java.lang.Math.random

class Model {
    companion object {
        const val WELCOME_TO_TICK = "Welcome to Tic Tac Toe"
        const val YOU_ARE_PLAYER = "You are player "
        const val AI_TURN = "AI turn: "
        const val HUMAN_TURN = "Human turn: "
        const val ENTER_ROW = "Enter row number"
        const val ENTER_COLUMN = "Enter column number"
        const val YOU_WIN = "YOU WIN: "
        const val PLAYER = "PLAYER "
        const val ITS_A_DRAW = "IT'S A DRAW"
        const val BOARD_SIZE = 3
    }

    var userRowEntry: Int = -1
    var userColumnEntry: Int = -1
    var currentPlayer: Player = Player.X
    var aiPlayerPick: Player = Player.NA
    var humanPlayerPick: Player = Player.NA
    var gameBoard = arrayOf(
        arrayOf(Player.NA, Player.NA, Player.NA),
        arrayOf(Player.NA, Player.NA, Player.NA),
        arrayOf(Player.NA, Player.NA, Player.NA)
    )
    var hasUserEnteredRow: Boolean = false
    var hasUserEnteredColumn: Boolean = false

    fun isHumanTurn(): Boolean = currentPlayer == humanPlayerPick

    fun isBoardFull(): Boolean {
        for (row in gameBoard) {
            for (column in row) {
                if (column == Player.NA) {
                    return false
                }
            }
        }
        return true
    }

    fun boardToString(): String {
        var board = ""
        for (row in gameBoard) {
            for (column in row) {
                board += "[${column.display}]"
            }
            board += "\n"
        }
        return board
    }

    fun hasPlayerWon(currentPlayer: Player): Boolean =
        doesUserWinDiagonally() || doesUserWinHorizontally() || doesUserWinVertically()

    private fun doesUserWinHorizontally(): Boolean {
        var numberOfContiguousMarks = 0
        for (row in gameBoard) {
            for (column in row) {
                if (column == currentPlayer) numberOfContiguousMarks++ else numberOfContiguousMarks = 0
            }
            if (numberOfContiguousMarks == BOARD_SIZE) return true
        }
        return false
    }

    private fun doesUserWinVertically(): Boolean {
        var numberOfContiguousMarks = 0
        var row: Int
        var column = 0
        while (column in 0..(BOARD_SIZE - 1)) {
            row = 0
            while (row in 0..(BOARD_SIZE - 1)) {
                if (gameBoard[row][column] == currentPlayer) numberOfContiguousMarks++ else numberOfContiguousMarks =
                    0
                row++
            }
            if (numberOfContiguousMarks >= BOARD_SIZE) return true
            numberOfContiguousMarks = 0
            column++
        }
        return false
    }

    private fun doesUserWinDiagonally(): Boolean {
        var numberOfContiguousMarks = 0
        var row = 0
        var column = 0

        while (row in 0..(BOARD_SIZE - 1)) {
            if (gameBoard[row][column] == currentPlayer) numberOfContiguousMarks++ else numberOfContiguousMarks =
                0
            row++
            column++
        }
        if (numberOfContiguousMarks >= BOARD_SIZE) return true

        numberOfContiguousMarks = 0

        row = 0
        column = 2
        while (row in 0..(BOARD_SIZE - 1)) {
            if (gameBoard[row][column] == currentPlayer) numberOfContiguousMarks++ else numberOfContiguousMarks =
                0
            row++
            column--
        }
        if (numberOfContiguousMarks >= BOARD_SIZE) return true

        return false
    }

}