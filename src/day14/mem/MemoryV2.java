package day14.mem;

/**
 * Class that holds memory using a predefined bitmap
 * 
 * @author Joris
 */
public class MemoryV2 extends Memory {
	/**
	 * Stores a value in the specified memory slot, now uses the bit mask to
	 * determine the targeted memory bank(s)
	 * 
	 * @param n The memory bank to store it in
	 * @param value The value to store
	 * @return The stored value
	 */
	@Override
	public long store( final long n, final long value ) {
		// apply bit mask to addressing to get all target registers
		final char[] addresses = applyAddressMask( n );		
		storeRec( addresses, value );
		
		return value;
	}
	
	/**
	 * Determines all memory addressed targeted after applying the current bit
	 * mask. Every 'X' in the resulting address means that both the 0 and 1 are
	 * targeted.
	 */
	protected void storeRec( final char[] addr, long value ) {
		// keep expanding 'X's until all bits are set
		for( int b = 0; b < addr.length; b++ ) {
			if( addr[b] != 'X' ) continue;
			
			// expand bit into two recursion paths
			addr[b] = '0';
			storeRec( addr, value );
			addr[b] = '1';
			storeRec( addr, value );
			addr[b] = 'X';
			
			// done now, other 'X's are expanded in next recursions
			return;
		}
		
		// no 'X's in my path, store the value
		final Long address = Long.parseLong( String.valueOf( addr ), 2 );
		mem.put( address, value );
	}
	
	
	/**
	 * Applies the current bit mask to the address to get a new bit mask of all
	 * targeted addresses
	 * 
	 * @param index The memory index in the operation
	 * @return The targeted indexes as a bitmap pattern	
	 */
	protected char[] applyAddressMask( final long index ) {
		// get 36 bit mask of the requested index, padded left by zeroes
		String bitstring = Long.toBinaryString( index );
		while( bitstring.length( ) < 36 ) bitstring = "0" + bitstring;
		char[] addrmask = bitstring.toCharArray( );
		
		// apply current bit mask
		for( int i = mask.length - 1; i >= 0; i-- ) {
			switch( mask[i] ) {
				case 'X':
				case '1':
					// manipulate bit as dictated by mask
					addrmask[i] = mask[i];
					
				case '0':
					/* do nothing */
					break;
				
				default:
					// unknown character in bit mask
					throw new RuntimeException( "Invalid bit mask: " + String.valueOf( mask ) );
			}
		}		
		return addrmask;
	}
}
