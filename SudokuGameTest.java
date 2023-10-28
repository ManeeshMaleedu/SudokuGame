package sudokugamer;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import sudokugamer.SudokuBoard;
import sudokugamer.SudokuDatabase;
import sudokugamer.SudokuGUI;

public class SudokuGameTest {
    private SudokuBoard board;
    private SudokuDatabase database;
    private SudokuGUI gui;

    @Before
    public void setUp() {
        // Initialize necessary objects for testing
        board = new SudokuBoard();
        database = new SudokuDatabase("test_database.db");
        gui = new SudokuGUI(board, database);
    }

    @Test
    public void testNewGameGeneration() {
        // Test if a new game is generated successfully
        gui.newGame();
        int[][] gameState = board.getState();
        assertNotNull(gameState);
    }

    @Test
    public void testValidMove() {
        // Test making a valid move on the Sudoku board
        int row = 0;
        int col = 0;
        int value = 1;
        boolean validMove = gui.isValidInput(row, col, value);
        assertTrue(validMove);
    }

    @Test
    public void testInvalidMove() {
        // Test making an invalid move on the Sudoku board
        int row = 0;
        int col = 0;
        int value = 5;
        boolean validMove = gui.isValidInput(row, col, value);
        assertFalse(validMove);
    }

    @Test
    public void testSudokuSolver() {
        // Test if the Sudoku solver can solve a valid puzzle
        int[][] puzzle = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };
        board.setState(puzzle);
        gui.solveSudoku();
        assertTrue(board.isSolved());
    }

    @Test
    public void testSaveAndLoadGame() {
        // Test saving and loading a game
        String saveName = "testSave";
        gui.saveGame(saveName);
        String loadedGame = database.loadGame(saveName);
        assertNotNull(loadedGame);
    }

    @Test
    public void testCheckSudoku() {
        // Test checking if the Sudoku board is solvable
        int[][] puzzle = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };
        board.setState(puzzle);
        boolean isSolvable = gui.isSolvable();
        assertTrue(isSolvable);
    }
}
