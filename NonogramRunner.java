import java.util.*;

public class NonogramRunner {
  public static void main(String[] args) {

    // Precondition: Must be a 1:1 ratio in size
    
    // Comment out whichever you don't use, both work!

    // Easier puzzle: "Person"
    int[][] puzzle = {{0, 0, 2, 0, 0},
                      {0, 2, 2, 2, 0},
                      {2, 0, 2, 0, 2},
                      {0, 2, 2, 2, 0},
                      {0, 2, 0, 2, 0}}; 
    
    // Harder puzzle: "Steve"
    // int[][] puzzle = {{2, 2, 2, 2, 2, 2, 2, 2},
    //                   {2, 2, 2, 2, 2, 2, 2, 2},
    //                   {2, 0, 0, 0, 0, 0, 0, 2},
    //                   {0, 0, 0, 0, 0, 0, 0, 0},
    //                   {0, 2, 2, 0, 0, 2, 2, 0},
    //                   {0, 0, 0, 2, 2, 0, 0, 0},
    //                   {0, 0, 2, 0, 0, 2, 0, 0},
    //                   {0, 0, 2, 2, 2, 2, 0, 0}};
    NonogramNormal nonogram = new NonogramNormal(puzzle, "Person");
    Board system = new Board(nonogram);

    system.mainMenu();
  }
}
