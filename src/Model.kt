open class Model() {
    companion object {
        const val WELCOME_TO_TICK = "Welcome to Tic Tac Toe\n"
        const val YOU_ARE_PLAYER_X = "You will be player X\n"
        const val YOU_ARE_PLAYER_Y = "You will be player Y\n"
        const val ENTER_ROW = "Enter row number\n"
        const val ENTER_COLUMN = "Enter column number\n"
        const val YOU_WIN = "YOU WIN: "
        const val THREE = 3
        const val TWO = 2
        const val ONE = 1
        const val ZERO = 0
        var gameStart: Boolean = false
        var enterRow: Boolean = false
        var enterColumn: Boolean = false
        var alreadyUsed: Boolean = false
        var didCurrentUserWin: Boolean = false
        var willUserWin: Boolean = false

        var player: Player = Player.X
        var playerGrid = arrayOf(
            arrayOf(Player.NA, Player.NA, Player.NA),
            arrayOf(Player.NA, Player.NA, Player.NA),
            arrayOf(Player.NA, Player.NA, Player.NA)
        )
    }
}