package day21;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AllergenRule {
	/** The list of ingredients */
	protected final Set<String> ingredients;
	
	/** The allergens they may contain */
	protected final Set<String> allergens;
	
	/**
	 * Creates a new allergen rule
	 * 
	 * @param ingredients The ingredients list
	 * @param allergens The known allergens list
	 */
	public AllergenRule( final Set<String> ingredients, final Set<String> allergens ) {
		this.ingredients = ingredients;
		this.allergens = allergens;
	}
	
	/** @return The ingredient set of this rule */
	protected Set<String> getIngredients( ) { return ingredients; }
	
	/** The allergens in this rule */
	protected Set<String> getAllergens( ) { return allergens; }
	
	/**
	 * Creates the allergen rule from a string
	 * 
	 * @param string The string description
	 * @return The allergen rule
	 */
	public static AllergenRule fromString( final String string ) {
		final Matcher m = Pattern.compile( "([\\w ]+)\\(contains ([\\w, ]+)\\)" ).matcher( string );
		if( !m.find( ) ) throw new RuntimeException( "Invalid allergen rule: " + string );
		
		// first group contains ingredients list
		final Set<String> ingredients = new HashSet<>( );
		for( final String i : m.group( 1 ).trim( ).split( " " ) )
			ingredients.add( i );
		
		// second group contains allergens
		final Set<String> allergens = new HashSet<>( );
		for( final String a : m.group( 2 ).trim( ).split( ", " ) )
			allergens.add( a );
		
		return new AllergenRule( ingredients, allergens );
	}
	
	/**
	 * @return A copy of the rule
	 */
	public AllergenRule copy( ) {
		return new AllergenRule( new HashSet<String>( ingredients ), new HashSet<String>( allergens ) );
	}
	
	/**
	 * Combines both rules, keeps only shared ingredients and allergens
	 * 
	 * @param r The other rule
	 * @return A new rule that contains only the shared ingredients and allergens
	 */
	public AllergenRule union( final AllergenRule r ) {
		final Set<String> ingredients = new HashSet<>( this.ingredients );
		ingredients.retainAll( r.getIngredients( ) );
		final Set<String> allergens = new HashSet<>( this.allergens );
		allergens.retainAll( r.getAllergens( ) );
		return new AllergenRule( ingredients, allergens );
	}
	
	/**
	 * Removes the ingredient and allergen from this rule (if present)
	 * 
	 * @param ingredient The ingredient to remove
	 * @param allergen The allergen to remove
	 */
	public void remove( final String ingredient, final String allergen ) {
		ingredients.remove( ingredient );
		allergens.remove( allergen );
	}
	
	@Override
	public String toString( ) {
		return ingredients + " (contains " + allergens + ")";
	}
	
	@Override
	public boolean equals( Object obj ) {
		if( obj == null || !(obj instanceof AllergenRule) ) return false;
		final AllergenRule a = (AllergenRule)obj;
		
		return ingredients.containsAll( a.ingredients ) && allergens.containsAll( a.allergens );
	}
}
