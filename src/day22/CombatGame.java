package day22;

import java.util.HashSet;
import java.util.Set;

/**
 * A game of combat where players draw cards
 * 
 * @author Joris
 */
public class CombatGame {
	/** The player decks */
	protected final Deck[] decks;
	
	/** Configurations already seen before */
	protected Set<String> seen;
	
	/** The current round number */
	protected int round;
	
	/**
	 * Creates a new game of combat
	 * 
	 * @param decks The deck to use in the game
	 */
	public CombatGame( final Deck... decks ) {
		this.decks = decks;
		this.round = 0;
	}
		
	/** @return The current round number */
	public int getRound( ) { return this.round; }
	
	/**
	 * Plays recursive combat until a winner has been found
	 * 
	 * @param rec True to play recursive combat, false for simple
	 * @return The index of the winning player 
	 */
	public int play( final boolean rec ) {
		// keep track of configurations already seen in play
		seen = new HashSet<>( );
		
		// play rounds until a winner is determined
		int winner = -1;
		do { winner = round( rec ); } while( winner == -1 );
		
		// return the index of the winning player
		return winner; 
	}
	
	/**
	 * Plays a round of (recursive) combat
	 * 
	 * @param rec True to play recursive style, false for regular
	 * @return The index of the winning player
	 */
	protected int round( final boolean rec ) {
		// check if this deck configuration has already been encountered before
		// (only in recursive games)
		if( rec ) {
			final String s1 = decks[0].toCardString( ), s2 = decks[1].toCardString( );
			if( seen.contains( s1 ) || seen.contains( s2 ) ) return 0;
			seen.add( s1 ); seen.add( s2 );
		}

		// draw a card and check if one of the decks is empty, that means we have a winner
		final int[] c = new int[decks.length];
		for( int i = 0; i < decks.length; i++ ) {
			if( decks[i].isEmpty( ) )
				return 1-i;
			c[i] = decks[i].draw( );
		}
				
		// do we run a sub game to decide the winner?
		final int winner;
		if( rec && c[0] <= decks[0].size( ) && c[1] <= decks[1].size( ) ) {
			// yes!
			final CombatGame g = new CombatGame( decks[0].copy( c[0] ), decks[1].copy( c[1] ) );
			winner = g.play( rec );
		} else {
			// no, just compare cards and the highest card wins
			winner = (c[0] > c[1] ? 0 : 1);
		}

		// update decks according to the winner of the game
		decks[winner].add( c[winner] );
		decks[winner].add( c[1-winner] );

		// increase round number, no winner yet
		this.round++;
		return -1;
	}
}
