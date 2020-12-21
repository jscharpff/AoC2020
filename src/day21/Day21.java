package day21;

import java.util.List;

import util.io.FileReader;

public class Day21 {
	/**
	 * Day 21 of the Advent of Code
	 * 
	 * @param args The command line arguments
	 * @throws Exception
	 */
	public static void main( final String[] args ) throws Exception {
		final List<String> ex_input = new FileReader( Day21.class.getResource( "day21_example.txt" ) ).readLines( );
		final List<String> input = new FileReader( Day21.class.getResource( "day21_input.txt" ) ).readLines( );
		
		System.out.println( "---[ Part 1 ]---" );
		System.out.println( "Example: " + part1( ex_input ) );
		System.out.println( "Part 1 : " + part1( input ) );

		System.out.println( "\n---[ Part 2 ]---" );
		System.out.println( "Example: " + part2( ex_input ) );
		System.out.println( "Part 2 : " + part2( input ) );
	}
	
	protected static long part1( final List<String> input ) {
		final AllergenInfo info = new AllergenInfo( input );
		if( !info.inferAllergens( ) ) throw new RuntimeException( "Failed to infer all allergens" );
		
		return info.countUnmapped( );
	}
	
	protected static String part2( final List<String> input ) {
		final AllergenInfo info = new AllergenInfo( input );
		if( !info.inferAllergens( ) ) throw new RuntimeException( "Failed to infer all allergens" );
		
		// get ordered list, join and return
		final List<String> ingredients = info.sortedList( );
		String res = "";
		for( String i : ingredients ) res += i + ",";
		return res.substring( 0, res.length( ) - 1 );
	}

}
