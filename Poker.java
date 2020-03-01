import java.io.IOException;

import sheffield.*;

public class Poker extends Rummy {

    public static String[] deck = shuffle(generateDeck());
    public static int deckStart = 0;

    public static void main(String[] args) throws IOException {
        EasyReader keyboard = new EasyReader();

        // Allows players to play multiple games in one run.
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

            // If there are insufficient cards to play a game with the desired number of players, shuffle the deck.
            if (!enoughCards(deck, numPlayers)) {
                System.out.println("Insufficient cards left in deck to play. Returning and shuffling all cards...");
                deck = shuffle(deck);
                deckStart = 0;
                System.out.println("New deck shuffled, dealing cards...");
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

            // Array containing the community cards.
            String[] comCards = new String[5];
            comCards = fillHand(comCards);

            System.out.println(
                    "All hands dealt, let's play! Each player will start with £5000, the big blind is 100, and the small blind is 50.");

            // Counter for the amount of players still active in the current round.
            int playersIn = numPlayers;

            // Indicates which player's turn it is.
            int player = startPlayer;

            int round = 0;

            while (playersIn > 1) {
                // Increase round by 1.
                round += 1;

                // Makes sure a player hasn't folded.
                if (hands[player - 1][0] == "0") {
                    System.out.println("Player + " + player + " has folded, skipping their turn...");
                    player += 1;
                }

                // Displays the player
                if (player <= numPlayers) {
                    System.out.println("Player " + player + "'s turn:");
                    player += 1;
                }

                // If the player's turn goes out of range, reset back to the start player -
                // completing the cycle.
                if (player > numPlayers) {
                    player = startPlayer;
                }

                displayHand(hands[player - 1]);

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

    public static boolean enoughCards(String[] cards, int numPlayers) {
        // Cards required for the number of players in the game.
        int cardsReq = 5 + (2 * numPlayers);

        // Cards remaining in the deck.
        int cardsRem = 51 - deckStart;

        // If there aren't enough cards to play a round.
        if (cardsReq > cardsRem) {
            return false;
        }
        // If there is.
        else {
            return true;
        }
    }

    public static String[] fold(String[] hand) {
        hand[0] = "0";
        hand[1] = "0";
        return hand;
    }
}