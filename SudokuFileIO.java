/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sudokugamer;

/**
 *
 * @author malee
 */
import java.io.*;
import java.util.Scanner;

public class SudokuFileIO {
    public static void saveSudokuBoard(SudokuBoard board, String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    writer.print(board.getCell(row, col));
                }
                writer.println();
            }
        } catch (IOException e) {
            System.err.println("Error saving Sudoku board to file: " + e.getMessage());
        }
    }

    public static SudokuBoard loadSudokuBoard(String fileName) {
        SudokuBoard board = new SudokuBoard();
        try (Scanner scanner = new Scanner(new File(fileName))) {
            for (int row = 0; row < 9; row++) {
                if (!scanner.hasNextLine()) {
                    throw new IOException("Invalid Sudoku file format: Not enough rows.");
                }
                String line = scanner.nextLine();
                if (line.length() != 9) {
                    throw new IOException("Invalid Sudoku file format: Row has incorrect length.");
                }
                for (int col = 0; col < 9; col++) {
                    char cellChar = line.charAt(col);
                    if (cellChar >= '1' && cellChar <= '9') {
                        board.setCell(row, col, cellChar - '0');
                    } else if (cellChar != '0') {
                        throw new IOException("Invalid Sudoku file format: Invalid character in the cell.");
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error loading Sudoku board from file: " + e.getMessage());
        }
        return board;
    }
}
