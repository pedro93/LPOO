
public class SingleMaze {
	private static SingleMaze instanceMaze = null;
	private SingleMaze() {}
	public static SingleMaze getInstance() {
		if (instanceMaze == null) 
			instanceMaze = new SingleMaze();
		return instanceMaze;
	}
}
