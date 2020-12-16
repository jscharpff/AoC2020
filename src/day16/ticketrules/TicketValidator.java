package day16.ticketrules;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TicketValidator {
	/** The rule set as a <Field, Rule> map */
	protected Map<String, TicketRule> rules;
	
	/**
	 * Creates a new ticket rule set
	 */
	protected TicketValidator( ) {
		this.rules = new HashMap<String, TicketRule>( );
	}
	
	/**
	 * Adds a single rule to the set
	 * 
	 * @param rule The rule to add
	 */
	protected void addRule( final TicketRule rule ) {
		this.rules.put( rule.getField( ), rule );
	}
	
	/**
	 * Creates a new TicketValidator from a string input
	 * 
	 * @param ruleset The ruleset as a set of strings
	 */
	public static TicketValidator fromInput( final String[] ruleset ) {
		final TicketValidator TV = new TicketValidator( );
		
		for( String s : ruleset )
			TV.addRule( TicketRule.fromString( s ) );
		
		return TV;
	}
	
	/**
	 * @return Set of all fields in this validator 
	 */
	public Set<String> getFields( ) { return rules.keySet( ); }
	
	/**
	 * Determines all fields that are valid for a given value
	 * 
	 * @param value The value
	 * @return Set of fields that are valid
	 */
	public Set<String> getValidFields( final int value ) {
		final Set<String> fields = new HashSet<>( );
		for( TicketRule tr : rules.values( ) )
			if( tr.isValidValue( value ) ) fields.add( tr.getField( ) );
		return fields;
	}
	
	/**
	 * Validates a given value against all known rules
	 * 
	 * @param value The value to check
	 * @return False if none of the validation rules allow the value
	 */
	public boolean isValidValue( final int value ) {
		for( TicketRule r : rules.values( ) )
			if( r.isValidValue( value ) ) return true;
		
		return false;
	}
	
	/** @return The number of rules in the set */
	public int size() { return rules.size( ); }
	
	@Override
	public String toString( ) {
		return rules.toString( );
	}
}
