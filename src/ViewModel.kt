import Player.O
import Player.X

class ViewModel : ViewListener {

    private lateinit var viewState: ViewState
    private lateinit var view: View
    private lateinit var model: Model

    override fun onUserInput(input: String?) {
        if (!model.hasUserEnteredRow) {
            model.userRowEntry = checkUserInput(input)
            if (isRowColumnWithinBoard(model.userRowEntry)) {
                model.hasUserEnteredRow = true
                model.hasUserEnteredColumn = false
                showRowOrColumn()
                dontDisplayOutPutButAskForUserInput()
                invalidateView()
                return
            }
            model.hasUserEnteredRow = false
            model.hasUserEnteredColumn = true
            showRowOrColumn()
            dontDisplayOutPutButAskForUserInput()
            invalidateView()
            return
        }

        if (!model.hasUserEnteredColumn) {
            model.userColumnEntry = checkUserInput(input)
            if (isRowColumnWithinBoard(model.userColumnEntry)) {
                if (isUserCoordinateValid(Pair(model.userRowEntry, model.userColumnEntry))) {
                    markCurrentPlayerMoveOnBoard(Pair(model.userRowEntry, model.userColumnEntry))
                    postTurnProcessing()
                    return
                }
                model.hasUserEnteredRow = true
                model.hasUserEnteredColumn = false
                showRowOrColumn()
                dontDisplayOutPutButAskForUserInput()
                invalidateView()
                return
            }
            model.hasUserEnteredRow = false
            model.hasUserEnteredColumn = true
            showRowOrColumn()
            dontDisplayOutPutButAskForUserInput()
            invalidateView()
        }
    }

    fun startGame() {
        viewState = ViewState()
        view = View(this)
        model = Model()
        with(model) {
            aiPlayerPick = if ((0..1).random() == 0) X else O
            humanPlayerPick = getOtherPlayer(aiPlayerPick)
            hasUserEnteredRow = aiPlayerPick == X
            showWelcomeMessage()
            showBoard()
            if (currentPlayer == aiPlayerPick) {
                playComputerTurn()
                postTurnProcessing()
            } else {
                hasUserEnteredRow = false
                hasUserEnteredColumn = true
                showRowOrColumn()
                dontDisplayOutPutButAskForUserInput()
                invalidateView()
            }
        }
    }

    private fun postTurnProcessing() {
        viewState.textToOutput = ""
        with(model) {
            showBoard()
            if (hasPlayerWon(currentPlayer)) {
                showGameOver()
                return
            }
            if (isBoardFull()) {
                showDrawGame()
                return
            }
            if (isHumanTurn()) {
                currentPlayer = getOtherPlayer(currentPlayer)
                playComputerTurn()
                postTurnProcessing()
                return
            }
            currentPlayer = getOtherPlayer(currentPlayer)
            hasUserEnteredRow = false
            hasUserEnteredColumn = true
            showHumanTurnMessage()
            showRowOrColumn()
            dontDisplayOutPutButAskForUserInput()
            invalidateView()
        }
    }

    private fun showBoard() {
        displayOutPutButDontAskForUserInput()
        viewState.textToOutput = model.boardToString()
        invalidateView()
    }

    private fun playComputerTurn() {
        showAITurnMessage()
        var coordinateCandidate: Pair<Int, Int>
        do coordinateCandidate = Pair((0..2).random(), (0..2).random())
        while (!isUserCoordinateValid(coordinateCandidate))
        markCurrentPlayerMoveOnBoard(coordinateCandidate)
    }

    private fun markCurrentPlayerMoveOnBoard(coordinate: Pair<Int, Int>) {
        model.gameBoard[coordinate.first][coordinate.second] = model.currentPlayer
    }

    private fun isUserCoordinateValid(coordinate: Pair<Int, Int>): Boolean =
        model.gameBoard[coordinate.first][coordinate.second] == Player.NA

    private fun displayOutPutAndAskForUserInput() {
        viewState.doesRequireUserInput = true
        viewState.isDisplayingOutput = true
    }

    private fun showAITurnMessage() {
        viewState.textToOutput = Model.AI_TURN + "\n"
        displayOutPutButDontAskForUserInput()
        invalidateView()
    }

    private fun showHumanTurnMessage() {
        viewState.textToOutput = Model.HUMAN_TURN + "\n"
        displayOutPutButDontAskForUserInput()
        invalidateView()
    }

    private fun displayOutPutButDontAskForUserInput() {
        viewState.doesRequireUserInput = false
        viewState.isDisplayingOutput = true
    }

    private fun dontDisplayOutPutButAskForUserInput() {
        viewState.doesRequireUserInput = true
        viewState.isDisplayingOutput = false
    }

    private fun getOtherPlayer(player: Player): Player = if (player == X) O else X

    private fun isRowColumnWithinBoard(input: Int) = input > -1 && input < 3

    private fun checkUserInput(input: String?): Int =
        if (input == null) -1
        else try {
            input.toInt()
        } catch (e: NumberFormatException) {
            -1
        }

    private fun showDrawGame() {
        viewState.textToOutput = Model.ITS_A_DRAW
        displayOutPutButDontAskForUserInput()
        invalidateView()
    }

    private fun showWelcomeMessage() {
        viewState.textToOutput =
            Model.WELCOME_TO_TICK + "\n" + Model.YOU_ARE_PLAYER + "${model.humanPlayerPick} \n"
        displayOutPutButDontAskForUserInput()
        invalidateView()
    }

    private fun showGameOver() {
        displayOutPutButDontAskForUserInput()
        viewState.textToOutput = "Player: ${model.currentPlayer} has won!"
        invalidateView()
    }

    private fun showRowOrColumn() {
        displayOutPutButDontAskForUserInput()
        if (!model.hasUserEnteredRow) {
            viewState.textToOutput = Model.ENTER_ROW + "\n"
            invalidateView()
            return
        }
        viewState.textToOutput = Model.ENTER_COLUMN + "\n"
        invalidateView()
    }

    private fun invalidateView() {
        view.setNewViewState(viewState)
    }
}