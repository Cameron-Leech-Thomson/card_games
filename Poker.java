import java.io.IOException;

import sheffield.*;

public class Poker extends Rummy {

    public static String[] deck = shuffle(generateDeck());
    public static int deckStart = 0;

    public static void main(String[] args) throws IOException {
        EasyReader keyboard = new EasyReader();

        // Read in number of players.
        int numPlayers = keyboard.readInt("How many players are you playing with? (Max 8, Min 3) > ");

        // Set up array of hands.
        String[][] hands = new String[numPlayers][2];

        // Loop through hands and deal each hand 2 cards.
        for (int i = 0; i < hands.length; i++) {
            hands[i] = fillHand(hands[i]);
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