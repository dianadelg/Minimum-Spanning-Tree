package apps;

import java.util.Iterator;
import java.util.NoSuchElementException;

import structures.Vertex;


public class PartialTreeList implements Iterable<PartialTree> {
    
	/**
	 * Inner class - to build the partial tree circular linked list 
	 * 
	 */
	public static class Node {
		/**
		 * Partial tree
		 */
		public PartialTree tree;
		
		/**
		 * Next node in linked list
		 */
		public Node next;
		
		/**
		 * Initializes this node by setting the tree part to the given tree,
		 * and setting next part to null
		 * 
		 * @param tree Partial tree
		 */
		public Node(PartialTree tree) {
			this.tree = tree;
			next = null;
		}
	}

	/**
	 * Pointer to last node of the circular linked list
	 */
	private Node rear;
	
	/**
	 * Number of nodes in the CLL
	 */
	private int size;
	
	/**
	 * Initializes this list to empty
	 */
    public PartialTreeList() {
    	rear = null;
    	size = 0;
    }

    /**
     * Adds a new tree to the end of the list
     * 
     * @param tree Tree to be added to the end of the list
     */
    public void append(PartialTree tree) {
    	Node ptr = new Node(tree);
    	if (rear == null) {
    		ptr.next = ptr;
    	} else {
    		ptr.next = rear.next;
    		rear.next = ptr;
    	}
    	rear = ptr;
    	size++;
    }

    /**
     * Removes the tree that is at the front of the list.
     * 
     * @return The tree that is removed from the front
     * @throws NoSuchElementException If the list is empty
     */
    public PartialTree remove() 
    throws NoSuchElementException {
    	//FINAL
    		
    	if(size==0) {
			throw new NoSuchElementException("Empty, cannot remove");
		}
		
		//rear keeps track of last thing in CLL
		//DIANA THIS IS A CLL
		
		if(rear.next==rear) {
			//one item in list
			PartialTree remove=rear.tree; //this is the item to be removed
			//same as rear.next.tree
			rear=null;
			size--;
			//System.out.println("We removed rear");
			return remove;
		}else {
			//means there is >1 item 
			//remove rear.next // first item
			PartialTree remove=rear.next.tree; //this will later be returned
			//this skips that tree, redirects pointers
			rear.next=rear.next.next;
			size--;
			return remove;
		}
		
		
	//double check this. just removing from a CLL first element
    }

    /**
     * Removes the tree in this list that contains a given vertex.
     * 
     * @param vertex Vertex whose tree is to be removed
     * @return The tree that is removed
     * @throws NoSuchElementException If there is no matching tree
     */
    public PartialTree removeTreeContaining(Vertex vertex) 
    throws NoSuchElementException {
    	if(size == 0||rear==null){
			throw new NoSuchElementException("Empty");
		}
    	
		Node lag=rear;
		Node ptr=rear.next;
		
		do{
			if (vertex.getRoot().equals(ptr.tree.getRoot())){
				if(rear==rear.next){
					//size==1
					PartialTree remove=ptr.tree; //because this is what we are removing
					size--;//take it out
					rear=null;//because we removed it
					return remove;
				}
				//meaning if we hit more than one object but it's also the rear thing being removed
				if(rear.tree.getRoot().equals(vertex.getRoot())) {
					//because we are removing the rear
					System.out.println("Rear match");
					PartialTree remove=ptr.tree;
					size--;
					lag.next=rear.next;
					rear=lag;
					return remove;					
				}
				
				//at this point we found tree and it's not rear
				//so we take tree and update everything else
				lag.next=ptr.next;
				PartialTree remove=ptr.tree;
				size--;
				return remove;
				
			}
			//else if it's not, we must climb
			System.out.println("We are climbing tree thing");
			lag=lag.next;
			ptr=ptr.next;
		}while(rear.next!=ptr); //meaning we reached end
		
		//if we have gotten to this point, it means we haven't returned
		//and if we haven't returned, it means we haven't found it
		throw new NoSuchElementException("Not in here");
    	
    }
    
    
    	//if we reach this point, it means we broke out of loop and didn't find it
    	//System.out.println("Rear is "+rear.tree.toString());
	
    
    
   /* private void printVerts(PartialTree huh, Vertex v) {
    	//takes in a partial tree, puts the verts in an array, and then prints them
    	
    	Vertex start=v;
    	while(v!=v.getRoot()) {
    		if(v.equals(huh.vertex)) {
    			
    		}
    	}
    	
    	Vertex[] verts=new Vertex[20];
    	
    	
    }*/
    
    //to be fixed
  /*  private boolean containsVert(PartialTree partialTree, Vertex vertexContained) {
    	//this is "going up" the parent tree
    	   Vertex treeRoot=partialTree.getRoot();
    	   boolean answer=false;
    	   if(treeRoot==vertexContained.getRoot()) {
    		   //means roots are the same
    		   answer=true;
    	   }
    	   
    	   if(treeRoot.equals(vertexContained)) {
    		   answer=true;
    	   }
    	   
    	   if(partialTree.parent==vertexContained||partialTree.parent.equals(vertexContained)) {
    		   answer=true;
    	   }
    	  
    	   return answer;
    	   */
    //}
     
    
    /**
     * Gives the number of trees in this list
     * 
     * @return Number of trees
     */
    public int size() {
    	return size;
    }
    
    /**
     * Returns an Iterator that can be used to step through the trees in this list.
     * The iterator does NOT support remove.
     * 
     * @return Iterator for this list
     */
    public Iterator<PartialTree> iterator() {
    	return new PartialTreeListIterator(this);
    }
    
    private class PartialTreeListIterator implements Iterator<PartialTree> {
    	
    	private PartialTreeList.Node ptr;
    	private int rest;
    	
    	public PartialTreeListIterator(PartialTreeList target) {
    		rest = target.size;
    		ptr = rest > 0 ? target.rear.next : null;
    	}
    	
    	public PartialTree next() 
    	throws NoSuchElementException {
    		if (rest <= 0) {
    			throw new NoSuchElementException();
    		}
    		PartialTree ret = ptr.tree;
    		ptr = ptr.next;
    		rest--;
    		return ret;
    	}
    	
    	public boolean hasNext() {
    		return rest != 0;
    	}
    	
    	public void remove() 
    	throws UnsupportedOperationException {
    		throw new UnsupportedOperationException();
    	}
    	
    }
}


