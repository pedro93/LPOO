package Game_Logic;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

// TODO: Auto-generated Javadoc
/**
 * The Class myRandom.
 * Class used in dragon class to randomize its movements
 * Implements random to replace the randomization of the dragon's movement
 */
@SuppressWarnings("serial")
public class myRandom extends Random{

	/**
	 *  The queue.
	 *  data used in Junit testing to direct dragons instead of random values
	 */
	public Queue<Integer> queue = new LinkedList<Integer>();

	/** The instance my random. */
	private static myRandom instanceMyRandom = null;
	
	/**
	 * Instantiates a new my random.
	 */
	private myRandom() {}
	
	/**
	 * Gets the single instance of myRandom.
	 *
	 * @return single instance of myRandom
	 */
	public static myRandom getInstance() {
		if (instanceMyRandom == null) 
			instanceMyRandom = new myRandom();
		return instanceMyRandom;
	}

	/* (non-Javadoc)
	 * @see java.util.Random#nextInt(int)
	 */
	/**
	 * Overload of function from random class for Junit Purposes
	 * If queue is empty works with original nextInt function,
	 * otherwise returns a value placed in the queue
	 *
	 * @return int which indicates the direction a dragon must go
	 **/
	@Override
	public int nextInt(int n) {
		int aux;
		if(queue.isEmpty())
		{
			Random r = new Random();
			aux = r.nextInt(4) + 1;
		}
		else {
			aux= queue.peek();
			queue.remove();
		}
		return aux;

	}
}
