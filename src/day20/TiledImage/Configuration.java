package day20.TiledImage;

import day20.TiledImage.Tile.Flip;

/**
 * Describes how tile A can be connected to tile B
 * 
 * @author Joris
 */
public class Configuration {
	/** The connector */
	public final Connector from;

	/** The connector */
	public final Connector to;
	
	/** The required rotation for connector B to connect to A*/
	public final int rotation;
	
	/** The required flip for connector B to connect to A */
	public final Flip flipped;
	
	/**
	 * Creates a new configuration to connect A to B
	 * 
	 * @param connA The initiating connector A
	 * @param connB The receiving connector B
	 * @param rotation The required rotation of the tile of connector B
	 * @param flipped The flip configuration of tile of connector B B
	 */
	protected Configuration( final Connector connA, final Connector connB, final int rotation, final Flip flipped ) {
		this.from = connA;
		this.to = connB;
		this.rotation = rotation;
		this.flipped = flipped;
	}
	
	/**
	 * Applies this configuration to the tile it connects to
	 * 
	 * @return The tile that was reconfigured
	 */
	public Tile configureTile( ) {
		final Tile t = to.tile;
		t.rotateTo( rotation );
		t.setFlipped( flipped );
		return t;
	}

	/**
	 * @return String representation of this configuration
	 */
	@Override
	public String toString( ) {
		return from.tile + " -> " + to.tile;
	}
}
