import Player.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ModelTest {

    @Test
    fun testIsBoardFull() {
        val model = Model()
        for (row in model.gameBoard.indices) {
            for (column in model.gameBoard.indices) {
                model.gameBoard[row][column] = X
            }
        }
        assertTrue(model.isBoardFull())
        assertTrue(model.isBoardFull())
    }

    @Test
    fun testHasPlayerWonVertically() {
        val model = Model().apply {
            gameBoard[0][0] = X
            gameBoard[1][0] = X
            gameBoard[2][0] = X
        }
        assertEquals(true, model.hasPlayerWon())
    }

    @Test
    fun testHasPlayerWonHorizontally() {
        val model = Model().apply {
            gameBoard[0][0] = X
            gameBoard[0][1] = X
            gameBoard[0][2] = X
        }
        assertEquals(true, model.hasPlayerWon())
    }

    @Test
    fun testHasPlayerWonDiagonally() {
        val model = Model().apply {
            gameBoard[0][0] = X
            gameBoard[1][1] = X
            gameBoard[2][2] = X
        }
        assertEquals(true, model.hasPlayerWon())
    }
    @Test
    fun testHasPlayerWonDiagonally2() {
        val model = Model().apply {
            gameBoard[0][2] = X
            gameBoard[1][1] = X
            gameBoard[2][0] = O
        }
        assertEquals(true, model.hasPlayerWon())
    }

}