package day5;

public class Boardingpass {
	/** The row number */
	final protected int row;
	
	/** The column number */
	final protected int column;
	
	/** Th seat ID */
	final protected int seatID;
	
	/**
	 * Creates a new boarding pass
	 * 
	 * @param row The row
	 * @param column The seat column
	 */
	public Boardingpass( final int row, final int column ) {
		this.row = row;
		this.column = column;
		this.seatID = row * 8 + column;
	}
	
	/** @return The row number */
	public int getRow( ) { return row; }
	
	/** @return The column number */
	public int getColumn( ) { return column; }
	
	/** @return The seatID */
	public int getSeatID( ) { return seatID; }
	
	/**
	 * Computes row or column from a binary string
	 * 
	 * @param code The binary string
	 * @return The integer value
	 */	
	protected static int fromBinary( final String code ) {
		int value = 0;
		final int bits = code.length( );
		for( int bit = 1; bit <= bits; bit++ ) {
			final char c = code.charAt( bits - bit );
			if( c == 'B' || c == 'R' ) value += Math.pow( 2, bit - 1);
		}
		return value;
	}
	
	/**
	 * Computes a boarding pass position from binary encoded string
	 * 
	 * @param code The binary encoded seat string
	 * @return The boarding pass for the specified position
	 */
	public static Boardingpass fromCode( final String code ) {
		// extract components
		final String rowcode = code.substring( 0, 7 );
		final String colcode = code.substring( 7 );

		return new Boardingpass( fromBinary( rowcode ), fromBinary( colcode ) );
	}
	
	/**
	 * @return The boarding pass string
	 */
	public String toString( ) {
		return "Row " + row + ", column " + column + ", seatID " + seatID;
	}
}
