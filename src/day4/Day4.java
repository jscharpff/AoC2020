package day4;

import java.util.List;

public class Day4 {
	/**
	 * Day 4 of the 2020 Advent of Code challenge
	 * 
	 * @param args
	 * @throws Exception 
	 */
	public static void main( String[] args ) throws Exception {
		final List<Passport> ex_passports = Passport.fromBatchFile( Day4.class.getResource( "day4_example.txt" ).getFile( ) ); 		
		final List<Passport> passports = Passport.fromBatchFile( Day4.class.getResource( "day4_input.txt" ).getFile( ) ); 		

		System.out.println( "---[ Part 1 ]---" );
		System.out.println( "Example: "+ countValid( ex_passports, false ) );
		System.out.println( "Part 1 : "+ countValid( passports, false ) );

		// examples specific to part2
		final List<Passport> passports_invalid = Passport.fromBatchFile( Day4.class.getResource( "day4_invalid.txt" ).getFile( ) ); 		
		final List<Passport> passports_valid = Passport.fromBatchFile( Day4.class.getResource( "day4_valid.txt" ).getFile( ) ); 		

		System.out.println( "\n---[ Part 2 ]---" );
		System.out.println( "Examples (invalid): "+ countValid( passports_invalid, true ) );
		System.out.println( "Examples (valid)  : "+ countValid( passports_valid, true ) );
		System.out.println( "Part 2            : "+ countValid( passports, true ) );

	}
	
	/**
	 * Counts the number of valid passports in the input
	 * 
	 * @param passports The list of passports
	 * @return The count
	 */
	protected static int countValid( final List<Passport> passports, final boolean extended ) {
		int count = 0;
		for( Passport p : passports )	{
			if( p.isValid( extended ) ) {
				//System.out.println( p );
				count++;
			}
		}
		return count;
	}
}
