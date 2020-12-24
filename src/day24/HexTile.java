package day24;

import java.util.HashSet;
import java.util.Set;

import util.geometry.Coord2D;

/**
 * Container for a single hexagonal tile
 * 
 * @author Joris
 */
public class HexTile {
	/** The current upper face, true for black */
	protected boolean face_black;
	
	/** The position of the tile */
	protected Coord2D pos;

	/**
	 * Creates a new tile at the specified position
	 * 
	 * @param pos The start position
	 */
	public HexTile( final Coord2D pos ) {
		face_black = false;
		this.pos = pos;
	}

	/**
	 * Moves the tile in one of the given directions: E, NE, NW, W, SW or SE
	 * Also flips the tile
	 * 
	 * @param direction The direction to move in
	 * @return The new position of the tile
	 */
	public Coord2D move( final String direction ) {
		final boolean evenrow = pos.y % 2 == 0;
		switch( direction.toLowerCase( ) ) {
			case "e": pos = pos.move( 1, 0 ); break;
			case "w": pos = pos.move( -1, 0 ); break;

			case "se": pos = pos.move( evenrow ?  1 : 0, 1 ); break;
			case "ne": pos = pos.move( evenrow ?  1 : 0, -1 ); break;
			
			case "sw": pos = pos.move( !evenrow ? -1 : 0 , 1 ); break;
			case "nw": pos = pos.move( !evenrow ? -1 : 0, -1 ); break;
			default: throw new RuntimeException( "Invalid movement direction: " + direction );
		}
		
		return pos;
	}
	
	/**
	 * Flips the tile
	 */
	public void flip( ) {
		face_black = !face_black;
	}
	
	/**
	 * @return Creates a copy of the tile
	 */
	public HexTile copy( ) {
		final HexTile tile = new HexTile( pos );
		tile.face_black = face_black;
		return tile;
	}
	
	/**
	 * Determines all neighbouring positions of the tile
	 * 
	 * @return All positions adjacent to the tile's position
	 */
	protected Set<Coord2D> getAdjacentPositions( ) {
		final boolean even = pos.y % 2 == 0;
		final Set<Coord2D> N = new HashSet<>( 6 );
		
		N.add( pos.move( -1, 0 ) ); // W
		N.add( pos.move( 1, 0 ) ); // E
		N.add( pos.move( !even ? -1 : 0, -1 ) ); // NW
		N.add( pos.move( !even ? -1 : 0, 1 ) ); // SW
		N.add( pos.move( even ? 1 : 0, -1 ) ); // NE
		N.add( pos.move( even ? 1 : 0, 1 ) ); // SE
		
		return N;
	}
	
	/** @return The position of the tile */
	public Coord2D getPosition( ) { return pos; }
	
	/** @return True if the black side faces up */
	public boolean isBlackUp( ) { return face_black; }

	/** @return The tile coordinate and face */
	@Override
	public String toString( ) {
		return pos.toString( ) + (face_black ? "B" : "W");
	}
}
