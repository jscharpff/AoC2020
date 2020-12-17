package day17;

import java.util.List;

import day17.sim.CubeSim;
import day17.sim.CubeSim4D;
import util.io.FileReader;

public class Day17 {
	/**
	 * Day 17 of the Advent of Code 2020
	 * 
	 * @param args The command line arguments
	 * @throws Exception
	 */
	public static void main( final String[] args ) throws Exception {
		final List<String> ex_input = new FileReader( Day17.class.getResource( "day17_example.txt" ) ).readLines( );
		final List<String> input = new FileReader( Day17.class.getResource( "day17_input.txt" ) ).readLines( );
		
		System.out.println( "---[ Part 1 ]---" );
		System.out.println( "Example: " + part1( ex_input, 6 ) ); 
		System.out.println( "Part 1 : " + part1( input, 6 ) ); 

		System.out.println( "\n---[ Part 2 ]---" );
		System.out.println( "Example: " + part2( ex_input, 6 ) ); 
		System.out.println( "Example: " + part2( input, 6 ) ); 
	}
	
	/**
	 * Simulates cubes becoming active and inactive in a 3D space, returns the
	 * count of active cubes after N rounds  
	 * 
	 * @param input A 2D slice of the initial cube space at Z=0
	 * @param rounds The number of rounds to simulate
	 * @return The number of active cubes after N rounds 
	 */
	protected static long part1( final List<String> input, final int rounds ) {
		// initialise simulation from 2D slice
		final CubeSim sim = new CubeSim( );
		sim.init( input.toArray( new String[ input.size( ) ] ), 0 );
		
		// simulate for the given number of rounds and return number of active cubes
		while( sim.nextRound( ) < rounds ) {};
		return sim.getActive( ).size( );
	}
	
	/**
	 * Simulates cubes becoming active and inactive in a 4D space, returns the
	 * count of active cubes after N rounds  
	 * 
	 * @param input A 2D slice of the initial cube space at Z=0, W=0
	 * @param rounds The number of rounds to simulate
	 * @return The number of active cubes after N rounds 
	 */
	protected static long part2( final List<String> input, final int rounds ) {
		// initialise simulation from 2D slice
		final CubeSim4D sim = new CubeSim4D( );
		sim.init( input.toArray( new String[ input.size( ) ] ), 0, 0 );
		
		// simulate for the given number of rounds and return number of active cubes
		while( sim.nextRound( ) < rounds ) {};
		return sim.getActive( ).size( );
	}	
}
