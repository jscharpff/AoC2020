package day3.map;

public enum Tile {
	Empty( 0, '.' ), Tree( 1, '#' );

	/** The tile code for storage */
	final int code;
	
	/** The tile display label */
	final char label;

	/**
	 * Creates a new tile type
	 * 
	 * @param code
	 *          Its intcode for storage
	 * @param label
	 *          Its label for display
	 */
	private Tile( final int code, final char label ) {
		this.code = code;
		this.label = label;
	}

	/**
	 * Constructs a tile from its character representation
	 * 
	 * @param tile
	 *          The tile label
	 * @return The tile that correspondsto the label
	 * @throws IllegalArgumentException
	 *           if the map tile is unknown
	 */
	protected static Tile fromLabel( final char tile ) throws IllegalArgumentException {
		for( Tile t : Tile.values( ) )
			if( t.label == tile ) return t;

		throw new IllegalArgumentException( "Invalid map tile: " + tile );
	}

	/**
	 * @return The label of the tile
	 */
	@Override
	public String toString( ) {
		return "" + this.label;
	}
}
