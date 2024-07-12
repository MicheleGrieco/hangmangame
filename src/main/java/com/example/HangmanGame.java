package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HangmanGame extends JFrame implements ActionListener {
    
    private String[] words = {"hangman", "java", "swing", "programming", "openai"};
    private String wordToGuess;
    private int guessesLeft = 6;
    private StringBuilder hiddenWord;

    private JLabel hiddenWordLabel;
    private JLabel guessesLeftLabel;
    private JTextField guessTextField;
    private JButton guessButton;
    
    public HangmanGame() {
        setTitle("Hangman Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);

        hiddenWordLabel = new JLabel();
        guessesLeftLabel = new JLabel("Guesses Left: " + guessesLeft);
        guessTextField = new JTextField(10);
        guessButton = new JButton("Guess");

        guessButton.addActionListener(this);
        guessTextField.addActionListener(this);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());
        mainPanel.add(hiddenWordLabel);
        mainPanel.add(guessesLeftLabel); 
        mainPanel.add(guessTextField); 
        mainPanel.add(guessButton); 
        
        getContentPane().add(mainPanel);
        initializeGame();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    /**
     * Initializes the game by selecting a random word from the list of words
     * and creating a hidden word with the same length as the word to guess.
     * Also resets the number of guesses left to 6.
     */
    private void initializeGame() {
        wordToGuess = words[(int) (Math.random()*words.length)];
        hiddenWord = new StringBuilder();
        for(int i = 0; i < wordToGuess.length(); i++) {
            hiddenWord.append("_");
        }
        hiddenWordLabel.setText(hiddenWord.toString());
        guessesLeft = 6;
        guessesLeftLabel.setText("Guesses Left: " + guessesLeft);
    }

    /**
     * Updates the hidden word with the guessed character.
     * If the guessed character is not in the word to guess, the number of guesses left is decremented.
     * @param guess
     */
    private void updateHiddenWord(char guess) {
        boolean found = false;
        for(int i = 0; i < wordToGuess.length(); i++) {
            if(wordToGuess.charAt(i) == guess) {
                hiddenWord.setCharAt(i, guess);
                found = true;
            }
        }
        hiddenWordLabel.setText(hiddenWord.toString());

        if(!found) {
            guessesLeft--;
            guessesLeftLabel.setText("Guesses Left: " + guessesLeft);
            if(guessesLeft == 0) {
                endGame("You Lose! The word was: " + wordToGuess);
            } else if(hiddenWord.toString().equals(wordToGuess)) {
                endGame("Congratulations! You won!");
            }
        }
    }

    /**
     * Ends the game by displaying a message dialog with the given message.
     * Also disables the text field and the guess button.
     * After the dialog is closed, the game is reinitialized.
     * @param message
     */
    private void endGame(String message) {
        guessTextField.setEnabled(false);
        guessButton.setEnabled(false);
        JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
        initializeGame();
        guessTextField.setEnabled(true);
        guessButton.setEnabled(true);
        guessTextField.requestFocus();
    }

    @Override
    /**
     * Handles the action event when the guess button is clicked or the enter key is pressed in the text field.
     * If the text field is not empty, the first character is taken as the guess and the hidden word is updated.
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == guessButton || e.getSource() == guessTextField) {
            String guessText = guessTextField.getText();
            if(guessText.length() > 0) {
                char guess = guessText.charAt(0);
                updateHiddenWord(guess);
                guessTextField.setText("");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HangmanGame::new);
    }
}
