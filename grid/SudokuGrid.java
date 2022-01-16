package grid;

/**
 * @author Jeffrey Chan & Minyi Li, RMIT 2020
 */
import java.io.*;


/**
 * Abstract class representing the general interface for a Sudoku grid.
 * Both standard and Killer Sudoku extend from this abstract class.
 */
public abstract class SudokuGrid
{
	
	 protected int size;
	 protected int[][] grid;
     private int empty = 0;
     protected int[] value;
     protected int valueID;
     protected int[][] cageRows;
     protected int[][] cageCols;
     protected int[] sum;
		 
     SudokuGrid(){
    	 
     }
	 SudokuGrid(int size, int[][]grid, int[] value, int valueID){
			 this.size = size;
			 this.grid = grid;
			 this.value = value;
			 this.valueID = valueID;
			 
		 }
	 
	 SudokuGrid(int size, int[][]grid, int[] value, int valueID, int[][]Rows, int[][]Cols, int[]sum){
		 this.size = size;
		 this.grid = grid;
		 this.value = value;
		 this.valueID = valueID;
		 this.cageRows = Rows;
		 this.cageCols = Cols;
		 this.sum = sum;
		 
	 }

    /**
     * Load the specified file and construct an initial grid from the contents
     * of the file.  See assignment specifications and sampleGames to see
     * more details about the format of the input files.
     *
     * @param filename Filename of the file containing the intial configuration
     *                  of the grid we will solve.
     *
     * @throws FileNotFoundException If filename is not found.
     * @throws IOException If there are some IO exceptions when openning or closing
     *                  the files.
     */
    public abstract void initGrid(String filename)
        throws FileNotFoundException, IOException;


    /**
     * Write out the current values in the grid to file.  This must be implemented
     * in order for your assignment to be evaluated by our testing.
     *
     * @param filename Name of file to write output to.
     *
     * @throws FileNotFoundException If filename is not found.
     * @throws IOException If there are some IO exceptions when openning or closing
     *                  the files.
     */
    public abstract void outputGrid(String filename)
        throws FileNotFoundException, IOException;


    /**
     * Converts grid to a String representation.  Useful for displaying to
     * output streams.
     *
     * @return String representation of the grid.
     */
    public abstract String toString();


    /**
     * Checks and validates whether the current grid satisfies the constraints
     * of the game in question (either standard or Killer Sudoku).  Override to
     * implement game specific checking.
     *
     * @return True if grid satisfies all constraints of the game in question.
     */
    public abstract boolean validate();
    

    public int getSize() {
    	return size;
    }
    
	public void setSize(int size) {
		this.size = size;
	}

	public int[][] getGrid() {
		return grid;
	}

	public void setGrid(int[][] grid) {
		this.grid = grid;
	}

	public int getEmpty() {
		return empty;
	}

	public void setEmpty(int empty) {
		this.empty = empty;
	}

	public int[] getValue() {
		return value;
	}

	public void setValue(int[] value) {
		this.value = value;
	}
	public int getValueID(int valueID) {
		
		for(int i = 0;i<value.length;i++){
			if(value[i]==valueID){
				valueID = i;
				return valueID;
			}
		}
		return valueID;

	}
	public void setValueID(int valueID) {
		this.valueID = valueID;
	}
	public int[][] getCageRows() {
		return cageRows;
	}
	public void setCageRows(int[][] cageRows) {
		this.cageRows = cageRows;
	}
	public int[][] getCageCols() {
		return cageCols;
	}
	public void setCageCols(int[][] cageCols) {
		this.cageCols = cageCols;
	}
	public int[] getSum() {
		return sum;
	}
	public void setSum(int[] sum) {
		this.sum = sum;
	}
	
} // end of abstract class SudokuGrid
