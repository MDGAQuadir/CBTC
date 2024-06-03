import javax.swing.*;
import java.util.Random;

class GuessTheNumberGame {

    public static void main(String[] args) {
        Random random = new Random();
        int numberToGuess = random.nextInt(100) + 1;
        boolean hasGuessed = false;
        int attempts = 0;
        int score = 0;

        JOptionPane.showMessageDialog(null, "Welcome to Guess a Number Game!\nI have selected a number between 1 and 100. Guess a Number");

        while (!hasGuessed) {
            String userInput = JOptionPane.showInputDialog(null, "Enter your guess:");
            if (userInput == null) {
                JOptionPane.showMessageDialog(null, "Game cancelled.");
                break;
            }

            try {
                int userGuess = Integer.parseInt(userInput);
                attempts++;

                if (userGuess == numberToGuess) {
                    score = 100 - attempts * 10;  // Simple scoring mechanism
                    JOptionPane.showMessageDialog(null, "Congratulations! You have guessed the number in " + attempts + " attempts.\nYour score is: " + score);
                    hasGuessed = true;
                } else if (userGuess < numberToGuess) {
                    JOptionPane.showMessageDialog(null, "The number is higher. Try again.");
                } else {
                    JOptionPane.showMessageDialog(null, "The number is lower. Try again.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number.");
            }
        }
    }
}
