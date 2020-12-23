package day23;

import java.util.ArrayList;
import java.util.List;

import util.io.FileReader;

public class Day23 {
	private static final int MILLION = 1000000;
	/**
	 * Day 23 of the Advent of Code 2020
	 * 
	 * @param args The command line arguments
	 * @throws Exception
	 */
	public static void main( final String[] args ) throws Exception {
		final String ex_input = new FileReader( Day23.class.getResource( "day23_example.txt" ) ).readLines( ).get( 0 );
		final String input = new FileReader( Day23.class.getResource( "day23_input.txt" ) ).readLines( ).get( 0 );
		
		System.out.println( "---[ Part 1 ]---" );
		System.out.println( "Example: " + part1( ex_input, 10 ) );
		System.out.println( "Example: " + part1( ex_input, 100 ) );
		System.out.println( "Part 1 : " + part1( input, 100 ) );
		
		System.out.println( "\n---[ Part 2 ]---" );
		System.out.println( "Example: " + part2( ex_input, MILLION * 10 ) );
		System.out.println( "Part 2 : " + part2( input, MILLION * 10 ) );
	}
	
	/**
	 * Part 1: play n rounds of the game and return the resulting configuration,
	 *  starting from the cup labelled 1 
	 *  
	 * @param input The ordering of 
	 * @param n The number of rounds to play
	 * @return The final configuration of cups from label 1 onwards (excluding 1)
	 */
	protected static String part1( final String input, final int n ) {
		// convert input to int list
		final List<Integer> cups = new ArrayList<Integer>( input.length( ) );
		for( int i = 0; i < input.length( ); i++ ) cups.add( (int)input.charAt( i ) - 48 );
		
		// play rounds
		final CupGame game = new CupGame( cups );
		game.play( n );

		// get all cups after cup 1
		String res = "";
		for( int cup : game.getCups( 1, input.length( ) - 1 ) )
			res += "" + cup;
		return res;
	}

	/**
	 * Part 2: play the game for a very large number of rounds. Also, there are a
	 *  million cups now
	 * 
	 * @param input The configuration of cups 1-9, the rest is filled in orderly fashion
	 * @param n The number of rounds to play
	 * @return The product of the two cups directly after label 1 in the final configuration
	 */
	protected static long part2( final String input, final int n ) {
		// convert input to int list and fill with numbers 10 till 1000000
		final List<Integer> cups = new ArrayList<Integer>( MILLION );
		for( int i = 0; i < input.length( ); i++ ) cups.add( (int)input.charAt( i ) - 48 );
		for( int c = 10; c <= MILLION; c++ ) cups.add( c );
		
		// play rounds
		final CupGame game = new CupGame( cups );
		game.play( n );

		// get the two cups after cup 1
		final List<Integer> list = game.getCups( 1, 2 );		
		return (long)list.get( 0 ) * (long)list.get( 1 );
	}
}
