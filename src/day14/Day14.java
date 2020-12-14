package day14;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import day14.mem.Memory;
import day14.mem.MemoryV2;
import util.io.FileReader;

public class Day14 {
	/**
	 * Day 14 of the Advent of Code 2020
	 * 
	 * @param args The command line arguments
	 * @throws Exception
	 */
	public static void main( final String[] args ) throws Exception {
		final List<String> ex_input = new FileReader( Day14.class.getResource( "day14_example.txt" ) ).readLines( );
		final List<String> ex2_input = new FileReader( Day14.class.getResource( "day14_example2.txt" ) ).readLines( );
		final List<String> input = new FileReader( Day14.class.getResource( "day14_input.txt" ) ).readLines( );
		
		System.out.println( "---[ Part 1 ]---" );
		System.out.println( "Example: " + sumMemory( ex_input, false ) );
		System.out.println( "Part 1 : " + sumMemory( input, false ) );
		
		System.out.println( "\n---[ Part 2 ]---" );
		System.out.println( "Example: " + sumMemory( ex2_input, true ) );
		System.out.println( "Part 1 : " + sumMemory( input, true ) );
	}
	
	/**
	 * Processes memory manipulation operations using the specified version of
	 * the memory chip. Sums all memory registers as result.
	 * 
	 * @param input The memory operations
	 * @param v2 True to use version 2 of the memory chip, false uses v1
	 * @return The sum of all memory banks after the manipulations
	 */
	protected static long sumMemory( final List<String> input, final boolean v2 ) {	
		// create memory bank
		final Memory M = v2 ? new MemoryV2() : new Memory( );
		
		// now process memory operations
		for( String line : input ) {
			// either the operation sets the bit mask or manipulates the value
			final Matcher m = Pattern.compile( "(mask|mem\\[(\\d+)\\])\\s+=\\s+([0-9X]+)" ).matcher( line );		
			if( !m.find( ) ) throw new RuntimeException( "Invalid operation in input: " + line );
			
			final String op = m.group( 1 ).toLowerCase( );
			if( op.equals( "mask" )  ) {
				M.setMask( m.group(3) );
			} else if( op.startsWith( "mem" ) )
				M.store( Long.valueOf( m.group( 2 ) ), Long.valueOf( m.group( 3 ) ) );
		}
		
		return M.sum( );
	}
}
