import java.util.Random;

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
			if(!Labirynth.getInstance().getGenerated_maze()[y-1][x].filled)
				y--;
			break;
		case 2:
			if(!Labirynth.getInstance().getGenerated_maze()[y+1][x].filled)
				y++;
			break;
		case 3:
			if(!Labirynth.getInstance().getGenerated_maze()[y][x-1].filled)
				x--;
			break;
		case 4:
			if(!Labirynth.getInstance().getGenerated_maze()[y][x+1].filled)
				x++;
			break;
		}
	}

	public Dragon init(int mode, Game_Loop jogo) {

		Dragon drag = new Dragon();		
		drag.dead=false;
		drag.mode=mode;
		switch (mode)
		{
		case 1:
			drag.symbol = 'd';
			break;
		case 2:
			drag.symbol='D';			
			break;
		case 3:
			Random rand = new Random();
			int sym = rand.nextInt(2);
			if(sym == 0) //dragon is awake
				drag.symbol = 'D'; 
			else 	 //dragon starts game sleeping
				drag.symbol = 'd'; 
			break;
		}
		int test_x,test_y;
		do 
		{
			Random rand = new Random();
			test_x=rand.nextInt(Labirynth.getInstance().COL_SIZE-2)+1;
			test_y=rand.nextInt(Labirynth.getInstance().ROW_SIZE-2)+1;
		} while (Labirynth.getInstance().getGenerated_maze()[test_y][test_x].filled || jogo.ocupied_byElements(test_x, test_y));
		drag.x=test_x;
		drag.y=test_y;

		jogo.elementos.add(drag);
		return drag;
	}



}
