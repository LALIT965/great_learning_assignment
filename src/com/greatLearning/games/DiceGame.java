package com.greatLearning.games;

import com.greatLearning.model.Player;
import com.greatLearning.utils.DiceGameRankComparator;
import com.greatLearning.utils.DiceGameScoreComparator;

import java.util.*;

public class DiceGame {
    private int numberOfPlayer;
    private int minimumScoreRequiredToCompleteGame;
    private HashMap<Integer, Player> playerRecord;
    private int currentMaximumRank = 0;
    private ArrayList<Player> rankList;
    Scanner scanner = new Scanner(System.in);

    public DiceGame(int players, int minimumScore) {
        this.numberOfPlayer = players;
        this.minimumScoreRequiredToCompleteGame = minimumScore;
        this.playerRecord = new HashMap<>();
        this.rankList = new ArrayList<>();
    }

    public int getNumberOfPlayer() {
        return numberOfPlayer;
    }

    public int getMinimumScoreRequiredToCompleteGame() {
        return minimumScoreRequiredToCompleteGame;
    }

    public HashMap<Integer, Player> getPlayerRecord() {
        return playerRecord;
    }

    /*
     * Initialize the playerRecords table i.e create players object containing information of player
     * like name,rank..etc
     */
    public void initialize() {
        this.initializePlayerRecords();
    }

    /*
    * Below function start the dice game.
     */
    public void start() {
        this.startTheGame();
    }

    /*
     * Below functions declares the end of game.
     */
    public void end() {
        System.out.println("Game Finished");
    }

    /*
     * Creates Player Object corresponding to each player participating in the game
     */
    private void initializePlayerRecords() {
        for (int i = 1; i <= this.numberOfPlayer; i++) {
            Player player = new Player("Player-" + i);
            this.playerRecord.put(i, player);
            this.rankList.add(player);
        }
    }

    /*
     * Implementation of the Dice Game/Business logic
     */
    private void startTheGame() {
        Queue<Integer> queue = this.addPlayersInQueueToRollDice();

        while (!queue.isEmpty()) {
            int currentPlayerNumber = queue.poll();

            Player currentPlayer = this.playerRecord.get(currentPlayerNumber);

            if (currentPlayer.isSkipTurn()) {
                queue.add(currentPlayerNumber);
                currentPlayer.setSkipTurn(false);
                continue;
            }

            System.out.println(currentPlayer.getName() + " its your turn");

            rollTheDice();

            int scoreAchieved = (int) ((Math.random() * (6)) + 1);

            System.out.println("Rolling the Dice.....");
            System.out.println(currentPlayer.getName() + " got: " + scoreAchieved + " points");

            // Handle special case when user rolls six and one respectively.
            if (scoreAchieved == 6) {
                this.playerRollsSix(scoreAchieved, currentPlayer);
            } else if (scoreAchieved == 1) {
                this.playerRollsOne(scoreAchieved, currentPlayer);
            } else {
                currentPlayer.setTotalScore(currentPlayer.getTotalScore() + scoreAchieved);
                currentPlayer.setLastScore(scoreAchieved);
            }

            if (currentPlayer.getTotalScore() >= this.minimumScoreRequiredToCompleteGame) {
                this.currentMaximumRank++;
                currentPlayer.setRank(this.currentMaximumRank);
                System.out.println(currentPlayer.getName() + " , you have finished your game at rank: " + currentPlayer.getRank());
            } else {
                queue.add(currentPlayerNumber);
            }

            this.sortPlayersRankWise();
            this.displayPlayersScoreCard();
        }

    }

    /*
     * It handles the case when player rolls a one and updates the total score accordingly
     * @param scoreAchieved  Point achieved by rolling the dice.
     * @param currentPlayer  Current Player Object i.e the one whose turn is presently going on.
     */
    private void playerRollsOne(int scoreAchieved, Player currentPlayer) {
        if (currentPlayer.getLastScore() == 1) {
            System.out.println(currentPlayer.getName() + ", You are being penalised for rolling 1 two consective times. Your next turn will be skipped");
            currentPlayer.setSkipTurn(true);
            currentPlayer.setLastScore(scoreAchieved);
        } else {
            currentPlayer.setTotalScore(currentPlayer.getTotalScore() + scoreAchieved);
            currentPlayer.setLastScore(scoreAchieved);
        }
    }

