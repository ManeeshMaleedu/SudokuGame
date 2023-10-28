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
import java.sql.SQLException;

public class SQLite {
    public static void main(String[] args) {
        

        try {
            Class.forName("org.sqlite.JDBC");
            
           String dbPath = "jdbc:sqlite:C:/Users/malee/OneDrive - AUT University/Documents/NetBeansProjects/SudokuGamer/Database/Sudoku.db";

            
            Connection connection = DriverManager.getConnection(dbPath);

            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
