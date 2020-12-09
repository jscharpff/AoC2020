package day02;

import java.util.List;

import day02.passwordvalidator.PasswordValidator;
import day02.passwordvalidator.PasswordValidator1;
import day02.passwordvalidator.PasswordValidator2;
import util.io.FileReader;

public class Day2 {

	/**
	 * Day2 of the 2020 Advent of Code challenge
	 * 
	 * @param args
	 * @throws Exception 
	 */
	public static void main( String[] args ) throws Exception {
		final FileReader f = new FileReader( Day2.class.getResource( "day2_input.txt" ).getFile( ) ); 
		final List<String> input = f.readLines( );		

		System.out.println( "---[ Part 1 ]---" );
		System.out.println( "Valid passwords: "+ countValid( input, new PasswordValidator1( ) ) );		

		System.out.println( "\n---[ Part 2 ]---" );
		System.out.println( "Valid passwords: "+ countValid( input, new PasswordValidator2( ) ) );		
	}

	/**
	 * Count the number of valid passwords in the input using the given validator
	 *  
	 * @param input List of strings with format <min-max pattern: password>
	 * @param pwv The password validator to use 
	 * @throws Exception 
	 */
	protected static int countValid( final List<String> input, final PasswordValidator pwv ) throws Exception {
		int validcount = 0;
		
		for( String s : input ) {
			final String[] in = s.split( ": " );
			
			// initialise validator using the pattern and check if the password is valid
			pwv.fromPattern( in[0] );
			if( pwv.isValid( in[1] ) ) validcount++; 
		}
		
		return validcount;		
	}
}
