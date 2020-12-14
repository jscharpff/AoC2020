package day14.mem;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that holds memory using a predefined bitmap
 * 
 * @author Joris
 */
public class Memory {
	/** The bitmask to use, string as it can hold 'X' to indicate no change */
	protected char[] mask;
	
	/** The current memory units in use */
	protected Map<Long, Long> mem;
	
	/**
	 * Creates a new memory container
	 */
	public Memory( ) {
		this.mem = new HashMap<>( );
		mask = new char[ 36 ];
		for( int i = 0; i < mask.length; i++ ) mask[i] = '1';
	}
	
	
	/**
	 * Sets the current bit mask
	 * 
	 * @param mask The bit mask to use
	 * @return The previous mask
	 */
	public String setMask( final String mask ) {
		final String old = String.valueOf( this.mask );
		this.mask = mask.toCharArray( );
		return old;
	}
	
	
	/**
	 * Loads the value of the spoecified memory bank
	 * 
	 * @param n The memory index
	 * @return The value stored at n
	 */
	public long load( final long n ) {
		return mem.get( n );
	}
		
	/**
	 * Stores a value in the specfied memory slot after appliyig the bit mask
	 * 
	 * @param n The memory bank to store it in
	 * @param value The value to store
	 * @return The stored value (after applying bit mask)
	 */
	public long store( final long n, final long value ) {
		final long v = applyValueMask( value );
		mem.put( n, v );
		return v;
	}
	
	/**
	 * Applies the current bit mask to a value
	 * 
	 * @param value The value to apply to
	 * @return The value after applying the bit mask	
	 */
	protected long applyValueMask( final long value ) {
		long result = value;
		for( int i = mask.length - 1; i >= 0; i-- ) {
			final char m = mask[i];
			if( m == 'X' ) continue;
			
			final int bit = mask.length - i - 1;
			final long bval = (long)Math.pow( 2, bit );
			if( m == '1' ) result |= bval;
			if( m == '0' ) result &= ~bval;
		}
		return result;
	}
	
	/**
	 * Sums all memory registers
	 * 
	 * @return The sum
	 */
	public long sum( ) {
		return mem.values( ).stream( ).reduce( 0l, (a, b) -> a+b );
	}

	/** @return A string containing all memory bank value */
	@Override
	public String toString( ) {
		return mem.toString( );
	}
}
