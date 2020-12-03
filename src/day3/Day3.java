package day3;

import day3.map.Map;
import day3.map.Tile;
import day3.map.Toboggan;
import util.geometry.Coord2D;

public class Day3 {
	
	/**
	 * Day3 of the 2020 Advent of Code challenge
	 * 
	 * @param args
	 * @throws Exception 
	 */
	public static void main( String[] args ) throws Exception {
		final Map ex_map = Map.fromFile( Day3.class.getResource( "day3_example.txt" ).getFile( ) );
		final Map map = Map.fromFile( Day3.class.getResource( "day3_input.txt" ).getFile( ) );
		
		System.out.println( "---[ Part 1 ]---" );
		System.out.println( "Example: " + part1( ex_map ) );
		System.out.println( "Part 1 : " + part1( map ) );
		
		System.out.println( "\n---[ Part 2 ]---" );
		System.out.println( "Example: " + part2( ex_map ) );
		System.out.println( "Part 2 : " + part2( map ) );
	}
	
	/**
	 * Part 1 of the day: compute number of trees for a given slope
	 * 
	 * @param map The map to traverse
	 */
	protected static int part1( final Map map ) throws Exception {
		// load the map from the input and create tobbogan
		final Toboggan tob = new Toboggan( new Coord2D( 0,0 ) );
		tob.setSlope( 3, 1 );
		
		return countTrees( map, tob );		
	}
	
	/**
	 * Part 2 of the day: compute number of trees for a set of slopes and multiply counts
	 * 
	 * @param map The map to traverse
	 */
	protected static int part2( final Map map ) throws Exception {
		// load the example map from the input and create Toboggan
		final Coord2D[] slopes = { new Coord2D( 1, 1 ), new Coord2D( 3, 1 ), new Coord2D( 5, 1 ), new Coord2D( 7, 1 ), new Coord2D( 1, 2 ) }; 
		int result = 1;
		for( Coord2D s : slopes ) {
			final Toboggan tob = new Toboggan( new Coord2D( 0, 0 ) );
			tob.setSlope( s.x, s.y );
		
			result *= countTrees( map, tob );
		}
		return result;
	}
	
	/**
	 * Counts the number of trees encountered on the path to the southern
	 * border of the map
	 * 
	 * @param map The map to traverse
	 * @param tob The Toboggan traversing it
	 * @return The number of trees encountered when the Toboggan travels along its slope
	 */
	protected static int countTrees( final Map map, final Toboggan tob ) {
		int count = 0;
		
		while( tob.getPosition( ).y < map.getHeight() ) {
			if( map.getTile( tob.getPosition( ) ) == Tile.Tree ) count++;
			tob.move( );
		}
		
		return count;
	}
}
