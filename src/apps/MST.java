package apps;

import structures.*;
import java.util.ArrayList;

public class MST {

	
	/**
	 * Initializes the algorithm by building single-vertex partial trees
	 * 
	 * @param graph Graph for which the MST is to be found
	 * @return The initial partial tree list
	 */
	public static PartialTreeList initialize(Graph graph) {
		//FINAL

		if(graph.vertices.length==0||graph==null) {
			//means there are no vertices
			System.out.println("Error?? No vertices.");
			return null;
		} //I believe these are the errors
		//double check this though
		
		PartialTreeList ptl=new PartialTreeList(); //initialize list
		//this needs to be filled --> empty rn
		
		int numVerts=graph.vertices.length; //this is the number for each vertex in graph
		//System.out.println("NumVerts is "+numVerts);
		
		for(int v=0; v<numVerts;v++) {
			//create a partial tree containing only v
			Vertex vert=graph.vertices[v]; //check to make sure
			//this is a, b, c, and so on
			
			//mark v as belong to t
			//idk how to do this
			PartialTree T=new PartialTree(vert);
			
			//create a priority queue (heap) P and associate it with T
			//go through neighbors for this I think???
			//there is already a heap in constructor for partial tree so we just add to it
			
			
			Vertex.Neighbor neighbor=vert.neighbors;  //this is a LL of neighbors
			//this should be the neighbors in a LL so for a --> h, b
			//loop through neighbors
			while(neighbor!=null) {
				PartialTree.Arc arcAdd=new PartialTree.Arc(vert, neighbor.vertex, neighbor.weight);
				T.getArcs().insert(arcAdd);
				//this adds the arcs to the arc heap
			System.out.println("Just added arc for vert "+vert+" +  "+neighbor.vertex+" to heap");
			neighbor=neighbor.next;
			}
		
			
			
			//insert all of the arcs (edges) connected to v into P (this uses hashmap)
			//make sure lower the weight, higher the priority
			
			
			//add partial tree T to the list L
			//initial ordering of partial trees doesn't matter so add to front
			ptl.append(T);
		}
		return ptl;
		
		//return null;
		
	}

	/**
	 * Executes the algorithm on a graph, starting with the initial partial tree list
	 * 
	 * @param ptlist Initial partial tree list
	 * @return Array list of all arcs that are in the MST - sequence of arcs is irrelevant
	 */
	public static ArrayList<PartialTree.Arc> execute(PartialTreeList ptlist) {
		

		//return the PartialTree.Arc
				ArrayList<PartialTree.Arc> finalArcs=new ArrayList<PartialTree.Arc>(); //this is to be returned 
				//ptlist == ptl
				//oh so we assume ptl list has been initialized
				
				//algo
				
				//remove the first partial tree from ptl
		while(ptlist.size()>1){
			
			PartialTree PTX = ptlist.remove(); //removed first tree
			
			PartialTree.Arc a= PTX.getArcs().getMin();
			System.out.println("Min is "+a.toString());
			a=PTX.getArcs().deleteMin(); //highest priority=lowest
			System.out.println("Deleted "+a.toString());
			//this needs to match with above
			Vertex v1=a.v1;
			Vertex v2=a.v2;
			
			System.out.println("v1 is "+v1.toString()+"  and v2 is  "+v2.toString()); //used to check vertex
			
			while(!PTX.getArcs().isEmpty()){ //meaning there are arcs still
				
				if (v2.getRoot().equals(PTX.getRoot())){
					System.out.println("We updated");
					a = PTX.getArcs().deleteMin();
					v2 = a.v2; //this is updating as the next smallest
				}else {
					System.out.println("We did not update");
					break;
				}
			}
			//when we break out means now we get PTY thing
			
			//should have updated accordingly 
			
			System.out.println("After updating (if any)");
			System.out.println("v1 is "+v1.toString()+"  and v2 is  "+v2.toString()); //used to check vertex
			
			//if(ptlist.) once we remove the thing. if the other PTLs DO NOT CONTAIN THE VERTEX, then this means the v2 vertex is in the tree and we have to go back and pick min
			/*while(v2 belongs to PTX)) //gotta look at how to do this
			 * I think condition might have something like either iterating over vertices until we find v2 (.equals??) OR
			 * there might be a way to check if vertex contains v2
			 * i think best bet is first option
			 * 
			 * 
			 * a=PTX.getArcs().deleteMin(); //this gets the next min
			 * v1=a.v1; //updates
			 * v2=a.v2;
			 *   like it should do that
			 * pick the next highest priority arc
			 * so would have to do 
			 * PartialTree.Arc a=PTX.getArcs().get the one after min???? idk how to do this
			 * ELSE --> see code below
			 */
			
			//once you break out of loop we have min and the correct verts. MAKE SURE!!!
			System.out.println(a.toString()+" is a component of MST"); //this is reporting the min thing
			//so add this to finArcs
			finalArcs.add(a);
			
			//find the partial tree PTY which v2 belongs to 
			PartialTree PTY = ptlist.removeTreeContaining(v2);
			System.out.println("PTY is "+PTY.toString());
			
			//combine PTX and PTY
			PTX.merge(PTY);
			ptlist.append(PTX);
			//result.add(a); -- this might need to be moved to end
		}
		
		return finalArcs;
	}
}
