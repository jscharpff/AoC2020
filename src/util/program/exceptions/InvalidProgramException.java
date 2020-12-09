package util.program.exceptions;

import util.program.Program;

@SuppressWarnings( "serial" )
public class InvalidProgramException extends ProgramException {

	/**
	 * Creates a new InvalidProgramException
	 * 
	 * @param program The program that is invalid
	 * @param message The exception message
	 * @param linenumber The line number at which the error is encountered, needed because
	 *                   there is no active instruction pointer yet
	 */
	public InvalidProgramException( Program program, String message, int linenumber ) {
		super( program, message, linenumber );
	}
}
