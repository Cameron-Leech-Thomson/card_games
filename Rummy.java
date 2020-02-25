import java.io.IOException;

import sheffield.*;

public class Rummy {
    public static String[] deck = generateDeck();
    public static int deckStart = 0;

    public static void main(String[] args) throws IOException {
        deck = shuffle(deck);

        EasyReader keyboard = new EasyReader();

        String[] hand1 = new String[8];
        String[] hand2 = new String[8];
        String[] hand3 = new String[8];
        String[] hand4 = new String[8];

        String[] pile = new String[52];

        String[] playableCards = new String[52];

        int numPlayers = keyboard.readInt("How many players are you playing with? (Max 4, Min 2) >");

        if (numPlayers == 2) {
            hand1 = fillHand(hand1);
            hand2 = fillHand(hand2);
        } else if (numPlayers == 3) {
            hand1 = fillHand(hand1);
            hand2 = fillHand(hand2);
            hand3 = fillHand(hand3);
        } else if (numPlayers == 4) {
            hand1 = fillHand(hand1);
            hand2 = fillHand(hand2);
            hand3 = fillHand(hand3);
            hand4 = fillHand(hand4);
        }

        boolean winner = false;

        int player = 1;

        int pileIndex = 0;

        int playIndex = 0;

        int count = 0;

        for (int i = deckStart; i < 52; i++) {
            playableCards[count] = deck[i];
            count += 1;
        }

        while (!winner) {
            if (player < 5) {
                System.out.println("Player " + player + "'s turn:");
                player += 1;
            }

            if (player > numPlayers) {
                player = 1;
            }

            if (pileIndex == 0) {
                pile[0] = playableCards[playIndex];
                playableCards = deleteElement(playableCards, playableCards[playIndex]);
                playableCards = rearrangeNull(playableCards);
            }

            displayHand(playableCards);

            System.out.println("Card on player pile: " + pile[pileIndex]);

            switch (player) {
                case 1:
                    displayHand(hand1);
                    break;
                case 2:
                    displayHand(hand2);
                    break;
                case 3:
                    displayHand(hand3);
                    break;
                case 4:
                    displayHand(hand4);
                    break;
                default:
                    break;
            }

            String pickPile = keyboard
                    .readString("Would you like to pick from the player pile [p] or the face-down pile? [f] >")
                    .toLowerCase();
            if (pickPile == "p") {
                switch (player) {
                    case 1:
                        hand1[0] = pile[pileIndex];
                        break;
                    case 2:
                        hand2[0] = pile[pileIndex];
                        break;
                    case 3:
                        hand3[0] = pile[pileIndex];
                        break;
                    case 4:
                        hand4[0] = pile[pileIndex];
                        break;
                    default:
                        continue;
                }

                pile = deleteElement(pile, pile[pileIndex]);
                pileIndex -= 1;
            } else {
                switch (player) {
                    case 1:
                        hand1[0] = playableCards[playIndex];
                        break;
                    case 2:
                        hand2[0] = playableCards[playIndex];
                        break;
                    case 3:
                        hand3[0] = playableCards[playIndex];
                        break;
                    case 4:
                        hand4[0] = playableCards[playIndex];
                        break;
                    default:
                        continue;
                }

                playableCards = deleteElement(playableCards, playableCards[playIndex]);
                playIndex += 1;
            }

            switch (player) {
                case 1:
                    displayHand(hand1);
                    break;
                case 2:
                    displayHand(hand2);
                    break;
                case 3:
                    displayHand(hand3);
                    break;
                case 4:
                    displayHand(hand4);
                    break;
                default:
                    break;
            }

            System.out.println(playIndex);

            // Checks if a player has won.
            switch (numPlayers) {
                case 2:
                    if (winCondition(hand1, 1) || winCondition(hand2, 2)) {
                        winner = true;
                    }
                    break;
                case 3:
                    if (winCondition(hand1, 1) || winCondition(hand2, 2) || winCondition(hand3, 3)) {
                        winner = true;
                    }
                    break;
                case 4:
                    if (winCondition(hand1, 1) || winCondition(hand2, 2) || winCondition(hand3, 3)
                            || winCondition(hand4, 4)) {
                        winner = true;
                    }
                    break;
                default:
                    break;
            }

            int putDown = keyboard.readInt("Select the index of the card you'd like to put down (Starting from 1) >")
                    - 1;

            switch (player) {
                case 1:
                    pile[pileIndex + 1] = hand1[putDown];
                    hand1 = deleteElement(hand1, hand1[putDown]);
                    hand1 = insertionSort(hand1);
                    break;
                case 2:
                    pile[pileIndex + 1] = hand2[putDown];
                    hand2 = deleteElement(hand2, hand2[putDown]);
                    hand2 = insertionSort(hand2);
                    break;
                case 3:
                    pile[pileIndex + 1] = hand3[putDown];
                    hand3 = deleteElement(hand3, hand3[putDown]);
                    hand3 = insertionSort(hand3);
                    break;
                case 4:
                    pile[pileIndex + 1] = hand4[putDown];
                    hand4 = deleteElement(hand4, hand4[putDown]);
                    hand4 = insertionSort(hand4);
                    break;
                default:
                    break;
            }

            // Increment pileIndex.
            pileIndex += 1;

            clearScreen();

            displayHand(playableCards);
        }

        // Close the reader.
        keyboard.close();

        // Exit the program.
        System.exit(0);
    }

