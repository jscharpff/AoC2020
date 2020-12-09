package util.program;

import util.program.exceptions.InvalidSyntaxException;

/**
 * Container for an instruction argument
 * 
 * @author Joris
 */
public class Argument {
	/** The argument value */
	protected final int value;
	
	/**
	 * Creates a new argument container
	 * 
	 * @param value The argument integer value
	 */
	public Argument( final int value ) {
		this.value = value;
	}
	
	/**
	 * @return The integer value of the object
	 */
	public int getValue( ) {
		 return value;
	 }
	
	/**
	 * Parses a string into an argument
	 * 
	 * @param arg The argument as string
	 * @return The argument
	 * @throws InvalidSyntaxException if the argument syntax is invalid
	 */
	public static Argument fromString( final String arg ) throws InvalidSyntaxException {
		try {
			return new Argument( Integer.parseInt( arg ) );
		} catch( Exception e ) {
			throw new InvalidSyntaxException( "Invalid argument string '" + arg + "'" );
		}
	}
	
	/**
	 * @return String representation of the argument
	 */
	@Override
	public String toString( ) {
		return "" + value;
	}
}
