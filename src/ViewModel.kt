import Player.O
import Player.X

class ViewModel : ViewListener {

    private lateinit var viewState: ViewState
    private lateinit var view: View
    private lateinit var model: Model

    override fun onUserInput(input: String?) {
        if (model.hasUserEnteredRow) {
            model.userRowEntry = checkUserInput(input)
            if (isRowColumnWithinBoard(model.userRowEntry)) {
                model.hasUserEnteredRow = false
                model.hasUserEnteredColumn = true
                invalidateView()
                return
            }
            model.hasUserEnteredRow = true
            model.hasUserEnteredColumn = false
            invalidateView()
            return
        }
        if (model.hasUserEnteredColumn) {
            model.userColumnEntry = checkUserInput(input)
            if (isRowColumnWithinBoard(model.userColumnEntry)) {
                if (isUserCoordinateValid(Pair(model.userRowEntry, model.userColumnEntry))) {
                    markCurrentPlayerMoveOnBoard(Pair(model.userRowEntry, model.userColumnEntry))
                    postTurnProcessing()
                }
            }
            model.hasUserEnteredRow = false
            model.hasUserEnteredColumn = true
            invalidateView()
        }
    }

    fun startGame() {
        viewState = ViewState()
        view = View(this)
        model = Model().apply {
            aiPlayerPick = if ((0..1).random() == 0) X else O
            humanPlayerPick = getOtherPlayer(aiPlayerPick)
            hasUserEnteredRow = aiPlayerPick != X
        }
        viewState.isDisplayingOutput = true
        viewState.doesRequireUserInput = true
        showWelcomeMessage()
        postTurnProcessing()
    }

    private fun postTurnProcessing() {
        showBoard()
        if (model.hasPlayerWon(model.currentPlayer)) {
            showGameOver()
            return
        }
        if (model.isBoardFull()) {
            showDrawGame()
            return
        }
        if (model.isHumanTurn()) {
            model.currentPlayer = model.aiPlayerPick
            playComputerTurn()
            postTurnProcessing()
            return
        }
        model.currentPlayer = model.humanPlayerPick
        viewState.isDisplayingOutput = true
        viewState.doesRequireUserInput = true
        invalidateView()
    }

    private fun showBoard() {
        viewState.doesRequireUserInput = false
        viewState.isDisplayingOutput = true
        viewState.textToOutput = model.boardToString()
        invalidateView()
    }

    private fun playComputerTurn() {
        var coordinateCandidate: Pair<Int, Int>
        do {
            coordinateCandidate = Pair((0..2).random(), (0..2).random())
        } while (!isUserCoordinateValid(coordinateCandidate))
        markCurrentPlayerMoveOnBoard(coordinateCandidate)
    }

    private fun markCurrentPlayerMoveOnBoard(coordinate: Pair<Int, Int>) {
        model.gameBoard[coordinate.first][coordinate.second] = model.currentPlayer
    }

    private fun isUserCoordinateValid(coordinate: Pair<Int, Int>): Boolean =
        model.gameBoard[coordinate.first][coordinate.second] == Player.NA

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
        viewState.doesRequireUserInput = false
        viewState.isDisplayingOutput = true
        viewState.textToOutput += Model.ITS_A_DRAW
        invalidateView()
    }

    private fun showWelcomeMessage() {
        viewState.textToOutput = Model.WELCOME_TO_TICK + "\n" + Model.YOU_ARE_PLAYER + "${model.humanPlayerPick} \n" + Model.ENTER_ROW
    }

    private fun showGameOver() {
        viewState.doesRequireUserInput = false
        viewState.isDisplayingOutput = true
        viewState.textToOutput = "Player: ${model.currentPlayer} has won!"
        invalidateView()
    }

    private fun invalidateView() {
        view.setNewViewState(viewState)
    }
}