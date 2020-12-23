package day23.rotatinglist;

/**
 * Container for a single Rotating List node
 * 
 * @author Joris
 * @param <T> The type of the contained element
 */
public class RLNode<T extends Object> {
	/** The next node */
	protected RLNode<T> next;
	
	/** The previous node */
	protected RLNode<T> prev;
	
	/** The value stored by this node */
	protected T value;
	
	/**
	 * Creates a new linked list container node
	 * 
	 * @param value The value to store
	 */
	public RLNode( final T value ) {
		this.value = value;
	}
	
	/** @return The value */
	public T getValue( ) { return value; }
	
	/**
	 * Checks if this node is equal to another node
	 * 
	 * @param obj The other node
	 * @return True iff their values equal
	 */
	@Override
	@SuppressWarnings("unchecked")
	public boolean equals( Object obj ) {
		if( obj == null || !(obj instanceof RLNode) ) return false;
		final RLNode<T> item = ((RLNode<T>) obj);
		return item.hasValue( value );
	}
	
	/**
	 * Checks if the node contains the specified value
	 *  
	 * @param value The value to check
	 * @return True iff the value of this node equals the specified value
	 */
	public boolean hasValue( final T value ) {
		return (this.value == null ? value == null : this.value.equals( value ) );
	}
	
	/** @return String description of the node */
	@Override
	public String toString( ) {
		return this.prev.value + " -> (" + this.value + ") -> " + this.next.value;
	}
}
