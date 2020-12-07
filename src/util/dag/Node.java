package util.dag;

import java.util.ArrayList;
import java.util.List;

/**
 * DAG Node
 * 
 * @author Joris
 */
public class Node {
	/** The node label */
	protected final String label;
	
	/** Incoming arcs */
	protected final List<Arc> in;
	
	/** Outgoing arcs */
	protected final List<Arc> out;
	
	/**
	 * Creates a new node with the specified label
	 * 
	 * @param label The node label
	 */
	public Node( final String label ) {
		this.label = label;
		
		this.in = new ArrayList<>( );
		this.out = new ArrayList<>( );
	}
	
	/** @return The label */
	public String getLabel( ) { return label; }
	
	/**
	 * @return String description of the node
	 */
	public String toString( ) {
		return "(" + getLabel( ) + ")";
	}

	/**
	 * Hash code neededd for hash set, simply return that of its label
	 */
	@Override
	public int hashCode( ) {
		return label.hashCode( );
	}
	
	/**
	 * @return True if this node is a root node (no incoming arcs) 
	 */
	public boolean isRoot( ) { return in.size( ) == 0; }
	
	/**
	 * @return True if this node is a leaf node (no outgoing arcs) 
	 */
	public boolean isLeaf( ) { return out.size( ) == 0; }
	
	/**
	 * Adds an incoming arc to the node arc list
	 * 
	 * @param arc The incoming arc
	 * @return False if the arc was already known
	 */
	public boolean addIncoming( final Arc arc ) {
		assert equals( arc.to ) : "Node " + toString( ) + " is not the incoming node of arc " + arc.toString( );
		return this.addArc( arc, in );
	}
	
	/**
	 * @return List of incoming arcs
	 */
	public List<Arc> getIncoming( ) {	return in; }
	
	/**
	 * Adds an outgoing arc to the node arc list
	 * 
	 * @param arc The outgoing arc
	 * @return False if the arc was already known
	 */
	public boolean addOutgoing( final Arc arc ) {
		assert equals( arc.from ) : "Node " + toString( ) + " is not the outgoing node of arc " + arc.toString( );
		return this.addArc( arc, out );
	}
	
	/**
	 * @return List of outgoing arcs
	 */
	public List<Arc> getOutgoing( ) {	return out; }
	
	/**
	 * Adds an  arc to the specified node arc list
	 * 
	 * @param arc The arc
	 * @param arclist The list to add it to
	 * @return False if the arc was already known
	 */
	protected boolean addArc( final Arc arc, final List<Arc> arclist ) {		
		// prevent duplicate arcs
		for( Arc a : arclist )
			if( a.equals( arc ) ) return false;
		
		arclist.add( arc );
		return true;
	}
	
	/**
	 * Check if it equals another node
	 * 
	 * @param obj The object representing the other node
	 * @return True iff the labels are equal
	 */
	public boolean equals( Object obj ) {
		if( obj == null || !(obj instanceof Node) ) return false;
		final Node node = (Node)obj;
		
		return node.label.equals( label );
	}
}
