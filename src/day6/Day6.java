package day6;

import java.util.List;

public class Day6 {
	
	/**
	 * Day 6 of the 2020 Advent of Code challenge
	 * 
	 * @param args
	 * @throws Exception 
	 */
	public static void main( String[] args ) throws Exception {
		final List<CustomsGroup> ex_groups = CustomsGroup.fromFile( Day6.class.getResource( "day6_example.txt" ).getFile( ) ); 
		final List<CustomsGroup> groups = CustomsGroup.fromFile( Day6.class.getResource( "day6_input.txt" ).getFile( ) ); 
		
		System.out.println( "---[ Part 1 ]---" );
		System.out.println( "Example: " + part1( ex_groups ) );
		System.out.println( "Part 1 : " + part1( groups ) );

		System.out.println( "\n---[ Part 2 ]---" );
		System.out.println( "Example: " + part2( ex_groups ) );
		System.out.println( "Part 2 : " + part2( groups ) );
}
	
	/**
	 * Sums the answer counts for each customs group
	 * 
	 * @param groups The list of customs groups
	 * @return The sum of all response counts
	 */
	protected static int part1( List<CustomsGroup> groups ) {
		int result = 0;
		for( CustomsGroup cg : groups)
			result += cg.countAny( );
		return result;
	}
	
	/**
	 * Sums the number of questions everybody answered yes to
	 * 
	 * @param groups The list of customs groups
	 * @return The sum of all response counts
	 */
	protected static int part2( List<CustomsGroup> groups ) {
		int result = 0;
		for( CustomsGroup cg : groups)
			result += cg.countEvery( );
		return result;
	}
}
