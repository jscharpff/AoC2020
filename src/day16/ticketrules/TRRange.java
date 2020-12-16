package day16.ticketrules;

/**
 * Stores a value range
 *  
 * @author Joris
 */
public class TRRange {
	/** Minimal range value */
	final int min;
	
	/** Maximum range value */
	final int max;
	
	/**
	 * Creates a new range container
	 * 
	 * @param min The minimal acceptable value
	 * @param max The maximal acceptable value
	 */
	public TRRange( final int min, final int max ) { 
		this.min = min;
		this.max = max;
	}
	
	/**
	 * Checks if a specified value is within this range
	 * 
	 * @param n The value
	 * @return True iff min <= n && n <= max
	 */
	public boolean inRange( final int n ) { 
		return min <= n && n <= max;
	}
		
	/** @return A string representation of the range */
	@Override
	public String toString( ) {
		return min + "-" + max;
	}
}
