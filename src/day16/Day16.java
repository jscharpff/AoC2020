package day16;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import day16.ticketrules.TicketValidator;
import util.io.FileReader;

public class Day16 {
	
	/**
	 * Day 16 of the Advent of Code 2020
	 * 
	 * @param args The command line arguments
	 * @throws Exception
	 */
	public static void main( final String[] args ) throws Exception {
		final List<String> input = new FileReader( Day16.class.getResource( "day16_input.txt" ) ).readLineGroups( "\n" );
		final List<String> ex_input = new FileReader( Day16.class.getResource( "day16_example.txt" ) ).readLineGroups( "\n" );
		final List<String> ex2_input = new FileReader( Day16.class.getResource( "day16_example2.txt" ) ).readLineGroups( "\n" );
		
		System.out.println( "---[ Part 1 ]---" );
		System.out.println( "Example: " + part1( ex_input ) );
		System.out.println( "Part 1 : " + part1( input ) );

		System.out.println( "\n---[ Part 2 ]---" );
		System.out.println( "Example: " + part2( ex2_input ) );
		System.out.println( "Part 2 : " + part2( input ) );
	}
	
	/**
	 * Sums the values of all invalid columns
	 * 
	 * @param input The input describing the ticket validation rules, my ticket and
	 * 							the tickets of nearby passengers
	 * @return The sum of all values that do not agree with any of the validation rules
	 */
	protected static long part1( final List<String> input ) {
		// start with the ticket validation rules
		final TicketValidator tv = TicketValidator.fromInput( input.get( 0 ).split( "\n" ) );
		
		// validate NEARBY tickets and sum invalid values
		final String[] nearby = input.get( 2 ).split( "\n" );
		long invalid_sum = 0;
		for( int i = 1; i < nearby.length; i++ ) {
			for( String s : nearby[i].split( "," ) ) {
				final int val = Integer.valueOf( s );
				if( !tv.isValidValue( val ) ) invalid_sum += val;
			}
		}
		
		return invalid_sum;
	}
	
	/**
	 * Returns the product of all fields in my ticket that start with "departure". First,
	 * however, the algorithm has to find out what the field mapping is.
	 * 
	 * @param input The input describing the ticket validation rules, my ticket and
	 * 							the tickets of nearby passengers
	 * @return The product of all field values of the fields that start with "departure"
	 */	
	protected static long part2( final List<String> input ) {
		// start with the ticket validation rules
		final TicketValidator tv = TicketValidator.fromInput( input.get( 0 ).split( "\n" ) );

		// get my ticket input
		final String myticket = input.get( 1 ).split( "\n" )[1];
		
		// and parse nearby tickets
		final String[] nearby_in = input.get( 2 ).split( "\n" );
		final List<String> nearby = new ArrayList<>( );
		for( int i = 1; i < nearby_in.length; i++ ) {
			// discard invalid tickets
			boolean valid = true;
			for( String val : nearby_in[i].split( "," ) )
				valid &= tv.isValidValue( Integer.valueOf( val ) );
			if( !valid ) continue;
			
			nearby.add( nearby_in[i] );
		}
		
		// use an inner class to store the valid field set per value
		class FMAP {
			final Set<String> fields;
			FMAP( final Set<String> fields ) { this.fields = new HashSet<>( fields ); }
			@Override public String toString( ) { return fields.toString( );}
		}
		
		// determine column to field mapping by checking all possible fields per
		// column and then iteratively setting an index for columns that can 
		/// only hold one field and eliminating that field from the remaining columns

		// start with all fields available for every column and retain only the
		// fields that are valid given the column values
		final int FIELDS = tv.size( );
		final FMAP[] colmap = new FMAP[ FIELDS ];
		for( int i = 0; i < FIELDS; i++ ) colmap[i] = new FMAP( tv.getFields( ) );
		
		// go over every value (per ticket) and remove invalid fields for that column
		for( String ticket : nearby ) {
			final String[] values = ticket.split( "," );
			for( int j = 0; j < FIELDS; j++ ) {
				colmap[j].fields.retainAll( tv.getValidFields( Integer.valueOf( values[j] ) ) );
			}
		}
		
		// use the reduced column map to find column assignments, i.e. iteratively 
		// fix columns that are valid for one field only and remove the field from
		// the available set in other columns
		final Map<String, Integer> fieldmap = new HashMap<>( FIELDS );
		while( fieldmap.size( ) < FIELDS ) {
			boolean reduced = false; // make sure something happened and we're not stuck indefinitely
			for( int i = 0; i < colmap.length; i++ ) {
				final Set<String> f = colmap[i].fields;

				// can we decide on the field mapping for this column?
				if( f.size( ) != 1 ) continue;

				// we found a conclusive mapping, fix it and remove this field from other columns			
				final String field = f.iterator( ).next( );
				fieldmap.put( field, i );
				for( FMAP fmap : colmap ) fmap.fields.remove( field );
				
				// pffewh, we're not stuck
				reduced = true;
			}
			if( !reduced ) throw new RuntimeException( "Cannot conclusively decide field mapping, current mapping: " + fieldmap.toString( ) );
		}
		
		// now return the product of all fields in my ticket that start with "depart_"
		final String[] myvalues = myticket.split( "," );
		long prod = 1;
		for( String field : fieldmap.keySet( ) ) {
			if( !field.startsWith( "departure " ) ) continue;			
			prod *= Long.valueOf( myvalues[ fieldmap.get( field ) ] );
		}
		
		return prod;
	}
}
