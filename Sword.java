import java.util.Random;


public class Sword extends Static{

	boolean used = false;

	public Sword init(Game_Loop jogo) {
		Sword auxSword = new Sword();
		auxSword.used=false;
		auxSword.symbol='E';
		int test_x,test_y;
		do 
		{
			Random rand = new Random();
			test_x=rand.nextInt(Labirynth.getInstance().COL_SIZE-2)+1;
			test_y=rand.nextInt(Labirynth.getInstance().ROW_SIZE-2)+1;
		} while (Labirynth.getInstance().getGenerated_maze()[test_y][test_x].filled || jogo.ocupied_byElements(test_x, test_y));
		auxSword.x=test_x;
		auxSword.y=test_y;
		jogo.elementos.add(auxSword);
		return auxSword;
	}
	
}
