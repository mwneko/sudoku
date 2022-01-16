/*
 * @author Jeffrey Chan & Minyi Li, RMIT 2020
 */

package solver;

import java.util.Arrays;
import java.util.List;


import grid.SudokuGrid;



/**
 * Dancing links solver for standard Sudoku.
 */
public class DancingLinksSolver extends StdSudokuSolver
{

    public DancingLinksSolver() {} // end of DancingLinksSolver()
    
    // Index in the cover matrix
    private int indexInCoverMatrix(int row, int column, int num, int size) {
      return (row - 1) * size * size + (column - 1) * size + (num - 1);
    }
    
    // Converting Sudoku grid as a cover matrix
    private int[][] convertInCoverMatrix(int[][] grid, int size ) {
    	
      AlgorXSolver x = new AlgorXSolver();
      
      int[][] coverMatrix =  x.createCoverMatrix(size);

      // Taking into account the values already entered in Sudoku's grid instance
      for (int row = 1; row <= size; row++) {
        for (int column = 1; column <= size; column++) {
          int n = grid[row - 1][column - 1];
          if (n != 0) {
            for (int num = 1; num <= size; num++) {
              if (num != n) {
                Arrays.fill(coverMatrix[indexInCoverMatrix(row, column, num, size)], 0);
               
              }
            }
          }
        }

      }
      
	return coverMatrix;
	
    }

    private int[][] convertDLXListToGrid(List<DancingNode> answer, int SIZE) {
   
    	  int[][] result = new int[SIZE][SIZE];

    	  for (DancingNode n : answer) {
    	    DancingNode rcNode = n;
    	    int min = Integer.parseInt(rcNode.column.name);

    	    for (DancingNode tmp = n.right; tmp != n; tmp = tmp.right) {
    	      int val = Integer.parseInt(tmp.column.name);

    	      if (val < min) {
    	        min = val;
    	        rcNode = tmp;
    	      }
    	    }

    	    //get line and column
    	    int ans1 = Integer.parseInt(rcNode.column.name);
    	    int ans2 = Integer.parseInt(rcNode.right.column.name);
    	    int r = ans1 / SIZE;
    	    int c = ans1 % SIZE;
    	    // and the affected value
    	    int num = (ans2 % SIZE) + 1;
    	    //affect that on the result grid
    	    result[r][c] = num;
    	  }
    	  return result;
    	}

	@Override
    public boolean solve(SudokuGrid grid) {
        // TODO: your implementation of the backtracking solver for standard Sudoku.
    	int[][] sudoku = grid.getGrid(); 
    	int size =grid.getSize();
    	int[][] coverMatrix = convertInCoverMatrix(sudoku, size);
		
		DLX dlx = new DLX(coverMatrix);
	    dlx.solve();
	    
	    sudoku = convertDLXListToGrid(dlx.result, size);
	    grid.setGrid(sudoku);
    	
		return dlx.solve();

    } // end of solve()
	 

} // end of class DancingLinksSolver
