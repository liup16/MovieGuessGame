/*
 * Author : Patrick Aung
 * Date   : August 21, 2018
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        File moveFile = new File ("movies.txt");
        Game movieGame = new Game(moveFile);
        movieGame.playGame();
        System.out.print("\nPlay again? (y/n)  ");
        Scanner scanner = new Scanner(System.in);
        String restartInput = scanner.nextLine();
        while (!restartInput.matches("[yY]") && !restartInput.matches("[nN]")) {
            System.out.println("Invalid input!");
            System.out.print("\nPlay again? (y/n)  ");
            scanner = new Scanner(System.in);
            restartInput = scanner.nextLine();
        }
        if (restartInput.matches("[yY]")){
            main(null);
        } else {
            System.out.println("\nThanks for playing GuessTheMovie! See you next time!");
        }
    }
}