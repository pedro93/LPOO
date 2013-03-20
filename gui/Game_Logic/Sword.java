package Game_Logic;
import java.util.Random;
import maze.Labirynth;

public class Sword extends Static{

	private boolean used = false;

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
		jogo.elementos.add(auxSword);
		return auxSword;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}
	
}
