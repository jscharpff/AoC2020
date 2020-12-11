package day11;

import util.grid.CharGrid;

/**
 * Class to simulate the occupation of seats on a floor plan
 * 
 * @author Joris
 */

public class FloorPlanSim {
	/** The grid that represents the floor plan */
	protected CharGrid grid;
	
	/** The current simulation round */
	protected int round;
	
	/** Used tile types (for convenience) */
	protected final char TILE_EMPTY = '.';
	protected final char TILE_AVAILABLE = 'L';
	protected final char TILE_OCCUPIED = '#'; 

	/**
	 * Creates a floor plan simulation from the grid
	 * 
	 * @param plan The floor plan grid
	 */
	public FloorPlanSim( final CharGrid plan ) {
		this.grid = plan;
		this.round = 0;
	}
	
	/** @return The number of simulated rounds so far */
	public int getRounds( ) { return round; }
	
	/**
	 * Simulate one round of seating rules
	 * 
	 * @return The number of changes in seats
	 */
	public int nextRound( final boolean visibleSeats ) {
		final CharGrid newround = grid.copy( );
		int changed = 0;
		
		// go over all positions and apply rules
		for( int y = 0; y < grid.getHeight( ); y++ ) {
			for( int x = 0; x < grid.getWidth( ); x++ ) {
				// get tile
				final char tile = grid.getTile( x, y );
				if( tile == TILE_EMPTY ) continue;
				
				// get occupied neighbours
				final int occupied = visibleSeats ? countVisiblyOccupied( x, y ) : grid.countNeighbours( x, y, TILE_OCCUPIED, true );
				if( tile == TILE_AVAILABLE && occupied == 0 ) {
					newround.setTile( x, y, TILE_OCCUPIED );
					changed++;
				}
				if( tile == TILE_OCCUPIED && occupied >= (visibleSeats ? 5 : 4) ) {
					newround.setTile( x, y, TILE_AVAILABLE );
					changed++;
				}
			}
		}

		// update grid and round number and return number of changes in this round
		round++;
		this.grid = newround;
		return changed;
	}

	/**
	 * Counts the number of seats that are visually occupied
	 * 
	 * @param x The horizontal position
	 * @param y The vertical position
	 * @return The count of visibly occupied seats
	 */
	public int countVisiblyOccupied( final int x, final int y ) {
		// count occupied seats in all directions
		return countVORec( x, y, -1, -1 ) + countVORec( x, y, 1, -1 ) +	countVORec( x, y, -1, 1 ) + countVORec( x, y, 1, 1 ) +
				countVORec( x, y, -1, 0 ) + countVORec( x, y, 1, 0 ) + countVORec( x, y, 0, -1 ) + countVORec( x, y, 0, 1 );
	}
	
	/**
	 * Recursively explores a vector to find out whether the first encountered seat is
	 * occupied or free
	 * 
	 * @param x Current horizontal position
	 * @param y Current vertical position
	 * @param dx The vector's horizontal movement
	 * @param dy The vector's vertical movement 
	 * @return 1 if an occupied seat is encountered, 0 otherwise
	 */	
	protected int countVORec( final int x, final int y, final int dx, final int dy ) {
		// get current tile and check if it is occupied or free, if no chair continue recursion
		final char t;
		try {
			t = grid.getTile( x + dx, y + dy );
		} catch( IndexOutOfBoundsException ioe ) { return 0; }
		if( t == TILE_OCCUPIED ) return 1;
		if( t == TILE_AVAILABLE ) return 0;
		return countVORec( x + dx, y + dy, dx, dy );
	}
	
	/**
	 * Counts the number of occupied seats
	 * 
	 * @return The count
	 */
	public int countOccupied( ) {
		return grid.countTile( TILE_OCCUPIED );
	}
	
	/** @return The string version of the floor plan */
	@Override
	public String toString( ) {
		return grid.toString( );
	}
}
