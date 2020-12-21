package day21;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class AllergenInfo {
	/** The set of allergen information rules */
	protected final List<AllergenRule> rules;
	
	/** The ingredient list, including mapping to allergen */
	protected final Map<String, String> allergenmap;

	/** All known ingredients */
	protected final Set<String> ingredients;

	/** All known allergens */
	protected final Set<String> allergens;

	/**
	 * Creates a new allergen information container from a string of rules
	 */
	protected AllergenInfo( final List<String> strrules ) {
		rules = new ArrayList<>( );
		allergenmap = new HashMap<String, String>( );
		ingredients = new HashSet<String>( );
		allergens = new HashSet<String>( );
		
		for( String s : strrules ) {
			final AllergenRule r = AllergenRule.fromString( s ); 
			rules.add( r );
			ingredients.addAll( r.getIngredients( ) );
			allergens.addAll( r.getAllergens( ) );
		}
		
	}
	
	/**
	 * Infer allergen mapping from the ingredients
	 */
	public boolean inferAllergens( ) {
		// map of ingredients that may potentially contain the allergen
		final Map<String, Set<String>> almap = new HashMap<>( allergens.size( ) );
		for( String a : allergens ) almap.put( a, new HashSet<>( ) );
		
		for( AllergenRule r : rules ) {
			for( String a : r.getAllergens( ) ) almap.get( a ).addAll( r.getIngredients( ) );
		}
		
		// copy set of known allergens
		final Set<String> remaining = new HashSet<String>( allergens );
			
		while( remaining.size( ) > 0 ) {
			// do a pairwise combination of rules to remove impossibilities
			for( AllergenRule a1 : rules ) {
				for( AllergenRule b1 : rules ) {
					if( a1.equals( b1 ) ) continue;
					final AllergenRule combined = a1.union( b1 );
					for( String a : combined.getAllergens( ) )
						almap.get( a ).retainAll( combined.getIngredients( ) );
				}
			}			
			
			// try to map an allergen by checking if there are no other ingredients using it
			final Stack<String> remove = new Stack<>( );
			for( String allergen : remaining ) {
				// conclusive answer for an allergen?
				if( almap.get( allergen ).size( ) == 1 ) {
					// map ingredient and update rule set
					allergenmap.put( allergen, almap.get( allergen ).iterator( ).next( ) );
					remove.push( allergen );
					break;
				}
			}

			// no changes?
			if( remove.size( ) == 0 ) return false;
			
			// now remove all mapped allergens from the list
			while( remove.size( ) > 0 ) {
				final String allergen = remove.pop( );
				final String ingredient = almap.get( allergen ).iterator( ).next( );
				
				for( Set<String> ingredients : almap.values( ) ) ingredients.remove( ingredient );
				remaining.remove( allergen );
			}
		}
		
		return true;
	}
	
	/**
	 * Count the number of ingredients for which no allergen mapping is known
	 * 
	 * @return The number of unmapped ingredients
	 */
	public long countUnmapped( ) {
		// go over rule set and count all ingredients that are not mapped yet to an allergen
		long count = 0;
		for( AllergenRule r : rules ) {
			for( String i : r.getIngredients( ) )
				if( !allergenmap.containsValue( i ) ) count++;
		}
		return count;
	}
	
	/**
	 * Returns ingredients with mapped allergens in a list, sorted on their allergen
	 * 
	 * @return Sorted ingredient list
	 */
	public List<String> sortedList( ) {
		final List<String> result = new ArrayList<>( );
		
		// first get ordered list of allergens
		result.addAll( allergenmap.keySet( ) );
		Collections.sort( result );
	
		// replace by their ingredients
		for( int i = 0; i < result.size( ); i++ )
			result.set( i, allergenmap.get( result.get( i ) ) );
		
		return result;
	}
	
	@Override
	public String toString( ) {
		String res = "== " + allergenmap + "==:\n";
		for( AllergenRule r : rules )
			res += r.toString( ) + "\n";
		return res;
	}
}
