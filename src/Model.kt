open class Model() {
    companion object {
        const val WELCOME_TO_TICK = "Welcome to Tic Tac Toe\n"
        const val YOU_ARE_PLAYER = "You are player "
        const val ENTER_ROW = "Enter row number\n"
        const val ENTER_COLUMN = "Enter column number\n"
        const val YOU_WIN = "YOU WIN: "
        const val PLAYER = "PLAYER "
        const val ITS_A_DRAW = "IT'S A DRAW"
        const val BOARD_SIZE = 3
        var didGameStart: Boolean = false
        var isItTimeToEnterRow: Boolean = false
        var isItTimeToEnterColumn: Boolean = false
        var isBoardFull: Boolean = false
        var isItTheComputersTurn: Boolean = false
        var isItTheUsersTurn: Boolean = false
        var didCurrentUserWin: Boolean = false
    }

    var row: Int = -1
    var column: Int = -1
    var player: Player = Player.X
    var gameBoard = arrayOf(
        arrayOf(Player.NA, Player.NA, Player.NA),
        arrayOf(Player.NA, Player.NA, Player.NA),
        arrayOf(Player.NA, Player.NA, Player.NA)
    )
}