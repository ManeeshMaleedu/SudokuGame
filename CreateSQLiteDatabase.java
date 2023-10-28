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
import java.sql.Statement;

public class CreateSQLiteDatabase {
    public static void main(String[] args) {
        
         // Register the SQLite JDBC driver
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        // JDBC URL for SQLite connection
 String jdbcUrl = "jdbc:sqlite:C:/Users/malee/OneDrive - AUT University/Documents/NetBeansProjects/SudokuGamer/Database/sqlite-tools-win32x64-202310241106/database.db";
 // Specify the path to your database file

        try {
            // Connect to the database or create it if it doesn't exist
            Connection connection = DriverManager.getConnection(jdbcUrl);

            // Create a Statement object for executing SQL commands
            Statement statement = connection.createStatement();

            // Create a table
            String createTableSQL = "CREATE TABLE IF NOT EXISTS your_table_name ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "name TEXT NOT NULL,"
                    + "age INTEGER"
                    + ")";

            // Execute the SQL command to create the table
            statement.execute(createTableSQL);

            // Close the statement and connection
            statement.close();
            connection.close();

            System.out.println("SQLite database and table created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
