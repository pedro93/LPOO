package maze;

import java.io.Serializable;
import java.util.Random;
import java.util.Stack;

// TODO: Auto-generated Javadoc
/**
 * The Class Labirynth.
 */
@SuppressWarnings("serial")
public class Labirynth implements Serializable
{

	/** The maze's row size. */
	public int ROW_SIZE;
	
	/** The maze's column size. */
	public int COL_SIZE;
	
	/** position of the exit x. */
	public int EXIT_X;
	
	/** position of the exit y. */
	public int EXIT_Y;

	/** The generated_maze. */
	public static Cell[][] generated_maze = null; 
	
	/** The maze. */
	public Cell[][] maze = null;

	/** instance of the maze. */
	private static Labirynth instanceMaze = null;
	
	/**
	 * Instantiates a new labirynth.
	 */
	private Labirynth() {}
	
	/**
	 * Gets the single instance of Labirynth.
	 *
	 * @return single instance of Labirynth
	 */
	public static Labirynth getInstance() {
		if (instanceMaze == null) 
			instanceMaze = new Labirynth();
		return instanceMaze;
	}

	/**
	 * Gets the generated_maze.
	 *
	 * @return the generated_maze
	 */
	public Cell[][] getGenerated_maze() 
	{
		return maze;
	}

	/**
	 * Sets the generated_maze.
	 *
	 * @param generated_maze the new generated_maze
	 */
	public void setGenerated_maze(Cell[][] generated_maze)
	{
		maze = generated_maze;
	}

