package util.dag;

/**
 * Directed arc from node A to B
 * 
 * @author Joris
 */
public class Arc {
	/** The starting node */
	protected final Node from;
	
	/** The end node */
	protected final Node to;
	
	/** The weight of the arc */
	protected int weight;
	
	/**
	 * Constructs a new arc from A to B width weight w
	 * 
	 * @param A The starting node
	 * @param B The ending node
	 * @param weight The arc weight
	 */
	public Arc( final Node A, final Node B, final int weight ) {
		this.from = A;
		this.to = B;
		this.weight = weight;
	}
	
	/** @return The start node */
	public Node getFrom( ) { return from; }
	
	/** @return The end node */
	public Node getTo( ) { return to; }

	/** @return The arc weight */
	public int getWeight( ) { return weight; }

	/**
	 * @return String representation of the arc
	 */
	public String toString( ) {
		return from.toString( ) + "--(" + weight + ")-->" + to.toString( );
	}
	
	/**
	 * Check if it equals another arc
	 * 
	 * @param obj The object representing the other arc
	 * @return True iff the arc contains the same nodes and weight
	 */
	public boolean equals( Object obj ) {
		if( obj == null || !(obj instanceof Arc) ) return false;
		final Arc arc = (Arc)obj;
		
		return arc.to.equals( to ) && arc.from.equals( from ) && arc.weight == weight;
	}
}
