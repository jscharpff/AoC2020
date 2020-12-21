package day20;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import day20.TiledImage.Tile;
import day20.TiledImage.TiledImage;
import day20.TiledImage.Tile.Flip;
import util.geometry.Coord2D;
import util.grid.CharGrid;
import util.io.FileReader;

public class Day20 {
	/**
	 * Day 20 of the Advent of Code 2020
	 * 
	 * @param args The command line arguments
	 * @throws Exception
	 */
	public static void main( final String[] args ) throws Exception {
		final List<String> ex_input = new FileReader( Day20.class.getResource( "day20_example.txt" ) ).readLineGroups( "\n" );
		final List<String> input = new FileReader( Day20.class.getResource( "day20_input.txt" ) ).readLineGroups( "\n" );
		
		System.out.println( "---[ Part 1 ]---" );
		System.out.println( "Example: " + part1( ex_input ) );
		System.out.println( "Part 1 : " + part1( input ) );
		
		System.out.println( "\n---[ Part 2 ]---" );
		System.out.println( "Example: " + part2( ex_input ) );		
		System.out.println( "Part 2 : " + part2( input ) );
	}

	/**
	 * Determines the corner tiles from the set of tiles and returns the product
	 * of their IDs
	 * 
	 * @param tiles The input as a series of tiles
	 * @return The product of IDs of the corner tiles
	 * @throws Exception if not exactly 4 corner pieces were found
	 */
	protected static long part1( final List<String> input ) throws Exception {
		// parse input into tiles
		final Set<Tile> tiles = new HashSet<>( input.size( ) );
		for( String s : input ) tiles.add( Tile.fromString( s ) );
		
		// create a new TiledImage and reconstruct it
		final TiledImage img = new TiledImage( tiles );
		final List<Tile> corners = img.getCornerTiles( );

		// check if number of corner pieces is 4
		if( corners.size( ) != 4 ) throw new Exception( "Failed to get corner tiles" );
		
		// return the product of their IDs
		long prod = 1;
		for( Tile t : corners ) prod *= (long)t.getID( );
		return prod;
	}	

	/**
	 * Reconstructs the image from the tiles by matching borders, then checks
	 * how many times a sea monster may be hidden in the image. After inserting
	 * the monsters, it will return the count of "#" tiles where no monster
	 * resides...
	 * 
	 * @param tiles The input as a series of tiles
	 * @return Count of '#' tiles without a monster
	 * @throws Exception if the reconstruction was unsuccessful
	 */
	protected static long part2( final List<String> input ) throws Exception {
		// parse input into tiles
		final Set<Tile> tiles = new HashSet<>( input.size( ) );
		for( String s : input ) tiles.add( Tile.fromString( s ) );
		
		// create a new TiledImage and reconstruct it
		final TiledImage img = new TiledImage( tiles );
		final CharGrid result = img.reconstruct( );
		
		if( result == null ) throw new Exception( "Failed to reconstruct image" );

		// reconstruction successful, find the sea monsters!
		final CharGrid seamonster = CharGrid.fromFile( Day20.class.getResource( "day20_monster.txt" ).getFile( ) );
		
		// try to find matches in all configurations
		for( Flip f : Flip.values( ) ) {
			for( int rotation = 0; rotation <= 270; rotation += 90 ) {
				CharGrid searchimg = result.copy( );
				if( f != Flip.None ) searchimg = searchimg.flip( f == Flip.Horizontal );
				searchimg = searchimg.rotate( rotation );
				
				final List<Coord2D> matches = searchimg.findMatches( seamonster, new char[] { '#' } );
				if( matches.size( ) == 0 ) continue;
				
				// replace tiles used by sea monster
				CharGrid monsters = searchimg;
				for( Coord2D c : matches ) {
					monsters = monsters.insertPattern( seamonster, c, '#', 'O' );
				}
				// count the tiles that are not part of any sea monster
				return monsters.countTile( '#' );
			}
		}
		
		// no sea monster here!
		return 0;
	}	
}
