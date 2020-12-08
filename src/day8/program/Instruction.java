package day8.program;

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
	protected final int argval;
	
	/**
	 * Creates a new instruction
	 * 
	 * @param op The operation
	 * @param val The operation argument value
	 */
	public Instruction( final Op op, final int val ) {
		this.op = op;
		this.argval = val;
	}
	
	/** @return The operation */
	public Op getOperation() { return op; }
	
	/** @return The instruction argument value */
	public int getArgument( ) { return argval; }
	
	/**
	 * Builds instruction from string operator and argument value
	 * 
	 * @param operation The operation as string
	 * @param value The integer argument value
	 */
	public static Instruction fromString( final String operation, final int value ) {
		try {
			final Op op = Op.valueOf( operation.toUpperCase( ) );
			return new Instruction( op, value );
		} catch( IllegalArgumentException e ) { return null; }
	}
	
	/**
	 * @return The string representation of the instruction
	 */
	public String toString( ) {
		return op + " " + argval;
	}
}
