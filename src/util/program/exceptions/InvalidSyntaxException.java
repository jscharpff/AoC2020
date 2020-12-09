package util.program.exceptions;

/**
 * Creates a new {@link ArgumentException}
 * 
 * @author Joris
 */
@SuppressWarnings( "serial" )
public class InvalidSyntaxException extends Exception {
	/**
	 * Creates a new invalid argument exception
	 * 
	 * @param message The error message
	 */
	public InvalidSyntaxException( final String message ) {
		super( message );
	}
}
