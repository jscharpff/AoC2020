package day10;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day10 {

	/**
	 * Day 10 of the Advent of Code of 2020
	 * 
	 * @param args The command line argument
	 * @throws Exception
	 */
	public static void main( final String[] args ) throws Exception {
		final List<Adapter> ex_adapters = Adapter.fromFile( Day10.class.getResource( "day10_example.txt" ).getFile( ) );
		final List<Adapter> ex_adapters2 = Adapter.fromFile( Day10.class.getResource( "day10_example2.txt" ).getFile( ) );
		final List<Adapter> adapters = Adapter.fromFile( Day10.class.getResource( "day10_input.txt" ).getFile( ) );
		
		System.out.println( "---[ Part 1 ]---" );
		System.out.println( "Example 1: " + part1( ex_adapters ) );
		System.out.println( "Example 2: " + part1( ex_adapters2 ) );
		System.out.println( "Part 1   : " + part1( adapters ) );

		System.out.println( "\n---[ Part 2 ]---" );
		System.out.println( "Example 1: " + part2( ex_adapters ) );
		System.out.println( "Example 2: " + part2( ex_adapters2 ) );
		System.out.println( "Part 2 : " + part2( adapters ) );
}
	
	/**
	 * Computes the product of 1J and 3J differences in the list of adapters
	 * 
	 * @param adapters The list of adapters
	 * @return The product of counts of 1 and 3 jolt differences
	 */
	protected static int part1( final List<Adapter> adapters ) {
		// sort the adapters on joltage
		Collections.sort( adapters );
		
		// current joltage and diff count
		int jolts = 0;
		int[] diffs = new int[4];
		
		// get diff to next adapter
		for( Adapter a : adapters ) {
			final int j = a.getJolts( );
			final int d = j - jolts;
			diffs[d]++;
			jolts = j;
		}
		
		// always add +3 for the outlet
		diffs[3]++;
		
		return diffs[1] * diffs[3];
	}
	
	/**
	 * Counts the number of unique arrangements of adapters that result in a 
	 * successful connection from outlet to device with a maximum joltage diff
	 * of 4 jolts
	 * 
	 * @param adapters The list of available adapters
	 * @return The number of unique configurations of adapters
	 */
	protected static long part2( final List<Adapter> adapters ) {
		// sort the adapters and determine device joltage (highest + 3)
		Collections.sort( adapters );
		final int device_jolts = adapters.get( adapters.size( ) - 1 ).getJolts( ) + 3;
		
		// do recursive addition of adapters until the target joltage is achieved, use memoisation
		// to prevent recomputing already tried configurations
		final Map<String, Long> M = new HashMap<>( adapters.size( ) ); 
		return rec( adapters, 0, 0, device_jolts, M );
	}
	
	/**
	 * Brute force computation of all permutations of adapters that lead to the
	 * desired output joltage
	 * 
	 * @param adapters The available adapters
	 * @param curridx The index of the current adapter to check
	 * @param currjolts The current joltage level we are at
	 * @param targetjolts The desired joltage
	 * @param M The memoisation cache that holds previously computed rec( index, currjolts ) values 
	 * @return Recursively explores the remaining adapters until the target is 
	 * reached, final iterations return 1 if the configuration leads to the desired 
	 * joltage, 0 if not
	 */
	protected static long rec( final List<Adapter> adapters, final int curridx, final int currjolts, final int targetjolts, final Map<String, Long> M ) {
		// do we already know this value from an earlier computation?
		final String M_key = curridx + "," + currjolts;
		if( M.containsKey( M_key ) ) return M.get( M_key ); 
		
		// are we at the target value?
		if( currjolts >= targetjolts - 3 && currjolts <= targetjolts + 3 ) return 1;
		
		// cannot add any more adapters
		if( curridx >= adapters.size( ) ) return 0;
		
		// check if the next adapter is still compatible
		final Adapter adapter = adapters.get( curridx );
		if( adapter.getJolts( ) - currjolts > 3 ) return 0;
		
		// check both the configuration with and without the current adapter
		final long configs = rec( adapters, curridx + 1, currjolts, targetjolts, M ) + rec( adapters, curridx + 1, adapter.getJolts( ), targetjolts, M );
		M.put( M_key, configs );
		return configs;
	}
	

}
