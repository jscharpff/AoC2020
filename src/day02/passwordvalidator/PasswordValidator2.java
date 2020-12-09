package day02.passwordvalidator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class PasswordValidator2 extends PasswordValidator {
	/** The char that needs to match */
	protected char matchchar;
	
	/** The indexes at which it should match */
	protected List<Integer> indexes;
	
	/**
	 * Constructs a new password validator from string input
	 * 
	 * @param pattern The validator format as string (min-max searchstring)
	 * @throws Exception
	 */
	public void fromPattern( final String pattern ) throws Exception {
		// extract pattern from string
		final Matcher m = parsePattern( pattern );
		
		matchchar = m.group( 3 ).charAt( 0 );
		indexes = new ArrayList<>( 2 );
		indexes.add( Integer.parseInt( m.group( 1 ) ) );
		indexes.add( Integer.parseInt( m.group( 2 ) ) );
	}
	
	/**
	 * Returns true iff the password is valid
	 * @param password
	 * @return
	 */
	public boolean isValid( final String password ) {
		final char[] pw = password.toCharArray( );
		int matchcount = 0;
		for( Integer i : indexes ) {
			if( i < 1 || i > pw.length ) continue;
			
			if( pw[i-1] == matchchar ) matchcount++;
		}
		return matchcount == 1;
	}
}
