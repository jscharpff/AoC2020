package day09;

public class XMASCypher {
	/** The cypher key */
	protected final long[] key;
	
	/** The rotating index */
	protected int index;
	
	/** True iff the cypher has been initialised */
	protected boolean initialised;
	
	/**
	 * Initialise new XMAS cypher
	 * 
	 * @param lenght The cypher length
	 */
	public XMASCypher( final int length ) {
		this.key = new long[ length ];
		this.index = 0;
		this.initialised = false;
	}
	
	/**
	 * Reads the next number and returns True if this number is valid
	 * 
	 * @param num The next number to read
	 * @return False if the number is not vald
	 */
	public boolean readNext( final long num ) {
		// check if the number is composite of present key values
		if( !isValid( num ) ) return false;

		// update the cypher key
		key[index] = num;

		// rotate index
		index++;
		if( index >= key.length ) {
			index = 0;
			initialised = true;
		}
		return true;
	}
	
	/**
	 * Checks if the number is a valid composite of any two key entries
	 * 
	 * @param num The number to check
	 * @return True if two cypher entries sum to the number of the cypher is still initialising
	 */
	protected boolean isValid( final long num ) {
		if( !initialised ) return true;
		
		for( int i = 0; i < key.length; i++ )
			for( int j = i + 1; j < key.length; j++ )
				if( key[i] + key[j] == num ) return true;
		
		return false;
	}
	
	/**
	 * @return String of current cypher
	 */
	@Override
	public String toString( ) {
		String s = "Cypher(" + key.length + "): ";
		for( long k : key ) s += "" + k + ",";
		return s.substring( 0, s.length( ) - 1 );

	}
}
