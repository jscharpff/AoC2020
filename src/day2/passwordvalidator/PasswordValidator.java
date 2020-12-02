package day2.passwordvalidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class PasswordValidator {
	/**
	 * Constructs a new password valid1ator from the pattern
	 * 
	 * @param pattern The pattern describing the password rules
	 * @throws Exception if the pattern is invalid
	 */
	public abstract void fromPattern( final String pattern ) throws Exception;
	
	/**
	 * Validates the given password
	 * 
	 * @param password The password to check
	 * @return True iff the password is valid
	 */
	public abstract boolean isValid( final String password );
	
	/**
	 * Reads password validator pattern from input
	 * 
	 * @param pattern The password pattern (min-max searchtext)
	 * @return The RegEx matcher with the matched groups
	 * @throws Exception
	 */
	protected Matcher parsePattern( final String pattern ) throws Exception {
		// extract pattern from string
		final Pattern input = Pattern.compile( "(\\d+)\\-(\\d+) (\\w+)" );
		final Matcher m = input.matcher( pattern );
		
		if( !m.find( ) ) throw new Exception( "Invalid password format: "+ pattern );
		return m;
	}
}