	/**
	 * Generates a maze accordingly to the user choice of having a generated or standard maze.
	 *
	 * @param dungeon_size the dungeon_size
	 * @param user_choice the user_choice
	 */
	public void generate(int dungeon_size, Boolean user_choice) 
	{	
		ROW_SIZE=dungeon_size-2;
		COL_SIZE=dungeon_size-2;
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
			erase3x3();
			//print_m();
			clearDoubleWall();
			//print_m();
		}
		ROW_SIZE=dungeon_size;
		COL_SIZE=dungeon_size;
	}




	/**
	 * 	Initiates the Cells for maze generation.
	 *
	 * @return the filled cell
	 */
	public Cell fill()
	{
		Cell x = new Cell();
		x.filled=true;
		return x;
	}

	
	/**
	 * Initiates the maze's content
	 *
	 * @param the size of the maze
	 */
	private void initMaze(int size) 
	{
		generated_maze = new Cell[size][size];
		maze = new Cell[size+2][size+2];

		//initialize maze;
		for(int i=0; i<size; i++)
		{
			for(int j =0; j<size;j++)
			{
				generated_maze[i][j] = fill();
			}
		}

		for(int i = 0; i < size+2; i++)
		{
			for(int j = 0; j < size+2;j++)
			{
				maze[i][j] = fill();
			}
		}
	}

	/**
	 * Creates a standard maze fulfilling the requirements needed to do so.
	 */
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

	/**
	 * Erases all the 3x3 filled cells possibly created by the maze gen algorithm to match the requirements in order to create a maze.
	 */
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
							//Find a white space around
							if(!maze[i+1][j+3].filled && j+3 < ROW_SIZE-1)
							{
								maze[i+1][j+1].filled = false;
								maze[i+1][j+2].filled = false;
							}
							else if(j-1 > 0 && !maze[i+1][j-1].filled)
							{
								maze[i+1][j].filled = false;
								maze[i+1][j+1].filled = false;
							}
							else if(i+3 < COL_SIZE-1 && !maze[i+3][j+1].filled)
							{
								maze[i+2][j+1].filled = false;
								maze[i+1][j+1].filled = false;
							}
							else if(i-1 > 0 && !maze[i-1][j+1].filled)
							{
								maze[i][j+1].filled = false;
								maze[i+1][j+1].filled = false;
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Clears the double wall sometimes generated by the maze gen algorithm. For esthetics purposes.
	 */
	private void clearDoubleWall()
	{

		Boolean hasWall = true;

		//Check north wall
		for(int i = 1; i < ROW_SIZE; i++)
		{
			if(!maze[1][i].filled)
			{
				hasWall = false;
				break;
			}
		}
		if(hasWall)
		{
			for(int k = 2; k < ROW_SIZE+2; k+=2)
			{
				if(!maze[2][k].filled)
					maze[1][k].filled = false;				
			}
		}
		hasWall = true;

		//Check south wall
		for(int i = 1; i < ROW_SIZE; i++)
		{
			if(!maze[ROW_SIZE][i].filled)
			{
				hasWall = false;
				break;
			}
		}
		if(hasWall)
		{
			for(int k = 2; k < ROW_SIZE+2; k+=2)
			{
				if(!maze[ROW_SIZE-1][k].filled)
					maze[ROW_SIZE][k].filled = false;
			}
		}
		hasWall = true;

		//Check east wall
		for(int i = 1; i < COL_SIZE; i++)
		{
			if(!maze[i][COL_SIZE].filled)
			{
				hasWall = false;
				break;
			}
		}

		if(hasWall)
		{
			for(int k = 2; k < COL_SIZE+2; k+=2)
			{
				if(!maze[k][COL_SIZE-1].filled)
					maze[k][COL_SIZE].filled = false;
			}
		}
		hasWall = true;

		//Check west wall
		for(int i = 1; i < COL_SIZE; i++)
		{
			if(!maze[i][1].filled)
			{
				hasWall = false;
				break;
			}
		}

		if(hasWall)
		{
			for(int k = 2; k < COL_SIZE+2; k+=2)
			{
				if(!maze[k][2].filled)
					maze[k][1].filled = false;
			}
		}
	}

	/**
	 * Updates the maze coordinates since the maze's size is changed in the generation.
	 */
	private void mazeUpdate() {
		for(int i = 1; i < COL_SIZE+1;i++)
		{
			for(int j = 1; j < ROW_SIZE+1; j++)
			{
				maze[i][j] = generated_maze[i-1][j-1];
			}
		}
	}

	/**
	 * Updates the maze's exit coordinates since the maze's size is changed in the generation.
	 */
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

	}

	/**
	 * Generates a random maze.
	 */
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

	/**
	 * Defines a randome exit point for the maze.
	 */
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

	/**
	 * Prints the maze to the console.
	 */
	private void print_m()
	{
		for(int i=0; i<COL_SIZE; i++)
		{
			for(int j=0; j<ROW_SIZE; j++)
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

	/**
	 * Tests the user maze accordingly to the requirements specified.
	 *
	 * @param possibleMaze user maze
	 * @param doorY position of the exit in x
	 * @param doorX position of the exit in y
	 * @return true, if successful
	 */
	public boolean testMaze(Cell[][] possibleMaze, int doorY, int doorX)
	{

		//test 3x3
		for(int i = 0; i < possibleMaze.length-2; i++)
		{
			for(int j = 0; j < possibleMaze[i].length-2; j++)
			{
				if( possibleMaze[i][j].filled && possibleMaze[i][j+1].filled && possibleMaze[i][j+2].filled)
				{
					if(possibleMaze[i][j].filled && possibleMaze[i+1][j+1].filled && possibleMaze[i+1][j+2].filled)
					{
						if(possibleMaze[i+2][j].filled && possibleMaze[i+2][j+1].filled && possibleMaze[i+2][j+2].filled)
						{
							//System.out.printf("Ha um 3x3 %d %d\n", i, j);
							return false;
						}
					}
				}
			}
		}

		//test diagonals
		for(int i = 1; i < possibleMaze.length-2; i++)
		{
			for(int j = 1; j < possibleMaze[i].length-2; j++)
			{
				if( possibleMaze[i][j].filled && possibleMaze[i+1][j+1].filled && !possibleMaze[i+1][j].filled && !possibleMaze[i][j+1].filled)
				{
					//System.out.printf("Ha diagonal %d %d\n", i, j);
					return false;
				}
				if( !possibleMaze[i][j].filled && !possibleMaze[i+1][j+1].filled && possibleMaze[i+1][j].filled && possibleMaze[i][j+1].filled)
				{
					//System.out.printf("Ha diagonal %d %d\n", i, j);
					return false;
				}
			}
		}

		//test open spaces
		for(int i = 1; i < possibleMaze.length-2; i++)
		{
			for(int j = 1; j < possibleMaze[i].length-2; j++)
			{
				if(!possibleMaze[i][j].filled && !possibleMaze[i+1][j].filled && !possibleMaze[i][j+1].filled && !possibleMaze[i+1][j+1].filled)
				{
					//System.out.printf("Ha clareira %d %d\n", i, j);
					return false;
				}
			}
		}

		//test all are reachable
		for(int i = 1; i < possibleMaze.length-1; i++)
		{
			for(int j = 1; j < possibleMaze[i].length-1; j++)
			{

				if(!possibleMaze[i][j].filled)
				{
					resetMaze(possibleMaze);

					if(!testHasPath(i,j,doorX, doorY, possibleMaze))
					{
						return false;
						//System.out.printf("NAO CONSIGO SAIR %d %d\n", i, j);

					}

				}
			}
		}
		//print_m(possibleMaze);
		return true;

	}

	/**
	 * Test if the user maze's cleared cells have path to the exit.
	 *
	 * @param current position in x.
	 * @param current position in y.
	 * @param doorY position of the exit in x
	 * @param doorY position of the exit in y
	 * @param the user maze
	 * @return true, if successful
	 */
	private boolean testHasPath(int x, int y, int doorX, int doorY, Cell[][] maze)
	{	
		maze[x][y].visited = true;

		if((x-1 == doorX || x+1 == doorX) && y == doorY)
			return true;
		else if((y-1 == doorY || y+1 == doorY) && x == doorX)
			return true;

		if(x+1 < maze.length && !maze[x+1][y].filled && !maze[x+1][y].visited)
		{
			if(testHasPath(x+1, y, doorX, doorY, maze))
				return true;
		}
		if(x-1 > 0 && !maze[x-1][y].filled && !maze[x-1][y].visited)
		{
			if (testHasPath(x-1, y, doorX, doorY, maze))
				return true;
		}
		if(y+1 < maze[x].length && !maze[x][y+1].filled && !maze[x][y+1].visited)
		{
			if(testHasPath(x, y+1, doorX, doorY, maze))
				return true;
		}
		if(y-1 > 0 && !maze[x][y-1].filled && !maze[x][y-1].visited)
		{
			if(testHasPath(x, y-1, doorX, doorY, maze))
				return true;
		}

		return false;	

	}

	/**
	 * Resets the visited cells of the maze for pathfiding purposes.
	 *
	 * @param the maze to use
	 */
	void resetMaze(Cell[][] maze)
	{
		for(int i = 1; i < maze.length-1; i++)
		{
			for(int j = 1; j < maze[i].length-1; j++)
			{
				maze[i][j].visited = false;
			}
		}
	}
}