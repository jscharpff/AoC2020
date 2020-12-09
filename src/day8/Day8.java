package day8;

import java.util.List;

import util.program.Instruction;
import util.program.Program;
import util.program.Instruction.Op;
import util.program.exceptions.InfiniteLoopException;
import util.program.exceptions.ProgramException;

public class Day8 {
	
	/**
	 * Day 8 of the 2020 Advent of Code challenge
	 * 
	 * @param args
	 * @throws Exception 
	 */
	public static void main( String[] args ) throws Exception {
		final Program ex_program = Program.fromFile( Day8.class.getResource( "day8_example.txt" ).getFile( ) );
		final Program program = Program.fromFile( Day8.class.getResource( "day8_input.txt" ).getFile( ) ); 
		
		System.out.println( "---[ Part 1 ]---" );
		System.out.println( "Example: " + part1( ex_program ) );
		System.out.println( "Part 1 : " + part1( program ) );
		
		System.out.println( "\n---[ Part 2 ]---" );
		System.out.println( "Example: " + part2( ex_program ) );
		System.out.println( "Part 2 : " + part2( program ) );
	}
	
	/**
	 * Run the program and return the value of the accumulator when a loop is encountered
	 * 
	 * @param program The program to run
	 * @return The value of the accumulator at the moment of halting
	 * @throws ProgramException in case of unexpected error
	 */
	protected static int part1( final Program program ) throws ProgramException {
		try {
			program.run( );
		} catch( InfiniteLoopException loop ) {
			// we have found the loop, return the register value at the time of looping
			return program.getRegisterValue( );
		}
		
		// no loop encountered
		throw new RuntimeException( "Failed to solve part 1: no loop detected in progam" );
	}
	
	/**
	 * Fixed any jump cycles by replacing them with NoOps, then runs it and returns its
	 * accumulator value
	 * 
	 * @param program The program to fix and run
	 * @return The accumulator value after fixing the jump cycles
	 * @throws ProgramException in case of unexpected error
	 */
	protected static int part2( final Program program ) throws ProgramException {
		// disable logging of errors
		program.getLogger( ).disable( );
		
		// get current instruction set of the program
		final List<Instruction> instructions = program.getInstructionSet( );
		
		// try every replacement of Nops / Jumps until no more loop occurs
		for( int i = 0; i < instructions.size( ); i++ ) {
			final Instruction curr = instructions.get( i );
			
			// check if we can replace an instruction
			final Instruction newinstr;
			if( curr.getOperation( ) == Op.JMP )
				newinstr = new Instruction( Op.NOP, curr.getArgument( ) );
			else if( curr.getOperation( ) == Op.NOP )
				newinstr = new Instruction( Op.JMP, curr.getArgument( ) );
			else
				continue; // nope, just try the next one
			
			// replace instruction and see if it runs smoothly now
			instructions.set( i, newinstr );				
			try {
				// if it terminated successfully, return the value of the global register
				program.run( );
				return program.getRegisterValue( );
			} catch( InfiniteLoopException ie ) { /* still contains an infinite loop */ }
			
			// restore instruction to its previous version, this was not it... 
			instructions.set( i, curr );
		}				
		
		// failed to repair the program
		throw new RuntimeException( "Failed to solve part 2: failed to repair the jump loop" );
	}
}
