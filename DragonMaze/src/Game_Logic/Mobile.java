package Game_Logic;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class Mobile.
 * Super class of dragon and hero classes
 * Contains variables and functions common to dragon and hero classes.
 */
@SuppressWarnings("serial")
public class Mobile extends Game_Elements implements Serializable{

	/** dead. 
	 * Boolean used to know if object is dead or not
	 * */
	private boolean dead = false;

	/**
	 * Checks if object is dead.
	 *
	 * @return true, if object is dead
	 */
	public boolean isDead() {
		return dead;
	}

	/**
	 * Sets boolean dead.
	 *
	 * @param dead the new dead
	 */
	public void setDead(boolean dead) {
		this.dead = dead;
	}
	
}
