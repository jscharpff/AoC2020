package day7;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.FileReader;
import util.dag.Arc;
import util.dag.DAG;
import util.dag.Node;

public class Day7 {
	/**
	 * Day 7 of the advent of code
	 * 
	 * @param args The command line arguments
	 * @throws Exception
	 */
	public static void main( final String[] args ) throws Exception {
		
		final DAG example1 = createDAG( Day7.class.getResource( "day7_example.txt" ).getFile( ) );
		final DAG example2 = createDAG( Day7.class.getResource( "day7_example2.txt" ).getFile( ) );
		final DAG input = createDAG( Day7.class.getResource( "day7_input.txt" ).getFile( ) );
		
		final String MY_BAG = "shiny gold";
		
		System.out.println( "---[ Part 1 ]---" );
		System.out.println( "Example: " + countContaining( example1, MY_BAG ) );
		System.out.println( "Part 1 : " + countContaining( input, MY_BAG ) );
		
		System.out.println( "\n---[ Part 2 ]---" );
		System.out.println( "Example: " + countContained( example1, MY_BAG ) );
		System.out.println( "Example: " + countContained( example2, MY_BAG ) );
		System.out.println( "Part 2 : " + countContained( input, MY_BAG ) );
	}
	
	/**
	 * Creates a DAG of nodes from a fileset of rules 
	 * 
	 * @param infile The input file
	 * @return The DAG
	 * @throws IOException
	 */
	protected static DAG createDAG( String infile ) throws IOException {
		final FileReader f = new FileReader( infile ); 
		final List<String> input = f.readLines( );
		final DAG dag = new DAG( );
		
		// go over rules and parse into nodes and arcs
		for( String s : input ) {
			final String[] rule = s.split( " bags contain " );
			final Node bag = dag.addNode( rule[0].toLowerCase( ) );
			
			// get all sub-bags
			final Matcher m = Pattern.compile( "(\\d+) ([\\w ]+) bags?" ).matcher( rule[1] );
			while( m.find( ) ) {
				final Node b2 = dag.addNode( m.group( 2 ) );
				dag.addArc( new Arc( bag, b2, Integer.parseInt( m.group( 1 ) ) ));
			}
		}
		
		return dag;
	}
	
	/**
	 * Counts the unique nodes that may contain the node (by label)
	 * 
	 * @param dag The directed acyclic graph describing the ruleset
	 * @param label The node label to check
	 * @return The number of nodes that may contain it 
	 */
	protected final static int countContaining( final DAG dag, final String label ) {
		final Node node = dag.getNode( label );
		if( node == null ) throw new RuntimeException( "Failed to find the node " + label );
		
		// all nodes that may contain it
		final Set<Node> containers = new HashSet<>( dag.size() );
		
		// nodes to be explored in next iteration
		final Stack<Node> explore = new Stack<>( );
		explore.push( node );

		while( explore.size( ) > 0 ) {
			final Node n = explore.pop( );
		
			// check if already in containers list
			if( containers.contains( n ) ) continue;
			containers.add( n );
			
			// get all nodes that may contain it
			for( Arc arc : n.getIncoming( ) ) {
				// add the node for exploration, will be ignored later if already explored
				explore.push( arc.getFrom( ) );
			}
		}
		
		// remove original node from the containers set and return the container count
		containers.remove( node );		
		return containers.size( );
	}
	
	/**
	 * Counts the unique nodes that are contained the node (by label)
	 * 
	 * @param dag The directed acyclic graph describing the ruleset
	 * @param label The node label to start from
	 * @return The number of nodes that may be contained by it 
	 */
	protected final static int countContained( final DAG dag, final String label ) {
		final Node node = dag.getNode( label );
		if( node == null ) throw new RuntimeException( "Failed to find the node " + label );

		// start recursive expansion of nodes via arcs, but exclude the starting bag from the final count
		// after all, the question is to determine the number of bags contained by it
		return countContained( dag, node ) - 1;
	}
	
	/**
	 * Recursive function to count the number of bags contained by the current bag 
	 * 
	 * @param dag The ruleset as a DAG
	 * @param node The current bag to explore
	 * @return The count of bags contained by the current bag
	 */	
	protected static int countContained( final DAG dag, final Node node ) {
		// no more bags inside me?
		if( node.isLeaf() ) return 1;
		
		// recurse for all outgoing arcs, i.e. contained bags
		int result = 1; // include the current node as a bag itself of course
		for( Arc a : node.getOutgoing( ) ) {
			result += countContained( dag, a.getTo( ) ) * a.getWeight( );
		}
		return result;
	}
}
