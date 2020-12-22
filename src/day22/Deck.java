package day22;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains a single deck of cards
 * 
 * @author Joris
 */
public class Deck {
	/** The label */
	protected final String label;
	
	/** The deck as a list of single integer value cards */
	protected final List<Integer> cards;
	
	/**
	 * Creates a new deck of cards
	 * 
	 * @param label The label of the deck
	 * @param cards The list of cards in this deck, ordered from top to bottom
	 */
	public Deck( final String label, final List<Integer> cards ) {
		this.label = label;
		this.cards = cards;
	}
	
	/** @ return The label */
	public String getLabel( ) { return label; }

	/**
	 * Draws the top card from the deck
	 * 
	 * @return The integer value of the top card
	 */
	public int draw( ) {
		return cards.remove( 0 );
	}
	
	/**
	 * Adds a card to the bottom of the deck
	 * 
	 * @param card The card to add
	 */
	public void add( final int card ) {
		cards.add( card );
	}
	
	/** @return The number of cards in this deck */
	public int size( ) { return cards.size( ); }
	
	/**
	 * @return True iff the deck is empty
	 */
	public boolean isEmpty( ) {
		return cards.size( ) == 0;
	}
	
	/** @return The deck score */
	public long getScore( ) {
		long score = 0;
		for( int i = cards.size( ) - 1; i >= 0; i-- )
			score += cards.get( i ) * (cards.size( ) - i);
		return score;
	}
	
	/**
	 * Copies the deck
	 * 
	 * @param n Copies only top x cards, -1 for all
	 * @return The copy
	 */
	public Deck copy( final int n ) {
		final List<Integer> cards = new ArrayList<Integer>( );
		final int copies = (n == -1 ? cards.size( ) : n);
		for( int i = 0; i < copies; i++ ) cards.add( this.cards.get( i ) );
		return new Deck( this.getLabel( ), cards );
	}
	
	/**
	 * Creates a card deck from a comma separated string
	 * 
	 * @param deck The string describing the deck, the first element being its label
	 * @return A new deck
	 */
	public static Deck fromString( final String deck ) {
		final String[] ds = deck.split( "," );
		final Deck d = new Deck( ds[0], new ArrayList<Integer>( ds.length - 1 ) );
		for( int i = 1; i < ds.length; i++ ) d.add( Integer.valueOf( ds[i] ) );
		return d;
	}
	
	/**
	 * @return The deck, described by the label and the deck configuration
	 */
	@Override
	public String toString( ) {
		return label + ": " + toCardString( );
	}
	
	/**
	 * @return A simple string describing the deck configuration
	 */
	public String toCardString( ) {
		String res = "";	
		for( int c : cards ) res += c + ",";
		return res;
	}
}
