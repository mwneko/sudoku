/*
 * @author Jeffrey Chan & Minyi Li, RMIT 2020
 */
package solver;


import grid.SudokuGrid;


/**
 * Algorithm X solver for standard Sudoku.
 */

public class AlgorXSolver extends StdSudokuSolver
{

	// 4 constraints : cell, line, column, boxes
	private static final int CONSTRAINTS = 4;
	private int[] rowStatus;
	private int[] colStatus;
	private int[][] coverMatrix;

    public AlgorXSolver() {} // end of AlgorXSolver()

    // Index in the cover matrix
    private int indexInCoverMatrix(int row, int column, int num, int size) {
      return (row - 1) * size * size + (column - 1) * size + (num - 1);
    }

    // Building of an empty cover matrix
    protected int[][] createCoverMatrix(int size) {
      int[][] coverMatrix = new int[size * size * size][size * size * CONSTRAINTS];

      int header = 0;
      header = createCellConstraints(coverMatrix, header, size);
      header = createRowConstraints(coverMatrix, header, size);
      header = createColumnConstraints(coverMatrix, header, size);
      createBoxConstraints(coverMatrix, header, size);

      return coverMatrix;
    }

    //Four constraints
    
    /*conforming to Sudoku rule are represented by four methods
     * Row unique, column unique, box unique
     * Each cell must be filled with Numbers
     */

    //header is a variable for dancingLinks
    protected int createCellConstraints(int[][] matrix, int header, int size) {
      for (int row = 1; row <= size; row++) {
        for (int column = 1; column <= size; column++, header++) {
          for (int n = 1; n <= size; n++) {
            int index = indexInCoverMatrix(row, column, n, size);
            matrix[index][header] = 1;
          }
        }
      }

      return header;
    }
    
    protected int createRowConstraints(int[][] matrix, int header, int size) {
    	
        for (int row = 1; row <= size; row++) {
          for (int n = 1; n <= size; n++, header++) {
            for (int column = 1; column <= size; column++) {
              int index = indexInCoverMatrix(row, column, n, size);
                matrix[index][header] = 1;
            }
          }
        }
    	
        return header;
      }
    
    protected int createColumnConstraints(int[][] matrix, int header, int size) {
    	
      for (int column = 1; column <= size; column++) {
        for (int n = 1; n <= size; n++, header++) {
          for (int row = 1; row <= size; row++) {
            int index = indexInCoverMatrix(row, column, n, size);
            matrix[index][header] = 1;
          }
        }
      }
  	
      return header;
    }
    
    protected int createBoxConstraints(int[][] matrix, int header, int size) {
        
        int BOX_SIZE =  (int) Math.sqrt(size);
      	
        for (int row = 1; row <= size; row += BOX_SIZE) {
          for (int column = 1; column <= size; column += BOX_SIZE) {
            for (int n = 1; n <= size; n++, header++) {
              for (int rowDelta = 0; rowDelta < BOX_SIZE; rowDelta++) {
                for (int columnDelta = 0; columnDelta < BOX_SIZE; columnDelta++) {
                  int index = indexInCoverMatrix(row + rowDelta, column + columnDelta, n, size);
                  matrix[index][header] = 1;
                }
              }
            }
          }
        }
    	
        return header;
      }

    
    // Converting Sudoku grid as a cover matrix
    private void convertInCoverMatrix(SudokuGrid grid) {
    	
      int size = grid.getSize();	
      coverMatrix = createCoverMatrix(size);
      rowStatus = new int[size*size*size];//size*size*size
      colStatus = new int[size*size * 4];//size*size*size
      
  	  int[][] sudoku = grid.getGrid(); 
  	  for(int i = 0; i < size; i++) {
		for(int j = 0; j < size; j++) {
			if(sudoku[i][j] > 0) {
				
				int value = sudoku[i][j];
				int valueID = grid.getValueID(value);			
				int rowID = size * size * i + size * j + valueID;
				
				select(rowID);
			}
		}
	}
	  
    } 
    
    //Select the remaining rows based on the existing Grid
    private boolean select(int row) {
    	
    	//Determines whether a row can be selected, if not,return false
    	for(int i = 0; i < coverMatrix[row].length; i++) {
    		if(coverMatrix[row][i] == 1 && colStatus[i] == 1) {
    			return false;
    		}
    		
    	}
    	
    	//select a row
    	//change status value to "1"
    	rowStatus[row] = 1;
    	
    	for(int i = 0; i < coverMatrix[row].length; i++) {
    		
    		//change the colStatus base the whole rowValue
    		if(coverMatrix[row][i] == 1) {
    			colStatus[i] = 1;

    			//Eliminate the possibilities that you don't need
    			for(int j = 0; j < coverMatrix.length; j++) {
    				if(j == row) {
    					continue;
    				}
    				//Other possibility Settings for fixed values to "-1"(Do not choose)
    				if(coverMatrix[j][i] == 1) { 
    					rowStatus[j] = -1;
    				}
    				
    			}
    		}
    	}
    	
		return true;
    	
    }
    
    /*
     * When a previously selected row does not run a solution
     * cancel it
     */
    private void unselect(int row) {
    	
    	//Reset the state for backtrace
    	rowStatus[row] = 0;
    	
    	//
    	for(int i = 0; i < coverMatrix[row].length; i++) {
    		if(coverMatrix[row][i] == 1) {
    			colStatus[i] = 0;
    			
    			for(int j = 0; j < coverMatrix.length; j++) {
    				if(j == row) {
    					continue;
    				}
    				if(coverMatrix[j][i] == 1) {
    					rowStatus[j] = 0;
    				}
    			}
    			
    		}
    	}
    	
    }


    //Six steps of algorithm X
    private boolean backTrack(int colID) {
    	
    	
    	if(colID >= colStatus.length) {
    		return true;
    	}
    	
    	if(colStatus[colID] == 1) {
    		return backTrack(colID + 1);
    	}
    	
    	for(int i = 0; i < coverMatrix.length; i++) {
    		if(rowStatus[i] == 0 && coverMatrix[i][colID] == 1) {
    			if(select(i)) {
    				if(backTrack(colID + 1)) {
    				return true;
    				}else {
    					unselect(i);
    				}
    			}
    		}
    	}
		return false;
    	
    }
    
    //Convert the selected row to Sudoku
    private void upadteGrid(SudokuGrid grid) {
    	
    	int[][] sudoku = grid.getGrid();
    	int size = grid.getSize();
    	
    	for(int i = 0; i < size; i++) {
    		for(int j = 0; j < size; j++) {
    			for(int k = 0; k < size; k++) {
    				
    				int rowID = size * size * i + size * j + k;
    				if(rowStatus[rowID] == 1) {
    					sudoku[i][j] = grid.getValue()[k];
    				}
    				
    			}
    		}
    	}
    	
    	
    }

    @Override
    public boolean solve(SudokuGrid grid) {
       
    	convertInCoverMatrix(grid);
    	backTrack(0);
    	upadteGrid(grid);
        // placeholder
		return backTrack(0);
    } // end of solve()

} // end of class AlgorXSolver
