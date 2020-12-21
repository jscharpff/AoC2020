package day19;

public class RecRule extends Rule {
	/**
	 * Creates a new recursive rule reference
	 * 
	 * @param ID The rule
	 */
	public RecRule( final int ID ) {
		// use specific set of identifiers to recognise Recursive rules
		super( 100000 + ID );
	}
	
	@Override
	public boolean equals( Object obj ) {
		if( obj == null || !(obj instanceof RecRule) ) return false;
		return super.equals( obj );
	}
	
	@Override
	public String reduce( ) {
		return "";
	}
	
	@Override
	public String toString( ) {
		return "" + this.ID;
	}
}
