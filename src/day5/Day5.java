package day5;

import java.util.List;

import util.FileReader;

public class Day5 {
	
	/**
	 * Day 5 of the 2020 Advent of Code challenge
	 * 
	 * @param args
	 * @throws Exception 
	 */
	public static void main( String[] args ) throws Exception {
		final FileReader f = new FileReader( Day5.class.getResource( "day5_input.txt" ).getFile( ) ); 
		final List<String> input = f.readLines( );		
		
		System.out.println( "---[ Part 1 ]---" );
		System.out.println( "Max SeatID: " + part1( input ) );
		
		System.out.println( "\n---[ Part 2 ]---" );
		System.out.println( "Seat ID: " + part2( input ) );
	}
	
	/**
	 * Computes highest seat ID for a list of string encoded boarding passes
	 * @param input The list containing passes
	 * @return Highest seat ID
	 */
	protected static int part1( final List<String> input ) {
		int highest = -1;
		for( String s : input ) {
			final Boardingpass b = Boardingpass.fromCode( s );
			final int seatID = b.getSeatID( );
			if( seatID > highest ) highest = seatID;
		}
		return highest;
	}
	
	/**
	 * Determine the "missing" seatID in the list of boarding passes
	 * 
	 * @param input The string encoded list of boarding passes
	 * @return The seat ID of the missing seat
	 */
	protected static int part2( final List<String> input ) {
		final int SEATS = 128 * 8;
		
		// create seat plan and flag taken seats
		final boolean[] seats = new boolean[SEATS];
		for( String s : input ) {
			final Boardingpass b = Boardingpass.fromCode( s );
			seats[ b.getSeatID( ) ] = true;
		}
		
		// determine the empty seat
		for( int s = 0; s < SEATS; s++ ) {
			try { 
				// check if surrounding seats are occupied
				if( !seats[s] && seats[s-1] && seats[s+1] ) {
					// yes, return seat ID
					return s;
				}
			} catch( ArrayIndexOutOfBoundsException e ) { /* no neighbour, skip it */ }
		}
		
		throw new RuntimeException( "Failed to find missing seat ID" );
	}
}
