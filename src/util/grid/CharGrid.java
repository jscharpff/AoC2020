package util.grid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import util.geometry.Coord2D;
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
	
	/** The grid tile set */
	protected final char[][] tiles;

	/**
	 * Creates a new grid of width w and height h, using the specified tile set
	 * 
	 * @param heigth The grid height
	 * @param width The grid width
	 */
	public CharGrid( final int height, final int width ) {
		this.width = width;
		this.height = height;
				
		tiles = new char[height][width];
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
		final char old = tiles[y][x];
		tiles[y][x] = tile;
		return old;
	}
	
	/** @return The width of the grid */
	public int getWidth( ) { return this.width; }
	
	/** @return The height of the grid */
	public int getHeight( ) { return this.height; }
	
	/**
	 * Counts the occurrence of a tile
	 * 
	 * @param tile The tile type to count
	 * @return The number of times it is on the map
	 */
	public int countTile( final char tile ) {
		int count = 0;
		for( int x = 0; x < width; x++ )
			for( int y = 0; y < height; y++ )
				if( getTile( x, y ) == tile ) count++;
		return count;
	}

	/**
	 * Returns the specified row as string
	 * 
	 * @param row The row number
	 * @return The row data as string
	 */
	public String getRow( final int row ) {
		assert row > 0 && row < height : "Invalid row number";
		
		String rowdata = "";
		for( int x = 0; x < width; x++ ) rowdata += getTile( x, row );
		return rowdata;
	}
	
	/**
	 * Returns the specified column as string
	 * 
	 * @param column The column number
	 * @return The column data as string
	 */
	public String getColumn( final int column ) {
		assert column > 0 && column < width : "Invalid column number";
		
		String coldata = "";
		for( int y = 0; y < height; y++ ) coldata += getTile( column, y );
		return coldata;
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
	 * Creates a copy of the grid that is flipped along the specified orientation
	 * 
	 * @param horizontal True for horizontal flip, false for vertical
	 * @return The copied grid
	 */
	public CharGrid flip( final boolean horizontal ) {
		final CharGrid grid = copy( );
		for( int x = 0; x < width; x++ )
			for( int y = 0; y < height; y++ )
				grid.setTile( (horizontal ? x : width - x - 1), (horizontal ? height - y - 1 : y), getTile( x, y ) );
		return grid;
	}
	
	/**
	 * Creates a copy of the grid that is rotated
	 * 
	 * @param rotation The rotation in degrees (multiples of 90)
	 * @return The rotated grid
	 */
	public CharGrid rotate( final int rotation ) {
		final CharGrid grid = copy( );
		
		// no rotation required?
		if( rotation == 0 ) return grid;
		
		for( int x = 0; x < width; x++ )
			for( int y = 0; y < height; y++ ) {
				final int newx;
				final int newy;
				if( rotation == 90 ) {
					newx = width - y - 1;
					newy = x;
				} else if( rotation == 180 ) {
					newx = width - x - 1;
					newy = height - y - 1;
				} else if( rotation == 270 ) {
					newx = y;
					newy = height - x - 1;
				} else throw new RuntimeException( "Invalid rotation " + rotation );
				
				grid.setTile( newx, newy, getTile( x, y ) );
			}
		
		// return the grid
		return grid;
	}
	
	
	/**
	 * Constructs a grid from a file
	 * 
	 * @param infile The input file to read from
	 * @param tileset The tile set to use in creating the grid
	 * @return A new grid
	 * @throws IOException if file reading failed
	 */
	public static CharGrid fromFile( final String infile ) throws IOException {
		final FileReader f = new FileReader( infile ); 
		final List<String> input = f.readLines( );

		return CharGrid.fromStringList( input );
	}
	
	/**
	 * Creates a char grid from a string list
	 * 
	 * @param input The string list that contains the grid data
	 * @param tileset The set of tiles accepted in creating the grid
	 * @return The grid
	 */
	public static CharGrid fromStringList( final List<String> input ) {	
		// get dimensions first
		final int heigth = input.size( );
		final int width = input.get( 0 ).length( );
		
		// create the map
		final CharGrid grid = new CharGrid( heigth, width );
		
		// then read the strings and set the tiles at the positions
		for( int y = 0; y < input.size( ); y++ ) {
			final String s = input.get( y );
			for( int x = 0; x < s.length( ); x++ ) {
				final char tile = s.charAt( x );
				
				grid.setTile( x, y, tile );
			}
		}
		
		return grid;
	}
	
	/**
	 * Finds any matches of the specified chargrid's characters within the current grid
	 * 
	 * @param grid The char grid to match
	 * @param chars The characters that must match, others are considered wildcards
	 * @return List of coordinates at which the grid is found 
	 */
	public List<Coord2D> findMatches( final CharGrid grid, final char[] chars ) {
		final List<Coord2D> matches = new ArrayList<>( );
		
		// build set of match characters, easier to check
		final Set<Character> mchars = new HashSet<>( );
		for( char c : chars ) mchars.add( c );
		
		// go over all positions to check for possible match
		for( int x = 0; x < width - grid.getWidth( ); x++ ) {
			for( int y = 0; y < height - grid.getHeight( ); y++ ) {
				// do inner comparison
				boolean match = true;
				for( int xin = 0; xin < grid.getWidth( ) && match; xin++ ) {
					for( int yin = 0; yin < grid.getHeight( ) && match; yin++ ) {
						final char c1 = getTile( x + xin, y + yin );
						final char c2 = grid.getTile( xin, yin );
						// match char?
						if( !mchars.contains( c2 ) ) continue;
						
						// yes, check if matches
						if( c1 != c2 ) match = false;
					}
				}
				// all required characters matched, add to list
				if( match ) matches.add( new Coord2D( x, y ) );
			}
		}
		
		return matches;
	}
	
	/**
	 * Inserts the specified pattern at the location, used pchar as char toi match
	 * in the pattern, which is replaced by rchar
	 * 
	 * @param grid The grid to insert
	 * @param c The position to insert it at
	 * @param pchar The pattern char
	 * @param rchar The replacement char
	 * @return Copy of the girid with the inserted chargrid 
	 */
	public CharGrid insertPattern( final CharGrid grid, final Coord2D pos, final char pchar, final char rchar ) {
		final CharGrid g = copy( ); 
		
		// apply pattern
		for( int x = 0; x < grid.getWidth( ); x++ ) {
			for( int y = 0; y < grid.getHeight( ); y++ ) {
				final char c = grid.getTile( x, y );
				if( c == pchar ) g.setTile( pos.x + x, pos.y + y, rchar );
			}
		}
		
		return g;
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
}
