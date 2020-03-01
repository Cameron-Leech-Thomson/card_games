import java.io.IOException;

import sheffield.*;

public class Poker extends Rummy {

    public static String[] deck = shuffle(generateDeck());
    public static int deckStart = 0;

    public static void main(String[] args) throws IOException, InterruptedException {
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

            // If there are insufficient cards to play a game with the desired number of
            // players, shuffle the deck.
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
                    "All hands dealt, let's play! Each player will start with £5000. The blind is £100, there is no small blind.");

            // Counter for the amount of players still active in the current round.
            int playersIn = numPlayers;

            // Indicates which player's turn it is.
            int player = startPlayer;

            // Round counter.
            int round = -1;

            // Int storing the winnings in the pot.
            int winnings = 0;

            // Store player bets in an array.
            int[] bets = new int[playersIn];
            bets = resetBets(bets);

            // Allows everyone to read instructions before clearing the screen ready for the
            // game.
            Thread.sleep(3000);
            clearScreen();

            while (playersIn > 1) {
                // Displays the round number.
                System.out.println("Current round: " + (round + 2));

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

                // If a round has been complete, increase the round counter.
                if ((player == startPlayer) && (nextRound(bets))) {
                    round += 1;
                    bets = resetBets(bets);
                }

                if (round > 0) {
                    int max = 0;
                    if ((round + 2) < 6) {
                        max = round + 2;
                    } else {
                        max = 5;
                    }
                    System.out.print("Community cards: ");
                    for (int i = 0; i < max; i++) {
                        System.out.print(comCards[i] + ", ");
                    }
                    System.out.println();
                }

                if ((player == startPlayer) && round == 0) {
                    System.out.println(
                            "This player has the big blind, they are automatically betting £100 and their first turn is skipped.");

                    // Add the blind to the bets & money & pot.
                    bets[player] = 100;
                    money[player] -= 100;
                    winnings += 100;

                    displayHand(hands[player - 1]);

                    Thread.sleep(1500);

                    clearScreen();
                    continue;
                }

                // Display current possible winnings.
                System.out.println("Current possible winnings: " + winnings);

                displayHand(hands[player - 1]);

                Thread.sleep(1500);

                clearScreen();
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

    public static int[] resetBets (int[] bets) {
        for (int i = 0; i < bets.length; i++) {
            bets[i] = 0;
        }
        return bets;
    }

    public static boolean nextRound(int[] bets) {
        for (int i = 1; i < bets.length; i++) {
            // If all the bets match, do nothing.
            if (bets[0] == bets[i]) {
            } else {
                return false;
            }
        }
        return true;
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