package day03;

import util.geometry.Coord2D;
import util.map.Grid;
import util.map.Tileset;

public class Day3 {
	
	/**
	 * Day3 of the 2020 Advent of Code challenge
	 * 
	 * @param args
	 * @throws Exception 
	 */
	public static void main( String[] args ) throws Exception {
		final Tileset tileset = new Tileset( '.', '#' );
		final Grid ex_grid = Grid.fromFile( Day3.class.getResource( "day3_example.txt" ).getFile( ), tileset );
		final Grid grid = Grid.fromFile( Day3.class.getResource( "day3_input.txt" ).getFile( ), tileset );
				
		System.out.println( "---[ Part 1 ]---" );
		System.out.println( "Example: " + part1( ex_grid ) );
		System.out.println( "Part 1 : " + part1( grid ) );
		
		System.out.println( "\n---[ Part 2 ]---" );
		System.out.println( "Example: " + part2( ex_grid ) );
		System.out.println( "Part 2 : " + part2( grid ) );
	}
	
	/**
	 * Part 1 of the day: compute number of trees for a given slope
	 * 
	 * @param grid The grid to traverse
	 */
	protected static int part1( final Grid grid ) throws Exception {
		// load the map from the input and create tobbogan
		final Toboggan tob = new Toboggan( new Coord2D( 0,0 ) );
		tob.setSlope( 3, 1 );
		
		return countTrees( grid, tob );		
	}
	
	/**
	 * Part 2 of the day: compute number of trees for a set of slopes and multiply counts
	 * 
	 * @param grid The map to traverse
	 */
	protected static int part2( final Grid grid ) throws Exception {
		// load the example map from the input and create Toboggan
		final Coord2D[] slopes = { new Coord2D( 1, 1 ), new Coord2D( 3, 1 ), new Coord2D( 5, 1 ), new Coord2D( 7, 1 ), new Coord2D( 1, 2 ) }; 
		int result = 1;
		for( Coord2D s : slopes ) {
			final Toboggan tob = new Toboggan( new Coord2D( 0, 0 ) );
			tob.setSlope( s.x, s.y );
		
			result *= countTrees( grid, tob );
		}
		return result;
	}
	
	/**
	 * Counts the number of trees encountered on the path to the southern
	 * border of the grid
	 * 
	 * @param grid The grid to traverse
	 * @param tob The Toboggan traversing it
	 * @return The number of trees encountered when the Toboggan travels along its slope
	 */
	protected static int countTrees( final Grid grid, final Toboggan tob ) {
		int count = 0;
		
		while( tob.getPosition( ).y < grid.getHeight() ) {
			if( grid.getTile( tob.getPosition( ) ).hasLabel( '#' ) ) count++;
			tob.move( );
		}
		
		return count;
	}
}
