package day18;

import java.util.List;

import day18.calculator.Calculator;
import day18.calculator.CalculatorV2;
import util.io.FileReader;

public class Day18 {
	/**
	 * Day 18 of the Advent of Code 2020
	 * 
	 * @param args The command line arguments
	 * @throws Exception
	 */
	public static void main( final String[] args ) throws Exception {
		final List<String> ex_input = new FileReader( Day18.class.getResource( "day18_example.txt" ) ).readLines( );
		final List<String> input = new FileReader( Day18.class.getResource( "day18_input.txt" ) ).readLines( );
		
		System.out.println( "---[ Part 1 ]---" );
		final Calculator c = new Calculator( );
		System.out.println( "Example: " + sumProblems( ex_input, c ) );
		System.out.println( "Part 1 : " + sumProblems( input, c ) );

		System.out.println( "\n---[ Part 2 ]---" );
		final Calculator c2 = new CalculatorV2( );
		System.out.println( "Example: " + sumProblems( ex_input, c2 ) );
		System.out.println( "Part 2 : " + sumProblems( input, c2 ) );
	}
	
	/**
	 * Computes sum of all arithmetic operations in the input, uses the specified
	 * calculator to solve the problems
	 * 
	 * @param input The input as one problem per line
	 * @return The sum of all answers
	 */
	protected static long sumProblems( final List<String> input, final Calculator c ) {
		// computes the value per line of the input and sum the results
		long sum = 0;
		for( String i : input ) sum += c.compute( i );
		return sum;
	}
}
