package solver;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;




public class DLX {

	  private ColumnNode header;
	  private List<DancingNode> answer;
	  public List<DancingNode> result;

	  //Initialize the header
	  public DLX(int[][] cover) {
	    header = createDLXList(cover);
	  }

	  private ColumnNode createDLXList(int[][] grid) {
	    final int nbColumns = grid[0].length;
	    ColumnNode headerNode = new ColumnNode("header");
	    List<ColumnNode> columnNodes = new ArrayList<>();

	    for (int i = 0; i < nbColumns; i++) {
	      ColumnNode n = new ColumnNode(i + "");
	      columnNodes.add(n);
	      headerNode = (ColumnNode) headerNode.linkRight(n);
	    }

	     headerNode = headerNode.right.column;

	    for (int[] aGrid : grid) {
	      DancingNode prev = null;

	      for (int j = 0; j < nbColumns; j++) {
	        if (aGrid[j] == 1) {
	          ColumnNode col = columnNodes.get(j);
	          DancingNode newNode = new DancingNode(col);

	          if (prev == null)
	            prev = newNode;

	          col.top.linkDown(newNode);
	          prev = prev.linkRight(newNode);
	          col.size++;
	        }
	      }
	    }

	    headerNode.size = nbColumns;

	    return headerNode;
	  }
    
	  //6 Steps of algorithm X
	  private boolean backTrack(int k) {
		  
		  if (header.right == header) {
		   //End
		   // Result is copied in a result list
		   result = new LinkedList<>(answer);
		  } else {
		    //choose column c
		    ColumnNode c = selectColumnNodeHeuristic();
		    c.cover();

		    for (DancingNode r = c.bottom; r != c; r = r.bottom) {
		      //add r line to partial solution
		      answer.add(r);

		      //cover columns
		      for (DancingNode j = r.right; j != r; j = j.right) {
		        j.column.cover();
		      }

		      //recursive
		      backTrack(k + 1);

		      //go back
		      r = answer.remove(answer.size() - 1);
		      c = r.column;

		      // uncover columns
		      for (DancingNode j = r.left; j != r; j = j.left) {
		        j.column.uncover();
		      }
		    }

		    c.uncover();
		  }
		  
		  return true;
		}


	private ColumnNode selectColumnNodeHeuristic() {
		// TODO Auto-generated method stub
	     int min = Integer.MAX_VALUE;
	     ColumnNode ret = null;
	     for(ColumnNode c = (ColumnNode) header.right; c != header; c = (ColumnNode) c.right){
	         if (c.size < min){
	             min = c.size;
	             ret = c;
	         }
	     }
	     return ret;
	}

	public boolean solve() {
		// TODO Auto-generated method stub
		answer = new LinkedList<DancingNode>();
		backTrack(0);
		return backTrack(0);
		
	}

}