package Game_Logic;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class Game_Elements.
 * Super class used to contain all common functions
 * of all game elements (hero, enemies, sword)
 */
@SuppressWarnings("serial")
public class Game_Elements implements Serializable{

	/**
	 *  The x. 
	 *  
	 *  x coordinate of a game element object
	 */
	private int x;
	
	/** 
	 * The y.
	 *  
	 * y coordinate of a game element object
	 */
	private int y;
	
	/** 
	 * The symbol.
	 * 
	 * The symbol of a game element object
	 * used to distinguish various possible object states 
	 * and used for visual purposes in console mode 
	 * 
	 */
	private char symbol;
	
	/**
	 * Gets the y.
	 *
	 * @return the y coordinate
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Sets the y.
	 *
	 * @param y the new y 
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Gets the x.
	 *
	 * @return the x coordinate
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Sets the x.
	 *
	 * @param x the new x
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Gets the symbol.
	 *
	 * @return the symbol
	 */
	public char getSymbol() {
		return symbol;
	}
	
	/**
	 * Sets the symbol.
	 *
	 * @param symbol the new symbol
	 */
	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}

}
