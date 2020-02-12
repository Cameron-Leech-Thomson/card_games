import java.util.ArrayList;
import java.util.Arrays;

import sheffield.*;

public class Solitaire {
    public static void main(String[] args) {
        // Create a deck instance.
        String[] deck = generateDeck();

        // Initialise the top piles.
        String[] pileC = new String[13];
        String[] pileH = new String[13];
        String[] pileS = new String[13];
        String[] pileD = new String[13];

        // Initialise the bottom piles.
        String[] pile1 = new String[14];
        String[] pile2 = new String[14];
        String[] pile3 = new String[14];
        String[] pile4 = new String[14];
        String[] pile5 = new String[14];
        String[] pile6 = new String[14];
        String[] pile7 = new String[14];

        // Initialise the stack of spare cards.
        String[] sparesStack = new String[28];

        // Fill piles:
        String[][] result = fillPiles(pile1, 1, deck);
        pile1 = result[0];
        deck = result[1];

        result = fillPiles(pile2, 2, deck);
        pile2 = result[0];
        deck = result[1];

        result = fillPiles(pile3, 3, deck);
        pile3 = result[0];
        deck = result[1];

        result = fillPiles(pile4, 4, deck);
        pile4 = result[0];
        deck = result[1];

        result = fillPiles(pile5, 5, deck);
        pile5 = result[0];
        deck = result[1];

        result = fillPiles(pile6, 6, deck);
        pile6 = result[0];
        deck = result[1];

        result = fillPiles(pile7, 7, deck);
        pile7 = result[0];
        deck = result[1];

        // Shuffle the remaining cards into the spares stack.
        sparesStack = shuffle(deck);

        // Display number of cards beneath the top card.
        System.out.println(displayTop(pileC)[1] + "  " + displayTop(pileH)[1] + "  " + displayTop(pileS)[1] + "  "
                + displayTop(pileD)[1] + "        " + displayTop(sparesStack)[1]);

        // Display suit piles.
        System.out.println(displayTop(pileC)[0] + "  " + displayTop(pileH)[0] + "  " + displayTop(pileS)[0] + "  "
                + displayTop(pileD)[0] + "        " + displayTop(sparesStack)[0]);

        // Print the number of cards beneath the top card.
        System.out.println();

        String cardLine = displayTop(pile1)[0] + " " + displayTop(pile2)[0] + " " + displayTop(pile3)[0] + " "
                + displayTop(pile4)[0] + " " + displayTop(pile5)[0] + " " + displayTop(pile6)[0] + " "
                + displayTop(pile7)[0];

        String cardNum = displayTop(pile1)[1] + "  " + displayTop(pile2)[1] + "  " + displayTop(pile3)[1] + "  "
                + displayTop(pile4)[1] + "  " + displayTop(pile5)[1] + "  " + displayTop(pile6)[1] + "  "
                + displayTop(pile7)[1];

        System.out.println(displayTop(pile1)[1] + "  " + displayTop(pile2)[1] + "  " + displayTop(pile3)[1] + "  "
                + displayTop(pile4)[1] + "  " + displayTop(pile5)[1] + "  " + displayTop(pile6)[1] + "  "
                + displayTop(pile7)[1]);

        // Display the top card for each pile.
        System.out.println(displayTop(pile1)[0] + " " + displayTop(pile2)[0] + " " + displayTop(pile3)[0] + " "
                + displayTop(pile4)[0] + " " + displayTop(pile5)[0] + " " + displayTop(pile6)[0] + " "
                + displayTop(pile7)[0]);
    }

    public static String formatOutput(String cards, String nums) {
        // Turns the cards string into an array.
        char[] cardsArr = cards.toCharArray();

        // Create index to check which cards are 3 chars long.
        String[] tripleIndex = new String[7];

        // Counter to check how long each card is.
        int count = 0;

        for (int i = 0; i < cardsArr.length; i++) {
            // Resets count if there's a space.
            if (cardsArr[i] == ' ') {
                count = 0;
            }
            // Increases count if there isn't a space.
            else{
                count += 1;
            }
            // Once there's 3 chars, note the index and reset the count.
            if (count == 3) {
                tripleIndex[i] = Integer.toString(i);
                count = 0;
            }
        }
        
        tripleIndex = removeNull(tripleIndex);

        if (tripleIndex.length == 0) {
 
        }
        else {
            for (int i = 0; i < tripleIndex.length; i++) {
                
            }
        }
    }

