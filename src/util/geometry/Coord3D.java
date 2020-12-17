package util.geometry;

/**
 * A 3 dimensional coordinate
 * 
 * @author Joris
 */
public class Coord3D {
	/** The x coordinate */
	public final int x;
	
	/** The y coordinate */
	public final int y;
	
	/** The z coordinate */
	public final int z;
	
	/**
	 * Creates a new 3D coordinate
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public Coord3D( final int x, final int y, final int z ) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Returns coordinate that holds the minimum value of each axis
	 * 
	 * @param coord The other coordinate
	 * @return A new coordinate that contains the minimum of both
	 */
	public Coord3D min( final Coord3D coord ) {
		return new Coord3D( 
				x < coord.x ? x : coord.x, 
				y < coord.y ? y : coord.y,
				z < coord.z ? z : coord.z
			);
	}
	
	/**
	 * Returns coordinate that holds the maximum value of each axis
	 * 
	 * @param coord The other coordinate
	 * @return A new coordinate that contains the maximum of both
	 */
	public Coord3D max( final Coord3D coord ) {
		return new Coord3D( 
				x > coord.x ? x : coord.x, 
				y > coord.y ? y : coord.y,
				z > coord.z ? z : coord.z
			);
	}
	
	/**
	 * Moves the coordinate by the specified dx, dy and dz
	 * 
	 * @param dx
	 * @param dy
	 * @param dz
	 * @return The new coordinate at (x + dx, y + dy, z + dz)
	 */
	public Coord3D move( final int dx, final int dy, final int dz ) {
		return new Coord3D( x + dx, y + dy, z + dz );
	}
	
	/**
	 * Returns difference with other coord in all axis
	 * 
	 * @param coord The other coordinate
	 * @return New coordinate that represents the difference (c.x-x, x.y-y, z.y-y)
	 */
	public Coord3D diff( final Coord3D coord ) {
		return new Coord3D( coord.x - x, coord.y - y, coord.z - z );
	}
	
	/** @returns a coordinate string (x,y,z) */
	@Override
	public String toString( ) {
		return "(" + x + "," + y + "," + z + ")";
	}
	
	/** @return The string's hashcode */
	@Override
	public int hashCode( ) {
		return toString( ).hashCode( );
	}

	@Override
	public boolean equals( Object obj ) {
		if( obj == null || !(obj instanceof Coord3D) ) return false;
		final Coord3D c = (Coord3D) obj;
		
		return x == c.x && y == c.y && z == c.z;
	}
}
