package day23.rotatinglist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Rotating list
 * 
 * @author Joris
 *
 * @param <T> The type of the contained elements
 */
public class IndexedRotatingList<T extends Object> implements Iterable<T> {
	/** The current item */
	protected RLNode<T> curr;
	
	/** Item index for quick lookups */
	protected final Map<T, RLNode<T>> nodemap;
	
	/**
	 * Creates a new rotating list from the collection. Sets the first element as
	 * the current node
	 * 
	 * @param elements The elements it should contain
	 */
	public IndexedRotatingList( final Collection<T> elements ) {
		this.curr = null;
		nodemap = new HashMap<>( );

		// add all elements
		addAll( elements );
	}
	
	/**
	 * Adds all the elements in the specified collection
	 * 
	 * @param elements The collection to add
	 */
	public void addAll( final Collection<T> elements ) {
		RLNode<T> node = curr;
		for( T e : elements ) {
			node = add( node, e );
		}
	}
	
	/**
	 * Inserts the node
	 * 
	 * @param after The node to insert it after, can be null to insert first element
	 * @param value The value to insert
	 * @return The newly insert node
	 */
	protected RLNode<T> add( final RLNode<T> after, final T value ) {
		// create element to hold the value
		final RLNode<T> item = new RLNode<T>( value );
		nodemap.put( value, item );		

		if( after == null ) {
			if( curr != null ) throw new RuntimeException( "After can oly be null if the list is empty" );
			
			// insert first item of the list
			curr = item;
			curr.next = curr;
			curr.prev = curr;
			return item;
		}
		
		
		// "insert" the item
		item.next = after.next;
		item.prev = after;
		
		// and update links in neighbours
		after.next.prev = item;
		after.next = item;

		// return the newly created item
		return item;
	}
	
	/**
	 * Adds the value after the specified find value 
	 * 
	 * @param findvalue The value of the node to insert the new value after
	 * @param value The value to insert
	 */
	public void add( final T findvalue, final T value ) {
		add( find( findvalue ), value );
	}
	
	/**
	 * Removes the specified node from the list
	 * 
	 * @param node The node to remove
	 * @return The node that is removed
	 */
	protected RLNode<T> remove( final RLNode<T> node ) {
		// remove the node 
		nodemap.remove( node.value );

		// move head or current node, if removed
		if( node.equals( curr ) ) {
			// no more elements
			if( size() == 1 ) {
				curr = null;
				node.next = null;
				node.prev = null;
				return node;
			}
			
			// move current to next node
			curr = curr.next;
		}
		
		// update links and administration
		node.prev.next = node.next;
		node.next.prev = node.prev;
		node.next = null;
		node.prev = null;
		return node;
	}
	
	/**
	 * Removes the specified value from the list
	 * 
	 * @param value The value to remove
	 */
	public void remove( final T value ) {
		remove( find( value ) );
	}

	/**
	 * Converts the entire rotating list into a flat list
	 * 
	 * @return The list
	 */
	public List<T> toList( ) {
		return toList( -1 );
	}
	
	/**
	 * Converts the rotating list into a "flat" list, starting from the current
	 * node onwards
	 * 
	 * @param n The number of nodes to include in the list, -1 for all
	 * @return The list containing n nodes, starting from the current node
	 */
	public List<T> toList( final int n ) {
		final List<T> list = new ArrayList<T>( size( ) );
		RLNode<T> node = this.curr;
		int nodes = (n < 0 || n > size( ) ? size( ) : n); 
		do {
			list.add( node.value );
			node = node.next;
		} while( --nodes > 0 );
		return list;
	}
	
	/** @return The value of the current node */
	public T getCurrent( ) { return curr.value; }
	
	/**
	 * Sets the current to the specified value
	 * 
	 * @param value The value to set as current
	 */
	public void setCurrent( final T value ) {
		this.curr = this.find( value );
	}
	
	/** @return The number of nodes in this list */
	public int size( ) { return nodemap.size( ); }

	/**
	 * Removes n elements after the current from the list
	 * 
	 * @param n The number of elements to remove
	 * @return The removed elements in a stack
	 */
	public Stack<T> removeAfter( final int n ) {
		final Stack<T> removed = new Stack<T>( );
		for( int i = 0; i < n; i++ ) {
			final RLNode<T> node = this.curr.next;
			removed.push( remove( node ).value );
		}
		return removed;
	}
	
	/**
	 * Returns the node with specified value
	 * 
	 * @param value The value to look for
	 * @return
	 */
	protected RLNode<T> find( final T value ) {
		final RLNode<T> node = nodemap.get( value );
		if( node == null ) throw new RuntimeException( "Failed to find value " + value );
		return node;
	}

	/**
	 * Moves the current node to the next node
	 */
	public void next( ) {
		this.curr = this.curr.next;
	}
	
	/**
	 * @return The list values as string, starting from the current node
	 */
	@Override
	public String toString( ) {
		String res = "";
		for( T value : toList( ) )
			res += "" + value + ",";
		return res.substring( 0, res.length( ) - 1 );
	}

	/**
	 * @return Iterator over the values contained in this list, starting from 
	 * the current node
	 */
	@Override
	public Iterator<T> iterator( ) {
		return new Iterator<T>( ) {
			/** Current node */
			protected RLNode<T> current = curr;
			
			/** Remaining iterations (easier than equals checking) */
			protected int iterations = size();

			@Override
			public boolean hasNext( ) {
				return iterations > 0;
			}

			@Override
			public T next( ) {
				if( !hasNext( ) ) throw new NoSuchElementException( "No more elements to iterate over" );
				iterations--;
				final T value = current.value;
				current = current.next;
				return value;
			}
			
		};
	}
}
