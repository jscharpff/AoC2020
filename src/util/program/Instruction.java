package util.program;

import util.program.exceptions.InvalidSyntaxException;

public class Instruction {
	/** The instruction operation */
	public enum Op {
		NOP,
		JMP,
		ACC
	}
	
	/** The operation to perform */
	protected final Op op;
	
	/** The operation argument */
	protected final Argument arg;
	
	/**
	 * Creates a new instruction
	 * 
	 * @param op The operation
	 * @param val The operation argument value
	 */
	public Instruction( final Op op, final Argument val ) {
		this.op = op;
		this.arg = val;
	}
	
	/** @return The operation */
	public Op getOperation() { return op; }
	
	/** @return The instruction argument value */
	public Argument getArgument( ) { return arg; }
	
	/**
	 * Builds instruction from string operator and argument value
	 * 
	 * @param operation The operation as string
	 * @param value The integer argument value
	 * @throws InvalidSyntaxException if the syntax is invalid
	 */
	public static Instruction fromString( final String operation, final String value ) throws InvalidSyntaxException {
		try {
			final Op op = Op.valueOf( operation.toUpperCase( ) );
			return new Instruction( op, Argument.fromString( value ) );
		} catch( IllegalArgumentException  e ) { 
			/** Thrown when the enum value cannot be matched */
			throw new InvalidSyntaxException( "Unknown operation '" + operation + "'" );
		}
	}
	
	/**
	 * @return The string representation of the instruction
	 */
	public String toString( ) {
		return op + " " + arg;
	}
}
