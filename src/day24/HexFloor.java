package day24;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import util.geometry.Coord2D;

/**
 * Floor made up of hexagon tiles
 * 
 * @author Joris
 */
public class HexFloor {
	/** Map of the tiles */
	protected Map<Coord2D, HexTile> floorplan;

	/**
	 * Creates a new hexagonal floor plan
	 */
	public HexFloor( ) {
		floorplan = new HashMap<>( );
	}
	
	/**
	 * Adds a tile and uses the directions string to flip it to the correct position
	 * 
	 * @param moves The string that describes the flips to get to its location
	 * @return The final position at which it ended
	 */
	public Coord2D addTile( final String moves ) {
		// create a new tile at the reference position 
		final HexTile t = new HexTile( new Coord2D( 0, 0 ) );
		
		// move the tile
		String m = moves;
		while( m.length( ) > 0 ) {
			final String move = ( m.startsWith( "n" ) || m.startsWith( "s" ) ) ? m.substring( 0, 2 ) : m.substring( 0, 1 );
			t.move( move );
			m = m.substring( move.length( ) );
		}
		
		
		// we are at the end position, set it in the grid. Update it it is already there
		final Coord2D pos = t.getPosition( );
		if( floorplan.containsKey( pos ) ) {
			floorplan.get( pos ).flip( );
		} else {
			t.flip( );
			floorplan.put( pos, t );
		}
		return pos;
	}
	
	/**
	 * Flip tiles according to rules
	 */
	public void flipTiles( ) {
		// create a new floor plan from simulating tile changes
		final Map<Coord2D, HexTile> newfloor = new HashMap<Coord2D, HexTile>( );
		
		// first build set of positions to consider
		final Set<Coord2D> positions = new HashSet<Coord2D>( );
		for( HexTile t : floorplan.values( ) ) {
			positions.addAll( t.getAdjacentPositions( ) );			
		}
		
		// for every possible tile, count its black neighbours 
		for( Coord2D coord : positions ) {
			final HexTile tile = floorplan.containsKey( coord ) ? floorplan.get( coord ).copy( ) : new HexTile( coord );
			final int b = countBlackNeighbours( tile );
			
			if( tile.isBlackUp( ) ) {
				// flip to white ?
				if( b == 0 || b > 2 ) tile.flip( );
			} else {
				// flip to black if exactly two neighbours are black
				if( b == 2 ) tile.flip( );
			}
			
			// only keep black tiles
			if( tile.isBlackUp( ) ) newfloor.put( coord, tile );
		}
		
		this.floorplan = newfloor;
	}
	
	/**
	 * Count number of black tiles in the current floor plan
	 * 
	 * @return The numnber of tiles with black face up
	 */
	public int countBlack( ) {
		int count = 0;
		for( HexTile t : floorplan.values( ) )
			if( t.isBlackUp( ) ) count++;
		return count;
	}

	
	/**
	 * Count the number of adjacent tiles that are black
	 * 
	 * @param tile The tile to count neighbours of
	 * @return The count of adjacent black tiles
	 */
	protected int countBlackNeighbours( final HexTile tile ) {
		final Set<Coord2D> N = tile.getAdjacentPositions( );
		int count = 0;
		for( Coord2D c : N ) 
			if( floorplan.containsKey( c ) && floorplan.get( c ).isBlackUp( ) ) count++;
		return count;
	}
	
	/** @return The floor plan as set string */
	@Override
	public String toString( ) {
		return floorplan.toString( );
	}
}
