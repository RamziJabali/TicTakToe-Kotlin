import com.sun.org.apache.xpath.internal.operations.Bool

open class ViewModel : ViewListener {

    lateinit var viewState: ViewState
    lateinit var view: View

    fun startGame() {
        viewState = ViewState()
        view = View(this)
        Model.gameStart = true
        Model.enterRow = false
        Model.enterColumn = false
        Model.alreadyUsed = false
        Model.didCurrentUserWin = false
        Model.willUserWin = false
        viewState.displayOutput = false
        viewState.askForInput = false

        invalidateTheView()
    }

    private fun generateStateFromModel() {
        if (Model.gameStart) {
            Model.enterRow = true
            viewState.displayOutput = true
            viewState.askForInput = true
            viewState.input = Model.WELCOME_TO_TICK
            viewState.input += Model.YOU_ARE_PLAYER_X
            viewState.input += Model.ENTER_ROW
            Model.gameStart = false
            return
        }

        if (Model.enterRow) {
            viewState.input = Model.ENTER_ROW
            viewState.askForInput = true
            viewState.displayOutput = true
        }

        if (Model.enterColumn) {
            viewState.input += Model.ENTER_COLUMN
            viewState.askForInput = true
            viewState.displayOutput = true
        }

        if (Model.didCurrentUserWin) {
            viewState.input = Model.YOU_WIN
            viewState.askForInput = false
            viewState.displayOutput = true
        }
    }

    open fun invalidateTheView() {
        generateStateFromModel()
        view.setNewViewState(viewState)
    }

    override fun enteredInput(input: String?) {
        var row: Int
        var col: Int
        if (Model.enterRow) {
            row = checkUserInput(input)
            if (isUserWithinRange(row)) {
                Model.enterRow = false
                Model.enterColumn = true
                invalidateTheView()
                return
            }
            Model.enterRow = true
            Model.enterColumn = false
            invalidateTheView()
            return
        }
        if (Model.enterColumn) {
            col = checkUserInput(input)
            if (isUserWithinRange(col)) {
                Model.enterRow = true
                Model.enterColumn = false
                invalidateTheView()
                return
            }
            Model.enterRow = false
            Model.enterColumn = true
            invalidateTheView()
            return
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


