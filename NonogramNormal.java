import java.util.*;

public class NonogramNormal {
  /*
  * Instance Variables
  */
  private int[][] actualPuzzle;
  private int[][] playerPuzzle;
  private int[][] rowNumbers;
  private int[][] columnNumbers;
  private String puzzleName;
  private int points;
  
  /*
  * Constructors
  */
  public NonogramNormal(int[][] puzzle, String name){
    actualPuzzle = puzzle;
    puzzleName = name;
    playerPuzzle = initiatePuzzle();
    assignGridNumbers();
  }

  /*
  * Methods
  */
  // Creates the user's start puzzle
  public int[][] initiatePuzzle(){
    points = 0;
    return new int[actualPuzzle.length][actualPuzzle[0].length];
  }

  // Inputs one answer into the system (ex. a1x)
  // 0 = failure, 1 = incorrect, 2 = correct, 3 = placing an x
  private int inputGuess(String guess){
    if (guess.length() != 3){
      System.out.println("Seems that your guess " + guess + " was in the incorrect" +
                         " format. Try again!");
      return 0;
    }
    
    try {
      String guessLet = guess.substring(0,1);
      int guessNum = Integer.parseInt(guess.substring(1,2)) - 1;
      if (guessNum < 0 || guessNum > actualPuzzle.length-1){
        System.out.println("Seems that your guess " + guess + " included an out-of-bounds" +
                           " number. Try again!");
        return 0;
      }

      int guessLetN = letterToNumber(guessLet);
      if (guessLetN == -1){
        System.out.println("Seems that your guess " + guess + " was in the incorrect" +
                           " format. Try again!");
        return 0;
      } 
      else {
        String guessType = guess.substring(2);
        int guessPos = actualPuzzle[guessLetN][guessNum];
        int playPos = playerPuzzle[guessLetN][guessNum];

        // Placing an X
        if (guessType.equalsIgnoreCase("x")){
          // Player already has x here
          if (playPos == 1){
            playerPuzzle[guessLetN][guessNum] = 0;
            System.out.println("X successfully removed at " +
                               guess.substring(0,2) + ".");
            return 0;
          }
          // Player has nothing here
          else if (playPos == 0){
            playerPuzzle[guessLetN][guessNum] = 1;
            return 3;
          } 
          // Player has already correctly guessed here
          else {
            System.out.println("Hey, don't place an x on a correct space (" +
                               guess.substring(0,2) + ")!");
            return 0;
          }
        } 
        // Attempting a guess
        else if (guessType.equalsIgnoreCase("o")){     
          if (guessPos == 0 && playPos != 1){
            playerPuzzle[guessLetN][guessNum] = 1;
            return 1;
          } else {
            if (playPos == 0){
              playerPuzzle[guessLetN][guessNum] = 2;
              return 2;
            } 
            else if (playPos == 1){
              System.out.println("An x has been placed here (" + 
                                 guess.substring(0,2) + "). Remove it to " +
                                 "guess in this spot.");
              return 0;
            } 
            else {
              System.out.println("Your guess (" + guess.substring(0,2) +
                                 ") has already been proven correct!");
              return 0;
            }
          }
        } 
        else {
          System.out.println("Seems that your guess " + guess + " was in the incorrect" +
                         " format. Try again!");
          return 0;
        }
      }
      
    } catch (Exception NumberFormatException){
      System.out.println("Seems that your guess " + guess + " was in the incorrect" +
                         " format. Try again!");
      return 0;
    }
  }

  // Inputs an answer into the system and returns
  // the appropriate feedback
  public String inputGuessFeedback(String guess){
    int g = inputGuess(guess);
    String gs = guess.substring(0,2);
    if (g == 0){
      return "";
    } else if (g == 3){
      return "X placed successfully at " + gs + ".";
    } else {
      if (g == 1){
        setPoints(points - 1);
        return "Your guess (" + gs + ") was incorrect!";
      } else {
        setPoints(points + 1);
        return "Your guess (" + gs + ") was correct!";
      }
    }
  }

  // Converts a letter to its appropriate index
  private int letterToNumber(String let){
    String[] letters = {"a", "b", "c", "d", "e",
                        "f", "g", "h", "i", "j",
                        "k", "l", "m", "n", "o",
                        "p", "q", "r", "s", "t", 
                        "u", "v", "w", "x", "y",
                        "z"};

    if (let.length() > 1){
      System.out.println("letterToNumber() was attempted on a String with " +
                         "a length greater than 1.");
      return -1;
    }

    int index = -1;
    
    for (int i = 0; i < letters.length; i++){
      if (let.equalsIgnoreCase(letters[i])){
        index = i;
      }
    }

    if (index == -1){
      System.out.println("letterToNumber() was attempted on a String with " +
                         "a character not in the English alphabet.");
    }

    return index;
  }

  // Creates a password and prints it to the player
  public void createPW(){
    System.out.println("Your Password: " + NonogramPassword.encode(playerPuzzle, points));
    System.out.println("Type it in the \"Begin w/ Password\" section when\n" +
                       "playing again to continue your game.");
  }

