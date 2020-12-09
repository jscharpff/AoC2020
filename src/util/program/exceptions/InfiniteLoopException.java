package util.program.exceptions;

import util.program.Program;

@SuppressWarnings( "serial" )
public class InfiniteLoopException extends ProgramException {
	/**
	 * Creates a new InifiteLoopException
	 * 
	 * @param program The program that encountered th exception
	 * @param jumpidx Index of the jump instruction
	 */
	public InfiniteLoopException( Program program, final int jumpidx ) {
		// correct line number by 2: 1 because jump sets index to instruction - 1 so that it is executed next,
		// the other because line numbers start at 1
		super( program, "Infinite loop detected while jumping to line " + (program.getInstructionPointer( ) + 2) );
		
		// overwrite the current line number by that of the jump instruction
		this.linenumber = jumpidx + 1;
	}
}
