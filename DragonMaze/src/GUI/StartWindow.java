package GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import maze.manualGen;
import Game_Logic.Game_Loop;
import java.awt.Label;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class StartWindow.
 * First graphical window display if user chooses to play
 * a game using graphic_mode == true.
 * Graphical equivalent to initGame in CUI class.
 * In addition to allowing the specification of the game 
 * definitions, allows the user to open a graphical window
 * designed for manual game generation.
 */
@SuppressWarnings("serial")
public class StartWindow extends JDialog {

	/** The content panel. */
	private final JPanel contentPanel = new JPanel();
	
	/** The text field. */
	private JTextField textField;
	
	/** The text field_1. */
	private JTextField textField_1;

	/**
	 * Creates the dialog box
	 */
	public StartWindow() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if(Game_GUI.getInstance().getstateGame()=="waiting")
				{
					Game_GUI.getInstance().setVisible(true);
					Game_GUI.getInstance().setstateGame(" ");
					Game_GUI.getInstance().setEnabled(true);
				}
				dispose();
			}
		});
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 450, 316);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblDungeonSize = new JLabel("Dungeon Size: ");
			lblDungeonSize.setBounds(10, 118, 111, 14);
			contentPanel.add(lblDungeonSize);
		}
		{
			textField = new JTextField();
			textField.setBounds(146, 115, 86, 20);
			contentPanel.add(textField);
			textField.setColumns(10);
		}
		{
			JLabel lblNumberOfDragons = new JLabel("Number of Dragons: ");
			lblNumberOfDragons.setBounds(10, 150, 134, 14);
			contentPanel.add(lblNumberOfDragons);
		}
		{
			textField_1 = new JTextField();
			textField_1.setBounds(146, 147, 86, 20);
			contentPanel.add(textField_1);
			textField_1.setColumns(10);
		}

		JLabel lblDragonsMode = new JLabel("Dragon's mode: ");
		lblDragonsMode.setBounds(10, 184, 104, 14);
		contentPanel.add(lblDragonsMode);

		JLabel lblStandardMaze = new JLabel("Standard Maze?");
		lblStandardMaze.setBounds(10, 85, 111, 20);
		contentPanel.add(lblStandardMaze);

		final JCheckBox checkbox = new JCheckBox("");
		checkbox.setBounds(146, 84, 95, 22);
		contentPanel.add(checkbox);

		final JRadioButton rdbtnImmovableDragons = new JRadioButton("Immovable dragons");
		rdbtnImmovableDragons.setActionCommand("1");
		rdbtnImmovableDragons.setBounds(137, 180, 297, 23);
		contentPanel.add(rdbtnImmovableDragons);

		final JRadioButton rdbtnAwakenedDragons = new JRadioButton("Awakened dragons",true);
		rdbtnAwakenedDragons.setActionCommand("2");
		rdbtnAwakenedDragons.setBounds(137, 205, 297, 23);
		contentPanel.add(rdbtnAwakenedDragons);

		final JRadioButton rdbtnAlternatingSleepingawakenedDragons = new JRadioButton("Alternating sleeping/awakened dragons");
		rdbtnAlternatingSleepingawakenedDragons.setActionCommand("3");
		rdbtnAlternatingSleepingawakenedDragons.setBounds(137, 230, 297, 23);
		contentPanel.add(rdbtnAlternatingSleepingawakenedDragons);

		//Create a ButtonGroup object, add buttons to the group
		final ButtonGroup dragonGroup = new ButtonGroup();
		dragonGroup.add(rdbtnImmovableDragons);
		dragonGroup.add(rdbtnAwakenedDragons);
		dragonGroup.add(rdbtnAlternatingSleepingawakenedDragons);

		Label label = new Label("Welcome to Dragon's Dungeon!");
		label.setFont(new Font("Arial", Font.PLAIN, 16));
		label.setAlignment(Label.CENTER);
		label.setBounds(0, 0, 434, 23);
		contentPanel.add(label);

		Label label_1 = new Label("Please select how you would like to play our game!");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_1.setBounds(10, 29, 414, 22);
		contentPanel.add(label_1);

		JLabel lblMazeGeneration = new JLabel("Maze Generation:");
		lblMazeGeneration.setBounds(10, 58, 114, 16);
		contentPanel.add(lblMazeGeneration);

		final JRadioButton rdbtnAutomatic = new JRadioButton("Automatic",true);
		rdbtnAutomatic.setActionCommand("Auto");
		rdbtnAutomatic.setBounds(257, 57, 109, 23);
		contentPanel.add(rdbtnAutomatic);

		final JRadioButton rdbtnManual = new JRadioButton("Manual");
		rdbtnManual.setActionCommand("Man");
		rdbtnManual.setBounds(146, 57, 109, 23);
		contentPanel.add(rdbtnManual);

		final ButtonGroup mazegenGroup = new ButtonGroup();
		mazegenGroup.add(rdbtnAutomatic);
		mazegenGroup.add(rdbtnManual);

		checkbox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) 
			{
				if(checkbox.isSelected())
				{
					textField.setEditable(false);
					textField_1.setEditable(false);
					rdbtnImmovableDragons.setEnabled(false);
					rdbtnAwakenedDragons.setEnabled(false);
					rdbtnAwakenedDragons.setSelected(true);
					rdbtnAlternatingSleepingawakenedDragons.setEnabled(false);
					rdbtnAutomatic.setEnabled(false);
					rdbtnManual.setEnabled(false);
					rdbtnAutomatic.setSelected(true);
				}
				else
				{
					textField.setEditable(true);
					textField_1.setEditable(true);
					rdbtnImmovableDragons.setEnabled(true);
					rdbtnAwakenedDragons.setEnabled(true);
					rdbtnAlternatingSleepingawakenedDragons.setEnabled(true);
					rdbtnAutomatic.setEnabled(true);
					rdbtnManual.setEnabled(true);
				}
			}});

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg1)
					{
						if(mazegenGroup.getSelection().getActionCommand() == "Man") //open maze generator
						{
							try {
								if(Integer.parseInt(textField.getText())<7 || Integer.parseInt(textField.getText())>200)
									throw new Exception();
								if(Integer.parseInt(textField_1.getText())<0 || Integer.parseInt(textField_1.getText())>Integer.parseInt(textField.getText())/5)
									throw new Exception();
								Game_Loop.getInstance().setDragon_mode(Integer.parseInt(dragonGroup.getSelection().getActionCommand()));
								Game_GUI.resetInstance();
								Game_GUI.getInstance(Integer.parseInt(textField.getText()));
								Game_GUI.getInstance().setStandard_maze(false); //checkbox is not checked
								Game_GUI.getInstance().setDungeon_size(Integer.parseInt(textField.getText()));
								Game_GUI.getInstance().setNumber_dragons(Integer.parseInt(textField_1.getText()));
								Game_GUI.getInstance().setKeys(ConfigPanel.getInstance().getCommandkeys());
								Game_GUI.getInstance().setstateGame(" ");
								setVisible(false);
								manualGen gerador = new manualGen(Integer.parseInt(textField.getText()));
								gerador.setVisible(true);
								dispose();
							} catch (Exception e) {
								JOptionPane.showMessageDialog(contentPanel, "Are you sure you didn't make a mistake somewhere? \nDungeon size between 0 and 200 \nNumber of dragons between 0 and dungeon size/5");
							}							
						}
						else //Automatic generation
						{
							if(checkbox.isSelected()) //standard maze
							{
								//save game configurations
								ConfigPanel.getInstance().SaveConfig("src/Resource/newConfigurations.txt", true, 10, 2, 2);
								Game_GUI.resetInstance();
								Game_GUI.getInstance(10);
								Game_GUI.getInstance().setStandard_maze(true); //checkbox is checked
								Game_GUI.getInstance().setDungeon_size(10);
								Game_GUI.getInstance().setNumber_dragons(2);
								Game_Loop.getInstance().setDragon_mode(2);
								Game_GUI.getInstance().setstateGame(" ");
								Game_GUI.getInstance().setKeys(ConfigPanel.getInstance().getCommandkeys());
								Game_GUI.getInstance().initGame();
								Game_GUI.getInstance().setVisible(true);
								dispose();
							}
							else //Automatic generation and maze
							{
								try {
									if(Integer.parseInt(textField.getText())<7 || Integer.parseInt(textField.getText())>200)
										throw new Exception();
									if(Integer.parseInt(textField_1.getText())<0 || Integer.parseInt(textField_1.getText())>Integer.parseInt(textField.getText())/5)
										throw new Exception();
									//save game configurations
									ConfigPanel.getInstance().SaveConfig("src/Resource/newConfigurations.txt", false, Integer.parseInt(textField.getText()), Integer.parseInt(textField_1.getText()), Integer.parseInt(dragonGroup.getSelection().getActionCommand()));
									Game_Loop.getInstance().setDragon_mode(Integer.parseInt(dragonGroup.getSelection().getActionCommand()));
									Game_GUI.resetInstance();
									Game_GUI.getInstance(Integer.parseInt(textField.getText()));
									Game_GUI.getInstance().setStandard_maze(false); //checkbox is not checked
									Game_GUI.getInstance().setDungeon_size(Integer.parseInt(textField.getText()));
									Game_GUI.getInstance().setNumber_dragons(Integer.parseInt(textField_1.getText()));
									Game_GUI.getInstance().setstateGame(" ");
									Game_GUI.getInstance().setKeys(ConfigPanel.getInstance().getCommandkeys());
									Game_GUI.getInstance().initGame();
									Game_GUI.getInstance().setVisible(true);
									dispose();
								} catch (Exception e) {
									JOptionPane.showMessageDialog(contentPanel, "Are you sure you didn't make a mistake somewhere? \nDungeon size between 0 and 200 \nNumber of dragons between 0 and dungeon size/5");
								}							
							}
						}
					}	
				});			
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						if(Game_GUI.getInstance().getstateGame()=="waiting")
						{
							Game_GUI.getInstance().setVisible(true);
							Game_GUI.getInstance().setstateGame(" ");
							Game_GUI.getInstance().setEnabled(true);
						}
						dispose();
					}
				});
			}

		}
	}
}
