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

        System.out.println("To move cards, name the pile you want to move from, followed by the pile you want. "
                + "For example, if you want to move the card on the leftmost pile to the card at the rightmost, you would type \"1 7\". "
                + "If you want to access the spares stack type \"spares\", and if you want to cycle the spares stack, type \"cycle\". "
                + "To move to one of the top piles, type the name of the suit that the card belongs in, for example \"CK clubs\". ");

        System.out.println();

        // Display number of cards beneath the top card.
        System.out.println(displayTop(pileC)[1] + "  " + displayTop(pileH)[1] + "  " + displayTop(pileS)[1] + "  "
                + displayTop(pileD)[1] + "        " + displayTop(sparesStack)[1]);

        // Display suit piles.
        System.out.println(displayTop(pileC)[0] + "  " + displayTop(pileH)[0] + "  " + displayTop(pileS)[0] + "  "
                + displayTop(pileD)[0] + "        " + displayTop(sparesStack)[0]);

        System.out.println();

        String cardLine = displayTop(pile1)[0] + " " + displayTop(pile2)[0] + " " + displayTop(pile3)[0] + " "
                + displayTop(pile4)[0] + " " + displayTop(pile5)[0] + " " + displayTop(pile6)[0] + " "
                + displayTop(pile7)[0];

        String cardNum = displayTop(pile1)[1] + "  " + displayTop(pile2)[1] + "  " + displayTop(pile3)[1] + "  "
                + displayTop(pile4)[1] + "  " + displayTop(pile5)[1] + "  " + displayTop(pile6)[1] + "  "
                + displayTop(pile7)[1];

        // Print the number of cards beneath the top card.
        System.out.println(formatOutput(cardLine, cardNum));

        // Display the top card for each pile.
        System.out.println(cardLine);
    }

    public static boolean winCondition (String[] pileC, String[] pileH, String[] pileS, String[] pileD) {
        pileC = removeNull(pileC);
        pileH = removeNull(pileH);
        pileS = removeNull(pileS);
        pileD = removeNull(pileD);

        if((pileC.length == pileH.length) && (pileC.length == pileD.length) && (pileC.length == pileS.length)) {
            System.out.println("Congratulations, you win!");
            System.exit(0);
            return true;
        }
        else{
            return false;
        }
    }

    public static String[][] moveCards(String[] pile1, String[] pile2) {
        // Sets up the result array to output the two altered piles.
        String[][] result = new String[2][];
        // Gets the value of the cards that are being moved.
        String card1 = displayTop(pile1)[0];
        // Makes sure the cards can stack before proceeding.
        if (!canStack(pile1, pile2)) {
            System.out.println("Sorry, you can't stack those cards.");
            // If not exit the function.
            result[0] = pile1;
            result[1] = pile2;
            return result;
        }
        // Finds the highest not-null index of the pile.
        int pileTop = Integer.parseInt(displayTop(pile2)[1]) + 2;

        // Adds the card to the pile.
        pile2 = addStringToArray(pile2, card1, pileTop);

        // Removes the card from the other pile.
        pile1 = deleteElement(pile1, card1);
        pile1 = removeNull(pile1);

        // Return the altered piles.
        result[0] = pile1;
        result[1] = pile2;
        return result;
    }

    public static String formatOutput(String cards, String nums) {
        // Turns the number string into an array.
        char[] numsArr = nums.toCharArray();

        // Turns the cards string into an array.
        char[] cardsArr = cards.toCharArray();

        // Create index to check which cards are 3 chars long.
        String[] tripleArray = new String[7];
        int tripleIndex = 0;

        // Counter to check how long each card is.
        int count = 0;

        for (int i = 0; i < cardsArr.length; i++) {
            // Resets count if there's a space.
            if (cardsArr[i] == ' ') {
                count = 0;
            }
            // Increases count if there isn't a space.
            else {
                count += 1;
            }
            // Once there's 3 chars, note the index and reset the count.
            if (count == 3) {
                tripleArray[tripleIndex] = Integer.toString(i);
                tripleIndex += 1;
                count = 0;
            }
        }

        tripleArray = removeNull(tripleArray);

        if (tripleArray.length == 0) {
            return nums;
        } else {
            for (int i = 0; i < tripleArray.length; i++) {
                // Finds the index to add the extra space to.
                int numsIndex = Integer.parseInt(tripleArray[i]);

                // Adds the extra space to the string.
                numsArr = addCharToArray(numsArr, ' ', numsIndex);
            }
            // Return the updated string.
            return assembleString(numsArr);
        }
    }

    public static String assembleString(char[] arr) {
        // Initilise the output string.
        String assembledString = "";

        for (int i = 0; i < arr.length; i++) {
            assembledString += arr[i];
        }
        return assembledString;
    }

    public static String[] addStringToArray(String[] arr, String content, int insertIndex) {
        // Initialise larger array.
        String[] resizedArray = new String[arr.length + 1];

        // Pointer for arr.
        int j = 0;

        for (int i = 0; i < resizedArray.length; i++) {
            if (i == insertIndex) {
                resizedArray[i] = content;
                j -= 1;
            } else {
                if (j == resizedArray.length - 1) {
                } else {
                    resizedArray[i] = arr[j];
                }
            }
            j += 1;
        }
        // Returns the resized array.
        return resizedArray;
    }

    public static char[] addCharToArray(char[] arr, char content, int insertIndex) {
        // Initialise larger array.
        char[] resizedArray = new char[arr.length + 1];

        // Pointer for arr.
        int j = 0;

        for (int i = 0; i < resizedArray.length; i++) {
            if (i == insertIndex) {
                resizedArray[i] = content;
                j -= 1;
            } else {
                if (j == resizedArray.length - 1) {
                } else {
                    resizedArray[i] = arr[j];
                }
            }
            j += 1;
        }
        // Returns the resized array.
        return resizedArray;
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

    public static boolean canStack(String[] pile1, String[] pile2) {
        // Takes value of the cards.
        String card1 = displayTop(pile1)[0];
        String card2 = displayTop(pile2)[0];
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
        } else if ((val2 == 13) && (Integer.parseInt(displayTop(pile2)[0]) == 0)) {
            return true;
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