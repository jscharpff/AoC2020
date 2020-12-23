package day23;

import java.util.Collection;
import java.util.List;
import java.util.Stack;

import day23.rotatinglist.IndexedRotatingList;

/**
 * A game of cups!
 * 
 * @author Joris
 */
public class CupGame {
	/** Current ordering of cups */
	protected final IndexedRotatingList<Integer> cups;
	
	/** The current cup to use (index) */
	protected int curr;
	
	/** The maximal cup value */
	protected final int highest;
	
	/** The minimal cup value */
	protected final int lowest;
	
	/** The current round number */
	protected int round;
	
	/** 
	 * Creates a new cups game
	 * 
	 * @param cups The list of cups
	 */
	public CupGame( final Collection<Integer> cups ) {
		this.cups = new IndexedRotatingList<>( cups );
		int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
		for( int i : cups ) {
			if( i < min ) min = i;
			if( i > max ) max = i;
		}
		this.lowest = min;
		this.highest = max;
		
		this.round = 0;
	}
	
	/** @return The current cup ordering */
	public List<Integer> getCups( ) { return cups.toList( -1 ); }
	
	/** @return The current round number */
	public int getRound( ) { return round; }

	
	/**
	 * Plays n rounds of the game
	 * 
	 * @param n The number of roudns to play
	 */
	public void play( final int n ) {
		while( round( ) < n ) { };
	}
	
	/**
	 * Plays a single round of the game
	 * 
	 * @return The new round number
	 */
	public int round( ) {		
		// get current cup
		final int cup = cups.getCurrent( );
	
		// displace the three elements to its right
		final Stack<Integer> move = cups.removeAfter( 3 );
		
		// get index of new position, equal to the next available cup lower than curr
		int destcup = cup;
		do {
			destcup--;
			if( destcup < lowest ) destcup = highest;
		} while( move.contains( destcup ) );

		// reinsert the stack
		while( move.size( ) > 0 ) cups.add( destcup, move.pop(  ) );
		
		// set next element as current
		cups.next( );
		return ++round;
	}
	
	/**
	 * Returns the n cups after the cup with value i
	 * 
	 * @param i The value
	 * @param n The number of cups to return
	 * @return List of cup values
	 */
	public List<Integer> getCups( final int i, final int n ) {
		// set current to match the item
		cups.setCurrent( i );
		
		final List<Integer> list = cups.toList( n + 1 );
		list.remove( 0 );
		
		return list;
	}
	
}
