package util.program;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import util.io.FileReader;
import util.program.Instruction.Op;
import util.program.ProgramLogger.LogType;
import util.program.exceptions.InfiniteLoopException;
import util.program.exceptions.InvalidProgramException;
import util.program.exceptions.InvalidSyntaxException;
import util.program.exceptions.ProgramException;
import util.program.exceptions.UnsupportedInstructionException;

public class Program {
	/** The instruction list */
	protected List<Instruction> program;
	
	/** The current instruction counter */
	protected int IP;
	
	/** Global register */
	protected int register;
	
	/** Logger */
	protected final ProgramLogger logger;
	
	/**
	 * Creates a new blank program
	 */
	protected Program( ) {
		program = new ArrayList<>( );
		logger = new ProgramLogger( LogType.Info, LogType.Error );
	}
		
	/** @return Current instruction pointer */
	public int getInstructionPointer( ) { return IP; }
	
	/** @return Current register value  */
	public int getRegisterValue( ) { return register; }

	/** @return The instruction set */
	public List<Instruction> getInstructionSet() { return program; }
	
	/** @return The logger used */
	public ProgramLogger getLogger( ) { return logger; }
	
	/**
	 * Adds an instruction to the program listing
	 * 
	 * @param instr The instruction to add
	 */
	protected void addInstruction( final Instruction instr ) {
		this.program.add( instr );
	}
	
	/**
	 * Starts the execution of the program
	 * 
	 * @return The exit status code, 0 for successful exit
	 */
	public int run( ) throws ProgramException {
		// check validity
		if( program.size( ) == 0 ) throw new InvalidProgramException( this, "No instructions in program", -1 );
		
		// initialise program
		IP = -1;
		register = 0;	
		int prevIP = -1;
		
		// keep track of executed instructions to prevent infinite loops
		final Set<Integer> executed = new HashSet<>( program.size( ) / 2 );
		
		// start running
		while( ++IP < program.size( ) ) {
			// do cycle detection
			if( executed.contains( IP ) ) halt( new InfiniteLoopException( this, prevIP ) );
			executed.add( IP );

			// store current instruction pointer for logging purposes
			prevIP = IP;
			
			final Instruction inst = program.get( IP );
			debug( "Executing instruction " + inst );
			performInstruction( inst );
		}
		
		// terminated successfully
		logger.info( "Program terminated successfully" );
		return 0;
	}
	
	/**
	 * Performs a single instruction
	 * 
	 * @param inst The instruction to perform
	 * @throws ProgramException in case of unsupported instruction
	 */
	public void performInstruction( final Instruction inst ) throws ProgramException {
		switch( inst.getOperation( ) ) {
			case NOP: 
				// do nothing
				break;
			
			case ACC:
				// change value of global register by argument
				final int addvalue = inst.getArgument( ).getValue( );
				debug( "Adding " + addvalue + " to global register (old value = " + register + ", new value = " + (register + addvalue) + ")" );
				register += addvalue;
				break;
				
			case JMP:
				// jump relative to current instruction by argument 
				final int targetip = IP + inst.getArgument( ).getValue( ) - 1;
				debug( "Jump to line " + (targetip + 2) );
				IP = targetip;
				break;
				
			default:
				// no definition for the encountered operation
				throw new UnsupportedInstructionException( this, inst );
		}
	}
	
	/**
	 * Halts the execution of the program due to the specified exception
	 * 
	 * @param exception The encountered problem
	 * @throws ProgramException The exception that was encountered
	 */
	protected void halt( final ProgramException exception ) throws ProgramException {
		logger.error( exception.toString( ) );
		throw exception;
	}
	
	/**
	 * Debug message including line number
	 * 
	 * @param message The message to log
	 */
	protected void debug( final String message ) {
		logger.debug( "[line " + (IP + 1) + "] " + message );
	}
	
	/**
	 * Attempts to resolve jump loops by changing a jmp into another instruction. Detects the
	 * jumps that will lead to revisiting a previously instruction and replaces them
	 * 
	 * @param fix_op The operation to replace them by
	 * @return The number of jumps replaced
	 */
	public int fixJumpCycles( final Op fix_op ) {
		int fixed = 0;
		int idx = -1;
		boolean[] visited = new boolean[ program.size( ) ];
		
		while( ++idx < program.size( ) ) {
			visited[ idx ] = true;
			
			// get current instruction, check for jumps only
			final Instruction instr = program.get( idx );
			if( instr.getOperation( ) != Op.JMP ) continue;
			
			// its a jump, check i it leads to a cycle by jumping to already visited code
			final int target_idx = idx + instr.getArgument( ).getValue( );
			if( target_idx >= program.size( ) || !visited[ target_idx ] ) {
				// not seen before, perform jump
				idx = target_idx - 1;
				continue;
			} else {
				// leads to a cycle, replace it
				program.set( idx, new Instruction( fix_op, instr.getArgument( ) ) );
				fixed++;
			}
		}
		return fixed;
	}
	
	/**
	 * Loads a program from a file
	 * 
	 * @param infile The file to load
	 * @return The program
	 * @throws IOException 
	 * @throws InvalidProgramException if the program definition is invalid
	 */
	public static Program fromFile( final String infile ) throws IOException, InvalidProgramException {
		final FileReader f = new FileReader( infile ); 
		final List<String> input = f.readLines( );

		// construct a new program by parsing instructions per line
		final Program program = new Program( );
		int lineno = 0;
		for( String line : input ) {
			// keep track of line number for logging and error messasges
			lineno++;
			
			final String[] s = line.split( " " );
			try {
				// check if the line format matches the instruction format
				if( s.length != 2 ) throw new InvalidSyntaxException( "Expected an instruction followed by argument, received '" + line + "'" );
				
				program.addInstruction( Instruction.fromString( s[0], s[1] ) );
			} catch( InvalidSyntaxException e ) {
				throw new InvalidProgramException( program, "Invalid syntax in program definition\n\n" + e.toString( ), lineno );
			}
		}
		
		return program;
	}
	
	/**
	 * @return The program listing as a string
	 */
	public String toString( ) {
		String r = "";
		for( Instruction i : program )
			r += i.toString() + "\n";
			return r;
	}
}
