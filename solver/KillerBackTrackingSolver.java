/*
 * @author Jeffrey Chan & Minyi Li, RMIT 2020
 */

package solver;

import grid.SudokuGrid;


/**
 * Backtracking solver for Killer Sudoku.
 */
public class KillerBackTrackingSolver extends KillerSudokuSolver
{
    // TODO: Add attributes as needed.

    public KillerBackTrackingSolver() {
        // TODO: any initialisation you want to implement.
    } // end of KillerBackTrackingSolver()
    
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
   	
   	//check cages conform to the constraint
   	private boolean isCage(int[][]cageRows, int[][]cageCols, int[]sum, int[][]grid) {
   		
   		for(int i = 0; i < sum.length; i++) {
   			int count = 0;
   			for(int j = 0; j < cageRows[i].length; j++) {
   				if(grid[cageRows[i][j]][cageCols[i][j]] != 0) {
   					count += grid[cageRows[i][j]][cageCols[i][j]];
   				}else {
   					return true;
   				}
   			}
   			
   			if(count != sum[i]) {
   				return false;
   			}
   			
   		}
	    return true;
   		
   	}
   	
       private boolean isTrue(int row, int col, int number, int size, int[][]grid, int[][]cageRows, int[][]cageCols, int[]sum) {
   		// TODO Auto-generated method stub
       	return !isInRow(row, number, size, grid) 
       			&&  !isInCol(col, number, size, grid)  
       			&&  !isInBox(row, col, number, size, grid)
       			&&  isCage(cageRows, cageCols, sum, grid);

   	}

    //Same as the standard Sudoku, but with one more constraint
   	private boolean backTrack(int[][] sudoku, int empty, int size, int[][]cageRows, int[][]cageCols, int[]sum) {
   		for (int row = 0; row < size; row++) {
   	         for (int col = 0; col < size; col++) {
   	          //search an empty cell
   	          if (sudoku[row][col] == empty) {
   	            //try possible numbers 
   	            for (int number = 1; number <= size; number++) {
   	              if (isTrue(row, col, number, size, sudoku, cageRows, cageCols, sum)) {
   	                // number ok. it respects sudoku constraints
   	            	  sudoku[row][col] = number;

   	            	//start backtracking recursively
   	                if (backTrack(sudoku, empty, size, cageRows, cageCols, sum)) { 
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
        // TODO: your implementation of a backtracking solver for Killer Sudoku.
    	int[][] sudoku = grid.getGrid(); 
    	int[][] cageRows = grid.getCageRows();
    	int[][] cageCols = grid.getCageCols();
    	int[] sum = grid.getSum();
        // placeholder
    	return backTrack(sudoku, grid.getEmpty(), grid.getSize(), cageRows, cageCols, sum);
    } // end of solve()

} // end of class KillerBackTrackingSolver()
