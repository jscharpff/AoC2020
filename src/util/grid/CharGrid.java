package util.grid;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import util.io.FileReader;

/**
 * Container for boardlike grid, uses a character type a tile representation
 * 
 * @author Joris
 */
public class CharGrid {
	/** The grid width */
	protected final int width;
	
	/** The grid height */
	protected final int height;
	
	/** The set of available tiles */
	protected final Set<Character> tileset;
	
	/** The grid tile set */
	protected final char[][] tiles;

	/**
	 * Creates a new grid of width w and height h, using the specified tile set
	 * 
	 * @param heigth The grid height
	 * @param width The grid width
	 * @param tileset The tile set to use
	 */
	public CharGrid( final int height, final int width, final char[] tileset ) {
		this.width = width;
		this.height = height;
				
		tiles = new char[height][width];
		
		// construct tile set
		this.tileset = new HashSet<>( );
		for( char c : tileset )
			this.tileset.add( c );
	}
	
	/**
	 * Creates a new char grid by copying an existing one
	 * 
	 * @param grid The existing grid to copy
	 */
	private CharGrid( final CharGrid grid ) {
		this.width = grid.width;
		this.height = grid.height;
		
		// copy the tile set and grid
		this.tileset = new HashSet<>( grid.tileset );
		this.tiles = new char[height][width];
		for( int y = 0; y < height; y++ )
			for( int x = 0; x < width; x++ )
				this.setTile( x, y, grid.getTile( x, y ) ); 
	}
	
	/**
	 * Initialises the entire grid with the specified tile
	 * 
	 * @param tile The tile to fill the map with
	 */
	public void clear( final char tile ) {
		// check validity of tile
		assertTile( tile );
		
		for( int y = 0; y < this.height; y++ )
			for( int x = 0; x < this.width; x++ )
				this.tiles[y][x] = tile;
	}
	
	/**
	 * Returns the tile at the specified position
	 * 
	 * @param x The horizontal position
	 * @param y The vertical position
	 * @return The tile at that position
	 */
	public char getTile( final int x, final int y ) {
		return tiles[y][x];
	}
	
	/**
	 * Sets the tile at position x and y
	 * 
	 * @param x The horizontal position
	 * @param y The vertical position
	 * @param tile The tile to set
	 * @return The previous value at the tile position
	 */
	public char setTile( final int x, final int y, final char tile ) {
		// check validity of tile
		assertTile( tile );

		final char old = tiles[y][x];
		tiles[y][x] = tile;
		return old;
	}
	
	/** @return The width of the grid */
	public int getWidth( ) { return this.width; }
	
	/** @return The height of the grid */
	public int getHeight( ) { return this.height; }
	
	/**
	 * Check if the tile character is available in this chargrid tileset
	 * 
	 * @param tile The tile character
	 * @return True iff the character is in the tile set
	 */
	public boolean hasTile( final char tile ) {
		return tileset.contains( tile );
	}
	
	/**
	 * Counts the occurrence of a tile
	 * 
	 * @param tile The tile type to count
	 * @return The number of times it is on the map
	 */
	public int countTile( final char tile ) {
		// check validity of tile
		assertTile( tile );

		int count = 0;
		for( int x = 0; x < width; x++ )
			for( int y = 0; y < height; y++ )
				if( getTile( x, y ) == tile ) count++;
		return count;
	}
	
	/**
	 * Checks if the position is within the grid bounds
	 * 
	 * @param x The horizontal position
	 * @param y The vertical position
	 * @return True iff 0 <= x < width and 0 <= y < height
	 */
	public boolean validPosition( final int x, final int y ) {
		return x >= 0 && x < width && y >= 0 && y < height;
	}
	
	/**
	 * Counts the number of neighbouring tiles of the specified type
	 * 
	 * @param x The horizontal position
	 * @param y The vertical position
	 * @param tile The tile to count
	 * @param diagonal True to include diagonals in the count
	 * @return The number of neighbours
	 */
	public int countNeighbours( final int x, final int y, final char tile, final boolean diagonal ) {
		int count = 0;
		count += (x > 0 && getTile( x - 1, y ) == tile ) ? 1 : 0;
		count += (x < width - 1 && getTile( x + 1, y ) == tile ) ? 1 : 0;
		count += (y > 0 && getTile( x, y - 1 ) == tile ) ? 1 : 0;
		count += (y < height - 1 && getTile( x, y + 1 ) == tile ) ? 1 : 0;
		
		if( !diagonal ) return count;
		
		count += (x > 0 && y > 0 && getTile( x - 1, y - 1 ) == tile ) ? 1 : 0;
		count += (x > 0 && y < height - 1 && getTile( x - 1, y + 1 ) == tile ) ? 1 : 0;
		count += (x < width - 1 && y > 0 && getTile( x + 1, y - 1 ) == tile ) ? 1 : 0;
		count += (x < width - 1 && y < height - 1 && getTile( x + 1, y + 1 ) == tile ) ? 1 : 0;
		return count;
	}
	
	/**
	 * Constructs a grid from a file
	 * 
	 * @param infile The input file to read from
	 * @param tileset The tile set to use in creating the grid
	 * @return A new grid
	 * @throws IOException if file reading failed
	 */
	public static CharGrid fromFile( final String infile, final char[] tileset ) throws IOException {
		final FileReader f = new FileReader( infile ); 
		final List<String> input = f.readLines( );

		// get dimensions first
		final int heigth = input.size( );
		final int width = input.get( 0 ).length( );
		
		// create the map
		final CharGrid grid = new CharGrid( heigth, width, tileset );
		
		// then read the strings and set the tiles at the positions
		for( int y = 0; y < input.size( ); y++ ) {
			final String s = input.get( y );
			for( int x = 0; x < s.length( ); x++ ) {
				final char tile = s.charAt( x );
				if( !grid.hasTile( tile ) ) throw new RuntimeException( "Unknown tile in grid input: " + tile );
				
				grid.setTile( x, y, tile );
			}
		}
		
		return grid;
	}
	
	/**
	 * Copies the grid into a new grid object
	 * 
	 * @return The copy
	 */
	public CharGrid copy( ) {
		return new CharGrid( this );
	}
	
	/**
	 * @return String representation of the grid
	 */
	public String toString( ) {
		String res = "";
		for( int y = 0; y < this.height; y++ ) {
			for( int x = 0; x < this.width; x++ ) {
				res += getTile( x, y );
			}
			if( y < this.height - 1 ) res += "\n";
		}
		return res;
	}
	
	/**
	 * Checks if the tile is part of the tile set
	 * 
	 * @param tile The tile to check
	 */
	protected void assertTile( final char tile ) {
		assert !hasTile( tile ) : "Tile '" + tile + "' not part of the tile set";
	}
}
