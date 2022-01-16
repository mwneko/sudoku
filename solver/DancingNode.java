package solver;

public class DancingNode {
	
	  public DancingNode left, right, top, bottom;
	  public ColumnNode column;

	  public DancingNode() {
	    left = right = top = bottom = this;
	  }

	  public DancingNode(ColumnNode c) {
	    this();
	    column = c;
	  }

	  public DancingNode linkDown(DancingNode node) {
	    node.bottom = bottom;
	    node.bottom.top = node;
	    node.top = this;
	    bottom = node;
	    return node;
	  }

	  public DancingNode linkRight(DancingNode node) {
	    node.right = right;
	    node.right.left = node;
	    node.left = this;
	    right = node;
	    return node;
	  }

	  public void removeLeftRight() {
	    left.right = right;
	    right.left = left;
	  }

	  public void reinsertLeftRight() {
	    left.right = this;
	    right.left = this;
	  }

	  public void removeTopBottom() {
	    top.bottom = bottom;
	    bottom.top = top;
	  }

	  public void reinsertTopBottom() {
	    top.bottom = this;
	    bottom.top = this;
	  }
	}


class ColumnNode extends DancingNode {

	  public int size;
	  public String name;

	  public ColumnNode(String n) {
	    super();
	    size = 0;
	    name = n;
	    column = this;
	  }

	   public void cover() {
	     removeLeftRight();

	     for (DancingNode i = bottom; i != this; i = i.bottom) {
	       for (DancingNode j = i.right; j != i; j = j.right) {
	         j.removeTopBottom();
	         j.column.size--;
	       }
	     }
	  }

	  public void uncover() {
	    for (DancingNode i = top; i != this; i = i.top) {
	      for (DancingNode j = i.left; j != i; j = j.left) {
	        j.column.size++;
	        j.reinsertTopBottom();
	      }
	    }

	    reinsertLeftRight();
	  }
	}