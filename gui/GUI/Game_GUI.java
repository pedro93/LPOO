package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSplitPane;
import javax.swing.JButton;

import maze.Labirynth;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Game_GUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	private static Game_GUI GUI = null;
	public static Game_GUI getInstance() {
		if (GUI == null) 
			GUI = new Game_GUI();
		return GUI;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Game_GUI frame = new Game_GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Game_GUI() {
		initGame();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new MazePanel(230,283);
		panel.setBounds(0, 0, 434, 300-69);
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));


		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 230, 284, 31);
		contentPane.add(panel_1);

		JButton btnNewGame = new JButton("New Game");
		btnNewGame.setBounds(32, 5, 100, 23);
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		panel_1.setLayout(null);
		panel_1.add(btnNewGame);

		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(300-132, 5, 100, 23);
		panel_1.add(btnExit);
	}

	public void initGame() {
		boolean standard_maze = true; //standard maze
		int dungeon_size =10;
		//Initializing game parameters
		Labirynth.getInstance().ROW_SIZE=dungeon_size-2;
		Labirynth.getInstance().COL_SIZE=dungeon_size-2;

		Labirynth.getInstance().generate(dungeon_size, standard_maze);

		Labirynth.getInstance().ROW_SIZE=dungeon_size;
		Labirynth.getInstance().COL_SIZE=dungeon_size;
		System.out.printf("Graphical dungeon size %d \n", dungeon_size);

	}

}
