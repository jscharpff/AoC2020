package day19;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Holds a rule set for message validation
 * 
 * @author schar503
 *
 */
public class RuleMatcher {
	/** The rule set */
	protected final Map<Integer, Rule> ruleset;
	
	/** The compiled ruleset */
	protected final Pattern rulepattern;
	
	/**
	 * Creates a new ruleset from the string input
	 * 
	 * @param input Array of rules
	 */
	public RuleMatcher( final String[] input ) {
		// build map of rules and keep track of rules that have not been parsed yet
		final Map<Integer, String> unparsed_rules = new HashMap<Integer, String>( );
		ruleset = new HashMap<Integer, Rule>( );
		
		for( final String rule : input ) {
			// parse the rule
			String[] s = rule.split( ": " );
			final int ID = Integer.valueOf( s[0] );
			
			// if this is a value rule, it can be parsed immediately. Other will have to be parsed
			// in later passes
			final Matcher m = Pattern.compile( "\"(\\w)\"" ).matcher( s[1] );
			if( m.find( ) ) {
				ruleset.put( ID, new ValueRule( ID, m.group( 1 ).charAt( 0 ) ) );
			} else {
				unparsed_rules.put( ID, s[1] );
			}
		}
		
		// parse them into
		while( unparsed_rules.size( ) > 0 ) {
			// list of items to remove from unparsed set to prevent concurrent modification
			final Set<Integer> remove = new HashSet<Integer>( );
			
			for( final Integer ruleID : unparsed_rules.keySet( ) ) {
				final String rule = unparsed_rules.get( ruleID );
				
				// parse rules if all elements are present
				final Rule r = ExprRule.fromString( ruleID, rule, ruleset );
				if( r == null ) continue;
				
				ruleset.put( ruleID , r );
				remove.add( ruleID );
				
			}
			for( Integer rID : remove )
				unparsed_rules.remove( rID );
		}
		
		rulepattern = Pattern.compile( "^" + ruleset.get( 0 ).reduce( ) + "$" ); 
	}
	
	/**
	 * Checks the message against the rule set to see whether it is valid
	 * 
	 * @param message The message
	 * @return True if the message is valid according to the validation rules
	 */
	public boolean isValid( final String message ) {
		return rulepattern.matcher( message ).find( );
	}
	
	/** @return The RegEx pattern */
	public Pattern getPattern( ) { return rulepattern; }
	
	@Override
	public String toString( ) {
		return ruleset.toString( );
	}
}
