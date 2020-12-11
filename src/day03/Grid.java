package day03;

import java.io.IOException;
import java.util.List;

import util.geometry.Coord2D;
import util.io.FileReader;

/**
 * Container for boardlike grid
 * 
 * @author Joris
 */
public class Grid {
	/** The grid width */
	protected final int width;
	
	/** The grid height */
	protected final int height;
	
	/** The set of available tiles */
	protected final Tileset tileset;
	
	/** The grid tile set */
	protected final Tile[][] tiles;

	/**
	 * Creates a new grid of width w and height h, using the specified tile set
	 * 
	 * @param heigth The grid height
	 * @param width The grid width
	 * @param tileset The tile set to use
	 */
	public Grid( final int height, final int width, final Tileset tileset ) {
		this.width = width;
		this.height = height;
				
		tiles = new Tile[height][width];
		this.tileset = tileset;
	}
	
	/**
	 * Initialises the entire grid with the specified tile
	 * 
	 * @param tile The tile to fill the map with
	 */
	public void clear( final Tile tile ) {
		for( int y = 0; y < this.height; y++ )
			for( int x = 0; x < this.width; x++ )
				this.tiles[y][x] = tile;
	}
	
	/**
	 * Returns the tile at the specified position
	 * 
	 * @param coord The coordinate
	 * @return The tile at that position
	 */
	public Tile getTile( final Coord2D coord ) {
		return this.getTile( coord.x % getWidth( ), coord.y % getHeight( ) );
	}
	
	/**
	 * Returns the tile at the specified position
	 * 
	 * @param x The horizontal position
	 * @param y The vertical position
	 * @return The tile at that position
	 */
	public Tile getTile( final int x, final int y ) {
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
	public Tile setTile( final int x, final int y, final Tile tile ) {
		final Tile old = tiles[y][x];
		tiles[y][x] = tile;
		return old;
	}
	
	/** @return The width of the grid */
	public int getWidth( ) { return this.width; }
	
	/** @return The height of the grid */
	public int getHeight( ) { return this.height; }
	
	/** @return The tileset */
	public Tileset getTileset( ) { return this.tileset; }
	
	/**
	 * Counts the occurrence of a tile
	 * 
	 * @param tile The tile type to count
	 * @return The number of times it is on the map
	 */
	public int countTile( final Tile tile ) {
		int count = 0;
		for( int x = 0; x < width; x++ )
			for( int y = 0; y < height; y++ )
				if( getTile( x, y ).equals( tile ) ) count++;
		return count;
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
	public int countNeighbours( final int x, final int y, final Tile tile, final boolean diagonal ) {
		int count = 0;
		count += (x > 0 && getTile( x - 1, y ).equals( tile ) ) ? 1 : 0;
		count += (x < width - 1 && getTile( x + 1, y ).equals( tile ) ) ? 1 : 0;
		count += (y > 0 && getTile( x, y - 1 ).equals( tile ) ) ? 1 : 0;
		count += (y < height - 1 && getTile( x, y + 1 ).equals( tile ) ) ? 1 : 0;
		
		if( !diagonal ) return count;
		
		count += (x > 0 && y > 0 && getTile( x - 1, y - 1 ).equals( tile ) ) ? 1 : 0;
		count += (x > 0 && y < height - 1 && getTile( x - 1, y + 1 ).equals( tile ) ) ? 1 : 0;
		count += (x < width - 1 && y > 0 && getTile( x + 1, y - 1 ).equals( tile ) ) ? 1 : 0;
		count += (x < width - 1 && y < height - 1 && getTile( x + 1, y + 1 ).equals( tile ) ) ? 1 : 0;
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
	public static Grid fromFile( final String infile, final Tileset tileset ) throws IOException {
		final FileReader f = new FileReader( infile ); 
		final List<String> input = f.readLines( );

		// get dimensions first
		final int heigth = input.size( );
		final int width = input.get( 0 ).length( );
		
		// create the map
		final Grid grid = new Grid( heigth, width, tileset );
		
		// then read the strings and set the tiles at the positions
		for( int y = 0; y < input.size( ); y++ ) {
			final String s = input.get( y );
			for( int x = 0; x < s.length( ); x++ ) {
				final char tilelabel = s.charAt( x );
				final Tile tile = tileset.getTile( tilelabel );
				if( tile == null ) throw new RuntimeException( "Unknown tile in grid input: " + tilelabel );
				
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
	public Grid copy( ) {
		final Grid g = new Grid( this.height, this.width, this.tileset );
		for( int x = 0; x < this.width; x++ )
			for( int y = 0; y < this.height; y++ )
				g.setTile( x, y, this.getTile( x, y ).copy( ) );
		return g;
	}
	
	/**
	 * @return String representation of the grid
	 */
	public String toString( ) {
		String res = "";
		for( int y = 0; y < this.height; y++ ) {
			for( int x = 0; x < this.width; x++ ) {
				res += getTile( x, y ).toString( );
			}
			if( y < this.height - 1 ) res += "\n";
		}
		return res;
	}
}
