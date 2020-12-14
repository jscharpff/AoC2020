package day12.ship;

import util.geometry.Coord2D;

/**
 * SJip that can move in various directions
 * 
 * @author schar503
 */
public class GuidedShip extends Ship {
	/** The ships waypoint */
	protected Coord2D wpoint;
	
	/**
	 * Creates a new ship that is guided by a waypoint
	 * 
	 * @param posisition The start position
	 * @param waypoint The waypoint initial position
	 */
	public GuidedShip( final Coord2D position, final Coord2D waypoint ) {
		super( position, DIR_EAST );
		
		this.wpoint = waypoint;
	}

	/** @return The current waypoint */
	public Coord2D getWaypoint( ) { return wpoint; }
	
	/**
	 * Moves a number of units in the specified direction
	 * 
	 * @param dir The direction to move to (in degrees, 0 = North)
	 * @param distance The number of units to move
	 */
	@Override
	public void moveDir( final int direction, final int distance ) {
		wpoint = wpoint.moveDir( direction, distance );
	}

	/**
	 * Moves the ship forward towards the waypoint (n times)
	 * 
	 * @param n The number of times it moves the waypoint distance
	 */
	@Override
	public void moveForward( final int n ) {
		// moves towards the waypoint
		for( int i = 0; i < n; i++ ) 
				pos = pos.move( wpoint.x, wpoint.y );
	}
	
	/**
	 * Turns the waypoint around the ship
	 * 
	 * @param rotation The rotation amount (positive is clockwise)
	 */
	@Override
	public void turn( final int rotation ) {
		this.wpoint = wpoint.rotate( rotation );
	}
}
