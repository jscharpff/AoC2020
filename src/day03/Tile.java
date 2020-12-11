package day03;

public class Tile {
	/** The tile code for storage */
	final int code;
	
	/** The tile display label */
	final char label;

	/**
	 * Creates a new tile type
	 * 
	 * @param code Its intcode for storage
	 * @param label Its label for display
	 */
	protected Tile( final int code, final char label ) {
		this.code = code;
		this.label = label;
	}
	
	/**
	 * Checks if a tile equals another tile
	 * 
	 * @param obj The object to check equality with
	 * @return True iff the tiles have an equal code and label
	 */
	@Override
	public boolean equals( Object obj ) {
		if( obj == null || !(obj instanceof Tile) ) return false;
		final Tile tile = (Tile) obj;
		
		return tile.code == code && tile.label == label;
	}
	
	/** @return The tile code for storage */
	public int getCode( ) { return code; }
	
	/** @return The tile label */
	public char getLabel( ) { return label; }

	/**
	 * Checks if the label equals the specified one
	 * 
	 * @param label The label to compare against
	 * @return True iff the label equals the specified label
	 */
	public boolean hasLabel( final char label ) {
		return this.label == label;
	}
	
	/**
	 * Hash code for tile set indexing
	 * @return The tile storage code
	 */
	@Override
	public int hashCode( ) {
		return code;
	}
	
	/**
	 * Copies the tile
	 * 
	 * @return the copy
	 */
	protected Tile copy( ) {
		return new Tile( code, label );
	}
	
	/**
	 * @return The label of the tile
	 */
	@Override
	public String toString( ) {
		return "" + this.label;
	}
}
