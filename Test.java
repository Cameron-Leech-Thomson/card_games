public class Test {
  public static void main(String[] args) throws NullPointerException{
    Deck deck = new Deck();
    deck.display();
    deck.shuffle();
    System.out.println();
    deck.display();

    System.out.println(deck.getCard(3));
  }

  public static String[] removeNull(String[] arr) {
    // Counter for the length of the list without nulls.
    int count = 0;

    System.out.println(arr.length);

    for (int i = 0; i < arr.length - 1; i++) {
        if (arr[i] == null) {}
        else {
            count += 1;
        }
    }

    System.out.println(count);

    // Initialise duplicate array to remove nulls.
    String[] arrNull = new String[count];
    int j = 0;

    //Fill the array.
    for (int i = 0; i < arr.length - 1; i++) {
        if (arr[i] == null) {}
        else {
            arrNull[j] = arr[i];
            j += 1;
        }
    }
    // Return the updated array.
    return arrNull;
}

  public static String[] insertionSort(String[] arr) throws NullPointerException{
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

  public static String[][] output() {
    String[] array1 = { "gay" };
    String[] array2 = { "retard" };

    String[][] returned = { array1, array2 };

    return returned;
  }

  // Purely for debugging arrays:
  public static void outputArray(String[] arr) {
    for (int i = 0; i < arr.length; i++) {
      System.out.print(arr[i] + ", ");
    }
    System.out.println("");
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
}