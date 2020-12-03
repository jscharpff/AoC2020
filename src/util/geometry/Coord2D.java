package util.geometry;

/**
 * Simple 2 dimensional coordinate
 * 
 *  @author Joris
 */
public class Coord2D {
	/** The horizontal position */
	public final int x;
	
	/** The vertical position */
	public final int y;
	
	/**
	 * Creates a new 2D coordinate
	 * 
	 * @param x The horizontal position
	 * @param y The vertical position
	 */
	public Coord2D( final int x, final int y ) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Computes new coordinate when moving with specified dx and dy
	 * 
	 * @param dx Horizontal movement
	 * @param dy Vertival movement
	 * @return The new coordinate
	 */
	public Coord2D move( final int dx, final int dy ) {
		return new Coord2D( x + dx, y + dy );
	}
}
