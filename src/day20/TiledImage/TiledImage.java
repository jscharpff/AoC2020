package day20.TiledImage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import day20.TiledImage.Tile.Flip;
import util.geometry.Coord2D;
import util.grid.CharGrid;

/**
 * Container for a tiled image, but with its tiles out of place!
 * 
 * @author Joris
 */
public class TiledImage {
	/** The set of tiles in this image */
	protected final Set<Tile> tiles;

	/** The reconstructed tile array */
	protected Tile[][] reconstructed;
	
	/** The reconstructed image */
	protected CharGrid image;

	/** Width of reconstructed image */
	protected int width;

	/** Height of the reconstructed image */
	protected int height;
	
	/** Cache of neighbours */
	protected Map<Tile, Set<Tile>> neighbours;

	/**
	 * Creates a new TiledImage
	 * 
	 * @param tiles The image tiles
	 */
	public TiledImage( final Set<Tile> tiles ) {
		this.tiles = tiles;
		this.reconstructed = null;
		this.image = null;
		this.width = -1;
		this.height = -1;

		// build cache of neighbours
		neighbours = new HashMap<Tile, Set<Tile>>( );
		for( final Tile t : tiles ) {
			final Set<Tile> myneighbours = new HashSet<>( );
			for( final Tile t2 : tiles ) {
				if( t.equals( t2 ) ) continue;
				if( t.canConnect( t2 ) ) myneighbours.add( t2 );
			}
			neighbours.put( t, myneighbours );
		}

	}

	/** @return The width of the reconstructed image */
	public int getWidth( ) {
		assert reconstructed != null : "Image not reconstructed yet";
		return width;
	}

	/** @return The height of the reconstructed image */
	public int getHeight( ) {
		assert reconstructed != null : "Image not reconstructed yet";
		return height;
	}

	/**
	 * Find corners by fetching tiles with exactly two neighbours
	 *  
	 * @return List containing the four corner tiles 
	 */
	public List<Tile> getCornerTiles( ) {

		// return tiles that have exactly 2 neighbours
		final List<Tile> corners = new ArrayList<>( 4 );
		for( final Tile t : neighbours.keySet( ) ) if( neighbours.get( t ).size( ) == 2 ) corners.add( t );

		return corners;
	}

	/**
	 * Reconstruct the image by fitting tiles together. First finds out the 
	 * correct placement of all tiles, then reconstructs the image by combining
	 * the tiles into one big image.
	 * 
	 * @return The resulting image (also stored internally for future calls)
	 */
	public CharGrid reconstruct( ) {
		if( this.image != null ) return this.image;
		
		// recursively try to connect tiles to a given starting tile
		this.width = (int)Math.sqrt( tiles.size( ) );
		this.height = this.width;
		this.reconstructed = new Tile[width][height];
		
		// pick the corner point with only connectors possible at EAST and SOUTH
		// and begin tile placement from there
		final Tile start = getStartTile( );
		final Set<Tile> remaining = new HashSet<>( tiles );
		reconstructed[0][0] = start;
		
		tiles.remove( start );	
		
		// fit the tiles
		if( !placeTiles( remaining, 0 ) ) throw new RuntimeException( "Failed to reconstruct image" );
		
		// return result of reconstruction
		return reconstructImage( );
	}
	
	/**
	 * Determine the top left tile to start placement algorithm from
	 *  
	 * @return The tile that is top left, in the correct rotation to only have 
	 * E and S connections possible 
	 */
	protected Tile getStartTile( ) {
		// pick any of the corner tiles and make sure it can only connect at East and South
		final Tile t = getCornerTiles( ).get( 0 );
		for( int rotation = 0; rotation <= 270; rotation += 90 ) {
			t.rotateTo( rotation );
			
			final Connector E = t.getConnector( Tile.R_EAST );
			final Connector S = t.getConnector( Tile.R_SOUTH );
			if( E.canConnectToAny( neighbours.get( t ) ) && S.canConnectToAny( neighbours.get( t ) ) ) {
				return t;
			}
		}
		
		throw new RuntimeException( "Failed to return starting tile" );
	}

	/**
	 * Iteratively places a single tile in the x, y grid by considering the tile
	 * at index idx such that x = idx % width, y = idx / width.
	 * 
	 * @param remaining The set of tiles still awaiting placement
	 * @param idx The index of the current spot to place a new tile
	 * @return True iff the placement was successful, false to indicate a back-track
	 */
	protected boolean placeTiles( final Set<Tile> remaining, final int idx ) {
		// done?
		if( idx == width * height - 1 ) return true;
		
		// get tile at the given position
		final Coord2D pos = new Coord2D( idx % width, idx / width );
		final Coord2D newpos = new Coord2D( (idx+1) % width, (int)(idx+1) / width );
		
		// check what direction to explore
		final int R = (newpos.y > pos.y) ? Tile.R_SOUTH : Tile.R_EAST;
		final Tile curr = (newpos.y > pos.y) ? reconstructed[0][pos.y] : reconstructed[pos.x][pos.y];
		final Connector connA = curr.getConnector( R );

		// determine possible connection configurations
		final List<Configuration> configs = new ArrayList<Configuration>( );
		for( final Tile t : neighbours.get( curr ) )
			if( remaining.contains( t ) )
				configs.addAll( connA.getConnections( t ) );

		// try all configurations on the next position
		for( Configuration cfg : configs ) {
			final Tile newtile = cfg.configureTile( );
			reconstructed[newpos.x][newpos.y] = newtile;
			remaining.remove( newtile );
			if( placeTiles( remaining, idx + 1 ) ) return true;
			remaining.add( newtile );
			reconstructed[newpos.x][newpos.y] = null;
		}
		
		// failed to reach full placement
		return false;
	}	
	
	/**
	 * If the positions and orientations of tiles are known the image can be
	 * reassembled into a big picture
	 * 
	 * @return The image as chargrid
	 */
	protected CharGrid reconstructImage( ) {
		if( this.image != null ) return this.image;
		if( this.reconstructed == null ) throw new RuntimeException( "Tile reconstruction not performed" );
		
		// create grid for redulting image
		final int T_SIZE = 8;
		image = new CharGrid( height * T_SIZE, width * T_SIZE );
		
		// reconstruct all the pieces according to their placement
		for( int x = 0; x < width; x++ ) {
			for( int y = 0; y < height; y++ ) {
				final CharGrid piece = reconstructed[x][y].getImageData( );
				for( int xin = 0; xin < piece.getWidth( ); xin++ ) {
					for( int yin = 0; yin < piece.getHeight( ); yin++ ) {
						image.setTile( x * T_SIZE + xin, y * T_SIZE + yin, piece.getTile( xin, yin ) );
					}
				}
			}
		}
		
		// return result
		return image;
	}

	/**
	 * If the reconstruction was successful this function returns a matrix of 
	 * tile configurations.
	 * 
	 * @return The string representation of the image
	 */
	@Override
	public String toString( ) {
		if( reconstructed == null ) return "Not restored yet, number of tiles " + tiles.size( );

		// return reconstructed tile array
		String res = "";
		for( int y = 0; y < height; y++ ) {
			for( int x = 0; x < width; x++ ) {
				final Tile tile = reconstructed[x][y];
				if( tile == null ) res += "XXXX---- ";
				else {
					res += tile.getID( ) + " " + String.format( "%03d", tile.rotation ) + Flip.toChar( tile.flipped ) + " ";
				}
			}
			res += "\n";
		}
		return res;
	}
}
