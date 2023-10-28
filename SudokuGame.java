/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sudokugamer;

/**
 *
 * @author malee
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class SudokuGame {
    private SudokuBoard board;
    private Scanner scanner;

    public SudokuGame() {
        board = new SudokuBoard();
        scanner = new Scanner(System.in);
    }

   
    public void playGame() {
        System.out.println("Welcome to Sudoku Game!");
        boolean solved = false;
        while (!solved) {
            displayMenu();
            int choice = getUserChoice();
            switch (choice) {
                case 1:
                    setCellValue();
                    break;
                case 2:
                    solved = board.solve();
                    if (solved) {
                        board.printBoard();
                        System.out.println("Congratulations! Sudoku solved!");
                    } else {
                        System.out.println("Sudoku cannot be solved.");
                    }
                    break;
                case 3:
                    saveSudokuToFile();
                    break;
                case 4:
                    loadSudokuFromFile();
                    break;
                case 5:
                    System.out.println("Thanks for playing Sudoku!");
                    return; // Gracefully exit the game.
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void displayMenu() {
        System.out.println("Options:");
        System.out.println("1. Set cell value");
        System.out.println("2. Solve Sudoku");
        System.out.println("3. Save Sudoku to file");
        System.out.println("4. Load Sudoku from file");
        System.out.println("5. Exit");
    }

    private int getUserChoice() {
        System.out.print("Enter your choice: ");
        return scanner.nextInt();
    }


    private void saveSudokuToFile() {
        System.out.print("Enter the file name to save Sudoku: ");
        String fileName = scanner.next();
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    writer.print(board.getCell(row, col));
                }
                writer.println();
            }
            System.out.println("Sudoku saved to " + fileName);
        } catch (IOException e) {
            System.err.println("Error saving Sudoku to file: " + e.getMessage());
        }
    }


   private void loadSudokuFromFile() {
        System.out.print("Enter the file name to load Sudoku: ");
        String fileName = scanner.next();
        try (Scanner fileScanner = new Scanner(new File(fileName))) {
            SudokuBoard loadedBoard = new SudokuBoard();
            for (int row = 0; row < 9; row++) {
                if (!fileScanner.hasNextLine()) {
                    System.err.println("Invalid Sudoku file format: Not enough rows.");
                    return;
                }
                String line = fileScanner.nextLine();
                if (line.length() != 9) {
                    System.err.println("Invalid Sudoku file format: Row has incorrect length.");
                    return;
                }
                for (int col = 0; col < 9; col++) {
                    char cellChar = line.charAt(col);
                    if (cellChar >= '1' && cellChar <= '9') {
                        loadedBoard.setCell(row, col, cellChar - '0');
                    } else if (cellChar != '0') {
                        System.err.println("Invalid Sudoku file format: Invalid character in the cell.");
                        return;
                    }
                }
            }
            board = loadedBoard;
            System.out.println("Sudoku loaded from " + fileName);
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
    }

private void setCellValue() {
    System.out.print("Enter row (1-9), column (1-9), and value (1-9), separated by spaces: ");
    
    try {
        int row = scanner.nextInt() - 1; // Subtract 1 to convert to array index.
        int col = scanner.nextInt() - 1; // Subtract 1 to convert to array index.
        int value = scanner.nextInt();

        if (row >= 0 && row < 9 && col >= 0 && col < 9 && value >= 1 && value <= 9) {
            if (board.isValidMove(row, col, value)) {
                board.setCell(row, col, value);
                System.out.println("Value set successfully.");
                board.printBoard();
            } else {
                System.out.println("Invalid move. Value conflicts with existing numbers in row, column, or box.");
            }
        } else {
            System.out.println("Invalid input. Please enter valid row, column, and value.");
        }
    } catch (java.util.InputMismatchException e) {
        System.out.println("Invalid input. Please enter valid integers for row, column, and value.");
        scanner.nextLine(); // Clear the input buffer
    }
}
     private boolean isValidInput(int row, int col, int value) {
        return row >= 0 && row < 9 && col >= 0 && col < 9 && value >= 1 && value <= 9;
    }
    public static void main(String[] args) {
        SudokuGame game = new SudokuGame();
        game.playGame();
    }
}
