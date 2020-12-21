package day19;

/**
 * Creates a rule that holds a single character value
 * 
 * @author schar503
 */
public class ValueRule extends Rule {
	/** The character value it holds */
	protected final char value;
	
	/**
	 * Creates a new value rule
	 * 
	 * @param ID The ID of the rule
	 * @param value The character value it holds
	 */
	public ValueRule( final int ID, final char value ) {
		super( ID );
		
		this.value = value;
	}
	
	@Override
	public String reduce( ) {
		return "" + value;
	}
	
	@Override
	public boolean equals( Object obj ) {
		if( obj == null || !(obj instanceof ValueRule) ) return false;
		final ValueRule vr = (ValueRule)obj;
		
		return super.equals( vr ) && vr.value == value;
	}
	
	@Override
	public String toString( ) {
		return "" + value;
	}
}
