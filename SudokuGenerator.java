/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sudokugamer;

/**
 *
 * @author malee
 */
import java.util.Random;

public class SudokuGenerator {
    private static final int SIZE = 9;
    private static final Random random = new Random();

    public static void main(String[] args) {
        int[][] puzzle = generateSudoku();
        printSudoku(puzzle);
    }

    public static int[][] generateSudoku() {
        int[][] grid = new int[SIZE][SIZE];
        fillDiagonalSubgrids(grid);
        if (solveSudoku(grid)) {
            removeNumbers(grid, 40); // Adjust the number of removed cells as needed
            return grid;
        } else {
            // Retry if a valid puzzle couldn't be generated
            return generateSudoku();
        }
    }

    public static void fillDiagonalSubgrids(int[][] grid) {
        for (int i = 0; i < SIZE; i += 3) {
            fillSubgrid(grid, i, i);
        }
    }

    public static void fillSubgrid(int[][] grid, int row, int col) {
        int[] values = randomPermutation();
        int index = 0;
        for (int i = row; i < row + 3; i++) {
            for (int j = col; j < col + 3; j++) {
                grid[i][j] = values[index++];
            }
        }
    }

    public static int[] randomPermutation() {
        int[] permutation = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int i = 8; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = permutation[i];
            permutation[i] = permutation[j];
            permutation[j] = temp;
        }
        return permutation;
    }

    public static boolean solveSudoku(int[][] grid) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (grid[row][col] == 0) {
                    for (int num = 1; num <= SIZE; num++) {
                        if (isValidMove(grid, row, col, num)) {
                            grid[row][col] = num;
                            if (solveSudoku(grid)) {
                                return true; // Continue solving
                            }
                            grid[row][col] = 0; // Backtrack if no solution found
                        }
                    }
                    return false; // No valid number can be placed
                }
            }
        }
        return true; // The puzzle is solved
    }

    public static boolean isValidMove(int[][] grid, int row, int col, int num) {
        return !usedInRow(grid, row, num) && !usedInColumn(grid, col, num) && !usedInSubgrid(grid, row, col, num);
    }

    public static boolean usedInRow(int[][] grid, int row, int num) {
        for (int col = 0; col < SIZE; col++) {
            if (grid[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    public static boolean usedInColumn(int[][] grid, int col, int num) {
        for (int row = 0; row < SIZE; row++) {
            if (grid[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    public static boolean usedInSubgrid(int[][] grid, int row, int col, int num) {
        int subgridRow = row - row % 3;
        int subgridCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[subgridRow + i][subgridCol + j] == num) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void removeNumbers(int[][] grid, int count) {
        for (int i = 0; i < count; i++) {
            int row, col;
            do {
                row = random.nextInt(SIZE);
                col = random.nextInt(SIZE);
            } while (grid[row][col] == 0); // Ensure the cell is not empty
            int temp = grid[row][col];
            grid[row][col] = 0; // Remove the number temporarily

            // Check if the puzzle still has a unique solution
            if (!hasUniqueSolution(grid)) {
                grid[row][col] = temp; // Restore the number if uniqueness is violated
                i--; // Retry removing another number
            }
        }
    }

    public static boolean hasUniqueSolution(int[][] grid) {
        int[][] copy = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(grid[i], 0, copy[i], 0, SIZE);
        }
        return solveSudoku(copy);
    }

    public static void printSudoku(int[][] grid) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                System.out.print(grid[row][col] + " ");
            }
            System.out.println();
        }
    }
}

