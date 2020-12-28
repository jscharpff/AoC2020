package day25;

public class Key {
	/** The public encryption key */
	protected final long key;
	
	/**
	 * Creates a new encryption key
	 * 
	 * @param publickey The public key of the device
	 */
	public Key( final long publickey ) {
		this.key = publickey;
	}
	
	/**
	 * Determine loop size, given a subject and target value
	 * 
	 * @param subject The initialising value
	 * @oaram targetvalue The value to find
	 * @return The number of loops required to find the value
	 */
	public int getLoopsize( final int subject, final long targetvalue ) {
		long value = 1;
		int loops = 0;
		while( value != targetvalue ) {
			loops++;
			value *= subject;
			value = value % 20201227;
		}
		return loops;
	}
	
	/**
	 * Performs secret encryption loops
	 * 
	 * @param subject The starting value
	 * @param n The number of encryption loops to perform
	 * @return The encryption key after n loops
	 */
	public long loop( final long subject, final int n ) {
		long newval = 1;
		for( int i = 0; i < n ; i++ ) {
			newval *= subject;
			newval  = newval % 20201227;
		}
		return newval;
	}

}
