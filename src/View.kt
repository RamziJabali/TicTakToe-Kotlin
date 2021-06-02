open class View(var listener: ViewListener) {

    open fun setNewViewState(newViewState: ViewState){
        if(newViewState.displayOutput){
            print(newViewState.input)
        }
        if(newViewState.askForInput){
            listener.enteredInput(readLine())
        }
    }
}