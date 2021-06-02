enum class Player(val player: String) {
    X("X"),
    O("O"),
    NA(" ");

    open fun getOtherPlayer(): Player {
        return if (this == X) {
            O
        } else {
            X
        }
    }
}
