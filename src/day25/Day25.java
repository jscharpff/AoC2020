package day25;

import java.util.List;

import util.io.FileReader;

public class Day25 {
	/**
	 * Day 25 of the Advent of Code 2020 and also the last day :(
	 * 
	 * @param args The command line arguments
	 * @throws Exception
	 */
	public static void main( final String[] args ) throws Exception {
		final List<String> ex_input = new FileReader( Day25.class.getResource( "day25_example.txt" ) ).readLines( );
		final List<String> input = new FileReader( Day25.class.getResource( "day25_input.txt" ) ).readLines( );
		
		System.out.println( "---[ Part 1 ]---" );
		System.out.println( "Example: " + part1( ex_input) );
		System.out.println( "Part 1 : " + part1( input) );

		// no part 2 today
	}
	
	protected static long part1( final List<String> input ) {
		// create the two keys
		final Key card = new Key( Integer.valueOf( input.get( 0 ) ) );
		final Key door = new Key( Integer.valueOf( input.get( 1 ) ) );
		
		// compute their secret loop number
		final int cardloops = card.getLoopsize( 7, card.key );
		final int doorloops = door.getLoopsize( 7, door.key );

		// return the encryption key by performing the lowest number of loops
		return cardloops < doorloops ? card.loop( door.key, cardloops ) : door.loop( card.key, doorloops );
	}
}
