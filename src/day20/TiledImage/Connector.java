package day20.TiledImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import day20.TiledImage.Tile.Flip;
import util.Util;

/**
 * Represents a tile connector
 * 
 * @author Joris
 */
public class Connector {
	/** The tile it belongs to */
	protected final Tile tile;
	
	/** The connector checksum */
	protected final int checksum;
	
	/** Its inverted checksum */
	protected final int checksumflipped;
	
	/**
	 * Creates a new tile connector from a string description
	 * 
	 * @param tile The tile it belongs to
	 * @param rotation The border of the tile it represents, in terms of degrees
	 * @param string The string that contains image data
	 */
	public Connector( final Tile tile, final String string ) {		
		this.tile = tile;
		
		this.checksum = computeChecksum( string );
		this.checksumflipped = computeChecksum( Util.reverseString( string ) );
	}
	
	/**
	 * Computes the check sum of a given string that represents a tile border
	 * 
	 * @param strdata The border as string of "." and "#" tokens
	 * @return The checksum
	 */
	protected int computeChecksum( final String data ) {		
		// every "#" is a one in a byte array of length x
		int chksum = 0;
		for( int i = data.length( ) - 1; i >= 0; i-- ) {
			if( data.charAt( i ) == '#' ) chksum += (int)Math.pow( 2.0, data.length( ) - i - 1 );
		}

		return chksum;
	}
	
	/** @return The tile it belongs to */
	public Tile getTile( ) { return this.tile; }
	
	/**
	 * Quick check if this connector may connect to the other at all (without
	 * respecting configuration)
	 * 
	 * @param conn The other connector
	 * @return True iff they may connect in at least one configuration
	 */
	public boolean canConnect( final Connector conn ) {
		return conn.checksum == this.checksum || conn.checksum == this.checksumflipped;
	}
	
	/**
	 * Quick check if this connector can connect to the other tile
	 * 
	 * @param tile The tile to check
	 * @return True if at least one of its connectors may connect to this connector
	 */
	public boolean canConnectTo( final Tile tile ) {
		for( Connector c : tile.getConnectors( ) )
			if( this.canConnect( c ) ) return true;
		return false;
	}
	
	/**
	 * Quick check to determine whether the connector can connect to any of the tiles
	 * connectors
	 * 
	 * @param tiles The set of tiles to check compatibility with
	 * @return True if any of the tiles may connect to this connector
	 */
	public boolean canConnectToAny( final Set<Tile> tiles ) {
		for( final Tile t : tiles ) {
			if( canConnectTo( t ) ) return true;
		}
		return false;
	}
	
	/**
	 * Determines set of configurations that may connect this connector to 
	 * the other tile. Returns an empty set if no connection is possible
	 * 
	 * @param tile The tile to connect to
	 * @return List of configuration options
	 */
	public List<Configuration> getConnections( final Tile tile ) {
		final List<Configuration> configs = new ArrayList<>( );

		// quickly check if a connection is possible at all
		if( !canConnectTo( tile ) ) return configs;
		
		// get the current orientation of the connector
		final int R = this.tile.getBorder( this );
		
		// try all configurations of the other tile
		for( final Flip flipped : Flip.values( ) ) {
			tile.setFlipped( flipped );
			for( int rotation = 0; rotation <= 270; rotation += 90 ) {
				tile.rotateTo( rotation );
				
				final Connector connB = tile.getConnector( (R - 180 + 360) % 360 );
				if( !fits( connB ) ) continue;
				
				// it fits, add to the list of configurations
				final Configuration cfg = new Configuration( this, connB, rotation, flipped );
				configs.add( cfg );
			}
		}		
		
		return configs;
	}
	
	/**
	 * Computes the check sum, given the current tile configuration
	 * 
	 * @param inverted True to get the inverted checksum (e.g. for matching other pieces)
	 * @return The correct checksum of the connector
	 */
	protected int getCheckSum( final boolean inverted ) {
		switch( tile.flipped ) {
			case None: return (inverted ? checksum : checksumflipped);
			case Horizontal:
			case Vertical:
				return (inverted ? checksumflipped : checksum );
			default:
				throw new RuntimeException( "Invalid flipped value " + tile.flipped );
		}
	}
	
	/**
	 * Checks if this connector can fit to the other connector in the specified 
	 * configuration
	 * 
	 * @param conn The other connection to check
	 * @return True if they fit, false otherwise
	 */
	public boolean fits( final Connector conn ) {
		// my checksum should match the inverted checksum of the other side
		return getCheckSum( false ) == conn.getCheckSum( true );
	}


	/**
	 * @return String representing the connector (i.e. its checksum)
	 */
	@Override
	public String toString( ) {
		return getCheckSum( false ) + "/" + getCheckSum( true );
	}
	
	/**
	 * Checks if this connector is equal to another connector
	 * 
	 * @param obj The other object
	 * @return Iff the other object is a Connector and its checksum matches
	 */
	@Override
	public boolean equals( Object obj ) {
		if( obj == null || !(obj instanceof Connector) ) return false;
		return ((Connector)obj).checksum == checksum;
	}
}
