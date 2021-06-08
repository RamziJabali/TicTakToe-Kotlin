fun main() {
    val viewModel = ViewModel()
    //viewModel.startGame()
    var model: Model = Model()
    model.gameBoard[0][0] = Player.X
    model.gameBoard[1][0] = Player.O
    model.gameBoard[2][0] = Player.X

    model.gameBoard[0][1] = Player.O
    model.gameBoard[1][1] = Player.X
    model.gameBoard[2][1] = Player.O

    model.gameBoard[0][2] = Player.X
    model.gameBoard[1][2] = Player.O
    model.gameBoard[2][2] = Player.O
    model.currentPlayer = Player.X


    print(boardToString(model))
    print(model.hasPlayerWon(currentPlayer = Player.X))

    // X X X
    // X O X
    // O X x
    //
}
fun boardToString(model: Model): String {
    var board = ""
    for (row in model.gameBoard) {
        for (column in row) {
            board += "[${column.display}]"
        }
        board += "\n"
    }
    return board
}