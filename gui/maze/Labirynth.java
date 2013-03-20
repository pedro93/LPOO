package maze;
import java.util.Random;
import java.util.Stack;

public class Labirynth {

	public int ROW_SIZE;
	public int COL_SIZE;
	public int EXIT_X;
	public int EXIT_Y;

	public static Cell[][] generated_maze = null; 
	public Cell[][] maze = null;

	private static Labirynth instanceMaze = null;
	private Labirynth() {}
	public static Labirynth getInstance() {
		if (instanceMaze == null) 
			instanceMaze = new Labirynth();
		return instanceMaze;
	}
	
	public Cell[][] getGenerated_maze() 
	{
		return maze;
	}

	public void setGenerated_maze(Cell[][] generated_maze)
	{
		this.maze = generated_maze;
	}

	public void generate(int dungeon_size, Boolean user_choice) 
	{
		initMaze(dungeon_size);

		if(user_choice) // standard 10x10 maze
		{
			StandardMaze();
		}
		else //random generation of maze
		{
			defineExit();
			maze_gen();
			exitUpdate();
			mazeUpdate();
			System.out.println("NOVO MAZE");
			erase3x3();
			// print_m(); only debug useful
		}
	}

	

	//Initiate Cell for maze generation
	public Cell fill()
	{
		Cell x = new Cell();
		x.filled=true;
		return x;
	}

	//Initiates the maze's content
	private void initMaze(int n) 
	{
		generated_maze = new Cell[n][n];
		maze = new Cell[n+2][n+2];

		//initialize maze;
		for(int i=0; i<n; i++)
		{
			for(int j =0; j<n;j++)
			{
				generated_maze[i][j] = fill();
			}
		}

		for(int i = 0; i < n+2; i++)
		{
			for(int j = 0; j < n+2;j++)
			{
				maze[i][j] = fill();
			}
		}
	}

	private void StandardMaze() 
	{
		for (int i = 0; i < 10; i++) //external walls
		{
			maze[0][i].filled = true; //north
			maze[i][0].filled = true; //west
			maze[0][9-i].filled = true; //east
			maze[9-i][0].filled = true; //west
		} 

		//second row
		maze[1][1].filled = false;
		maze[1][2].filled = false;
		maze[1][3].filled = false;
		maze[1][4].filled = false;
		maze[1][5].filled = false;
		maze[1][6].filled = false;
		maze[1][7].filled = false;
		maze[1][8].filled = false;

		//3rd 4th 5th rows
		for(int i=0;i<3;i++)
		{
			maze[2+i][1].filled = false;
			maze[2+i][2].filled = true;	
			maze[2+i][3].filled = true;	
			maze[2+i][4].filled = false;
			maze[2+i][5].filled = true;	
			maze[2+i][6].filled = false;
			maze[2+i][7].filled = true;	
			maze[2+i][8].filled = false;
		}

		//6th row
		maze[5][1].filled = false;
		maze[5][2].filled = false;
		maze[5][3].filled = false;
		maze[5][4].filled = false;
		maze[5][5].filled = false;
		maze[5][6].filled = false;
		maze[5][7].filled = true;
		maze[5][8].filled = false;

		//7th 8th rows
		for(int i=0;i<2;i++)
		{
			maze[6+i][1].filled = false;
			maze[6+i][2].filled = true;	
			maze[6+i][3].filled = true;	
			maze[6+i][4].filled = false;
			maze[6+i][5].filled = true;	
			maze[6+i][6].filled = false;
			maze[6+i][7].filled = true;	
			maze[6+i][8].filled = false;
		}

		//9th row
		maze[8][1].filled = false;
		maze[8][2].filled = true;
		maze[8][3].filled = true;
		maze[8][4].filled = false;
		maze[8][5].filled = false;
		maze[8][6].filled = false;
		maze[8][7].filled = false;
		maze[8][8].filled = false;

		//Exit
		maze[5][9].filled = true;
		EXIT_X=9;
		EXIT_Y=5;
	}

	private void erase3x3()
	{	
		for(int i = 0; i < COL_SIZE-3; i++)
		{
			for(int j = 0; j < ROW_SIZE-3; j++)
			{
				if( maze[i][j].filled && maze[i][j+1].filled && maze[i][j+2].filled)
				{
					if(maze[i+1][j].filled && maze[i+1][j+1].filled && maze[i+1][j+2].filled)
					{
						if(maze[i+2][j].filled && maze[i+2][j+1].filled && maze[i+2][j+2].filled)
						{

							System.out.println("ENCONTEI UM 3x3");
							System.out.println(i);
							System.out.println(j);

							//Find a white space around
							if(!maze[i+1][j+3].filled && j+3 < ROW_SIZE-1)
							{
								maze[i+1][j+1].filled = false;
								maze[i+1][j+2].filled = false;
							}
							else if(!maze[i+1][j-1].filled && j-1 > 0)
							{
								maze[i+1][j].filled = false;
								maze[i+1][j+1].filled = false;
							}
							else if(!maze[i+3][j+1].filled && i+3 < COL_SIZE-1)
							{
								maze[i+2][j+1].filled = false;
								maze[i+1][j+1].filled = false;
							}
							else if(!maze[i-1][j+1].filled && i-1 > 0)
							{
								maze[i][j+1].filled = false;
								maze[i+1][j+1].filled = false;
							}
						}
					}
				}
			}
		}

		System.out.println("DELETED 3x3");
	}

