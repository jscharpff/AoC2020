package day8.program.exceptions;

import day8.program.Program;

@SuppressWarnings( "serial" )
public class InvalidProgramException extends ProgramException {

	public InvalidProgramException( Program program, String message ) {
		super( program, message );
	}
}
