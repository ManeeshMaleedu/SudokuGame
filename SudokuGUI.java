/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sudokugamer;

/**
 *
 * @author malee
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

public class SudokuGUI {
    private JFrame frame;
    private JPanel boardPanel;
    private JButton[][] sudokuCells;
    private SudokuBoard board;
    private SudokuDatabase database;
    private JComboBox<String> savedGamesDropdown;

    public SudokuGUI(SudokuBoard board, SudokuDatabase database) {
        this.board = board;
        this.database = database;
        frame = new JFrame("Sudoku Game");

        JPanel homePanel = createHomePage();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(homePanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    private void loadGame() {
        String saveName = null;
        String savedGame = database.loadGame(saveName);

        if (savedGame != null) {
            int[][] loadedBoard = SudokuSerializer.deserialize(savedGame);
            board.setState(loadedBoard);
            updateBoardUI();
            JOptionPane.showMessageDialog(frame, "Game loaded successfully!");
        } else {
            JOptionPane.showMessageDialog(frame, "No saved game found with the specified name.");
        }
    }

    private void initializeMainGameGUI() {
        frame.getContentPane().removeAll();
        boardPanel = new JPanel(new GridLayout(9, 9));
        sudokuCells = new JButton[9][9];

        board = new SudokuBoard();

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                sudokuCells[row][col] = new JButton();
                sudokuCells[row][col].setFont(new Font("Arial", Font.PLAIN, 24));
                sudokuCells[row][col].setFocusPainted(false);
                final int r = row;
                final int c = col;

                sudokuCells[row][col].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handleCellClick(r, c);
                    }
                });

                boardPanel.add(sudokuCells[row][col]);
            }
        }

        frame.getContentPane().add(boardPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();

        JButton checkButton = new JButton("Check");
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkSudoku();
            }
        });
        controlPanel.add(checkButton);

        JButton hintButton = new JButton("Hint");
        hintButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                provideHint();
            }
        });
        controlPanel.add(hintButton);

        JButton solveButton = new JButton("Solve");
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solveSudoku();
            }
        });
        controlPanel.add(solveButton);

        JButton saveButton = new JButton("Save Game");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String saveName = JOptionPane.showInputDialog("Enter a name for the save:");
                if (saveName != null && !saveName.isEmpty()) {
                    saveGame(saveName);
                }
            }
        });
        JButton returnToHomeButton = new JButton("Return to Home");
    returnToHomeButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Call a method to go back to the home page
            goToHomePage();
        }

           private void goToHomePage() {
    frame.getContentPane().removeAll();
    JPanel homePanel = createHomePage();
    frame.getContentPane().add(homePanel, BorderLayout.CENTER);
    frame.pack();
    frame.setVisible(true);
}
    });
    controlPanel.add(returnToHomeButton);
    
        controlPanel.add(saveButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitGame();
            }
        });
        controlPanel.add(exitButton);

        frame.getContentPane().add(controlPanel, BorderLayout.SOUTH);

        JPanel savedGamesPanel = new JPanel();
        savedGamesPanel.setLayout(new FlowLayout());

        savedGamesDropdown = new JComboBox<>();
        savedGamesDropdown.setPreferredSize(new Dimension(200, 30));
        populateSavedGamesDropdown();

        JButton loadSavedGameButton = new JButton("Load Saved Game");
        loadSavedGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedSave = (String) savedGamesDropdown.getSelectedItem();
                if (selectedSave != null && !selectedSave.isEmpty()) {
                    loadGame();
                }
            }
        });
        savedGamesPanel.add(savedGamesDropdown);
        savedGamesPanel.add(loadSavedGameButton);

        frame.getContentPane().add(savedGamesPanel, BorderLayout.NORTH);

        frame.pack();
        frame.setVisible(true);

        loadGame();
    }

    private void handleCellClick(int row, int col) {
        if (board.getCell(row, col) == 0) {
            String input = JOptionPane.showInputDialog("Enter a number (1-9) for this cell:");
            try {
                int value = Integer.parseInt(input);
                if (isValidInput(row, col, value)) {
                    board.setCell(row, col, value);
                    updateBoardUI();
                    saveGame();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid input! Please enter a valid number (1-9).");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid input! Please enter a valid number (1-9).");
            }
        }
    }

    private void updateBoardUI() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int cellValue = board.getCell(row, col);
                if (cellValue != 0) {
                    sudokuCells[row][col].setText(Integer.toString(cellValue));
                    sudokuCells[row][col].setEnabled(false);
                } else {
                    sudokuCells[row][col].setText("");
                    sudokuCells[row][col].setEnabled(true);
                }
            }
        }
    }

    private void solveSudoku() {
        if (board.solve()) {
            updateBoardUI();
            JOptionPane.showMessageDialog(frame, "Sudoku solved!");
        } else {
            JOptionPane.showMessageDialog(frame, "Sudoku cannot be solved.");
        }
    }

    private void newGame() {
        generateNewGame();
        updateBoardUI();
        JOptionPane.showMessageDialog(frame, "New game generated!");
        saveGame();
    }

    private void provideHint() {
        Random random = new Random();
        int row, col;

        do {
            row = random.nextInt(9);
            col = random.nextInt(9);
        } while (board.getCell(row, col) != 0);

        int hintValue = generateHintValue(row, col);

        board.setCell(row, col, hintValue);
        updateBoardUI();
        saveGame();
    }

    private void checkSudoku() {
        if (board.solve()) {
            JOptionPane.showMessageDialog(frame, "Sudoku can be solved.");
        } else {
            JOptionPane.showMessageDialog(frame, "Sudoku cannot be solved.");
        }
    }

    private void saveGame() {
        String serializedGame = SudokuSerializer.serialize(board.getState());
        String saveName = null;
        database.saveGame(serializedGame, board.isSolved(),saveName);
        JOptionPane.showMessageDialog(frame, "Game saved successfully!");
        populateSavedGamesDropdown();
    }

    private void saveGame(String saveName) {
        String serializedGame = SudokuSerializer.serialize(board.getState());
        database.saveGame(serializedGame, board.isSolved(), saveName);
        JOptionPane.showMessageDialog(frame, "Game saved successfully!");
        populateSavedGamesDropdown();
    }

private void exitGame() {
    System.exit(0);
}





    private void populateSavedGamesDropdown() {
        savedGamesDropdown.removeAllItems();
        String[] savedGameNames = database.getSavedGameNames();

        if (savedGameNames != null) {
            for (String gameName : savedGameNames) {
                savedGamesDropdown.addItem(gameName);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SudokuBoard initialBoard = new SudokuBoard();
                SudokuDatabase database = new SudokuDatabase("sudoku.db");
                new SudokuGUI(initialBoard, database);
            }
        });
    }

    private int generateHintValue(int row, int col) {
        Random random = new Random();
        int hintValue;

        do {
            hintValue = random.nextInt(9) + 1;
        } while (!isValidInput(row, col, hintValue));

        return hintValue;
    }

    private void generateNewGame() {
        int[][] puzzle = SudokuGenerator.generateSudoku();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int cellValue = puzzle[i][j];
                board.setCell(i, j, cellValue);
            }
        }

        updateBoardUI();
        saveGame();
    }

    private boolean isValidInput(int row, int col, int hintValue) {
        return hintValue >= 1 && hintValue <= 9 && board.isValidMove(row, col, hintValue);
    }

    private JPanel createHomePage() {
        JPanel homePanel = new JPanel();
        homePanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Sudoku Game");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        homePanel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton startGameButton = new JButton("Start Game");
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Added debugging statement
                System.out.println("Start Game button clicked");
                initializeMainGameGUI();
            }
        });
        buttonPanel.add(startGameButton);

        JButton loadGameButton = new JButton("Load Game");
        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Added debugging statement
                System.out.println("Load Game button clicked");
                String[] savedGameNames = database.getSavedGameNames();
                JFrame loadGameFrame = new JFrame("Load Game");
                loadGameFrame.setLayout(new BorderLayout());
                JList<String> gameList = new JList<>(savedGameNames);
                JScrollPane scrollPane = new JScrollPane(gameList);
                loadGameFrame.add(scrollPane, BorderLayout.CENTER);
                JButton loadButton = new JButton("Load");
                loadButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String selectedGameName = gameList.getSelectedValue();
                        if (selectedGameName != null) {
                            String savedGameData = database.loadGame(selectedGameName);
                            if (savedGameData != null) {
                                initializeMainGameGUIWithLoadedData(savedGameData);
                                loadGameFrame.dispose();
                            } else {
                                JOptionPane.showMessageDialog(loadGameFrame, "Failed to load the selected game.");
                            }
                        }
                    }

                    private void initializeMainGameGUIWithLoadedData(String savedGameData) {
                        // Implement this method to initialize the main game GUI with loaded data
                        // You can deserialize 'savedGameData' and set up the game accordingly
                    }
                });

                loadGameFrame.add(loadButton, BorderLayout.SOUTH);
                loadGameFrame.pack();
                loadGameFrame.setVisible(true);
            }
        });

        buttonPanel.add(loadGameButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        buttonPanel.add(exitButton);

        homePanel.add(buttonPanel, BorderLayout.CENTER);

        return homePanel;
    }
}
