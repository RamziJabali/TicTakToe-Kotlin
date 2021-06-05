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

    fun isAiTurn(): Boolean = currentPlayer == aiPlayerPick
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

    fun didPlayerWin(player: Player): Boolean {
        return true
    }

    private fun doesUserWinHorizontallyAtRow(row: Int): Boolean {
        return false
    }

    private fun doesUserWinVertically(): Boolean {
        return false
    }

    private fun doesUserWinDiagonally(): Boolean {
        return false
    }

    fun hasPlayerWon(currentPlayer: Player): Boolean {
        return false
    }

}