  // Returns the player's puzzle as a String
  public String puzzleToString(){
    String puz = "";

    // Column Numbers to String
    for (int i = 0; i < columnNumbers[0].length; i++){
      String col = "";
      // Adds appropriate spaces
      for (int j = 0; j < (rowNumbers[0].length*2)+3; j++){
        col += " ";
      }

      // Creates columns w/ numbers
      for (int j = 0; j < columnNumbers.length; j++){
        if (columnNumbers[j][i] == 0){
          col += "  ";
        } else {
          col += columnNumbers[j][i] + " ";
        }
      }

      puz += col + "\n";
    }

    puz += "\n";

    // Row Numbers + Puzzle Rows to String
    for (int i = 0; i < rowNumbers.length; i++){
      String row = "";

      // Adds row numbers
      for (int j = 0; j < rowNumbers[0].length; j++){
        if (rowNumbers[i][j] == 0){
          row += "  ";
        } else {
          row += rowNumbers[i][j] + " ";
        }
      }

      // Adds respective row
      row += "  " + rowToString(playerPuzzle[i]);
      
      puz += row + "\n";
    }
    
    
    return puz;
  }

  // Returns the final puzzle as a String;
  // only used when completed
  public String actualPuzzleToString(){
    String fin = "";
    for (int row = 0; row < actualPuzzle.length; row++){
      fin += "\n";
      for (int col = 0; col < actualPuzzle[0].length; col++){
        if (actualPuzzle[row][col] == 2){
          fin += "G ";
        } else {
          fin += "  ";
        }
      }
    }

    return fin;
  }

  // Converts a puzzle row into a String
  private String rowToString(int[] row){
    String result = "|";

    for (int i = 0; i < row.length; i++){
      if (row[i] == 0){
        result += " ";
      } else if (row[i] == 1){
        result += "x";
      } else {
        result += "G";
      }
      result += "|";
    }

    return result;
  }

  // Assigns the Nonogram row and column numbers
  private void assignGridNumbers(){
    // Row Number Assignment
    int[][] rowNums = new int[actualPuzzle.length][actualPuzzle[0].length];
    int[][] colNums = new int[actualPuzzle.length][actualPuzzle[0].length];
    for (int row = actualPuzzle.length-1; row >= 0; row--){
      int[] numbers = new int[getGridMax()];
      int currentValue = 0;
      int currentIndex = 0;
      
      for (int col = actualPuzzle[0].length-1; col >= 0; col--){
        if (actualPuzzle[row][col] == 2){
          currentValue++;
        } else {
          if (currentValue > 0){
            numbers[numbers.length-1-currentIndex] = currentValue;
            currentIndex++;
            currentValue = 0;
          }
        }

        if (currentValue > 0){
          numbers[numbers.length-1-currentIndex] = currentValue;
        }
      }
      rowNums[row] = numbers;
    }

    // Column Number Assignment
    for (int col = actualPuzzle[0].length-1; col >= 0; col--){
      int[] numbers = new int[getGridMax()];
      int currentValue = 0;
      int currentIndex = 0;
      
      for (int row = actualPuzzle.length-1; row >= 0; row--){
        if (actualPuzzle[row][col] == 2){
          currentValue++;
        } else {
          if (currentValue > 0){
            numbers[numbers.length-1-currentIndex] = currentValue;
            currentIndex++;
            currentValue = 0;
          }
        }

        if (currentValue > 0){
          numbers[numbers.length-1-currentIndex] = currentValue;
        }
      }
      colNums[col] = numbers;
      
    }

    rowNumbers = rowNums;
    columnNumbers = colNums;

    // System.out.println("\nRows:" + int2DToString(rowNums));
    // System.out.println("\nColumns:" + int2DToString(colNums));
  }

  // Returns the maximum number of numbers 
  // the sides of the grid can show
  private int getGridMax(){
    int max = actualPuzzle.length;
    int count = 0;
    boolean lastChecked = true;
    
    for (int i = 0; i < max; i++){
      if (lastChecked){
        count++;
        lastChecked = false;
      } else {
        lastChecked = true;
      }
    }
    return count;
  }

  // Returns a 2D array of integers as a String
  public static String int2DToString(int[][] list){
    String result = "";
    for (int row = 0; row < list.length; row++){
      result += "\n";
      for (int col = 0; col < list[0].length; col++){
        result += list[row][col] + " ";
      }
    }

    return result;
  }

  // Returns a hint version of this
  public NonogramHint toHint(){
    int[][] puz = getActualPuzzle();
    String name = getPuzzleName();

    NonogramHint temp = new NonogramHint(puz, name);
    temp.setPoints(this.getPoints());
    return temp;
  }

  // Returns a password version of this
  public NonogramPassword toPassword(){
    int[][] puz = getActualPuzzle();
    String name = getPuzzleName();

    NonogramPassword temp = new NonogramPassword(puz, name);
    return temp;
  }

  // Assigns a new value to points
  public void setPoints(int p){
    points = p;
  }

  // Returns the value of points
  public int getPoints(){
    return points;
  }

  // Returns the actual puzzle
  public int[][] getActualPuzzle(){
    return actualPuzzle;
  }

    // Returns the player's puzzle
  public int[][] getPlayerPuzzle(){
    return playerPuzzle;
  }

  // Returns the puzzle's name
  public String getPuzzleName(){
    return puzzleName;
  }
}
