import java.io.IOException;

import sheffield.*;

public class Poker extends Rummy {

    public static String[] deck = shuffle(generateDeck());
    public static int deckStart = 0;

    public static void main(String[] args) throws IOException, InterruptedException {
        EasyReader keyboard = new EasyReader();

        clearScreen();

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
            Thread.sleep(5000);
            clearScreen();

            boolean firstGo;

            while (playersIn > 1) {
                if ((winnings == 0) && (round == -1) && player == startPlayer) {
                    firstGo = true;
                } else {
                    firstGo = false;
                }

                // Displays the round number.
                System.out.println("Current round: " + (round + 2));

                // Makes sure a player hasn't folded.
                if (hands[player - 1][0] == "0") {
                    System.out.println("Player " + player + " has folded, skipping their turn...");
                    player += 1;
                    continue;
                }

                // Displays the player
                if (player <= numPlayers) {
                    System.out.println("Player " + player + "'s turn:");
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
                        System.out.print(comCards[i] + " ");
                    }
                    System.out.println();
                }

                if (firstGo) {
                    System.out.println(
                            "This player has the big blind, they are automatically betting £100 and their first turn is skipped.");

                    // Add the blind to the bets & money & pot.
                    bets[player - 1] = 100;
                    money[player - 1] -= 100;
                    winnings += 100;

                    // Moves on to next player.
                    player += 1;

                    displayHand(hands[player - 1]);

                    Thread.sleep(5000);

                    clearScreen();
                    continue;
                }

                // Display player's money.
                System.out.print("You currently have £" + money[player - 1] + ". ");

                // Display current possible winnings.
                System.out.println("Current possible winnings: " + winnings);

                // Display the player's hand.
                displayHand(hands[player - 1]);

                boolean inputsReq = true;

                while (inputsReq) {
                    inputs: {
                        // Check if the player is bust.
                        if (isBust(money[player - 1])) {
                            System.out.println(
                                    "You have gone bust, you cannot bet for the rest of this round, you can only check or fold.");
                        }

                        // Check whether it is possible to check.
                        boolean checkPos;

                        // Index to check the previous bet.
                        int preIndex = player - 2;

                        System.out.print("Would you like to bet[b], or fold[f]? ");
                        if ((preIndex) < 0) {
                            preIndex = numPlayers - 1;
                        }
                        if (bets[player - 1] == bets[preIndex]) {
                            System.out.println("It is currently possible to check[c].");
                            checkPos = true;
                        } else {
                            System.out.println("It is currently not possible to check.");
                            checkPos = false;
                        }

                        boolean validInput = false;

                        while (!validInput) {
                            // Take in the user's input.
                            String userInput = keyboard.readString("> ");

                            switch (userInput.toLowerCase()) {
                                case "b":
                                    // Make sure the player can actually bet before continuing.
                                    if (isBust(money[player - 1])) {
                                        break inputs;
                                    }
                                    // Initialise betVal.
                                    int betVal = 0;

                                    System.out.println("How much would you like to bet? You currently have £"
                                            + money[player - 1] + ". You must at least match the previous bid ("
                                            + bets[preIndex] + "). If you would like to go back, type [back].");

                                    boolean validBet = false;
                                    while (!validBet) {
                                        // Read in the user's bet.
                                        String bet = keyboard.readString("> ");

                                        if ((bet.toLowerCase()).equals("back")) {
                                            // Back out;
                                            break inputs;
                                        }
                                        // If valid:
                                        try {
                                            if ((Integer.valueOf(bet) >= bets[preIndex])
                                                    && (Integer.valueOf(bet) <= money[player - 1])) {
                                                System.out.println("Betting " + bet + ".");
                                                betVal = Integer.valueOf(bet);
                                                inputsReq = false;
                                                validBet = true;
                                            } else {
                                                NumberFormatException e = new NumberFormatException();
                                                throw e;
                                            }
                                        } catch (NumberFormatException e) {
                                            System.out.println("Invalid input - please enter a valid bet.");
                                        } finally {
                                        }
                                    }

                                    // Update the arrays with the new values for money & bets.
                                    bets[player - 1] += betVal;
                                    money[player - 1] -= betVal;
                                    winnings += betVal;

                                    System.out.println("Bet (£" + betVal
                                            + ") successful, the winnings and your money will be updated.");
                                    inputsReq = false;
                                    break inputs;
                                case "f":
                                    boolean validFold = false;
                                    while (!validFold) {
                                        System.out.println(
                                                "If you fold you are out of this game, are you sure you want to do this? [yes/no]");

                                        // Read in user's input.
                                        String userInput1 = keyboard.readString("> ");

                                        if ((userInput1.toLowerCase()).equals("yes")) {
                                            // Fold for the user:
                                            hands[player - 1] = fold(hands[player - 1]);
                                            System.out.println(
                                                    "Fold successful - your hand has been wiped, you are out of this game.");
                                            playersIn -= 1;
                                            validFold = true;
                                            inputsReq = false;
                                        } else if ((userInput1.toLowerCase()).equals("no")) {
                                            // Back out:
                                            validFold = true;
                                            break inputs;
                                        } else {
                                            System.out.println("Invalid input - please enter a valid entry.");
                                        }
                                    }
                                    inputsReq = false;
                                    break inputs;
                                case "c":
                                    // If it isn't possible to check:
                                    if (checkPos == false) {
                                        System.out.println(
                                                "It is currently not possible to check, please pick another option.");
                                        break inputs;
                                    } else {
                                        System.out.println("You have checked, your bets and money have not changed.");
                                    }
                                    inputsReq = false;
                                    break inputs;
                                default:
                                    System.out.println("Invalid input - please provide a valid entry.");
                                    break inputs;
                            }
                        }
                    }
                }

                // Increases the player counter.
                player += 1;

                // If the player's turn goes out of range, reset back to the start player -
                // completing the cycle.
                if (player > numPlayers) {
                    player = startPlayer;
                }

                Thread.sleep(5000);

                clearScreen();
                clearScreen();
            }

            // Once the game has been completed...

            // Find the remaining player:
            int remPlayer = findLP(hands);

            // Update their statistics.
            money[remPlayer] += winnings;

            // Display the winner and their winnings.
            System.out.println("Player " + (remPlayer + 1) + " has won " + winnings + ", bringing their total to £"
                    + money[remPlayer] + ". Their hand was " + hands[remPlayer][0] + " & " + hands[remPlayer][1]
                    + ". \n");

            // Check for valid input.
            boolean validAgain = false;

            while (validAgain == false) {
                // Check if the user wants to play again.
                System.out.println("Would you like to play again? [yes/no]");
                String againString = keyboard.readString("> ").toLowerCase();

                if (againString.equals("yes")) {
                    System.out.println("Restarting game...");
                    playAgain = true;
                    validAgain = true;
                } else if (againString.equals("no")) {
                    System.out.println("Exiting...");
                    playAgain = false;
                    validAgain = true;
                } else {
                    System.out.println("Invalid input - please enter yes or no.");
                }
            }

            // Increase the position of the start player.
            startPlayer += 1;

            clearScreen();
        }

        keyboard.close();
    }

    public static int findLP(String[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            // If player has folded, do nothing.
            if (arr[i][0] == "0") {
            }
            // If they haven't, return the index.
            else {
                return i;
            }
        }
        // If no player is found, return -1.
        return -1;
    }

    public static boolean isBust(int money) {
        if (money <= 50) {
            return true;
        } else {
            return false;
        }
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

    public static int[] resetBets(int[] bets) {
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