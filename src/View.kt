open class View(var listener: ViewListener) {

    open fun setNewViewState(newViewState: ViewState){
        if(newViewState.displayOutput){
            print(newViewState.output)
        }
        if(newViewState.askForInput){
            listener.enteredInput(readLine())
        }
    }
}