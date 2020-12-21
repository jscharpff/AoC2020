package day20.TiledImage;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.Util;
import util.grid.CharGrid;

/**
 * Holds a single tile of the image
 * 
 * @author schar503
 */
public class Tile {
	/** The tile ID */
	protected final int ID;

	/** The tile connections */
	protected final Connector[] connectors;
	
	/** Border facings constants */
	public static final int R_NORTH = 0;
	public static final int R_EAST = 90;
	public static final int R_SOUTH = 180;
	public static final int R_WEST = 270;
	
	/** Its current rotation */
	protected int rotation;
	
	/** Flip options */
	public enum Flip {
		None, Horizontal, Vertical;
		public static char toChar( final Flip f ) {
			switch( f ) {
				case Horizontal: return 'h';
				case Vertical:   return 'v';
				default: return ' ';
			}
		}
	}
	
	/** Flipped or not */
	protected Flip flipped;
	
	/** The image data */
	protected final CharGrid data;
	
	/**
	 * Creates a new image tile
	 * 
	 * @param ID The tile ID
	 * @param image The image data as a char grid
	 */
	public Tile( final int ID, final CharGrid image ) {
		this.ID = ID;
		this.rotation = 0;
		this.flipped = Flip.None;
		
		// create connectors: N, E, S, W
		connectors = new Connector[] {
				new Connector( this, image.getRow( 0 ) ),
				new Connector( this, image.getColumn( image.getWidth( ) - 1 ) ),
				new Connector( this, Util.reverseString( image.getRow( image.getHeight( ) - 1 ) ) ),
				new Connector( this, Util.reverseString( image.getColumn( 0 ) ) )
		};
		
		this.data = image;
	}
	
	/** @return The tile ID */
	public int getID( ) { return ID; }

	/**
	 * Rotates the tile to the specified rotation
	 * 
	 * @param r The rotation
	 */
	public void rotateTo( final int r ) {
		this.rotation = r;
	}
	
	/**
	 * Sets whether the tile is flipped or not
	 * 
	 * @param f True to set the tile as flipped
	 */
	public void setFlipped( final Flip f ) {
		this.flipped = f;
	}
	
	/**
	 * @return The set of its connectors
	 */
	public Connector[] getConnectors( ) { return connectors; }
	
	/**
	 * Returns the connector at the specific border (in degrees)
	 * 
	 * @param border The border in multiples of 90 degrees
	 * @return The connector (currently) at that edge of the tile
	 */
	public Connector getConnector( final int border ) {
		// determine rotation offset due to rotated piece and flipping
		final int R;
		switch( flipped ) {
			case None: R = border - rotation; break;
			case Horizontal: R = 180 - border + rotation; break;
			case Vertical: R = 360 - border + rotation; break;
			default: throw new RuntimeException( "Illegal rotation " + border );
		}
		
		// get the specified border, given the current rotation and flip
		final int idx = (R + 720) % 360 / 90;
		return connectors[idx];
	}
	
	/**
	 * Checks if this tile may connect to another tile at all by quickly checking
	 * for compatible connectors
	 * 
	 * @param tile The other tile
	 * @return True iff there is at least one compatible connector
	 */
	public boolean canConnect( final Tile t ) {
		for( Connector c : connectors )
			for( Connector c2 : t.connectors )
				if( c.canConnect( c2 ) ) return true;
		
		return false;
	}

	/**
	 * Determines the border at which the connector is, given the current 
	 * rotation of the tile
	 * 
	 * @param conn The connector
	 * @return The current border (in degrees) the connector is on 
	 */
	public int getBorder( final Connector conn ) {
		for( int i = 0; i <= 270; i += 90 )
			if( getConnector( i ).equals( conn ) ) return i;
		
		throw new RuntimeException( "Connector is not part of this tile" );
	}
	
	/**
	 * Returns the inner image data (without edges) in the correct orientation and 
	 * flipping
	 * 
	 * @return The image as a character grid
	 */
	public CharGrid getImageData( ) {
		CharGrid grid = new CharGrid( data.getHeight( ) - 2, data.getWidth( ) - 2 );
		
		// copy data first
		for( int x = 1; x < data.getWidth( ) - 1; x++ ) {
			for( int y = 1; y < data.getHeight( ) - 1; y++ ) {
				grid.setTile( x-1, y-1, data.getTile( x, y )	);
			}
		}
		
		// then flip and rotate the grid
		if( flipped != Flip.None) grid = grid.flip( flipped == Flip.Horizontal );
		if( rotation != 0 ) grid = grid.rotate( rotation );
		
		return grid;
	}
	
	/**
	 * Creates a tile from string
	 * 
	 * @param str The tile string, starting with its ID and then the image data per line
	 * @return The tile
	 */
	public static Tile fromString( final String str ) {
		final List<String> strdata = new ArrayList<>( );
		for( String s : str.split( "\n" ) ) strdata.add( s );
		
		// get tile ID
		final String sID = strdata.remove( 0 );
		final Matcher m = Pattern.compile( "Tile (\\d+):" ).matcher( sID );
		if( !m.find( ) ) throw new IllegalArgumentException( "Data should start with tile ID: " + sID );
		final int ID = Integer.valueOf( m.group( 1 ) );
		
		// parse tile data
		final CharGrid grid = CharGrid.fromStringList( strdata );
		
		return new Tile( ID, grid );
	}
	
	/**
	 * @return The string representation of the tile
	 */
	@Override
	public String toString( ) {
		return "Tile " + ID;
	}
	
	
	/**
	 * @return The long string representation of the tile
	 */
	public String toLongString( ) {
		String res = toString( ) + ", rotation " + rotation + ", flipped " + flipped +  ": ";
		for( int i = 0; i <= 270; i += 90 ) {
			final Connector c = getConnector( i );
			res += "\nConnector " + i + ": " + c;
		}
		return res;
	}
	
	
	/**
	 * Checks if this tile is equals to another object
	 * 
	 * @param obj The the other object
	 * @return True iff the other object is a tile and as the same ID
	 */
	@Override
	public boolean equals( Object obj ) {
		if( obj == null || !(obj instanceof Tile) ) return false;
		return ((Tile)obj).getID( ) == ID;
	}
	
	/** @return The hash code (its ID) */
	@Override	public int hashCode( ) { return ID; }
}