    /*
     * It handles the case when player rolls a six and updates the total score accordingly.
     * @param scoreAchieved  Point achieved by rolling the dice.
     * @param currentPlayer  Current Player Object i.e the one whose turn is presently going on.
     */
    private void playerRollsSix(int scoreAchieved, Player currentPlayer) {
        while (scoreAchieved == 6) {
            currentPlayer.setTotalScore(currentPlayer.getTotalScore() + scoreAchieved);
            currentPlayer.setLastScore(scoreAchieved);

            if (currentPlayer.getTotalScore() >= this.minimumScoreRequiredToCompleteGame) {
                break;
            }

            System.out.println(currentPlayer.getName() + ", You scored a 6. You are getting another chance to roll again.");
            rollTheDice();
            scoreAchieved = (int) ((Math.random() * (6)) + 1);
        }
        if (scoreAchieved != 6) {
            System.out.println("Rolling the dice");
            System.out.println(currentPlayer.getName() + " got: " + scoreAchieved + "points");
            currentPlayer.setTotalScore(currentPlayer.getTotalScore() + scoreAchieved);
            currentPlayer.setLastScore(scoreAchieved);
        }
    }

    /*
     * It sorts the players on the basis of rank.
     */
    private void sortPlayersRankWise() {
        Collections.sort(this.rankList, new DiceGameScoreComparator());
        this.assignRankToPlayersBasedOnCurrentScore();
        Collections.sort(this.rankList, new DiceGameRankComparator());
    }

    /*
     * Implementation of assigning the ranks to players after each players turn.
     */
    private void assignRankToPlayersBasedOnCurrentScore() {
        int tempRank = this.currentMaximumRank;

        for (Player player : this.rankList) {

            if (player.getTotalScore() < this.minimumScoreRequiredToCompleteGame) {
                tempRank++;
                player.setRank(tempRank);
            }
        }
    }

    /*
     * It displays rank/name/score of the players in a table form.
     */

    private void displayPlayersScoreCard() {
        System.out.println("Rank  Name  Score");


        for (Player player : this.rankList) {
            System.out.println(player.getRank() + "  " + player.getName() + "  " + player.getTotalScore());
        }
    }

    /*
     * Simulation of rolling of dice for players
     */
    private void rollTheDice() {
        boolean flag = this.rollTheDiceHelper();

        while (!flag) {
            flag = rollTheDiceHelper();
        }
    }

    /*
     * Implementation /Helper of simulation of rolling a dice
     */
    private boolean rollTheDiceHelper() {
        System.out.println("Press 'r' to roll the dice");
        char input = this.scanner.next().charAt(0);

        if (input == 'r') {
            return true;
        }

        System.out.println("Wrong key pressed");
        return false;
    }

    /*
     * Algorithm to put players randomly in a queue to play.
     */

    private Queue<Integer> addPlayersInQueueToRollDice() {
        Queue<Integer> queue = new LinkedList<>();
        HashMap<Integer, Integer> randomPlayerRecord = new HashMap<>();
        int firstTurnPlayer = (int) ((Math.random() * (this.numberOfPlayer)) + 1);
        randomPlayerRecord.put(firstTurnPlayer, firstTurnPlayer);
        queue.add(firstTurnPlayer);

        while (randomPlayerRecord.size() < this.numberOfPlayer) {

            int randomPlayer = (int) ((Math.random() * (this.numberOfPlayer)) + 1);

            if (!randomPlayerRecord.containsKey(randomPlayer)) {
                randomPlayerRecord.put(randomPlayer, randomPlayer);
                queue.add(randomPlayer);
            }
        }

        return queue;
    }
}
