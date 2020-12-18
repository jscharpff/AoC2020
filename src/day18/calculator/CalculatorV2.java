package day18.calculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to perform expression based calculations
 * 
 * @author Joris
 */
public class CalculatorV2 extends Calculator {
	/**
	 * Perform recursive reduction of the string expression
	 * 
	 * @param str The string expression
	 */
	@Override
	protected String eval( final String str ) {
		// first evaluate all nested expressions
		String expr = "" + str; 
		while( expr.contains( "(" ) ) {
			// recurse on parentheses
			final int s = expr.lastIndexOf( "(" );
			final int e = expr.indexOf( ")", s );
			expr = expr.substring( 0, s ) + eval( expr.substring( s + 1, e ) ) + expr.substring( e + 1 );
		}
		
		// first perform all additions
		Pattern p = Pattern.compile( "(\\d+)(\\+)(\\d+)" );
		Matcher m = p.matcher( expr );
		while( m.find( ) ) {
			final long val = Long.valueOf( m.group( 1 ) ) + Long.valueOf( m.group( 3 ) );
			expr = expr.substring( 0, m.start( 1 )) + val + expr.substring( m.end( 3 ) );
			m = p.matcher( expr );
		}
		
		// then all multiplications
		p = Pattern.compile( "(\\d+)(\\*)(\\d+)" );
		m = p.matcher( expr );
		while( m.find( ) ) {
			final long val = Long.valueOf( m.group( 1 ) ) * Long.valueOf( m.group( 3 ) );
			expr = expr.substring( 0, m.start( 1 ) ) + val + expr.substring( m.end( 3 ) );
			m = p.matcher( expr );
		}
		
		// return reduced value
		return expr;
	}
}
