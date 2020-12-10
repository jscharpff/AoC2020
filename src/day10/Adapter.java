package day10;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.io.FileReader;

/**
 * Container for an adapter that provides a specific joltage
 * 
 * @author Joris
 */
public class Adapter implements Comparable<Adapter> {
	/** The joltage */
	protected final int jolts;
	
	/**
	 * Creates a new adapter of the specified joltage
	 * 
	 * @param jolts The joltage
	 */
	public Adapter( final int jolts ) {
		this.jolts = jolts;
	}
	
	/** @return The joltage */
	public final int getJolts( ) { return this.jolts; }
	
	/**
	 * Compare this adapter to another adapter
	 * 
	 * @param a The other adapter
	 * @return a negative number if my voltage is lower, 0 if equal
	 * and positive if mine is higher
	 */
	@Override
	public int compareTo( final Adapter a ) {
		return this.getJolts( ) - a.getJolts( );
	}
	
	/**
	 * Checks equality with another object
	 * 
	 * @param obj The other objtec
	 * @return True iff the jolatges are equal
	 */
	@Override
	public boolean equals( Object obj ) {
		if( obj == null || !(obj instanceof Adapter) ) return false;
		final Adapter a = (Adapter)obj;
		
		return a.getJolts( ) == this.getJolts( );
	}
	
	/**
	 * Creates a list of adapters from input file
	 * 
	 * @param list The adapter file
	 * @return A list of adapters
	 * @throws IOException
	 */
	public static List<Adapter> fromFile( final String infile ) throws IOException {
		final List<String> input = new FileReader( infile ).readLines( );
		final List<Adapter> adapters = new ArrayList<>( input.size( ) );
		
		for( String s : input )
			adapters.add( new Adapter( Integer.parseInt( s ) ) );
		
		return adapters;
	}
	
	/** @return The adapter as string */
	@Override
	public String toString( ) {
		return "(" + this.jolts + "j)";
	}
}