    public static String[] displayTop(String[] pile) throws IndexOutOfBoundsException {
        // Array containing the top card & how many cards are beneath it.
        String[] result = new String[2];
        // Search pile for the first null value.
        for (int i = pile.length - 1; i >= 0; i--) {
            if (pile[i] == null) {
            } else {
                result[0] = pile[i];
                result[1] = Integer.toString(i);
                return result;
            }
        }
        result[0] = "0";
        result[1] = "0";
        return result;
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

    public static boolean canStack(String card1, String card2) {
        // Obtain details of card 1 (top).
        char suit1 = card1.toCharArray()[0];
        int val1;
        switch (card1.toCharArray()[1]) {
        case 'A':
            val1 = 1;
            break;
        case 'J':
            val1 = 11;
            break;
        case 'Q':
            val1 = 12;
            break;
        case 'K':
            val1 = 13;
            break;
        default:
            val1 = card1.toCharArray()[1];
            break;
        }

        // Obtain details of card 2 (beaneath).
        char suit2 = card2.toCharArray()[0];
        int val2;
        switch (card2.toCharArray()[1]) {
        case 'A':
            val2 = 1;
            break;
        case 'J':
            val2 = 11;
            break;
        case 'Q':
            val2 = 12;
            break;
        case 'K':
            val2 = 13;
            break;
        default:
            val2 = card2.toCharArray()[1];
            break;
        }

        if ((suit1 == 'C' || suit1 == 'S') && (suit2 == 'H' || suit2 == 'D')) {
            if (val1 + 1 == val2) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    public static String[] shuffle(String[] deck) {
        for (int i = deck.length - 1; i > 0; i--) {

            int index = rand(0, i + 1);

            // Create temp value
            String card = deck[index];
            // Swap the two cards.
            deck[index] = deck[i];
            deck[i] = card;
        }
        // Return the shuffled deck.
        return deck;
    }

    public static String[][] fillPiles(String[] pile, int numCards, String[] deck) {
        for (int i = 0; i < numCards; i++) {
            // Generate a random card from the deck.
            int rand = rand(0, deck.length - 1);
            while (linearSearch(pile, deck[rand]) != -1) {
                rand = rand(0, deck.length - 1);
            }

            // Places that card in the pile.
            pile[i] = (String) deck[rand];

            // Removes the card from the deck to avoid duplicates.
            deck = deleteElement(deck, deck[rand]);
            deck = removeNull(deck);
        }
        // Puts the pile & the deck together so it can be returned.
        String[][] result = { pile, deck };

        // Returns the filled pile & the updated deck.
        return result;
    }

    public static int rand(int min, int max) {
        // Generate a random value between the min and the max values passed.
        int rand = (int) Math.round(Math.random() * (max - min) + min);

        // Return the random value.
        return rand;
    }

    public static String[] deleteElement(String[] arr, String element) {
        // Finds the index of the element to be deleted.
        int index = linearSearch(arr, element);

        // Set element to null.
        arr[index] = null;

        // Return the altered array.
        return arr;
    }

    public static int linearSearch(String[] arr, String element) {
        // Checks array isn't empty.
        if (arr == null) {
            return -1;
        }
        for (int i = 0; i < arr.length; i++) {
            // If the desired element is in position i:
            if (arr[i] == element) {
                // Return the value of i.
                return i;
            }
        }
        // If i is not found, return -1.
        return -1;
    }

    public static String[] insertionSort(String[] arr) {
        for (int j = 1; j < arr.length; j++) {
            String key = arr[j];
            int i = j - 1;
            while (i >= 0) {
                if (key.compareTo(arr[i]) > 0) {
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

    public static String[] generateDeck() {
        // Generate array for the deck.
        String[] deck = new String[52];

        // Initialise the suit var.
        char suit;

        String cardValue;

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
            // Give each card its value:
            // Cases for special cards (Ace, Jack, Queen, King).
            int j = i % 13;
            switch (j) {
            case 1:
                cardValue = suit + "A";
                deck[i - 1] = cardValue;
                break;
            case 11:
                cardValue = suit + "J";
                deck[i - 1] = cardValue;
                break;
            case 12:
                cardValue = suit + "Q";
                deck[i - 1] = cardValue;
                break;
            case 0:
                cardValue = suit + "K";
                deck[i - 1] = cardValue;
                break;
            default:
                cardValue = suit + Integer.toString(j);
                deck[i - 1] = cardValue;
                break;
            }
        }
        // Sort the deck.
        deck = insertionSort(deck);
        // Return the filled deck.
        return deck;
    }

    // Purely for debugging arrays:
    public static void outputArray(String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null) {
                System.out.print(arr[i] + ", ");
            }
        }
        System.out.println("");
    }
}