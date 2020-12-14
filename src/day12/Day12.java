package day12;

import java.util.List;

import day12.ship.GuidedShip;
import day12.ship.Ship;
import util.geometry.Coord2D;
import util.io.FileReader;

public class Day12 {
	/**
	 * Day 12 of the Advent of Code 2020
	 * 
	 * @param args The command line arguments
	 * @throws Exception
	 */
	public static void main( final String[] args ) throws Exception {
		final List<String> ex_input = new FileReader( Day12.class.getResource( "day12_example.txt" ) ).readLines( );
		final List<String> input = new FileReader( Day12.class.getResource( "day12_input.txt" ) ).readLines( );
		
		System.out.println( "---[ Part 1 ]---" );
		System.out.println( "Example: " + getDistance( ex_input, null ) );
		System.out.println( "Part 1 : " + getDistance( input, null ) );

		System.out.println( "\n---[ Part 2 ]---" );
		final Coord2D wpoint = new Coord2D( 10, -1 );
		System.out.println( "Example: " + getDistance( ex_input, wpoint ) );
		System.out.println( "Part 2 : " + getDistance( input, wpoint ) );
	}

	/**
	 * Process commands to move the ship, the use of a waypoint depends on whether 
	 * it is specified in the function call
	 * 
	 * @param moves The list of move commands
	 * @param startwp The waypoint start coordinate, null to not use a waypoint
	 * @return The Manhattan distance
	 */
	protected static int getDistance( final List<String> moves, final Coord2D startwp ) {
		final Coord2D start = new Coord2D( 0, 0 );
		final Ship ship = startwp == null ? new Ship( start, Ship.DIR_EAST ) : new GuidedShip( start, startwp );
		
		for( String m : moves )
			ship.doAction( m );
		
		return start.getManhattanDistance( ship.getPosition( ) );
	}
}
