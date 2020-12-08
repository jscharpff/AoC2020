package day1;

import java.io.IOException;

import util.io.FileReader;

/**
 * @author Joris
 */
public class Day1 {
	
	/**
	 * Part one of the advent of code 2020
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main( String[] args ) throws IOException {
		final FileReader f = new FileReader( Day1.class.getResource( "day1_input.txt" ).getFile( ) ); 
		final int[] input = f.readIntArray();
		
		System.out.println( "---[ Part 1 ]---" );
		part1( input );
		
		System.out.println( "\n---[ Part 2 ]---" );
		part2( input );
	}
	
	/**
	 * Determines which two numbers sum up to 2020 and multiplies them for a fixed input
	 * 
	 * @param input Array of integers as input
	 */
	private static void part1( final int[] input ) {
		for( int i = 0; i < input.length - 1; i++ ) {
			for( int j = i + 1; j < input.length; j++ ) {
				final int x = input[i];
				final int y = input[j];
				if( x + y == 2020 ) System.out.println( x + " + " + y + " = 2020\n" + x + " x " + y + " = " + (x * y) );
			}
		}
	}
	
	/**
	 * Determines which two numbers sum up to 2020 and multiplies them for a fixed input
	 * 
	 * @param input Array of integers as input
	 */
	private static void part2( final int[] input ) {
		for( int i = 0; i < input.length - 2; i++ ) {
			for( int j = i + 1; j < input.length - 1; j++ ) {
					for( int k = j + 1; k < input.length; k++ ) {
						final int x = input[i];
						final int y = input[j];
						final int z = input[k];
						if( x + y + z == 2020 ) System.out.println( x + " + " + y + " + " + z + " = 2020\n" + x + " x " + y + " x " + z + " = " + (x * y * z) );
					}
			}
		}
	}
}
