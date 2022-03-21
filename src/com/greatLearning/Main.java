package com.greatLearning;
 import com.greatLearning.games.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of players participating in the game");
        int numberOfPlayer = scanner.nextInt();
        System.out.println("Enter the minimum score playes needs to complete the game");
        int minimumScoreToCompleteTheGame = scanner.nextInt();
        DiceGame onGoingDiceGame = new DiceGame(numberOfPlayer, minimumScoreToCompleteTheGame);
        onGoingDiceGame.initialize();
        onGoingDiceGame.start();
        onGoingDiceGame.end();
    }
}
