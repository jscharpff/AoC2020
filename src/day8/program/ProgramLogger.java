package day8.program;

import java.util.EnumSet;

/**
 * Logger for the logging of messages during program execution
 * 
 * @author Joris
 */
public class ProgramLogger {
	/** The available log levels */
	public enum LogType {
		None,
		Error,
		Info,
		Debug;
	}
	
	/** The currently configured log types */
	protected final EnumSet<LogType> logtypes;
	
	/**
	 * Creates a new logger
	 * 
	 * @param logtypes The log types to output
	 */
	public ProgramLogger( final LogType... logtypes ) {
		// create empty log type enum and add specified types
		this.logtypes = EnumSet.noneOf( LogType.class );
		for( LogType l : logtypes ) this.logtypes.add( l );
	}
	
	/**
	 * Enables/disables the logging of the specified log type
	 * 
	 * @param logtype The type to enable or disable
	 * @param enable True to enable, false to diable
	 */
	public void setLogtypeEnabled( final LogType logtype, final boolean enable ) {
		if( enable ) 
			this.logtypes.add( logtype );
		else 
			this.logtypes.remove( logtype );
	}
	
	/**
	 * Disables all logging
	 */
	public void disable( ) {
		this.logtypes.clear( );
	}
	
	/**
	 * Logs a message to sysout if the logtype is configured to output
	 * 
	 * @param logtype The log type required
	 * @param message The message to log
	 */
	public void log( final LogType logtype, final String message ) {
		// determine if the log level should lead to output
		if( !this.logtypes.contains( logtype ) ) return;
		
		if( logtype == LogType.Error )
			System.err.println( message );
		else
			System.out.println( message );
	}
	
	/**
	 * Convenient function to log error messages
	 * 
	 * @param message The error message to log
	 */
	public void error( final String message ) {
		this.log( LogType.Error, message );
	}
	
	
	/**
	 * Convenient function to log info messages
	 * 
	 * @param message The info message to log
	 */
	public void info( final String message ) {
		this.log( LogType.Info, message );
	}
	
	/**
	 * Convenient function to log debug messages
	 * 
	 * @param message The debug message to log
	 */
	public void debug( final String message ) {
		this.log( LogType.Debug, message );
	}
}
