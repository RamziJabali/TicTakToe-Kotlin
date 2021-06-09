import Player.*
import org.junit.Assert
import org.junit.Test

class ModelTest {

    @Test
    fun testIsBoardFull() {
        val model = Model()
        for (row in model.gameBoard.indices) {
            for (column in model.gameBoard.indices) {
                model.gameBoard[row][column] = X
            }
        }
        Assert.assertEquals(true, model.isBoardFull())
    }

    fun testHasPlayerWonVertically() {
        val model = Model().apply {
            gameBoard[0][0] = X
            gameBoard[1][0] = X
            gameBoard[2][0] = X
        }
        Assert.assertEquals(true, model.hasPlayerWon(X))
    }
    fun testHasPlayerWonHorizontally() {
        val model = Model().apply {
            gameBoard[0][0] = X
            gameBoard[0][1] = X
            gameBoard[0][2] = X
        }
        Assert.assertEquals(true, model.isBoardFull())
    }
    fun testHasPlayerWonDiagonally() {
        val model = Model().apply {
            gameBoard[0][0] = X
            gameBoard[1][1] = X
            gameBoard[2][2] = X
        }
        Assert.assertEquals(true, model.isBoardFull())
    }

}