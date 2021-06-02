open class ViewModel : ViewListener {

    lateinit var viewState: ViewState
    lateinit var view: View

    fun startGame() {
        viewState = ViewState()
        view = View(this)
        Model.gameStart = true
        Model.enterColumn = false
        Model.enterRow = false
        viewState.displayOutput =false
        viewState.askForInput = false
        invalidateTheView()
    }

    override fun enteredInput(input: String?) {

    }

    fun generateStateFromModel() {
        if (Model.gameStart) {
            viewState.displayOutput = true
            viewState.askForInput = true
            viewState.input = Model.WELCOME_TO_TICK
            viewState.input += Model.YOU_ARE_PLAYER_X
            viewState.input += Model.ENTER_ROW
            Model.gameStart = false
        }
    }

    open fun invalidateTheView() {
        generateStateFromModel()
        view.setNewViewState(viewState)
    }

}

fun main() {
    var viewModel = ViewModel()
    for (row in Model.playerGrid) {
        for (column in row) {
            print("[${column.player}]")
        }
        println()
    }
    println(Model.playerGrid.size)
    println("Player ${Model.player}")
    Model.player = Model.player.getOtherPlayer()
    print("Player ${Model.player}")
}


