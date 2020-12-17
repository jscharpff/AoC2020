package day17.sim;

import java.util.HashSet;
import java.util.Set;

import util.geometry.Coord3D;

/**
 * Simulation of energy cubes in 3D space
 * 
 * @author Joris
 */
public class CubeSim {
	/** The active cubes */
	protected final Set<Coord3D> active;

	/** Current simulation round */
	protected int round;
	
	/** Current minimal coordinate space size */
	protected Coord3D min;
	
	/** Current maximal coordinate space size */
	protected Coord3D max;
	
	/**
	 * Creates a new Cube simulation
	 */
	public CubeSim( ) {
		active = new HashSet<>( );
		round = 0;
	}
	
	/**
	 * Initialises the simulation from a 2D slice at given z
	 * 
	 * @param slice The slice as an array per row
	 * @param z The value for the z parameter
	 */
	public void init( final String[] slice, final int z ) {
		// initialise dimensions of the sim
		min = new Coord3D( 0, 0, 0 );
		max = new Coord3D( slice[0].length( ) - 1, slice.length - 1, 0 );
		
		// get all active cubes
		for( int y = 0; y < slice.length; y++ ) {
			final String s = slice[y];
			for( int x = 0; x < s.length( ); x++ )
				if( s.charAt( x ) == '#' ) activate( new Coord3D( x, y, z ) );
		}
		
		// update space bounds
		updateBounds( );
	}
	
	/**
	 * Activates the cube at x, y, z
	 * 
	 * @param coord The coordinate
	 * @return True if newly activated, false otherwise
	 */
	protected boolean activate( final Coord3D coord ) {
		if( active.contains( coord ) ) return false;
		
		// add coordinate and update bounds
		active.add( coord );
		return true;
	}
	
	/**
	 * Updates the min and max coordinates that hold the current space bounds. 
	 * Should be called after changes to the cube list
	 */
	protected void updateBounds( ) {
		for( Coord3D c : active ) {
			this.min = min.min( c );
			this.max = max.max( c );
		}
	}
	
	/**
	 * Checks whether the position contains an active cube
	 * 
	 * @param coord The position
	 * @return True iff the coordinate contains an active cube 
	 */
	public boolean isActive( final Coord3D coord ) {
		return active.contains( coord );
	}
	
	/** @return The set of active cubes */
	public Set<Coord3D> getActive( ) { return active; }
	
	/**
	 * Simulates a round of execution, counts the neighbours of every 3D coordinate
	 * and reconstructs a new active cube list by applying the simulation rules to
	 * this count 
	 * 
	 * @return The round number 
	 */
	public int nextRound( ) { 
		final Set<Coord3D> newactive = new HashSet<>( );
		
		// apply rules to each position, expanding by one in every direction
		for( int z = min.z - 1; z <= max.z + 1; z++ )
			for( int y = min.y - 1; y <= max.y + 1; y++ )
				for( int x = min.x - 1; x <= max.x + 1; x++ ) {
					// get number of neighbouring cubes in current list
					final Coord3D c = new Coord3D( x, y, z );
					final int neighbours = getActiveNeighbours( c );
					if( isActive( c ) && (neighbours == 2 || neighbours == 3 ) ) newactive.add( c );
					if( !isActive( c ) && (neighbours == 3) ) newactive.add( c );
				}
		
		
		// exchange active sets and recompute space bounds
		active.clear( );
		active.addAll( newactive );
		updateBounds( );
				
		return ++round;
	}
	
	/**
	 * Counts the number of active neighbours of the specified position
	 * 
	 * @param pos The position to check
	 * @return The active neighbour count
	 */
	public int getActiveNeighbours( final Coord3D coord ) {
		int count = 0;
		for( int dx = -1; dx <= 1; dx++ )
			for( int dy = -1; dy <= 1; dy++ )
				for( int dz = -1; dz <= 1; dz ++ ) {
					if( dx == 0 && dy == 0 && dz == 0 ) continue; // don't count myself
					count += isActive( coord.move( dx, dy, dz ) ) ? 1 : 0;
				}
		return count;
	}
	
	/** @return The 2D view of active cubes at each Z */
	@Override
	public String toString( ) {
		String res = "[Active cubes " + active.size( ) + "]\n";
		for( int z = min.z; z <= max.z; z++ ) {
			res += "z = " + z + "\n";
			for( int y = min.y; y <= max.y; y++ ) {
				for( int x = min.x; x <= max.x; x++ ) {
					res += isActive( new Coord3D( x, y, z ) ) ? '#' : '.';
				}
				res += "\n";
			}
			res += "\n";
		}
		
		return res;
	}
}
