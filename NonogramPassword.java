import java.util.*;

public class NonogramPassword extends NonogramNormal {
  /*
  * Instance Variables
  */

  /*
  * Constructors
  */
  public NonogramPassword(int[][] puzzle, String name){
    super(puzzle, name);
  }
  
  /*
  * Methods
  */
  // Creates the user's start puzzle
  public int[][] initiatePuzzle(){
    int[][] play = createPuzzle();
    if (play == null){
      return initiatePuzzle();
    } else {
      return play;
    }
  }

  // Asks the user for a password
  public int[][] createPuzzle(){
    Scanner input = new Scanner(System.in);
    System.out.println("Please enter a given password:");
    String pw = input.nextLine();
    input.close();
    return decode(pw);
  }
  
  // Encodes a puzzle into a password
  public static String encode(int[][] puzzle, int points){
    String result = "";
    
    for (int i = 0; i < puzzle.length; i++){
      String row = "";
      for (int j = 0; j < puzzle[0].length; j++){
        int p = puzzle[i][j];
        if (p == 1){
          row += 0;
        } else if (p == 2){
          row += 1;
        } else {
          row += 0;
        }
      }
      int bin = Integer.parseInt(row, 2);
      result += bin + "x";
    }
    
    return result + points;
  }

  // Decodes a password into a puzzle
  public int[][] decode(String pw){
    try {
      int len = getActualPuzzle().length;
      int[][] puz = new int[len][len];
      int x = pw.indexOf("x");
      String sub = pw;
      ArrayList<Integer> rows = new ArrayList<Integer>();
  
      // Converts String to int ArrayList
      while (x != -1){
        String part = sub.substring(0,x);
        Integer num = Integer.parseInt(part);
        rows.add(num);
        sub = sub.substring(x+1);
        x = sub.indexOf("x");
      }
      setPoints(Integer.parseInt(sub));
      System.out.println("Points: " + getPoints());
      ArrayList<String> sRows = new ArrayList<String>();
  
      // Converts ints to binary Strings
      for (int i = 0; i < rows.size(); i++){
        int curr = rows.get(i);
        String bin = Integer.toBinaryString(curr);
        if (bin.length() != len){
          int diff = len - bin.length();
          for (int j = 0; j < diff; j++){
            bin = "0" + bin;
          }
        }
        sRows.add(bin);
      }
      if (sRows.size() != getActualPuzzle().length){
        throw new Exception();
      }
  
      // Converts binary Strings to puzzle
      for (int i = 0; i < sRows.size(); i++){
        String bin = sRows.get(i);
        String[] list = stringToList(bin);
  
        puz[i] = new int[list.length];
        for (int j = 0; j < list.length; j++){
          int rowIndex = Integer.parseInt(list[j]);
          if (rowIndex == 1){
            rowIndex = 2;
          }
  
          puz[i][j] = rowIndex;
        }
      }

      return puz;
    } catch (Exception e){
      System.out.println("It seems you password isn't functional. Please\n" +
                         "check that your password is typed exactly as given.");
      return null;
    }
  }

  // Converts a String into an array of its characters
  public static String[] stringToList(String s){
    String[] temp = new String[s.length()];
    String sub = s;

    for (int i = 0; i < s.length(); i++){
      temp[i] = sub.substring(0,1);
      sub = sub.substring(1);
    }
    
    return temp;
  }

}
