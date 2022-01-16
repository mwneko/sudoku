/**
 * @author Jeffrey Chan & Minyi Li, RMIT 2020
 */
package grid;

import java.io.*;
import java.util.Arrays;


/**
 * Class implementing the grid for standard Sudoku.
 * Extends SudokuGrid (hence implements all abstract methods in that abstract
 * class).
 * You will need to complete the implementation for this for task A and
 * subsequently use it to complete the other classes.
 * See the comments in SudokuGrid to understand what each overriden method is
 * aiming to do (and hence what you should aim for in your implementation).
 */
public class StdSudokuGrid extends SudokuGrid
{
    // TODO: Add your own attributes
	 private String str;
     private String[] list;


    public StdSudokuGrid(int size, int[][]grid, int[] value, int valueID) {
        super(size, grid, value, valueID);
        // TODO: any necessary initialisation at the constructor
    } // end of StdSudokuGrid()

    /* ********************************************************* */


	public StdSudokuGrid() {
		// TODO Auto-generated constructor stub
		super();
	}

	@Override
    public void initGrid(String filename)
        throws FileNotFoundException, IOException
    {
        // TODO
    	BufferedReader in = new BufferedReader(new FileReader(filename));
    	size =  Integer.parseInt(in.readLine());
    	
    	setGrid(new int[size][size]);
    	
    	//To form a zero two dimensional array
    	for (int i = 0; i < size; i++) {
    		for (int j = 0; j < size; j++) {
    			getGrid()[i][j] = getEmpty();
    			}
    		}
    	
    	//Fill in the Numbers you can fill in
    	str = in.readLine();
    	String[] symbol = new String[size];
    	symbol = str.split(" ");
    	value = new int[size];
    	for(int i = 0 ; i < size; i++) {
    		value[i] = Integer.parseInt(symbol[i]);
    	}
    	
    	//Replaces the values in the file with a two-dimensional array
    	while ((str = in.readLine()) != null) {
          	list = str.split(" ");
          	
          	if (list.length > 1) {
          	if(list[0].split(",").length > 1) {
          		String value = list[1];
          		int row = Integer.parseInt(list[0].split(",")[0]);
          		int col = Integer.parseInt(list[0].split(",")[1]);
          		int values = Integer.parseInt(value);
          		getGrid()[row][col] = values;
          	}
          }
    	}

    } // end of initBoard()


    @Override
    public void outputGrid(String filename)
        throws FileNotFoundException, IOException
    {
        // TODO
    	FileWriter writer = new FileWriter(new File(filename));
    	writer.write(this.toString());
    	writer.close();

    	
    } // end of outputBoard()


    @Override
    public String toString() {
        // TODO
		int count=0;
	 	String solution = "";
    	 for (int i = 0; i < size; i++) {
    		 for (int j = 0; j < size; j++) {
    			 count++;
    			 
    			 if(count < size)
    			 solution = solution + getGrid()[i][j] + ",";
    			 if(count == size) {
    			 solution = solution + getGrid()[i][j] + "\n";
    			 count = 0;
    			 }
    			}
    		 }
        // placeholder
        return String.valueOf(solution);
    } // end of toString()


    @Override
    public boolean validate() {
        // TODO
    	
    	//Determine if a valid Sudoku can be generated
    	double n =  Math.sqrt(size);
    	if(n*n != size) {
    		System.out.println("The size is invalid, please input another file");
    		return false;
    	}
    	
        for(int i = 0; i < getGrid().length; i++){
            if (getGrid()[i].length != getGrid().length)
                return false;
            for(int j = 0; j < getGrid()[i].length; j++){
                if (!(i >= 0 && i <= getGrid().length))
                    return false; // 0 means not filled in
            }
        }
        int N = getGrid().length;

        boolean[] b = new boolean[N+1];

        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                if (getGrid()[i][j] == 0)
                    continue;
                if (b[getGrid()[i][j]])
                    return false;
                b[getGrid()[i][j]] = true;
            }
            Arrays.fill(b, false);
        }

        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                if (getGrid()[j][i] == 0)
                    continue;
                if (b[getGrid()[j][i]])
                    return false;
                b[getGrid()[j][i]] = true;
            }
            Arrays.fill(b, false);
        }
        
        int side = (int)Math.sqrt(N);
        
        for(int i = 0; i < N; i += side){
            for(int j = 0; j < N; j += side){
                for(int d1 = 0; d1 < side; d1++){
                    for(int d2 = 0; d2 < side; d2++){
                        if (getGrid()[i + d1][j + d2] == 0)
                            continue;
                        if (b[getGrid()[i + d1][j + d2]])
                            return false;
                        b[getGrid()[i + d1][j + d2]] = true;
                    }
                }
                Arrays.fill(b, false);
            }
        }
        return true;
       // placeholder
    } // end of validate()
    

    
} // end of class StdSudokuGrid
