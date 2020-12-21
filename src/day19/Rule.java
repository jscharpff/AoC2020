package day19;

/**
 * Stores a single input validation rule
 * 
 * @author schar503
 */
public abstract class Rule {
	/** The rule ID number */
	protected final int ID;
	
	/**
	 * Creates a new rule with the specified ID
	 * 
	 * @param ID The rule ID
	 */
	public Rule( final int ID ) {
		this.ID = ID;
	}
	
	/** @return The ID */
	public int getID( ) { return ID; }
	
	/** @return The hashcode */
	@Override public int hashCode( ) { return ID; }
	
	/**
	 * Reduces the rule to its RegEx pattern
	 * 
	 * @return The reduced rule
	 */
	public abstract String reduce( );
	
	/**
	 * Compares to another object
	 * 
	 * @param obj The other object
	 * @return True iff they are the same object type and their IDs match
	 */
	@Override
	public boolean equals( Object obj ) {
		if( obj == null || !(obj instanceof Rule) ) return false;
		return ((Rule)obj).ID == ID;
	}
	
	@Override
	public abstract String toString( );
}
