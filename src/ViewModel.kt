import javax.jws.WebParam

open class ViewModel : ViewListener {

    private lateinit var viewState: ViewState
    private lateinit var view: View
    private lateinit var model: Model

    fun startGame() {
        viewState = ViewState()
        view = View(this)
        model = Model()
        Model.isItTheComputersTurn = (0..1).random() == 0
        Model.didGameStart = true
        Model.didCurrentUserWin = false
        Model.isItTimeToEnterRow = !Model.isItTheComputersTurn
        Model.isItTimeToEnterColumn = false
        Model.isBoardFull = false
        viewState.displayOutput = false
        viewState.askForInput = false
        invalidateTheView()
    }

    private fun generateStateFromModel() {
        if (Model.didGameStart) {
            viewState.output = Model.WELCOME_TO_TICK
            viewState.output += boardToString()
            if (Model.isItTheComputersTurn) {
                playComputerTurn()
                return
            }
            viewState.output += Model.YOU_ARE_PLAYER
            viewState.output += "${Player.X} \n"
            viewState.output += Model.ENTER_ROW
            viewState.displayOutput = true
            viewState.askForInput = true
            return
        }
        if (Model.isItTheComputersTurn) {
            playComputerTurn()
        }
        if (Model.isItTheUsersTurn) {
            if (Model.isItTimeToEnterRow) {
                viewState.output = boardToString()
                viewState.output += Model.PLAYER + model.player
                viewState.output += "\n"
                viewState.output += Model.ENTER_ROW
                viewState.askForInput = true
                viewState.displayOutput = true
            }

            if (Model.isItTimeToEnterColumn) {
                viewState.output = Model.ENTER_COLUMN
                viewState.askForInput = true
                viewState.displayOutput = true
            }
            return
        }

        if (Model.didCurrentUserWin) {
            viewState.output = Model.YOU_WIN
            viewState.askForInput = false
            viewState.displayOutput = true
            return
        }

        if (Model.isBoardFull) {
            viewState.output = boardToString()
            viewState.output += Model.ITS_A_DRAW
            viewState.displayOutput = true
            viewState.askForInput = false
            Model.isBoardFull = false
            return
        }
    }

    private fun playComputerTurn() {
        Model.didGameStart = false
        var row = (0..2).random()
        var column = (0..2).random()
        while (!isUserCoordinateValid(row, column)) {
            row = (0..2).random()
            column = (0..2).random()
        }
        markCurrentPlayerMoveOnBoard(row, column)
        if (isBoardFull()) {
            Model.isBoardFull = true
            Model.didCurrentUserWin = false
            Model.isItTheUsersTurn = false
            Model.isItTheComputersTurn = false
            Model.isItTimeToEnterRow = false
            Model.isItTimeToEnterColumn = false
            invalidateTheView()
            return
            //TODO: Check is there is a winner and update the winner status
        }
        model.player = getOtherPlayer(model.player)
        Model.isItTheUsersTurn = true
        Model.isItTheComputersTurn = false
        Model.isItTimeToEnterRow = true
        Model.isItTimeToEnterColumn = false
        invalidateTheView()
    }

    private fun isBoardFull(): Boolean {
        for (row in model.gameBoard) {
            for (column in row) {
                if (column == Player.NA) {
                    return false
                }
            }
        }
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

    open fun invalidateTheView() {
        generateStateFromModel()
        view.setNewViewState(viewState)
    }

    override fun enteredInput(input: String?) {
        Model.didGameStart = false
        if (Model.isItTimeToEnterRow) {
            model.row = checkUserInput(input)
            if (isUserWithinRange(model.row)) {
                Model.isItTheComputersTurn = false
                Model.isItTheUsersTurn = true
                Model.isItTimeToEnterRow = false
                Model.isItTimeToEnterColumn = true
                invalidateTheView()
                return
            }
            Model.isItTheComputersTurn = false
            Model.isItTheUsersTurn = true
            Model.isItTimeToEnterRow = true
            Model.isItTimeToEnterColumn = false
            invalidateTheView()
            return
        }
        if (Model.isItTimeToEnterColumn) {
            model.column = checkUserInput(input)
            if (isUserWithinRange(model.column)) {
                if (isUserCoordinateValid(model.row, model.column)) {
                    markCurrentPlayerMoveOnBoard(model.row, model.column)
                    model.player = getOtherPlayer(model.player)
                    if (!isBoardFull()) {
                        Model.isItTheComputersTurn = true
                        Model.isItTheUsersTurn = false
                        Model.isItTimeToEnterRow = false
                        Model.isItTimeToEnterColumn = false
                        Model.isBoardFull = false
                        invalidateTheView()
                    }else {
                        Model.isItTheComputersTurn = false
                        Model.isItTheUsersTurn = false
                        Model.isItTimeToEnterRow = false
                        Model.isItTimeToEnterColumn = false
                        Model.isBoardFull = true
                        invalidateTheView()
                    }
                    return
                }
                Model.isItTheComputersTurn = false
                Model.isItTheUsersTurn = true
                Model.isItTimeToEnterRow = true
                Model.isItTimeToEnterColumn = false
                invalidateTheView()
                return
            }
            Model.isItTheComputersTurn = false
            Model.isItTheUsersTurn = true
            Model.isItTimeToEnterRow = false
            Model.isItTimeToEnterColumn = true
            invalidateTheView()
        }
    }

    private fun boardToString(): String {
        var board = ""
        for (row in model.gameBoard) {
            for (column in row) {
                board += "[${column.player}]"
            }
            board += "\n"
        }
        return board
    }

    private fun markCurrentPlayerMoveOnBoard(row: Int, column: Int) {
        model.gameBoard[row][column] = model.player
    }

    private fun isUserCoordinateValid(row: Int, column: Int): Boolean {
        return model.gameBoard[row][column] == Player.NA

    }

    private fun getOtherPlayer(player: Player): Player {
        return if (player == Player.X) {
            Player.O
        } else {
            Player.X
        }
    }

    private fun isUserWithinRange(input: Int): Boolean {
        return input > -1 && input < 3
    }

    private fun checkUserInput(input: String?): Int {
        if (input == null) return -1
        return try {
            input.toInt()
        } catch (e: NumberFormatException) {
            -1
        }
    }
}

fun main() {
    val viewModel = ViewModel()
    viewModel.startGame()
}


