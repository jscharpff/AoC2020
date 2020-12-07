package util.dag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Container for a directed, acyclic graph
 * 
 * @author Joris
 */
public class DAG {
	/** The node set, indexed by node label */
	protected final Map<String, Node> nodes;
	
	/** The arc set */
	protected final List<Arc> arcs;
	
	/**
	 * Creates a new, empty Directed Acyclic Graph
	 */
	public DAG( ) {
		nodes = new HashMap<String, Node>( );
		arcs = new ArrayList<Arc>( );
	}
	
	/**
	 * Adds a node to the DAG, gets the exiting one if already in the DAG
	 * 
	 * @param nodelabel The node to add by label
	 * @return The node
	 */
	public Node addNode( final String nodelabel ) {
		Node node = this.getNode( nodelabel );
		if( node == null ) node = new Node( nodelabel );
		
		nodes.put( node.getLabel( ), node );
		return node;
	}
	
	/**
	 * Retrieves a node by its label
	 * 
	 * @param label The node label
	 * @return The node or null if not found in the DAG
	 */
	public Node getNode( final String label ) {
		return nodes.get( label );
	}
	
	/**
	 * @return The number of nodes in the dag
	 */
	public int size() { return nodes.size( ); }
	
	/**
	 * Adds an arc to the DAG to connect the two specified nodes
	 * 
	 * @param arc The arc to add
	 */
	public void addArc( final Arc arc ) {
		arcs.add( arc );
		
		// make sure nodes also know of the arc
		arc.from.addOutgoing( arc );
		arc.to.addIncoming( arc );
	}
	
	/**
	 * @return String representation of the tree
	 */
	public String toString( ) {
		return "[Nodes " + size( ) + "]\n" + nodes.toString( ) + 
				"\n\n[Arcs " + arcs.size( ) + "]\n" + arcs.toString( );
	}
}
