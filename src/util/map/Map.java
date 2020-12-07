package util.map;

import java.io.IOException;
import java.util.List;

import util.FileReader;
import util.geometry.Coord2D;

/**
 * Container for boardlike maps
 * 
 * @author Joris
 */
public class Map {
	/** The map width */
	protected final int width;
	
	/** The map height */
	protected final int height;
	
	/** The set of available tiles */
	protected final Tileset tileset;
	
	/** The map tile set */
	protected final Tile[][] tiles;

	/**
	 * Creates a new map of width w and height h, using the specified tile set
	 * 
	 * @param heigth The map height
	 * @param width The map width
	 * @param tileset The tile set to use
	 */
	public Map( final int height, final int width, final Tileset tileset ) {
		this.width = width;
		this.height = height;
				
		tiles = new Tile[height][width];
		this.tileset = tileset;
	}
	
	/**
	 * Initialises the entire map with the specified tile
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
	protected Tile getTile( final int x, final int y ) {
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
	protected Tile setTile( final int x, final int y, final Tile tile ) {
		final Tile old = tiles[y][x];
		tiles[y][x] = tile;
		return old;
	}
	
	/** @return The width of the map */
	public int getWidth( ) { return this.width; }
	
	/** @return The height of the map */
	public int getHeight( ) { return this.height; }
	
	/**
	 * Constructs a map from a list of strings that describes the rows of the map
	 * 
	 * @param infile The input file to read from
	 * @param tileset The tile set to use in creating the map
	 * @return A new map
	 * @throws IOException if file reading failed
	 */
	public static Map fromFile( final String infile, final Tileset tileset ) throws IOException {
		final FileReader f = new FileReader( infile ); 
		final List<String> input = f.readLines( );
		

		// get dimensions first
		final int heigth = input.size( );
		final int width = input.get( 0 ).length( );
		
		// create the map
		final Map map = new Map( heigth, width, tileset );
		
		// then read the strings and set the tiles at the positions
		for( int y = 0; y < input.size( ); y++ ) {
			final String s = input.get( y );
			for( int x = 0; x < s.length( ); x++ ) {
				final char tilelabel = s.charAt( x );
				final Tile tile = tileset.getTile( tilelabel );
				if( tile == null ) throw new RuntimeException( "Unknown tile in map input: " + tilelabel );
				
				map.setTile( x, y, tile );
			}
		}
		
		return map;
	}
	
	/**
	 * @return String representation of the map
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
