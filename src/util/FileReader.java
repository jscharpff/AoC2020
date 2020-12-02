package util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileReader {
	/** The actual file open for reading */
	private File file;

	/** 
	 * Creates a new FileReader
	 * 
	 * @param path The path of the file to read
	 */
	public FileReader( Path path ) {
		this( path.toString( ) );
	}
	
	/** 
	 * Creates a new FileReader
	 * 
	 * @param path The path of the file to read
	 */
	public FileReader( String path ) {
		this.file = new File( path );
	}
		
	/**
	 * Reads the entire file and parses lines into an Int array
	 *  
	 * @return Array of integers, one per line
	 * @throws IOException 
	 */
	public int[] readIntArray() throws IOException {
		final List<String> lines = this.readLines( );
		final int[] res = new int[ lines.size( ) ];
		
		for( int i = 0; i < lines.size( ); i++ ) {
			res[ i ] = Integer.parseInt( lines.get( i ) );
		}
		
		return res;
	}
	
	/**
	 * Reads file
	 * 
	 * @return List of strings, one per new line
	 * @throws IOException
	 */
	public List<String> readLines( ) throws IOException {
		return Files.readAllLines( this.file.toPath( ) );
	}
	
}