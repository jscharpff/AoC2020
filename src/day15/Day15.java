package day15;

import java.util.List;

import util.io.FileReader;

public class Day15 {

	/**
	 * Day 15 of the Advent of Code 2020
	 * 
	 * @param args The command line arguments
	 * @throws Exception
	 */
	public static void main( final String[] args ) throws Exception {
		final List<String> ex_input = new FileReader( Day15.class.getResource( "day15_example.txt" ) ).readLines( );
		final List<String> input = new FileReader( Day15.class.getResource( "day15_input.txt" ) ).readLines( );
		
		System.out.println( "---[ Part 1 ]---" );
		System.out.println( "Example: " + getCall( ex_input, 2020 ) ); 
		System.out.println( "Part 1 : " + getCall( input, 2020 ) ); 

		System.out.println( "\n---[ Part 2 ]---" );
		System.out.println( "Example: " + getCall( ex_input, 30000000 ) ); 
		System.out.println( "Part 2 : " + getCall( input, 30000000 ) ); 
	}
	
	/**
	 * Determines the number that is called in the N-th round
	 * 
	 * @param input The starting sequence to initialise the game
	 * @param turn The turn to get the call of
	 * @return The number that is called in the N-th round 
	 */
	protected static int getCall( final List<String> input, final int turn ) {
		// create a new memory game from the input
		final MemGame game = new MemGame( input.get( 0 ) );
		while( game.getTurns( ) < turn ) {
			game.turn();
		}
		
		return game.getLast( );
	}
}
