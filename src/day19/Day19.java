package day19;

import java.util.List;

import util.io.FileReader;

public class Day19 {
	/**
	 * Day 19 of the Advent of Code 2020
	 * 
	 * @param args The command line arguments
	 * @throws Exception
	 */
	public static void main( final String[] args ) throws Exception {
		final List<String> ex_input = new FileReader( Day19.class.getResource( "day19_example.txt" ) ).readLineGroups( "\n" );
		final List<String> ex2_input = new FileReader( Day19.class.getResource( "day19_example2.txt" ) ).readLineGroups( "\n" );
		final List<String> input = new FileReader( Day19.class.getResource( "day19_input.txt" ) ).readLineGroups( "\n" );
		
		System.out.println( "---[ Part 1 ]---" );
		System.out.println( "Example: " + part1( ex_input ) );
		System.out.println( "Part 1 : " + part1( input ) );

		System.out.println( "\n---[ Part 2 ]---" );
		System.out.println( "Example: " + part2( ex2_input ) );
		System.out.println( "Part 2 : " + part2( input ) );
	}
	
	protected static long part1( final List<String> input ) {
		final RuleMatcher rm = new RuleMatcher( input.get( 0 ).split( "\n" ) );

		// validate messages
		int count = 0;
		for( String s : input.get( 1 ).split( "\n" ) )
			if( rm.isValid( s ) ) count++;
		
		return count;
	}
	
	protected static long part2( final List<String> input ) {
		// replace rule 8 and 11 in input
		final String[] in_rules = input.get( 0 ).split( "\n" );
		for( int i = 0; i < in_rules.length; i++ ) {
			if( in_rules[i].startsWith( "8:" ) ) in_rules[i] = "8: 42 | 42 8";
			else if( in_rules[i].startsWith( "11:" ) ) in_rules[i] = "11: 42 31 | 42 11 31";
		}
		final RuleMatcher rm = new RuleMatcher( in_rules );
				
		// validate messages
		int count = 0;
		for( String s : input.get( 1 ).split( "\n" ) )
			if( rm.isValid( s ) ) count++;
		
		return count;
	}
}
