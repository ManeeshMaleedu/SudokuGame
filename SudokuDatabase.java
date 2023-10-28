/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sudokugamer;

/**
 *
 * @author malee
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SudokuDatabase {
    private Connection connection;
    private ResultSet resultSet;
    private PreparedStatement statement;

    public SudokuDatabase(String databasePath) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + databasePath);
            createTableIfNotExists();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTableIfNotExists() {
        try {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS sudoku_game ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "board TEXT NOT NULL,"
                    + "is_solved INTEGER DEFAULT 0,"
                    + "save_name TEXT"
                    + ")";
            statement = connection.prepareStatement(createTableSQL);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveGame(String boardState, boolean isSolved, String saveName) {
        try {
            String insertSQL = "INSERT INTO sudoku_game (board, is_solved, save_name) VALUES (?, ?, ?)";
            statement = connection.prepareStatement(insertSQL);
            statement.setString(1, boardState);
            statement.setInt(2, isSolved ? 1 : 0);
            statement.setString(3, saveName);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String loadGame(String saveName) {
        try {
            String selectSQL = "SELECT board FROM sudoku_game WHERE save_name = ? AND is_solved = 0 LIMIT 1";
            statement = connection.prepareStatement(selectSQL);
            statement.setString(1, saveName);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String boardState = resultSet.getString("board");
                return boardState;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String[] getSavedGameNames() {
        try {
            String selectSQL = "SELECT DISTINCT save_name FROM sudoku_game WHERE is_solved = 0";
            statement = connection.prepareStatement(selectSQL);
            resultSet = statement.executeQuery();

            List<String> savedGameNames = new ArrayList<>();
            while (resultSet.next()) {
                String saveName = resultSet.getString("save_name");
                savedGameNames.add(saveName);
            }

            return savedGameNames.toArray(new String[0]);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void markGameAsSolved() {
        try {
            String updateSQL = "UPDATE sudoku_game SET is_solved = 1 WHERE is_solved = 0";
            statement = connection.prepareStatement(updateSQL);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
