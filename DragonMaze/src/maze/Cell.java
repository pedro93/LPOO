package maze;
import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class Cell.
 * A cell is an element of the maze.
 */
@SuppressWarnings("serial")
public class Cell implements Serializable {
	
	/** The filled parameter of the cell. */
	boolean filled = true;
	
	/** The visited parameter of the cell. */
	boolean visited = false;

	/**
	 * Checks if is filled.
	 *
	 * @return true, if is filled
	 */
	public boolean isFilled() {
		
		return filled;
	}

	/**
	 * Sets the filled.
	 *
	 * @param filled the new filled
	 */
	public void setFilled(boolean filled) {
		this.filled = filled;
	}
	
}
