package util.program.exceptions;

import util.program.Program;

@SuppressWarnings( "serial" )
public abstract class ProgramException extends Exception {
	/** Store the line number at which the exception occured */
	protected int linenumber;
	
	/**
	 * Creates a new program exception
	 *  
	 * @param program The program that halted
	 * @param message The exception message
	 */
	public ProgramException( final Program program, final String message ) {
		super( message );
		
		// store line number, correct IP by one because line numbers start at 1
		this.linenumber = program.getInstructionPointer( ) + 1;
	}
	
	/**
	 * @return The string value of the exception
	 */
	public String toString( ) {
		return super.toString( ) + " (line " + linenumber + ")";
	}
}
