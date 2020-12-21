package day19;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Holds an expression that combines rules
 * 
 * @author Joris
 */
public class ExprRule extends Rule {
	/** The terms, grouped per OR clause */
	protected final List<Rule[]> terms;
	
	/** 
	 * Creates a new expression rule
	 * 
	 * @param ID The rule ID
	 * @param terms The terms as a list of arrays such that each list item is an or clause
	 */
	public ExprRule( final int ID, final List<Rule[]> terms ) {
		super( ID );
		this.terms = new ArrayList<Rule[]>( terms );
	}
	
	@Override
	public String reduce( ) {
		String res = "(?:";
		for( Rule[] rules : terms ) {
			// check if the term contains a recursive rule
			boolean recursive = false;
			for( Rule r : rules ) recursive |= (r instanceof RecRule);
			
			if( !recursive ) {
				res += "(?:";
				for( Rule r : rules ) {
					res += r.reduce( );
				}
				res += ")|";
			} else {
				// HACKY but this is the only easy way I saw to make sure all parts of 
				// the recursive expression BOTH match exactly n times
				// generate recursive for N=1,2,3,4,5 matches
				for( int i = 1; i <= 5; i++ ) {
					res += "(?:";
					for( Rule r : rules ) {
						if( r instanceof RecRule ) continue;
						res += r.reduce( ) + "{" + i + "}";
					}
					res += ")|";
				}
			}
		}
		// remove last pipe and close group
		res = res.substring( 0, res.length( ) - 1 ) + ")";
		return res;
	}
	
	@Override
	public boolean equals( Object obj ) {
		if( obj == null || !(obj instanceof ExprRule) ) return false;
		if( !super.equals( obj ) ) return false;
		
		// ID check is sufficient
		return true;
	}
	
	@Override
	public String toString( ) {
		String res = "";
		for( Rule[] rules : terms ) {
			for( Rule r : rules ) {
				res += r.getID( ) + " ";
			}
			res += "| ";
		}
		// remove last pipe and close group
		res = res.substring( 0, res.length( ) - 3 ) + ")";
		return res;
	}
	
	/**
	 * Tries to parse the string into a rule with the given map of parsed rules
	 * 
	 * @param ID The ID of the rule
	 * @param rule The string of the rule to parse
	 * @param rules The known rule set
	 * @return A new ExprRule object if the string was parsed successfully, null otherwise
	 */
	public static ExprRule fromString( final int ID, final String rule, final Map<Integer, Rule> rules ) {
		final List<Rule[]> terms = new ArrayList<Rule[]>( );
				
		// split over potential pipes
		for( String r : rule.split( " \\| " ) ) {
			final String[] rIDs = r.split( " " );
			final Rule[] matched = new Rule[ rIDs.length ];

			for( int i = 0; i < rIDs.length; i++ ) {
				final int rID = Integer.valueOf( rIDs[i] );
				
				// handle recursive rules differently
				if( rID == ID ) {
					matched[i] = new RecRule( rID );
					continue;
				}
				
				// unknown rule in rule string, cannot parse (yet)
				if( !rules.containsKey( rID ) ) return null;
				matched[i] = rules.get( rID );
			}
			
			// add all matched terms
			terms.add( matched );
		}
		
		// all was parsed successfully, create ExprRule
		return new ExprRule( ID, terms );
	}
}
