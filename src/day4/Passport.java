package day4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.FileReader;

public class Passport {
	/** The passport key/value set */
	protected final Map<String, String> entries;
	
	/** Required keys */
	protected static final String[] KEYS_REQUIRED = { "byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid" };
	
	/**
	 * Creates a new password
	 * 
	 * @param entries Initial set of entries
	 */
	protected Passport( final Map<String, String> entries ) {
		this.entries = entries;
	}
	
	/**
	 * Creates a passport from a string representation
	 * 
	 * @param entries The passport entries as space-separated string
	 * @return The passport
	 */
	public static Passport fromString( final String entries ) {
		final String[] keyvals = entries.split( " " );
		final Map<String, String> E = new HashMap<>( keyvals.length );
		
		for( String pair : keyvals ) {
			final String[] k = pair.split( ":" ); 
			E.put( k[0], k[1].toLowerCase( ) );
		}
		
		return new Passport( E );		
	}
	
	/**
	 * Reads passports from a batch file containing  multiple entries
	 * 
	 * @param infile The file to read
	 * @return The passport
	 * @throws IOException
	 */
	public static List<Passport> fromBatchFile( final String infile ) throws IOException {
		final FileReader f = new FileReader( infile ); 
		final List<String> input = f.readLines( );
		final List<Passport> passports = new ArrayList<Passport>( );
		
		// read passports, one per block ending with a new line
		// append a new line to guarantee completing the last passport
		input.add( "" );
		String curr = "";
		for( String s : input ) {
			if( s.equals( "" ) ) {
				passports.add( Passport.fromString( curr ) );
				curr = "";
				continue;
			}
			curr += s.trim() + " ";
		}
				
		return passports;
	}
	
	/**
	 * @return Passport as a string
	 */
	public String toString() {
		return "Passport: " + entries.toString( );
	};
	
	/**
	 * Checks if the passport is valid, i.e. it contains all required keys
	 * 
	 * @param extended Use extended validation rules
	 * @return True iff all mandatory keys are present
	 */
	public boolean isValid( final boolean extended ) {
		// check for missing fields
		for( String k : KEYS_REQUIRED ) {
			if( !entries.containsKey( k ) ) return false;
		}
		
		// mandatory key check if sufficient if extended validation is not required 
		if( !extended ) return true;
		
		// all mandatories present, now validate the fields
		try {
			
			// birth year at least 1920, no more than 2002
			if( !Pattern.matches( "^\\d{4}$", entries.get( "byr" ) ) ) return false;
			final int byr = Integer.parseInt( entries.get( "byr" ) );
			if( byr < 1920 || byr > 2002 ) return false;

			// issue year, not before 2010 or after 2020
			if( !Pattern.matches( "^\\d{4}$", entries.get( "iyr" ) ) ) return false;
			final int iyr = Integer.parseInt( entries.get( "iyr" ) );
			if( iyr < 2010 || iyr > 2020 ) return false;
			
			// expiration year, not before 2020 or after 2030
			if( !Pattern.matches( "^\\d{4}$", entries.get( "eyr" ) ) ) return false;
			final int eyr = Integer.parseInt( entries.get( "eyr" ) );
			if( eyr < 2020 || eyr > 2030 ) return false;
			
			// height, either [150, 193] cm or [59, 76] in
			final Matcher m = Pattern.compile( "^(\\d+)(cm|in)$" ).matcher( entries.get( "hgt" ) );
			if( !m.find() ) return false;
			final int h = Integer.parseInt( m.group( 1 ) );
			if( m.group( 2 ).equals( "cm" ) && (h < 150 || h > 193	) ) return false;
			if( m.group( 2 ).equals( "in" ) && (h < 59 || h > 76) ) return false;
			
			// hair colour as rgb hex
			if( !Pattern.matches( "^#[0-9a-f]{6}$", entries.get( "hcl" ) ) ) return false;
						
			// eye colour, one of preset codes
			if( !Pattern.matches( "^amb|blu|brn|gry|grn|hzl|oth$", entries.get( "ecl" ) ) ) return false;

			// passport ID, 9 digits
			if( !Pattern.matches( "^\\d{9}$", entries.get( "pid" ) ) ) return false;

			return true;
		} catch( Exception e ) {
			// format exception somewhere, return invalid
			return false;
		}
	}
}
