package util.program.exceptions;

import util.program.Instruction;
import util.program.Program;

@SuppressWarnings( "serial" )
public class UnsupportedInstructionException extends ProgramException {

	public UnsupportedInstructionException( Program program, final Instruction inst ) {
		super( program, "Unsupported instruction: " + inst.toString( ) );
	}
}
