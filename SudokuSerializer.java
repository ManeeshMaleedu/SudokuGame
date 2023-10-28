/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sudokugamer;

/**
 *
 * @author malee
 */
public class SudokuSerializer {
    private static final String DELIMITER = ","; // Change this delimiter as needed

    public static String serialize(int[][] boardState) {
        StringBuilder serialized = new StringBuilder();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int cellValue = boardState[row][col];
                serialized.append(cellValue);
                if (col < 8) {
                    serialized.append(DELIMITER);
                }
            }
            if (row < 8) {
                serialized.append("\n"); // Use a newline character to separate rows
            }
        }
        return serialized.toString();
    }

    public static int[][] deserialize(String serialized) {
        String[] rows = serialized.split("\n");
        int[][] board = new int[9][9];
        for (int row = 0; row < 9; row++) {
            String[] cells = rows[row].split(DELIMITER);
            for (int col = 0; col < 9; col++) {
                board[row][col] = Integer.parseInt(cells[col]);
            }
        }
        return board;
    }
}