    public static boolean winCondition(String[] hand, int playerNo) {
        // Declare score, score of 2 wins.
        int score = 0;

        // Sorts the array so it can be searched for straights.
        hand = insertionSort(hand);

        // Win conditions for the 4:
        for (int i = 0; i < hand.length - 4; i++) {
            if ((getSuit(hand[i]) == getSuit(hand[i + 1])) && (getSuit(hand[i]) == getSuit(hand[i + 2]))
                    && (getSuit(hand[i]) == getSuit(hand[i + 3]))) {
                if ((getRank(hand[i]) == getRank(hand[i + 1]) + 1) && (getRank(hand[i]) == getRank(hand[i + 2]) + 2)
                        && (getRank(hand[i]) == getRank(hand[i + 3]) + 3)) {
                    score += 1;

                    for (int j = 0; j < 4; j++) {
                        hand = deleteElement(hand, hand[i + j]);
                    }

                    hand = removeNull(hand);
                } else if ((getRank(hand[i]) == getRank(hand[i + 1]) - 1)
                        && (getRank(hand[i]) == getRank(hand[i + 2]) - 2)
                        && (getRank(hand[i]) == getRank(hand[i + 3]) - 3)) {
                    score += 1;

                    for (int j = 0; j < 4; j++) {
                        hand = deleteElement(hand, hand[i + j]);
                    }

                    hand = removeNull(hand);
                }
            } else if ((getRank(hand[i]) == getRank(hand[i + 1])) && (getRank(hand[i]) == getRank(hand[i + 2]))
                    && (getRank(hand[i]) == getRank(hand[i + 3]))) {
                score += 1;

                for (int j = 0; j < 4; j++) {
                    hand = deleteElement(hand, hand[i + j]);
                }

                hand = removeNull(hand);
            } else {
            }
        }

        for (int i = 0; i < hand.length - 3; i++) {
            if ((getSuit(hand[i]) == getSuit(hand[i] + 1)) && (getSuit(hand[i]) == getSuit(hand[i] + 2))
                    && (getSuit(hand[i]) == getSuit(hand[i] + 3))) {
                if ((getRank(hand[i]) == getRank(hand[i + 1]) + 1) && (getRank(hand[i]) == getRank(hand[i + 2]) + 2)
                        && (getRank(hand[i]) == getRank(hand[i + 3]) + 3)) {
                    score += 1;

                    hand = removeNull(hand);
                } else if ((getRank(hand[i]) == getRank(hand[i + 1]) - 1)
                        && (getRank(hand[i]) == getRank(hand[i + 2]) - 2)
                        && (getRank(hand[i]) == getRank(hand[i + 3]) - 3)) {
                    score += 1;
                }
            } else if ((getRank(hand[i]) == getRank(hand[i + 1])) && (getRank(hand[i]) == getRank(hand[i + 2]))
                    && (getRank(hand[i]) == getRank(hand[i + 3]))) {
                score += 1;
            } else {
            }
        }

        if (score == 2) {
            System.out.println("Player " + playerNo + " Wins!!");
            System.exit(0);
            return true;
        } else {
            return false;
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static char getSuit(String card) {
        char[] arr = card.toCharArray();
        char suit = arr[0];
        return suit;
    }

    public static int getRank(String card) {
        char[] arr = card.toCharArray();

        // Intitialise rank.
        int rank = 0;

        switch (arr[1]) {
            case 'A':
                rank = 1;
                break;
            case 'J':
                rank = 11;
                break;
            case 'Q':
                rank = 12;
                break;
            case 'K':
                rank = 13;
                break;
            case '1':
                rank = 10;
                break;
            default:
                rank = Character.getNumericValue(arr[1]);
                break;
        }

        return rank;
    }

    public static String[] fillHand(String[] hand) {
        for (int i = 0; i < 7; i++) {
            hand[i] = deck[deckStart];
            deckStart += 1;
        }

        // Sort the array to make it easier to read.
        hand = insertionSort(hand);

        return hand;
    }

    public static int linearSearch(String[] arr, String element) {
        // Checks array isn't empty.
        if (arr == null) {
            return -1;
        }
        for (int i = 0; i < arr.length - 1; i++) {
            // If the desired element is in position i:
            if (arr[i] == element) {
                // Return the value of i.
                return i;
            }
        }
        // If i is not found, return -1.
        return -1;
    }

    public static int rand(int min, int max) {
        // Generate a random value between the min and the max values passed.
        int rand = (int) Math.round(Math.random() * (max - min) + min);

        // Return the random value.
        return rand;
    }

    public static String[] generateDeck() {
        // Generate array for the deck.
        String[] deck = new String[52];

        // Initialise the suit var.
        char suit;

        String StringValue;

        // Generate the deck.
        for (int i = 1; i <= 52; i++) {
            // Set the suit, according to CHaSeD notation.
            if ((i % 4) == 0) {
                suit = 'C';
            } else if ((i % 4) == 1) {
                suit = 'H';
            } else if ((i % 4) == 2) {
                suit = 'S';
            } else {
                suit = 'D';
            }
            // Give each String its value:
            // Cases for special Strings (Ace, Jack, Queen, King).
            int j = i % 13;
            switch (j) {
                case 1:
                    StringValue = suit + "A";
                    deck[i - 1] = StringValue;
                    break;
                case 11:
                    StringValue = suit + "J";
                    deck[i - 1] = StringValue;
                    break;
                case 12:
                    StringValue = suit + "Q";
                    deck[i - 1] = StringValue;
                    break;
                case 0:
                    StringValue = suit + "K";
                    deck[i - 1] = StringValue;
                    break;
                default:
                    StringValue = suit + Integer.toString(j);
                    deck[i - 1] = StringValue;
                    break;
            }
        }
        // Sort the deck.
        deck = insertionSort(deck);
        // Return the filled deck.
        return deck;
    }

    public static String[] shuffle(String[] deck) {
        for (int i = deck.length - 1; i > 0; i--) {

            int index = rand(0, i + 1);

            // Create temp value
            String String = deck[index];
            // Swap the two Strings.
            deck[index] = deck[i];
            deck[i] = String;
        }
        // Return the shuffled deck.
        return deck;
    }

    public static String[] insertionSort(String[] arr) {
        for (int j = 1; j < arr.length; j++) {
            String key = arr[j];
            int i = j - 1;
            while (i >= 0) {
                if (compareKeys(key, arr[i]) > 0) {
                    break;
                }
                arr[i + 1] = arr[i];
                i--;
            }
            arr[i + 1] = key;
        }
        // Return sorted array.
        return arr;
    }

    private static int compareKeys(final String first, final String second) {
        if (first == null || second == null) {
            return 0;
        } else {
            return first.compareTo(second);
        }
    }

    public static String[] deleteElement(String[] arr, String element) {
        // Finds the index of the element to be deleted.
        int index = linearSearch(arr, element);

        // Set element to null.
        arr[index] = null;

        // Return the altered array.
        return arr;
    }

    public static String[] removeNull(String[] arr) {
        // Checks to see if the array is empty.
        if (arr.length == 0) {
            String[] empty = {};
            return empty;
        }

        // Counter for the length of the list without nulls.
        int count = 0;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == null) {
            } else {
                count += 1;
            }
        }

        // Initialise duplicate array to remove nulls.
        String[] arrNull = new String[count];
        int j = 0;

        // Fill the array.
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == null) {
            } else {
                arrNull[j] = arr[i];
                j += 1;
            }
        }
        // Return the updated array.
        return arrNull;
    }

    public static String[] rearrangeNull(String[] arr) {
        // Checks to see if the array is empty.
        if (arr.length == 0) {
            String[] empty = {};
            return empty;
        }

        // Initialise duplicate array with rearranged nulls.
        String[] arrNull = new String[arr.length];
        int j = 0;

        // Fill the array.
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == null) {
            } else {
                arrNull[j] = arr[i];
                j += 1;
            }
        }

        return arrNull;
    }

    public static void displayHand(String[] hand) {
        String output = "";
        for (int i = 0; i < hand.length; i++) {
            if (hand[i] == null) {
            } else {
                output += "" + hand[i] + ", ";
            }
        }
        System.out.println(output);
    }

}
