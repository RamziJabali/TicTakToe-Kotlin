open class View {
    private var listener: ViewListener? = null

    open fun view(listener: ViewListener) {
        this.listener = listener
    }

    open fun setNewViewState(newViewState: ViewState){
        if(newViewState.displayOutput){
            print(newViewState.input)
        }
        if(newViewState.askForInput){
            listener?.enteredInput(readLine())
        }
    }
}