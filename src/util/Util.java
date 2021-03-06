package util;

import java.util.stream.Stream;

/**
 * Common helper functions
 * 
 * @author Joris
 */
public class Util {
	/**
	 * Parses a comma separated string into an int array
	 * 
	 * @param str The string value
	 * @return An array of ints
	 */
	public static int[] toIntArray( final String str ) {
		return Stream.of( str.split( "," ) ).mapToInt( x -> Integer.valueOf( x ) ).toArray( );
	}
	
	/**
	 * Reverses a string
	 * 
	 * @param str The string to revers
	 * @return The reversed string
	 */
	public static String reverseString( final String str ) {
		 return new StringBuffer( str ).reverse( ).toString( );
	}
}
