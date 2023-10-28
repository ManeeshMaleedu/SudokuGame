/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sudokugamer;

import java.util.Arrays;

/**
 *
 * @author malee
 */
public class SudokuBoard {
    private int[][] board;

    public SudokuBoard() {
        board = new int[9][9];
       
    }

    public void setCell(int row, int col, int value) {
        
        board[row][col] = value;
    }

    public int getCell(int row, int col) {
        // Get the value of a cell on the board.
        return board[row][col];
    }

    public boolean isSolved() {
    // Checking if every row, column, and 3x3 subgrid contains all numbers from 1 to 9.
    for (int num = 1; num <= 9; num++) {
        if (!isValidSet(board, num)) {
            return false;
        }
    }
    return true;
}
private boolean isValidSet(int[][] board, int num) {
    // Helper method to check if a set (row, column, or subgrid) contains all numbers from 1 to 9.
    boolean[] used = new boolean[9];
    
    // Checking rows and columns
    for (int i = 0; i < 9; i++) {
        Arrays.fill(used, false);
        for (int j = 0; j < 9; j++) {
            if (board[i][j] == num) {
                if (used[num - 1]) {
                    return false; // If a Number is repeated in a row
                }
                used[num - 1] = true;
            }
            if (board[j][i] == num) {
                if (used[num - 1]) {
                    return false; // If a Number is repeated in a column
                }
                used[num - 1] = true;
            }
        }
    }
   
   for (int rowOffset = 0; rowOffset < 9; rowOffset += 3) {
        for (int colOffset = 0; colOffset < 9; colOffset += 3) {
            Arrays.fill(used, false);
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    int row = rowOffset + i;
                    int col = colOffset + j;
                    if (board[row][col] == num) {
                        if (used[num - 1]) {
                            return false; // if a Number is repeated in a subgrid
                        }
                        used[num - 1] = true;
                    }
                }
            }
        }
    }
    
    return true;
}
       public boolean solve() {
        return solve(0, 0);
    }

    private boolean solve(int row, int col) {
        if (row == 9) {
            return true; // If all rows are filled; the puzzle is solved.
        }

       
        int nextRow = (col == 8) ? row + 1 : row;
        int nextCol = (col == 8) ? 0 : col + 1;

        // If the current cell is already filled, move to the next cell.
        if (board[row][col] != 0) {
            return solve(nextRow, nextCol);
        }

        for (int num = 1; num <= 9; num++) {
            if (isValidMove(row, col, num)) {
                //  placing 'num' in the current cell.
                board[row][col] = num;

                if (solve(nextRow, nextCol)) {
                    return true; // If the rest of the puzzle can be solved, return true.
                }

                // If the puzzle cannot be solved with 'num' in this cell, backtrack.
                board[row][col] = 0;
            }
        }

        return false; 
    }
    public void printBoard() {
    for (int row = 0; row < 9; row++) {
        if (row % 3 == 0 && row != 0) {
            System.out.println("-----------");
        }
        for (int col = 0; col < 9; col++) {
            if (col % 3 == 0 && col != 0) {
                System.out.print("|");
            }
            int value = board[row][col];
            if (value != 0) {
                System.out.print(" " + value + " ");
            } else {
                System.out.print(" . ");
            }
        }
        System.out.println();
    }
}
   boolean isValidMove(int row, int col, int num) {
        // Check if 'num' can be placed in the given cell.
        return isValidInRow(row, num) && isValidInColumn(col, num) && isValidInBox(row, col, num);
    }


    private boolean isValidInRow(int row, int num) {
        for (int col = 0; col < 9; col++) {
            if (board[row][col] == num) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidInColumn(int col, int num) {
        for (int row = 0; row < 9; row++) {
            if (board[row][col] == num) {
                return false;
            }
        }
        return true;
    }
    private boolean isValidInBox(int row, int col, int num) {
        int boxStartRow = row - (row % 3);
        int boxStartCol = col - (col % 3);

        for (int i = boxStartRow; i < boxStartRow + 3; i++) {
            for (int j = boxStartCol; j < boxStartCol + 3; j++) {
                if (board[i][j] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    public int[][] getState() {
    // Return a copy of the current state of the Sudoku board.
    int[][] copy = new int[9][9];
    for (int row = 0; row < 9; row++) {
        for (int col = 0; col < 9; col++) {
            copy[row][col] = board[row][col];
        }
    }
    return copy;
}

public void setState(int[][] newState) {
    // Set the state of the Sudoku board using the provided state.
    for (int row = 0; row < 9; row++) {
        for (int col = 0; col < 9; col++) {
            board[row][col] = newState[row][col];
        }
    }
}
}
