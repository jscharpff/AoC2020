package day8.program.exceptions;

import day8.program.Instruction;
import day8.program.Program;

@SuppressWarnings( "serial" )
public class UnsupportedInstructionException extends ProgramException {

	public UnsupportedInstructionException( Program program, final Instruction inst ) {
		super( program, "Unsupported instruction: " + inst.toString( ) );
	}
}
