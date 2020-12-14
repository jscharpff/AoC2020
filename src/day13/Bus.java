package day13;

public class Bus {
	/** The bus schedule interval */
	protected final long interval;
	
	/** Its start offset */
	protected final long offset;
	
	/**
	 * Creates a new bus
	 * 
	 * @param ID THe bus ID, which is also its loop time
	 */
	public Bus( final int ID ) {
		this( ID, 0 );
	}
	
	/**
	 * Creates a new bus
	 * 
	 * @param interval THe bus schedule interval
	 * @param offset The offset at which the bus' schedule starts
	 */
	public Bus( final long interval, final long offset ) {
		this.interval = interval;
		this.offset = offset;
	}
	
	/** @return The schedule interval of the bus */
	public long getInterval( ) { return interval; }
	
	/**
	 * Get earliest departure time for given arrival
	 * 
	 * @param arrival Arrival time
	 * @return The earliest departure time of the bus > time
	 */
	public long getDeparture( final long arrival ) {
		return (long)Math.ceil( (double)arrival / interval ) * interval;
	}
	
	/**
	 * Checks whether the bus departs at the given time
	 * 
	 * @param time The departure time
	 * @return True iff the bus departs at the given time
	 */
	public boolean departsAt( final long time ) {
		return time % interval == 0;
	}
	
	/**
	 * Combines this bus (schedule) with another bus schedule
	 * 
	 * @param b2 The other bus
	 * @return Returns a new bus schedule that represents the combination of both
	 */
	protected Bus combine( final Bus b2 ) {
		long max_range = this.interval * b2.interval;
		
		for( long i = offset; i < max_range; i += this.interval ) {
			if( (i + b2.offset) % b2.interval == 0 ) return new Bus( this.interval * b2.interval, i );
		}
		
		throw new RuntimeException( "Cannot combine bus schedules" );
	}

	
	/** @return String representation of the bus */
	public String toString( ) {
		return "Bus " + interval;
	}
}
