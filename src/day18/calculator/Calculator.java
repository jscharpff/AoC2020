package day18.calculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to perform expression based calculations
 * 
 * @author Joris
 */
public class Calculator {	
	/**
	 * Perform recursive reduction of the string expression
	 * 
	 * @param str The string expression
	 */
	protected String eval( final String str ) {
		// first evaluate all nested expressions
		String expr = "" + str; 
		while( expr.contains( "(" ) ) {
			// recurse on parentheses
			final int s = expr.lastIndexOf( "(" );
			final int e = expr.indexOf( ")", s );
			expr = expr.substring( 0, s ) + eval( expr.substring( s + 1, e ) ) + expr.substring( e + 1 );
		}
		
		// parse binary operator patterns until all has been reduced
		final Pattern p = Pattern.compile( "^(\\d+)(\\+|\\*)(\\d+)" );
		Matcher m = p.matcher( expr );
		while( m.find( ) ) {
			final int length = m.group(1).length( ) + m.group( 3 ).length( ) + 1;
			final long val;
			if( m.group( 2 ).equals( "+" ) ) val = Long.valueOf( m.group( 1 ) ) + Long.valueOf( m.group( 3 ) );
			else if( m.group( 2 ).equals( "*" ) ) val = Long.valueOf( m.group( 1 ) ) * Long.valueOf( m.group( 3 ) );
			else throw new RuntimeException( "Invalid operator: " + m.group( 2 ) );
			expr = val + expr.substring( length );
			m = p.matcher( expr );
		}
		
		// return reduced value
		return expr;
	}
	
	
	/**
	 * Computes the value of the expression by reducing it
	 * 
	 * @return The resulting value
	 */
	public long compute( final String expr ) {
		return Long.valueOf( eval( expr.replaceAll( " ", "" ) ) );
	}
}
