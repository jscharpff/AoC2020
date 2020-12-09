package util.program.exceptions;

import util.program.Program;

@SuppressWarnings( "serial" )
public abstract class ProgramException extends Exception {
	/** Store the line number at which the exception occured */
	protected final int linenumber;
	
	/**
	 * Creates a new program exception
	 *  
	 * @param program The program that halted
	 * @param message The exception message
	 */
	public ProgramException( final Program program, final String message ) {
		// store line number, correct IP by one because line numbers start at 1
		this( program, message, program.getInstructionPointer( ) + 1 );
	}
	
	/**
	 * Creates a new program exception with a specific line number
	 *  
	 * @param program The program that halted
	 * @param message The exception message
	 * @param linenumber The linenumber at which the error is encountered
	 */
	public ProgramException( final Program program, final String message, int linenumber ) {
		super( message );
		
		// store line number
		this.linenumber = linenumber;
	}
	
	/**
	 * @return The string value of the exception
	 */
	public String toString( ) {
		return super.toString( ) + " (line " + linenumber + ")";
	}
}
