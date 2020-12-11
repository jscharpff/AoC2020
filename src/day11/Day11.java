package day11;

import util.grid.CharGrid;

public class Day11 {

	/**
	 * Day 11 of the Advent of Code of 2020
	 * 
	 * @param args The command line argument
	 * @throws Exception
	 */
	public static void main( final String[] args ) throws Exception {
		final char[] tileset = new char[] { '.', 'L', '#' };
		final CharGrid ex_grid = CharGrid.fromFile( Day11.class.getResource( "day11_example.txt" ).getFile( ), tileset );
		final CharGrid grid = CharGrid.fromFile( Day11.class.getResource( "day11_input.txt" ).getFile( ), tileset );
		
		System.out.println( "---[ Part 1 ]---" );
		System.out.println( "Example: " + countSeats( ex_grid, false ) );
		System.out.println( "Part 1 : " + countSeats( grid, false ) );

		System.out.println( "\n---[ Part 2 ]---" );
		System.out.println( "Example: " + countSeats( ex_grid, true ) );
		System.out.println( "Part 2 : " + countSeats( grid, true ) );
	}
	
	/**
	 * Count the number of occupied seats in the eventual equilibrium
	 * 
	 * @param grid The grid layout to use in the floorplan simulation
	 * @param visibleseats False to count neighbouring seats, true for visible 
	 * seats in all directions
	 * @return The number of occupied seats
	 */
	protected static int countSeats( final CharGrid grid, final boolean visibleseats ) {
		final FloorPlanSim sim = new FloorPlanSim( grid );

		// simulate (max 1000000 rounds to prevent infinite loops)
		int rounds = 1000000;
		while( sim.nextRound( visibleseats ) > 0 && rounds > 0 ) {	rounds--; }
		
		if( rounds == 0 ) throw new RuntimeException( "Failed to reach equilibrium" );
		System.out.println( "Simulation terminated in equilibrium after " + sim.getRounds( ) + " round(s)" );
		
		// count occupied seats
		return sim.countOccupied( );
	}
}
