import java.util.*;

public class Board {
  /*
  * Instance Variables
  */
  private NonogramNormal game;
  private Scanner input;
  
  /*
  * Constructors
  */
  public Board(NonogramNormal game){
    this.game = game;
    input = new Scanner(System.in);
  }

  /*
  * Methods
  */
  // Starts the main menu
  public void mainMenu(){
    System.out.println("Welcome to Console Nonogram!\n" +
                       "[1] Begin\n" +
                       "[2] View Instructions\n" +
                       "[3] Quit");
    int answer = input.nextInt();
    if (answer == 1){
      beginMenu();
    }
    else if (answer == 2){
      viewInstructions();
    }
    else {
      input.close();
    }
  }

  // Asks whether the user wants to view simple
  // or complex instructions
  private void viewInstructions(){
    System.out.println("Which instructions would you like to view?\n" +
                       "[1] Veteran Instructions\n" +
                       "[2] Newbie Instructions");
    int answer = input.nextInt();
    if (answer == 1){
      veteranInstructions();
    } else if (answer == 2) {
      newbieInstructions();
    } else {
      System.out.println("Seems you typed an invalid number. Please try again.");
      viewInstructions();
      return;
    }
    
    mainMenu();
  }

  // Gives complex instructions + simple instructions
  private void newbieInstructions(){
    System.out.println("This is a game of Nonogram puzzles.\n" +
                       "It's a picture logic puzzle where you have to fill\n" +
                       "in boxes according to the numbers on the top and left.\n" +
                       "In a normal game, you'd fill in squares according to\n" +
                       "the numbers, but here, \"G\" represents filled in.\n" +
                       "Your goal is to fill in boxes so that each row and\n" +
                       "column is filled in with the exact # of boxes specified\n" +
                       "by their adjacent numbers. Example: ");
    System.out.println("       1  \n" +
                       "     1 1 1\n" +
                       "  1 | |G| |\n" +
                       "1 1 |G| |G|\n" +
                       "  1 | |G| |\n");
    System.out.println("Each row or column is filled with the exact amount\n" +
                       "of squares as specified. Whenever there's a space,\n" +
                       "it means there's an unspecified length of space in\n" +
                       "between the numbers separated. You have to find which\n" +
                       "squares are filled, any empty squares, etc. via logic.\n");
    veteranInstructions();
  }

  // Gives simple instructions
  private void veteranInstructions(){
    System.out.println("Normally, one would click or tap squares to guess,\n" +
                       "switching between clicking to x-out or fill in.\n" +
                       "This isn't normal, though, as this is Console Nonogram.\n" +
                       "When guessing, you need to format your guess as follows:\n" +
                       "a1x --- a: Row Letter (Top to Bottom)\n" +
                       "        1: Column Number (Left to Right)\n" +
                       "        x: Either \"x\" to x-out or \"o\" to Fill In\n" +
                       "Example:\n" +
                       "   1 2 3 \n" +
                       "a | | | |\n" +
                       "b | | |G|\n" +
                       "c | | | |\n" +
                       "(G would be placed at b3)");
    System.out.println("You can guess multiple guesses, just separate them with\n" +
                       "a comma (no spaces). Example:" +
                       "a1x,b2o,c3o,d4x\n" +
                       "NOTE: If you start with hints, a random column and row\n" +
                       "will be given to you.\n");
  }

  // Asks how to begin
  private void beginMenu(){
    System.out.println("How would you like to start?\n" +
                       "[1] Normal\n" +
                       "[2] w/ Hints\n" +
                       "[3] w/ Password\n" +
                       "[4] Return");
    int answer = input.nextInt();

    if (answer > 0 && answer < 4){
      begin(answer);
    }
    else {
      mainMenu();
    }
  }

  // Begins the game
  public void begin(int type){
    if (type == 2){
      game = game.toHint();
    } else if (type == 3){
      game = game.toPassword();
    }

    input.nextLine();
    System.out.println(game.puzzleToString());
    prompt();
  }

  // Prompts the user for a response
  public void prompt(){
    if (checkCompletion()){
      System.out.println();
      System.out.println("Congratulations! You completed the puzzle!");
      System.out.println("Puzzle Name: " + game.getPuzzleName());
      System.out.println(game.actualPuzzleToString());
      System.out.println("\nFinal Points: " + game.getPoints());
      return;
    }
    System.out.println("Please input your guess (Ex. a1o or a1x,b2o)\n" +
                       "or type \"pw\" to receive a password and stop.\n");
    String ans = input.nextLine();

    if (ans.indexOf(" ") != -1){
      System.out.println("A space was detected in your response. Please try again.");
      prompt();
    }
    else {
      if (ans.equalsIgnoreCase("pw")){
        // Create Password
        game.createPW();
        input.close();
      } else if (ans.length() <= 3){
        guess(ans);
      } else {
        guess(toList(ans));
      }
    }
    
  }

  // Converts a String of guesses into an array
  public ArrayList<String> toList(String s){
    if (s.indexOf(" ") != -1){
      System.out.println("A space was detected in your response.\n" +
                         "Please try again.");
      ArrayList<String> temp = new ArrayList<String>();
      temp.add(null);
      return temp;
    } 
    
    ArrayList<String> list = new ArrayList<String>();
    int comma = s.indexOf(",");
    String sub = s;

    while (comma != -1){
      String word = sub.substring(0,comma);
      if (word.length() != 3){
        System.out.println("It seems one of your guesses is less\n" +
                           "than 3 characters. Please try again.");
        ArrayList<String> temp = new ArrayList<String>();
        temp.add(null);
        return temp;
      }
      list.add(word);
      sub = sub.substring(comma+1);
      comma = sub.indexOf(",");
    }

    if (sub.length() != 3){
      System.out.println("It seems one of your guesses is less\n" +
                         "than 3 characters. Please try again.");
      ArrayList<String> temp = new ArrayList<String>();
      temp.add(null);
      return temp;
    } 
    else {
      list.add(sub);
    }

    return list;
  }

  // Takes one guess and inputs it
  public void guess(String guess){
    String response = game.inputGuessFeedback(guess);
    if (response != null){
      System.out.println(response);
    }
    System.out.println(game.puzzleToString());
    System.out.println("Points: " + game.getPoints());
    prompt();
  }

  // Takes multiple guesses and inputs them
  public void guess(ArrayList<String> guesses){
    if (guesses.get(0) == null){
      prompt();
      return;
    }
    
    for (int i = 1; i < guesses.size()+1; i++){
      String response = game.inputGuessFeedback(guesses.get(i-1));
      if (response != null){
        System.out.println(i + ": " + response);
      }
    }
    System.out.println(game.puzzleToString());
    System.out.println("Points: " + game.getPoints());
    prompt();
  }

  // Checks if the player has completed the puzzle
  public boolean checkCompletion(){
    int[][] play = game.getPlayerPuzzle();
    int[][] act = game.getActualPuzzle();
    boolean completed = true;

    for (int row = 0; row < act.length; row++){
      for (int col = 0; col < act[0].length; col++){
        if (act[row][col] == 2){
          if (play[row][col] != 2){
            completed = false;
          }
        }
      }
    }

    return completed;
  }

  // Converts a String ArrayList to an array
  public static ArrayList<String> arrayListToArray(String[] list){
    ArrayList<String> result = new ArrayList<String>();
    for (int i = 0; i < list.length; i++){
      result.add(list[i]);
    }

    return result;
  }

}
