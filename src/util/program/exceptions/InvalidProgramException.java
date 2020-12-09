package util.program.exceptions;

import util.program.Program;

@SuppressWarnings( "serial" )
public class InvalidProgramException extends ProgramException {

	public InvalidProgramException( Program program, String message ) {
		super( program, message );
	}
}
