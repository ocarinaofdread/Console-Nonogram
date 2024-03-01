import java.util.*;

public class NonogramHint extends NonogramNormal {
  /*
  * Instance Variables
  */

  /*
  * Constructors
  */
  public NonogramHint(int[][] puzzle, String name){
    super(puzzle, name);
  }
  
  /*
  * Methods
  */
  // Creates the user's start puzzle + a hint
  public int[][] initiatePuzzle(){
    int[][] actualPuzzle = getActualPuzzle();
    
    int rowRan = (int) (Math.random()*actualPuzzle.length);
    int colRan = (int) (Math.random()*actualPuzzle[0].length);

    int[][] temp = new int[actualPuzzle.length][actualPuzzle[0].length];
    for (int i = 0; i < actualPuzzle.length; i++){
      
      if (actualPuzzle[rowRan][i] == 0){
        temp[rowRan][i] = 1;
      } else {
        temp[rowRan][i] = actualPuzzle[rowRan][i];
      }
      
      if (actualPuzzle[i][colRan] == 0){
        temp[i][colRan] = 1;
      } else {
        temp[i][colRan] = actualPuzzle[i][colRan];
      }
      
    }

    return temp;
  }
}
