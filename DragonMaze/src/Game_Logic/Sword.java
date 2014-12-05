package Game_Logic;
import java.io.Serializable;
import java.util.Random;
import maze.Labirynth;

// TODO: Auto-generated Javadoc
/**
 * The Class Sword.
 * Class used by the user to change Hero class (upgrade)
 */
@SuppressWarnings("serial")
public class Sword extends Static implements Serializable{

	/**
	 *  The used. 
	 * Boolean used to indicate if object has been used or not.
	 * */
	private boolean used = false;

	/**
	 * Checks if is used.
	 *
	 * @return true, if is used
	 */
	public boolean isUsed() {
		return used;
	}

	/**
	 * Sets the used.
	 *
	 * @param used the new used
	 */
	public void setUsed(boolean used) {
		this.used = used;
	}
	
	/**
	 * Inits.
	 *
	 * Initiates the sword with the specified parameters and returns it
	 *
	 * @param jogo the game instance
	 * @return the sword
	 */
	public Sword init(Game_Loop jogo) {
		Sword auxSword = new Sword();
		auxSword.setUsed(false);
		auxSword.setSymbol('E');
		int test_x,test_y;
		do 
		{
			Random rand = new Random();
			test_x=rand.nextInt(Labirynth.getInstance().COL_SIZE-2)+1;
			test_y=rand.nextInt(Labirynth.getInstance().ROW_SIZE-2)+1;
		} while (Labirynth.getInstance().getGenerated_maze()[test_y][test_x].isFilled() || jogo.ocupied_byElements(test_x, test_y));
		auxSword.setX(test_x);
		auxSword.setY(test_y);
		jogo.elements.add(auxSword);
		return auxSword;
	}
	
	/**
	 * InitTest.
	 *
	 * Initiates sword with specific parameters suitable for testing (Junit) purposes
	 *
	 * @param jogo the game instance
	 * @return the sword
	 */
	public Sword initTest(Game_Loop jogo) {
		Sword auxSword = new Sword();
		auxSword.setUsed(false);
		auxSword.setSymbol('E');
		auxSword.setX(1);
		auxSword.setY(8);
		jogo.elements.add(auxSword);
		return auxSword;
	}
}
