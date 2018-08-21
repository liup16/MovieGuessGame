/*
 * Author : Patrick Aung
 * Date   : August 21, 2018
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

public class Game {
    private String movieName;
    private String solvedName = "";
    private String correctLetters = "";
    private String wrongLetters = "";
    private boolean hasWon = false;
    private boolean isGameOver = false;

    public Game(File filename) throws FileNotFoundException {
        try{
            Scanner scanner = new Scanner(filename);
            ArrayList<String> movieArray = new ArrayList<String>();
            int numOfMovies = 0;
            Random randomNumber = new Random();
            while (scanner.hasNextLine()){
                String nextMovie = scanner.nextLine();
                movieArray.add(nextMovie);
                numOfMovies ++;
            }
            int randomIndex = randomNumber.nextInt(numOfMovies+1);
            this.movieName = movieArray.get(randomIndex);
            this.solvedName = this.movieName.replaceAll("[a-zA-Z0-9!@'$^*]", "-");

        } catch (FileNotFoundException exception){
            System.out.println("Invalid file or file name.");
        }
    }

    private void printGameInstructions(){
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("                           ~ Movie Guessing Game ~                           ");
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("  Here's a randomly selected movie title. If a letter you guessed is in the");
        System.out.println("  movie title, the correct position of the letter(s) will be revealed. If");
        System.out.println("  your guess is incorrect, you you lose a point. If you lose more 10 points,");
        System.out.println("  Game Over! Partially correct/incorrect guesses are not accepted. Guesses");
        System.out.println("  already made are not accepted. You do not lost points in both case.");
        System.out.println("-----------------------------------------------------------------------------");
    }

    private String inputGuess(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().toLowerCase();
    }

    private void updateStats(){
        System.out.println("\nYou have (" + (this.wrongLetters.length())/2 + ") wrong letters: "
                + this.wrongLetters.replaceAll("[A-Z]", " "));
        System.out.println("You are guessing: " + this.solvedName);
        System.out.print("Guess a letter: ");
    }

    private void updateGame(){
        while (this.wrongLetters.length()/2 < 10 && !this.solvedName.equals(this.movieName)){
            updateStats();
            String inputGuess = inputGuess();
            if (this.correctLetters.contains(inputGuess) || this.wrongLetters.contains(inputGuess)) {
                System.out.println("\nResult: You have already made this guess. Try again.");
                updateGame();
            }
            else if (!inputGuess.matches("[a-z]")) {
                System.out.println("\nResult: Input only one letter. Try again.");
                updateGame();
            }
            else if (inputGuess.matches("[a-z]") && this.movieName.contains(inputGuess) ){
                System.out.println("\nResult: Your guess is correct.");
                this.correctLetters += inputGuess + inputGuess.toUpperCase();
                this.solvedName = this.movieName.replaceAll("[a-zA-Z&&[^" + this.correctLetters +"]]", "_");
                updateGame();
            }
            else{
                System.out.println("\nResult: Your guess is incorrect. Try again.");
                this.wrongLetters += inputGuess + inputGuess.toUpperCase();
                updateGame();
            }
        }
        if (this.wrongLetters.length() >= 10){ this.isGameOver = true; }
        else { this.hasWon = true; }
    }

    public void playGame(){
        printGameInstructions();
        while (!this.hasWon && !this.isGameOver){
//            System.out.println("\n" + this.movieName);
            updateGame();
        }
        if (this.hasWon) {
            System.out.println("You Won! The correct answer is " + this.movieName + ".");

        } else {
            System.out.println("Game Over. Better Luck Next Time!");
            System.out.println("The correct answer is " + this.movieName + ".");
        }
    }
}