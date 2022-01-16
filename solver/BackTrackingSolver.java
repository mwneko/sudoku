/*
 * @author Jeffrey Chan & Minyi Li, RMIT 2020
 */

package solver;

import grid.SudokuGrid;


/**
 * Backtracking solver for standard Sudoku.
 */
public class BackTrackingSolver extends StdSudokuSolver
{

    public BackTrackingSolver() {} // end of BackTrackingSolver()
    
    //check Small squares conform to the constraint
	private boolean isInBox(int row, int col, int number, int size, int[][] grid) {
		// TODO Auto-generated method stub
	    int n =  (int) Math.sqrt(size);
		int r = row - row % n;
		int c = col - col % n;
		
		for (int i = r; i < r + n; i++)
			for (int j = c; j < c + n; j++)
				if (grid[i][j] == number)
					return true;
		
		return false;
	}
    //check columns conform to the constraint
	private boolean isInCol(int col, int number, int size, int[][] grid) {
		for (int i = 0; i < size; i++)
			if (grid[i][col] == number)
				return true;
		
		return false;
	}
    //check rows conform to the constraint
	private boolean isInRow(int row, int number, int size, int[][]grid) {
		// TODO Auto-generated method stub
		for (int i = 0; i < size; i++)
			if (grid[row][i] == number)
				return true;
		
		return false;
	}
	
    private boolean isTrue(int row, int col, int number, int size, int[][]grid) {
		// TODO Auto-generated method stub
    	return !isInRow(row, number, size, grid)  &&  !isInCol(col, number, size, grid)  &&  !isInBox(row, col, number, size, grid);

	}

	private boolean backTrack(int[][] sudoku, int empty, int size) {
		for (int row = 0; row < size; row++) {
	         for (int col = 0; col < size; col++) {
	          //search an empty cell
	          if (sudoku[row][col] == empty) {
	            //try possible numbers 
	            for (int number = 1; number <= size; number++) {
	              if (isTrue(row, col, number, size, sudoku)) {
	                // number ok. it respects sudoku constraints
	            	  sudoku[row][col] = number;

	                if (backTrack(sudoku, empty, size)) { // we start backtracking recursively
	                  return true;
	                } else { // if not a solution, we empty the cell and we continue
	                 sudoku[row][col] = empty;
	                }
	              }
	            }

	            return false; 
	           }
	          }
	         }

	       return true; 
	}



	@Override
    public boolean solve(SudokuGrid grid) {
    	
        // TODO: your implementation of the backtracking solver for standard Sudoku.
    	int[][] sudoku = grid.getGrid(); 
        // placeholder
       return backTrack(sudoku, grid.getEmpty(), grid.getSize());
    } // end of solve()

} // end of class BackTrackingSolver()
