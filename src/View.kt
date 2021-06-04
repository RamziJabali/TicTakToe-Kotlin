class View(
    private val listener: ViewListener
) {

    fun setNewViewState(newViewState: ViewState) {
        with(newViewState) {
            if (isDisplayingOutput) {
                print(textToOutput)
            }
            if (doesRequireUserInput) {
                listener.onUserInput(readLine())
            }
        }
    }
}