package day15;

import java.util.HashMap;
import java.util.Map;

/**
 * A memory game played by the Elves
 * 
 * @author Joris
 */
public class MemGame {
	/** The current turn */
	protected int turn;
	
	/** The previously called number */
	protected int last;
	
	/** The history of when a number was called last */
	protected Map<Integer, Integer> hist;
	
	/**
	 * Creates a new game
	 * 
	 * @param startseq The starting sequence of numbers to initialise the game
	 */
	public MemGame( final String startseq ) {
		this.turn = -1;
		this.last = -1;
		this.hist = new HashMap<>( );
		
		init( startseq );
	}
	
	/**
	 * Initialises the game through the starting sequence of numbers
	 * 
	 * @param seq The starting sequence, comma separated
	 */
	protected void init( final String seq ) {
		// start in turn 0 and process called numbers as is, simply initialising the history
		turn = 0;
		final String[] numbers = seq.split( "," );
		for( String s : numbers ) {
			turn++;
			hist.put( Integer.valueOf( s ), turn );
		}

		// store last called number for use in next round
		last = Integer.valueOf( numbers[ numbers.length - 1 ] ); 
	}
	
	
	/**
	 * Plays one turn of the game, and returns the new number that is called. This
	 * number is 0 if the previous call is not seen before or the current turn
	 * minus the last time it was called
	 * 
	 * @return The number that is called out
	 */
	public int turn( ) {
		// determine new call value
		final int newcall = hist.containsKey( last ) ? turn - hist.get( last ) : 0;

		// set the turn as new value in the history for the call
		hist.put( last, turn );
		
		// now update last called value and increase round number 
		last = newcall;
		turn++;
		return newcall;
	}
	
	/** @return The number of played turns */
	public int getTurns( ) { return turn; }
	
	/** @return The last called value */
	public int getLast( ) { return last; }
}
