package day16.ticketrules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Single field validator
 * 
 * @author Joris
 */
public class TicketRule {
	/** The field name */
	protected final String field;
	
	/** The rule ranges, each is treated as an "OR" range */
	protected TRRange[] ranges;
	
	/**
	 * Creates a new validation rule
	 * 
	 * @param field The field name
	 * @param ranges The allowed value ranges
	 */
	public TicketRule( final String field, final TRRange[] ranges ) {
		this.field = field;
		this.ranges = ranges;
	}
	
	/** @return The field name */
	public String getField( ) { return this.field; }
	
	/**
	 * Checks if value is within field range
	 * 
	 * @param n The field value
	 * @return True if in one of the field ranges
	 */
	public boolean isValidValue( final int n ) {
		for( TRRange r : ranges )
			if( r.inRange( n ) ) return true;
		
		return false;
	}
	
	/**
	 * Creates a new rule from a string
	 * 
	 * @param rule The rule string in the form <field>: <range1> or <range2>
	 * @return The rule
	 */
	public static TicketRule fromString( final String str ) {
		final Matcher m = Pattern.compile( "([\\w\\s]+): (\\d+)-(\\d+) or (\\d+)-(\\d+)" ).matcher( str );
		if( !m.find( ) ) throw new IllegalArgumentException( "Incorrect rule format: " + str );
		
		final TRRange[] ranges = new TRRange[2];
		for( int i = 1; i <= ranges.length; i++ )
			ranges[i-1] = new TRRange( Integer.valueOf( m.group( i * 2 ) ), Integer.valueOf( m.group( i * 2 + 1 ) ) );
		
		return new TicketRule( m.group( 1 ), ranges );
	}
	
	@Override
	public String toString( ) {
		return this.field + ": "+ ranges[0] + " or " + ranges[1];
	}
}