	private void mazeUpdate() {
		for(int i = 1; i < COL_SIZE+1;i++)
		{
			for(int j = 1; j < ROW_SIZE+1; j++)
			{
				maze[i][j] = generated_maze[i-1][j-1];
			}
		}
	}

	public void exitUpdate()
	{
		maze[EXIT_Y+1][EXIT_X+1].filled = false;

		if(EXIT_X == 0)
		{
			EXIT_X--; 
		}
		if(EXIT_X == COL_SIZE-1)
		{
			EXIT_X++;
		}
		if(EXIT_Y == 0)
		{
			EXIT_Y--;
		}
		if(EXIT_Y == ROW_SIZE-1)
		{
			EXIT_Y++;
		}

		EXIT_X++;
		EXIT_Y++;
		System.out.printf("Y: %d ,X: %d",EXIT_Y,EXIT_X );

	}

	public void maze_gen() 
	{

		Random myRand = new Random();
		Stack<Integer> s = new Stack<Integer>();

		int x = EXIT_X;
		int y = EXIT_Y;

		
		int total = (ROW_SIZE * COL_SIZE) / 2; //Square form and the movement in pairs ensure this total.
		int visited = 1; //1;
		int random[] = new int[4];
		int totalrand;
		boolean isEmpty = false;

		while (visited < total || isEmpty) 
		{
			//Find the directions where we can start digging
			totalrand = 0;
			if (x > 1 && generated_maze[y][x-2].filled && y > 0)
				random[totalrand++] = 1;
			if (x < COL_SIZE - 2 && generated_maze[y][x+2].filled && y > 0)
				random[totalrand++] = 2;
			if (y > 1 && generated_maze[y-2][x].filled && x > 0)
				random[totalrand++] = 3;
			if (y < ROW_SIZE - 2 && generated_maze[y+2][x].filled && x > 0)
				random[totalrand++] = 4;

			if (totalrand > 0) 
			{

				//Chooses random direction and advances two cells to ensure conditions
				switch(random[myRand.nextInt(totalrand)]) 
				{
				case 1: generated_maze[y][x-2].filled = false;
				generated_maze[y][x-1].filled = false;
				x -= 2;
				s.push(x * COL_SIZE + y);
				visited++;
				break;

				case 2: generated_maze[y][x+2].filled = false;
				generated_maze[y][x+1].filled = false;
				x += 2;
				s.push(x * COL_SIZE + y);
				visited++;
				break;

				case 3: generated_maze[y-2][x].filled = false;
				generated_maze[y-1][x].filled = false;
				y -= 2;
				s.push(x * COL_SIZE + y);
				visited++;
				break;

				case 4: generated_maze[y+2][x].filled = false;
				generated_maze[y+1][x].filled = false;
				y += 2;
				s.push(x * COL_SIZE + y);
				visited++;
				break;
				}
			}
			else //No move can be made, pop last stack from cell
			{ 
				if(s.empty())
				{
					isEmpty = true;
					break;
				}
				else
				{
					int vert = s.pop();
					x = vert / COL_SIZE;
					y = vert % COL_SIZE;
				}
			}
		}
	}

	private void defineExit() {

		//Defines Exit point, excluding corners.
		Random rand = new Random();
		EXIT_X = rand.nextInt(COL_SIZE-1);
		if(EXIT_X == 0 || EXIT_X == COL_SIZE-1)
		{
			EXIT_Y = rand.nextInt(ROW_SIZE-2)+1;
		}
		else
		{
			EXIT_Y = rand.nextInt(2);
			if(EXIT_Y == 1)
				EXIT_Y = COL_SIZE-1;
		}
	}
/*
	private void print_m()
	{
		for(int i=0; i<COL_SIZE+2; i++)
		{
			for(int j=0; j<ROW_SIZE+2; j++)
			{
				if (i == EXIT_Y && j == EXIT_X)
					System.out.print("S ");
				else if(maze[i][j].filled == true) 
					System.out.print("X ");
				else if (maze[i][j].filled == false) 
					System.out.print("  ");
			}			
			System.out.println();
		}
		
		System.out.println();
	}
	*/
}
