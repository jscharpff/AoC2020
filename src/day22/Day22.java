package day22;

import java.util.List;

import util.io.FileReader;

public class Day22 {
	/**
	 * Day 22 of the Advent of Code 2020
	 * 
	 * @param args The command line arguments
	 * @throws Exception
	 */
	public static void main( final String[] agrs ) throws Exception {
		final List<String> ex_input = new FileReader( Day22.class.getResource( "day22_example.txt" ) ).readLineGroups( "," );
		final List<String> input = new FileReader( Day22.class.getResource( "day22_input.txt" ) ).readLineGroups( "," );
		
		System.out.println( "---[ Part 1 ]---" );
		System.out.println( "Example: " + play( ex_input, false ) );
		System.out.println( "Part 1 : " + play( input, false ) );

		System.out.println( "\n---[ Part 2 ]---" );
		System.out.println( "Example: " + play( ex_input, true ) );
		System.out.println( "Part 2 : " + play( input, true ) );
	}

	/**
	 * Plays the Combat Game
	 * 
	 * @param input The input describing the decks of cards
	 * @param recursive True to play recursive variant, false for regular
	 * @return The score of the winning deck
	 */
	protected static long play( final List<String> input, final boolean recursive ) {
		// initialise the decks and the game
		final Deck[] decks = new Deck[] { Deck.fromString( input.get( 0 ) ), Deck.fromString( input.get( 1 ) ) };
		final CombatGame game = new CombatGame( decks );
		
		// determine the winner by playing the game and return score of winning deck
		final int winner = game.play( recursive );
		return decks[winner].getScore( );
	}
}
