package Game_Logic;
import java.util.Random;
import maze.Labirynth;


public class Dragon extends Mobile{
	private int mode; //dragon game mode

	public void setMode(int x)
	{
		mode =x;
	}

	public int getMode()
	{
		return mode;
	}

	public void dragonMovement(Game_Loop jogo) {
		Random r = new Random();
		int n = r.nextInt(4) + 1;

		switch(n) {
		case 1:
			if(!Labirynth.getInstance().getGenerated_maze()[getY()-1][getX()].isFilled())
				setY(getY() - 1);
			break;
		case 2:
			if(!Labirynth.getInstance().getGenerated_maze()[getY()+1][getX()].isFilled())
				setY(getY() + 1);
			break;
		case 3:
			if(!Labirynth.getInstance().getGenerated_maze()[getY()][getX()-1].isFilled())
				setX(getX() - 1);
			break;
		case 4:
			if(!Labirynth.getInstance().getGenerated_maze()[getY()][getX()+1].isFilled())
				setX(getX() + 1);
			break;
		}
	}

	public Dragon init(int mode, Game_Loop jogo) {

		Dragon drag = new Dragon();		
		drag.setDead(false);
		drag.mode=mode;
		switch (mode)
		{
		case 1:
			drag.setSymbol('d');
			break;
		case 2:
			drag.setSymbol('D');			
			break;
		case 3:
			Random rand = new Random();
			int sym = rand.nextInt(2);
			if(sym == 0) //dragon is awake
				drag.setSymbol('D'); 
			else 	 //dragon starts game sleeping
				drag.setSymbol('d'); 
			break;
		}
		int test_x,test_y;
		do 
		{
			Random rand = new Random();
			test_x=rand.nextInt(Labirynth.getInstance().COL_SIZE-2)+1;
			test_y=rand.nextInt(Labirynth.getInstance().ROW_SIZE-2)+1;
		} while (Labirynth.getInstance().getGenerated_maze()[test_y][test_x].isFilled() || jogo.ocupied_byElements(test_x, test_y));
		drag.setX(test_x);
		drag.setY(test_y);

		jogo.elementos.add(drag);
		return drag;
	}



}
