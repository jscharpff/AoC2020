package day09;

import util.io.FileReader;

public class Day9 {
	
	/**
	 * Day 9 of the Advent of Code 2020
	 * 
	 * @param args The command line arguments
	 * @throws Exception
	 */
	public static void main( String[] args ) throws Exception {
		final long[] ex_input = new FileReader( Day9.class.getResource( "day9_example.txt" ) ).readLongArray( );
		final long[] input = new FileReader( Day9.class.getResource( "day9_input.txt" ) ).readLongArray( );
		
		System.out.println( "---[ Part 1 ]---" );
		System.out.println( "Example: " + part1( new XMASCypher( 5 ), ex_input ) );
		System.out.println( "Part 1 : " + part1( new XMASCypher( 25 ), input ) );

		System.out.println( "\n---[ Part 2 ]---" );
		System.out.println( "Example: " + part2( new XMASCypher( 5 ), ex_input ) );
		System.out.println( "Part 2 : " + part2( new XMASCypher( 25 ), input ) );
}
	
	/**
	 * Finds first invalid number in sequence using the given cypher
	 * 
	 * @param cypher The cypher to detect invalid numbers
	 * @param input The number ioput
	 * @return The first invalid number in the sequence
	 */
	protected static long part1( final XMASCypher cypher, final long[] input ) {
		// just keep streaming numbers into the cypher until we encounter an invalid one
		for( long i : input ) {
			if( !cypher.readNext( i ) )
				return i;
		}
		
		throw new RuntimeException( "All numbers in the sequence are valid" );
	}
	
	/**
	 * Finds the set of contiguous numbers in the input that sum up to the
	 * first invalid value according to the cypher
	 * 
	 * @param target The goal value
	 * @param input The input as a set of longs
	 * @return The sum of the smallest and largest values in the contiguous set
	 */
	protected static long part2( final XMASCypher cypher, final long[] input ) {
		// first compute target value
		final long target = part1( cypher, input );
		
		// now use a sliding window over the input to find the set
		int sx = 0;
		int ex = -1;
		long currsum = 0;
		
		// keep expanding and moving the set until we found the desired summation value
		do {
			// add next number to sum
			currsum += input[++ex];
			
			// while we are overshooting the value, increase start index of the window
			while( currsum > target ) currsum -= input[sx++];
			
		} while( currsum != target && ex < input.length - 1);
		
		// check if we managed to find it
		if( currsum != target ) throw new RuntimeException( "Did not find the a summation set of value " + target );
		
		// go over range and return smallest and largest number in the range
		long smallest = Long.MAX_VALUE; long largest = -1;
		for( int i = sx; i <= ex; i++ ) {
			if( input[i] < smallest ) smallest = input[i];
			if( input[i] > largest ) largest = input[i];
		}
		
		return smallest + largest;
	}

}
