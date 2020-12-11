package day03;

import java.util.HashMap;

/**
 * Set of available tile types
 * @author Joris
 *
 */
public class Tileset {
	/** The set of available tiles, indexed by label */
	protected final java.util.Map<Character, Tile> tileset;
	
	/** The next available storage code */
	protected static int code_number = 0;
	
	/**
	 * Constructs a new empty tile set
	 */
	protected Tileset( ) {
		tileset = new HashMap<>( );
	}
	
	/**
	 * Constructs a new tile set with initial tiles specified by a label array
	 * 
	 * @param tiles The initial tile set
	 */
	public Tileset( final char... tiles ) {
		this( );
		
		for( Character s : tiles )
			addTile( s );
	}
	
	/**
	 * Constructs a new tile set from a string containing the tile labels
	 * 
	 * @param tiles The tile set string
	 */
	public Tileset( final String tiles ) {
		this( tiles.toCharArray( ) );
	}
	
	/**
	 * Add tile type to the tileset, will not add the tile if the label alreay exists. Will
	 * assign a unique tile code for internal storage
	 * 
	 * @param tilelabel The label of the tile
	 * @return The tile 
	 */
	public boolean addTile( final char tilelabel ) {
		if( tileset.containsKey( tilelabel ) ) return false;
		
		final Tile tile = new Tile( code_number++, tilelabel );
		tileset.put( tilelabel, tile );
		return true;
	}

	/**
	 * Retrieves a tile from the set by its label
	 * 
	 * @param tilelabel The label of the tile to fetch
	 * @return The tile with the specified label, null if not found
	 */
	public Tile getTile( final char tilelabel ) {
		return tileset.get( tilelabel );
	}
	
	/**
	 * Checks if a tile is part of the tile set
	 * 
	 * @param tile The tile character
	 * @return True iff the tile is part of the set
	 */
	public boolean hasTile( final char tile ) {
		return tileset.containsKey( tile );
	}
	
	/** @return The number of tiles in this set */
	public int size() { return tileset.size( ); } 

	/**
	 * @return String representation
	 */
	@Override
	public String toString( ) {
		return "[Tiles " + size() + "]\n" + tileset;
	}
}
