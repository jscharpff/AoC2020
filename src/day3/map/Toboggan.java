package day3.map;

import util.geometry.Coord2D;

/**
 * Some sort of sled I presume?
 * 
 * @author Joris
 */
public class Toboggan {
	/** It's position */
	protected Coord2D pos;
	
	/** It's current slope */
	protected Slope slope;
	
	/** Inner class to represent the slope */
	protected class Slope {
		protected final int horizontal;
		protected final int vertical;
		protected Slope( final int hor, final int vert ) {
			this.horizontal = hor;
			this.vertical = vert;
		}
	}
	
	/**
	 * Creates a new Toboggan
	 * 
	 * @param start The start position
	 */
	public Toboggan( final Coord2D start ) {
		this.pos = start;
		
		// make sure to initialise a slope
		setSlope( 0, 0 );
	}
	
	/**
	 * Sets the new slope for the Toboggan
	 * 
	 * @param dx The horizontal slope
	 * @param dy The vertical slope
	 */
	public void setSlope( final int dx, final int dy ) {
		this.slope = new Slope( dx, dy );
	}
	
	/**
	 * @return The current position of the Toboggan
	 */
	public Coord2D getPosition( ) {
		return this.pos;
	}
	
	/**
	 * Moves the tobbogan one times the slope on the map
	 * @return The new position
	 */
	public Coord2D move( ) {
		this.pos = this.pos.move( slope.horizontal, slope.vertical );
		return this.pos;
	}
}
