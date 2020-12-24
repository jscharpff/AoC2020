package day24;

import java.util.List;

import util.io.FileReader;

public class Day24 {
	/**
	 * Day 24 of the Advent of Code 2020
	 * 
	 * @param args The command line arguments
	 * @throws Exception
	 */
	public static void main( final String[] args ) throws Exception {
		final List<String> ex_input = new FileReader( Day24.class.getResource( "day24_example.txt" ) ).readLines( );
		final List<String> input = new FileReader( Day24.class.getResource( "day24_input.txt" ) ).readLines( );
		
		System.out.println( "---[ Part 1 ]---" );
		System.out.println( "Example: " + part1( ex_input ) );
		System.out.println( "Part 1 : " + part1( input ) );
		
		System.out.println( "\n---[ Part 2 ]---" );
		System.out.println( "Example: " + part2( ex_input, 100 ) );
		System.out.println( "Part 2 : " + part2( input, 100 ) );
	}
	
	/**
	 * Part 1: flip the tiles at the given positions, identified through a series of
	 *   moves. Return the number of black tiles. 
	 *   
	 * @param input The list of moves per tile
	 * @return The number of black tiles after all moves have been processed
	 */
	protected static long part1( final List<String> input ) {
		// create new floor plan and add the tiles
		final HexFloor floor = new HexFloor( );
		for( final String s : input ) floor.addTile( s );
		return floor.countBlack( );
	}

	/**
	 * Part 2: start the same as part 1 but then update the floor plan every day
	 * 	through a set of rules that depend on the number of neighbouring black
	 *  tiles. Returns the number of black tiles after N days of simulation  
	 * 
	 * @param input The initial tile set as a series of moves
	 * @param days The number of days to simulate
	 * @return The number of black tiles after N days of simulation
	 */
	protected static long part2( final List<String> input, final int days ) {
		// create new floor plan and add the tiles
		final HexFloor floor = new HexFloor( );
		for( final String s : input ) floor.addTile( s );
		
		// simulate floor art
		for( int day = 0; day < days; day++ ) {
			floor.flipTiles( );
		}
		
		// return the number of black tiles
		return floor.countBlack( );
	}

}
