package day12.ship;

import util.geometry.Coord2D;

/**
 * SJip that can move in various directions
 * 
 * @author schar503
 */
public class Ship {
	/** The ships current position */
	protected Coord2D pos;
	
	/** The ships direction in degrees (0 = north) */
	protected int dir;
	
	/** Major directions */
	public static int DIR_NORTH = 0;
	public static int DIR_EAST = 90;
	public static int DIR_SOUTH = 180;
	public static int DIR_WEST = 270;
	
	/**
	 * Creates a new ship
	 * 
	 * @param posisition The start position
	 * @param facing The direction it faces at start (0 = North)
	 */
	public Ship( final Coord2D position, final int direction  ) {
		this.pos = position;
		this.dir = direction;
	}
	
	/** @return The current position */
	public Coord2D getPosition( ) { return pos; }
	
	/** @return The current direction in degrees */
	public int getDirection( ) { return dir; }
	
	/**
	 * Moves a number of units in the specified direction
	 * 
	 * @param dir The direction to move to (in degrees, 0 = North)
	 * @param distance The number of units to move
	 */
	public void moveDir( final int direction, final int distance ) {
		pos = pos.moveDir( direction, distance );
	}

	/**
	 * Moves the ship forward in its current direction
	 * 
	 * @param distance The number of units to move
	 */
	public void moveForward( final int distance ) {
		this.moveDir( this.dir, distance );
	}
	
	/**
	 * Turns the ship
	 * 
	 * @param rotation The rotation amount (positive is clockwise)
	 */
	public void turn( final int rotation ) {
		this.dir = (this.dir + rotation + 360) % 360;
	}
	
	/**
	 * Performs a specified action
	 * 
	 * @param action The string describing the action
	 * @throws IllegalArgumentException if the action is invalid
	 */
	public void doAction( final String action ) {
		final char act = action.charAt( 0 );
		final int param = Integer.parseInt( action.substring( 1 ) );
				
		switch( act ) {
			case 'N': moveDir( DIR_NORTH, param ); break; 			
			case 'E':	moveDir( DIR_EAST, param ); break;
			case 'S': moveDir( DIR_SOUTH, param ); break;
			case 'W': moveDir( DIR_WEST, param ); break;
			case 'F': moveForward( param ); break;
			case 'L': turn( -param ); break;
			case 'R': turn( param ); break;
			default:
				throw new IllegalArgumentException(  "Invalid action: " + action );
		}
	}
}
