package day02.passwordvalidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator1 extends PasswordValidator {
	/** The pattern used in matching */
	protected Pattern pwpattern;
	
	/** The required min and max counts */
	protected int mincount;
	protected int maxcount;
	
	/**
	 * Constructs a new password validator from string input
	 * 
	 * @param pattern The validator format as string (min-max searchstring)
	 * @throws Exception
	 */
	public void fromPattern( final String pattern ) throws Exception {
		final Matcher m = parsePattern( pattern );
		
		pwpattern = Pattern.compile( "(" + m.group( 3 ) + ")" );
		mincount = Integer.parseInt( m.group( 1 ) );
		maxcount = Integer.parseInt( m.group( 2 ) );
	}
	
	/**
	 * Returns true iff the password is valid
	 * @param password
	 * @return
	 */
	public boolean isValid( final String password ) {
		final Matcher m = pwpattern.matcher( password );
		int count = 0;
		while( m.find( ) ) count++;
		
		return count >= mincount && count <= maxcount;
	}
}
