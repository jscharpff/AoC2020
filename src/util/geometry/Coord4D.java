package util.geometry;

/**
 * A 3 dimensional coordinate
 * 
 * @author Joris
 */
public class Coord4D {
	/** The x coordinate */
	public final int x;
	
	/** The y coordinate */
	public final int y;
	
	/** The z coordinate */
	public final int z;
	
	/** The w coordinate */
	public final int w;
	
	/**
	 * Creates a new 3D coordinate
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param w
	 */
	public Coord4D( final int x, final int y, final int z, final int w ) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	/**
	 * Returns coordinate that holds the minimum value of each axis
	 * 
	 * @param coord The other coordinate
	 * @return A new coordinate that contains the minimum of both
	 */
	public Coord4D min( final Coord4D coord ) {
		return new Coord4D( 
				x < coord.x ? x : coord.x, 
				y < coord.y ? y : coord.y,
				z < coord.z ? z : coord.z,
				w < coord.w ? w : coord.w
			);
	}
	
	/**
	 * Returns coordinate that holds the maximum value of each axis
	 * 
	 * @param coord The other coordinate
	 * @return A new coordinate that contains the maximum of both
	 */
	public Coord4D max( final Coord4D coord ) {
		return new Coord4D( 
				x > coord.x ? x : coord.x, 
				y > coord.y ? y : coord.y,
				z > coord.z ? z : coord.z,
				w > coord.w ? w : coord.w 
			);
	}
	
	/**
	 * Moves the coordinate by the specified dx, dy, dz and dw
	 * 
	 * @param dx
	 * @param dy
	 * @param dz
	 * @return The new coordinate at (x + dx, y + dy, z + dz)
	 */
	public Coord4D move( final int dx, final int dy, final int dz, final int dw ) {
		return new Coord4D( x + dx, y + dy, z + dz, w + dw );
	}
	
	/**
	 * Returns difference with other coord in all axis
	 * 
	 * @param coord The other coordinate
	 * @return New coordinate that represents the difference (c.x-x, c.y-y, c.z-z, c.w-w)
	 */
	public Coord4D diff( final Coord4D coord ) {
		return new Coord4D( coord.x - x, coord.y - y, coord.z - z, coord.w - w );
	}
	
	/** @returns a coordinate string (x,y,z,w) */
	@Override
	public String toString( ) {
		return "(" + x + "," + y + "," + z + "," + w + ")";
	}
	
	/** @return The string's hashcode */
	@Override
	public int hashCode( ) {
		return toString( ).hashCode( );
	}

	@Override
	public boolean equals( Object obj ) {
		if( obj == null || !(obj instanceof Coord4D) ) return false;
		final Coord4D c = (Coord4D) obj;
		
		return x == c.x && y == c.y && z == c.z && w == c.w;
	}
}
