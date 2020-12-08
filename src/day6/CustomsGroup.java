package day6;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.io.FileReader;

public class CustomsGroup {
	/** Max questions on the customs form */
	private final static int QUESTIONS = 26;
	
	/** The number of group members */
	protected int members;
	
	/** The count of responses per question */
	protected final int[] responses;
	
	/**
	 * Creates a new customs group
	 */
	protected CustomsGroup( ) {
		members = 0;
		responses = new int[ QUESTIONS ];
	}
	
	/**
	 * Creates a new customs group from a comma-separated string response set
	 * 
	 * @param responses The responses as comma separated string
	 */
	public CustomsGroup( final String responses ) {
		this( );

		// go over responses individually and add answers to counts
		for( String s : responses.split( "," ) ) {
			this.members++;
			for( char c : s.toCharArray( ) )
				// convert character "yes" responses into array indices
				this.responses[ c - 'a' ]++;		
		}
	}
	
	/**
	 *  Count the number questions that has at least one "yes" 
	 *  
	 *  @return The number of questions with at least one yes
	 */
	public int countAny( ) {
		int count = 0;
		for( int i = 0; i < QUESTIONS; i++ )
			if( this.responses[i] > 0 ) count++;
		return count;
	}
	
	/**
	 *  Count the number of questions with only yes answers in the customs group
	 *  
	 *  @return The number of questions answered yes by all members
	 */
	public int countEvery( ) {
		int count = 0;
		for( int i = 0; i < QUESTIONS; i++ )
			if( this.responses[i] == this.members ) count++;
		return count;
	}
	
	/**
	 * Creates multiple custom groups from a multi-group file
	 * 
	 * @param infile The input file
	 * @return List of CustomGroups read from the file
	 * @throws IOException
	 */
	public static List<CustomsGroup> fromFile( String infile ) throws IOException {
		final FileReader f = new FileReader( infile ); 
		final List<String> input = f.readLineGroups( "," );

		// build result array of custom groups per input group
		final List<CustomsGroup> groups = new ArrayList<CustomsGroup>( input.size( ) );
		for( String s : input ) {
			groups.add( new CustomsGroup( s ) );
		}
		
		return groups;
	}
	
	/**
	 * @return String containing the responses of the customs group
	 */
	public String toString( ) {
		String res = "";
		for( int i = 0; i < QUESTIONS; i++ )
			if( this.responses[i] > 0 ) res += (char)(i + 'a') + "(" + this.responses[i] + ")";
		return res;
	}
}
