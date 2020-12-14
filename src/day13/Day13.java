package day13;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

import util.io.FileReader;

public class Day13 {

	/**
	 * Day 13 of the Advent of Code 2020
	 * 
	 * @param args The command line arguments
	 * @throws Exception
	 */
	public static void main( final String[] args ) throws Exception {
		final List<String> ex_input = new FileReader( Day13.class.getResource( "day13_example.txt" ) ).readLines( );
		final List<String> input = new FileReader( Day13.class.getResource( "day13_input.txt" ) ).readLines( );
		
		System.out.println( "---[ Part 1 ]---" );
		System.out.println( "Example: " + part1( ex_input ) );
		System.out.println( "Part 1 : " + part1( input ) );

		System.out.println( "\n---[ Part 2 ]---" );
		System.out.println( "Example: " + part2( ex_input ) );
		System.out.println( "Part 2 : " + part2( input ) );
	}
	
	/**
	 * Determines the minimal time that you will have to wait for a bus to depart
	 * given a set of bus schedules and a desired departure time  
	 * 
	 * @param input String list with the desired departure time (line 1) and the bus schedules (line 2)
	 * @return The wait time times the ID of the bus that you can take
	 */
	protected static long part1( List<String> input ) {
		// first line is the arrival time
		final int arrival = Integer.valueOf( input.get( 0 ) );
		
		// get earliest departure time
		Bus earliest = null;
		
		// second line contains available busses
		for( String b : input.get( 1 ).split(",") ) {
			// ignore x'es for now
			if( b.equals( "x" ) ) continue;
			
			// create bus and check if its earliest departure time is before the best we know
			final Bus bus = new Bus( Integer.valueOf( b ) );
			if( earliest == null || earliest.getDeparture( arrival ) > bus.getDeparture( arrival ) ) earliest = bus;
		}
		
		// return wait time (earliest departure - arrival) times the ID of the bus (it's interval)
		return (earliest.getDeparture( arrival ) - arrival) * earliest.getInterval( );		
	}
	
	/**
	 * Computes the time stamp at which the first occurrence of a desired bus 
	 * schedule configuration occurs. The desired configuration is given by the
	 * second line of the input so that for each n-th bus the departure time
	 * should be (n-1) time steps later than that of the 1st bus.  
	 * 
	 * @param input The input that contains the bus schedules and desired configuration
	 * @return The earliest time step at which the desired configuration occurs
	 */
	protected static long part2( List<String> input ) {
		// skip arrival time, get all bus schedules and their offset
		final String[] busses_input = input.get( 1 ).split( "," );
		
		
		// read busses and the desired offset
		final Stack<Bus> busses = new Stack<Bus>( );
		int curroffset = -1;
		for( String b : busses_input ) {
			curroffset++;
			if( b.equals( "x" ) ) continue;
			
			busses.add( new Bus( Long.valueOf( b ), (long)curroffset ) );
		}
		
		// reverse the list (more efficient removes)
		Collections.reverse( busses );
		
		// keep combining Bus schedules until we know the interval and start offset of
		// the desired configuration
		while( busses.size( ) > 1 ) {
			final Bus b1 = busses.pop( );
			final Bus b2 = busses.pop( );
			busses.push( b1.combine( b2 ) );
		}
		
		// the resulting function has the desired time stamp as its offset, this is
		// the first time at which the desired configuration occurs
		return busses.pop( ).offset;
	}
}
