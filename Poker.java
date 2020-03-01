import java.io.IOException;

import sheffield.*;

public class Poker extends Rummy {

    public static String[] deck = shuffle(generateDeck());
    public static int deckStart = 0;

    public static void main(String[] args) throws IOException {
        EasyReader keyboard = new EasyReader();

        // Allows players to play multiple rounds in one run.
        boolean playAgain = true;

        int startPlayer = 1;

        while (playAgain == true) {
            // Sets up variable for number of players.
            int numPlayers = 0;

            while (numPlayers < 3 || numPlayers > 10) {
                // Read in number of players.
                numPlayers = keyboard.readInt("How many players are you playing with? (Max 10, Min 3) > ");

                // Display error if input out of range.
                if (numPlayers < 3 || numPlayers > 10) {
                    System.out.println("Error - input out of range. Please enter a value between 10 & 3.");
                }
            }

            // Completes the cycle for who starts.
            if (startPlayer > numPlayers) {
                startPlayer = 1;
            }

            // Set up array of hands.
            String[][] hands = new String[numPlayers][2];

            // Set up array of money.
            int[] money = new int[numPlayers];

            // Loop through hands and deal each hand 2 cards.
            for (int i = 0; i < hands.length; i++) {
                hands[i] = fillHand(hands[i]);
                money[i] = 5000;
            }

            System.out.println(
                    "All hands dealt, let's play! Each player will start with £5000, the big blind is 100, and the small blind is 50.");

            // Counter for the amount of players still active in the current round.
            int playersIn = numPlayers;

            // Indicates which player's turn it is.
            int player = startPlayer;

            while (playersIn > 1) {
                // Displays the player
                if (player <= numPlayers) {
                    System.out.println("Player " + player + "'s turn:");
                    player += 1;
                }

                // If the player's turn goes out of range, reset back to the start player - completing the cycle.
                if (player > numPlayers) {
                    player = startPlayer;
                }

                

            }

            // Increase the position of the start player.
            startPlayer += 1;

        }

        keyboard.close();
    }

    public static String[] fillHand(String[] hand) {
        // Fill the hand.
        for (int i = 0; i < hand.length; i++) {
            hand[i] = deck[deckStart];
            deckStart += 1;
        }

        // Sort the hand for easier reading.
        hand = insertionSort(hand);

        // Return the hand.
        return hand;
    }
}