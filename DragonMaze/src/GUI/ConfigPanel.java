package GUI;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import java.awt.Font;
import javax.swing.ButtonGroup;
import Game_Logic.Game_Loop;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class ConfigPanel.
 * Graphical class which enables the configuration of the game parameters while in-game
 */
@SuppressWarnings("serial")
public class ConfigPanel extends JDialog{

	/** The button group. (swing generated)*/
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	/**
	 * The command keys. 
	 * int array used to define the keys used to direct the hero in graphical mode 
	 * (used as int which is the keyCode correspondance to the char values).
	 *  The initial values {87,83,65,68,82} correspond to {w,s,a,d,r} .
	 *  The indexes have the following meaning:
	 *  0 -> up
	 *  1 -> down
	 *  2 -> left
	 *  3 -> right
	 *  4 -> send eagle
	 * */
	private int[] commandKeys= {87,83,65,68,82};
	
	//private variables for posterior game change

	/**
	 * Gets the commandkeys.
	 *
	 * @return the commandkeys
	 */
	public int[] getCommandkeys()
	{
		return commandKeys;
	}

	/**
	 * Create the dialog.
	 * Singleton
	 */
	private static ConfigPanel conf = null;

	/**
	 * Gets the single instance of ConfigPanel.
	 *
	 * @return single instance of ConfigPanel
	 */
	public static ConfigPanel getInstance() {
		if (conf == null) 
			conf = new ConfigPanel();
		return conf;
	}

	/**
	 * A new configuration panel.
	 * Creates a graphical window with 2 tabs
	 * First tab allows the configuration of the game settings which will take effect after game has restarted
	 * Second tab allows the edition of the command keys which will take immediate effect 
	 */
	public ConfigPanel() {
		setFont(new Font("Dialog", Font.PLAIN, 11));
		setTitle("Customize Game");
		setBounds(100, 100, 400, 300);
		getContentPane().setLayout(null);
		{
			JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.setBounds(0, 0, 384, 228);
			getContentPane().add(tabbedPane);

			final JPanel Options = new JPanel();
			Options.setLayout(null);
			tabbedPane.addTab("Maze Options", null, Options, null);

			JLabel label = new JLabel("Dungeon Size: ");
			label.setBounds(10, 43, 111, 14);
			Options.add(label);

			final JTextField textField = new JTextField();
			textField.setColumns(10);
			textField.setBounds(127, 40, 86, 20);
			Options.add(textField);

			JLabel label_1 = new JLabel("Number of Dragons: ");
			label_1.setBounds(10, 74, 134, 14);
			Options.add(label_1);

			final JTextField textField_1 = new JTextField();
			textField_1.setColumns(10);
			textField_1.setBounds(127, 71, 86, 20);
			Options.add(textField_1);

			JLabel label_2 = new JLabel("Dragon's mode: ");
			label_2.setBounds(10, 109, 104, 14);
			Options.add(label_2);

			JLabel label_3 = new JLabel("Standard Maze?");
			label_3.setBounds(10, 11, 111, 20);
			Options.add(label_3);

			final JRadioButton rdbtnImmovableDragons = new JRadioButton("Immovable dragons");
			buttonGroup.add(rdbtnImmovableDragons);
			rdbtnImmovableDragons.setActionCommand("1");
			rdbtnImmovableDragons.setBounds(120, 109, 242, 23);
			Options.add(rdbtnImmovableDragons);

			final JRadioButton radioButton_1 = new JRadioButton("Awakened dragons", true);
			buttonGroup.add(radioButton_1);
			radioButton_1.setActionCommand("2");
			radioButton_1.setBounds(120, 135, 242, 23);
			Options.add(radioButton_1);

			final JRadioButton radioButton_2 = new JRadioButton("Alternating sleeping/awakened dragons");
			buttonGroup.add(radioButton_2);
			radioButton_2.setActionCommand("3");
			radioButton_2.setBounds(120, 161, 242, 23);
			Options.add(radioButton_2);

			JPanel Keys = new JPanel();
			tabbedPane.addTab("Key Bindings", null, Keys, null);
			Keys.setLayout(null);

			JLabel lblNewLabel = new JLabel("Move Up");
			lblNewLabel.setBounds(20, 48, 71, 20);
			Keys.add(lblNewLabel);

			final JCheckBox checkBox = new JCheckBox("");
			checkBox.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					if(checkBox.isSelected())
					{
						textField.setEditable(false);
						textField.setText("10");
						textField_1.setEditable(false);
						textField_1.setText("2");
						rdbtnImmovableDragons.setEnabled(false);
						radioButton_1.setEnabled(false);
						radioButton_2.setEnabled(false);
						radioButton_1.setSelected(true);
						radioButton_1.setSelected(true);
					}
					else
					{
						textField.setEditable(true);
						textField_1.setEditable(true);
						rdbtnImmovableDragons.setEnabled(true);
						radioButton_1.setEnabled(true);
						radioButton_2.setEnabled(true);
					}
				}
			});
			checkBox.setBounds(127, 11, 95, 22);
			Options.add(checkBox);
			
			final JTextField txtW = new JTextField();
			txtW.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent arg0) {
					int code = arg0.getKeyCode();				
					switch(code)
					{
					case KeyEvent.VK_UP:
						txtW.setText("Up");
						break;
					case KeyEvent.VK_DOWN:
						txtW.setText("Down");
						break;
					case KeyEvent.VK_RIGHT:
						txtW.setText("Right");
						break;
					case KeyEvent.VK_LEFT:
						txtW.setText("Left");
						break;
					default:
						if(code >= 65 && code <= 90)
							txtW.setText(String.valueOf(arg0.getKeyChar()));
						else
							txtW.setText("");
						break;
					}
					commandKeys[0]=code;
				}
			});

			txtW.setHorizontalAlignment(SwingConstants.LEFT);
			txtW.setText("w");
			txtW.setColumns(10);
			txtW.setBounds(101, 48, 70, 20);
			Keys.add(txtW);
			txtW.setEditable(false);


			JLabel lblMoveDown = new JLabel("Move Down");
			lblMoveDown.setVerticalAlignment(SwingConstants.TOP);
			lblMoveDown.setBounds(20, 76, 71, 20);
			Keys.add(lblMoveDown);

			final JTextField txtS = new JTextField();
			txtS.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent arg1) {
					int code = arg1.getKeyCode();				
					switch(code)
					{
					case KeyEvent.VK_UP:
						txtS.setText("Up");
						break;
					case KeyEvent.VK_DOWN:
						txtS.setText("Down");
						break;
					case KeyEvent.VK_RIGHT:
						txtS.setText("Right");
						break;
					case KeyEvent.VK_LEFT:
						txtS.setText("Left");
						break;
					default:
						if(code >= 65 && code <= 90)
							txtS.setText(String.valueOf(arg1.getKeyChar()));
						else
							txtS.setText("");
						break;
					}
					commandKeys[1]=code;
				}
			});
			txtS.setText("s");
			txtS.setColumns(10);
			txtS.setBounds(101, 76, 70, 20);
			Keys.add(txtS);
			txtS.setEditable(false);

			JLabel lblMoveLeft = new JLabel("Move Left");
			lblMoveLeft.setVerticalAlignment(SwingConstants.TOP);
			lblMoveLeft.setBounds(20, 107, 71, 20);
			Keys.add(lblMoveLeft);

			final JTextField txtA = new JTextField();
			txtA.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent arg0) {
					int code = arg0.getKeyCode();				
					switch(code)
					{
					case KeyEvent.VK_UP:
						txtA.setText("Up");
						break;
					case KeyEvent.VK_DOWN:
						txtA.setText("Down");
						break;
					case KeyEvent.VK_RIGHT:
						txtA.setText("Right");
						break;
					case KeyEvent.VK_LEFT:
						txtA.setText("Left");
						break;
					default:
						if(code >= 65 && code <= 90)
							txtA.setText(String.valueOf(arg0.getKeyChar()));
						else
							txtA.setText("");
						break;
					}
					commandKeys[2]=code;
				}
			});
			txtA.setText("a");
			txtA.setColumns(10);
			txtA.setBounds(101, 107, 70, 20);
			Keys.add(txtA);
			txtA.setEditable(false);

			JLabel lblMoveRight = new JLabel("Move Right");
			lblMoveRight.setVerticalAlignment(SwingConstants.TOP);
			lblMoveRight.setBounds(20, 138, 71, 20);
			Keys.add(lblMoveRight);

			final JTextField txtD = new JTextField();
			txtD.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent arg0) {
					int code = arg0.getKeyCode();				
					switch(code)
					{
					case KeyEvent.VK_UP:
						txtD.setText("Up");
						break;
					case KeyEvent.VK_DOWN:
						txtD.setText("Down");
						break;
					case KeyEvent.VK_RIGHT:
						txtD.setText("Right");
						break;
					case KeyEvent.VK_LEFT:
						txtD.setText("Left");
						break;
					default:
						if(code >= 65 && code <= 90)
							txtD.setText(String.valueOf(arg0.getKeyChar()));
						else
							txtD.setText("");
						break;
					}
					commandKeys[3]=code;
				}
			});
			txtD.setText("d");
			txtD.setColumns(10);
			txtD.setBounds(101, 138, 70, 20);
			Keys.add(txtD);
			txtD.setEditable(false);

			JLabel lblCallEagle = new JLabel("Call Eagle");
			lblCallEagle.setVerticalAlignment(SwingConstants.TOP);
			lblCallEagle.setBounds(20, 169, 71, 20);
			Keys.add(lblCallEagle);


			final JTextField txtR = new JTextField();
			txtR.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent arg0) {
					int code = arg0.getKeyCode();				
					switch(code)
					{
					case KeyEvent.VK_UP:
						txtR.setText("Up");
						break;
					case KeyEvent.VK_DOWN:
						txtR.setText("Down");
						break;
					case KeyEvent.VK_RIGHT:
						txtR.setText("Right");
						break;
					case KeyEvent.VK_LEFT:
						txtR.setText("Left");
						break;
					default:
						if(code >= 65 && code <= 90)
							txtR.setText(String.valueOf(arg0.getKeyChar()));
						else
							txtR.setText("");
						break;
					}
					commandKeys[4]=code;
				}
			});
			txtR.setText("r");
			txtR.setColumns(10);
			txtR.setBounds(101, 169, 70, 20);
			Keys.add(txtR);
			txtR.setEditable(false);

			JLabel lblOnlyCharacterKeys = new JLabel("Only character keys and direction arrows allowed");
			lblOnlyCharacterKeys.setHorizontalAlignment(SwingConstants.CENTER);
			lblOnlyCharacterKeys.setBounds(10, 11, 244, 33);
			Keys.add(lblOnlyCharacterKeys);

			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 228, 374, 33);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			{
				JButton okButton = new JButton("OK");
				okButton.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent arg1) {
						if(anyEqual(commandKeys))
							JOptionPane.showMessageDialog(getContentPane(), "At least two keys are the same! \n That won't work in the game!", "Invalid keys!!", JOptionPane.INFORMATION_MESSAGE);
						//imediate action
						Game_GUI.getInstance().setKeys(commandKeys);
						//these are only in effect after game restarts
						try {
							if((Integer.parseInt(textField.getText())<7 || Integer.parseInt(textField.getText())>200) && (Integer.parseInt(textField_1.getText()) <0 || Integer.parseInt(textField_1.getText())>Integer.parseInt(textField.getText())/5))
								throw new NumberFormatException();
							SaveConfig("src/Resource/newConfigurations.txt", checkBox.isSelected(),Integer.parseInt(textField.getText()),Integer.parseInt(textField_1.getText()),Integer.parseInt(buttonGroup.getSelection().getActionCommand()));
						} catch (NumberFormatException e2) {
							JOptionPane.showMessageDialog(conf, "Are you sure you didn't make a mistake somewhere? \nDungeon size between 0 and 200 \nNumber of dragons between 0 and dungeon size/5");
						}
						setVisible(false);
						Game_GUI.getInstance().setVisible(true);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Game_GUI.getInstance().setVisible(true);
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}

		}
	}

	/**
	 * Any equal.
	 * 
	 * Funtion used to detect if any 2 possible key values are the same 
	 * 
	 * @param test the int array of key values to be tested
	 * @return true, if successful
	 */
	private boolean anyEqual(int[] test)
	{
		for(int i=0;i<test.length;i++)
			for(int j=0;j<test.length;j++)
				if(i!=j)
					if(test[i]==test[j])
					{
						return true;
					}
		return false;
	}

	/**
	 * Save config.
	 *
	 * Saves the configurations defined by the user
	 *
	 * @param path the path of the file which will contain the configurations
	 * @param standard, boolean indicating if user wishes to play the standard maze or not
	 * @param mazeSize the maze size
	 * @param nDragons the number of dragons the user wishes to play against
	 * @param dragonMode the dragon mode the user wishes the dragons to be on
	 */
	public void SaveConfig(String path, boolean standard, int mazeSize, int nDragons, int dragonMode)
	{
		File xFile = new File (path);
		try {
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(xFile));
			os.writeBoolean(standard);
			os.writeInt(mazeSize);
			os.writeInt(nDragons);
			os.writeInt(dragonMode);
			for(int i=0; i<5;i++)
				os.writeInt(i);
			os.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();	
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load config.
	 *
	 * loads configurations from File X and overwrites the respective parameters,
	 * Deletes former game and configures a new with the content of file. 
	 *
	 * @param x the File from which configurations will be loaded
	 */
	public void LoadConfig(File x)
	{
		boolean tempbool = false;
		int size = 0;
		int ndrags=0;
		int mode=0;
		try {
			ObjectInputStream is = new ObjectInputStream(new FileInputStream(x));
			tempbool =is.readBoolean();
			size = is.readInt();
			ndrags=is.readInt();
			mode = is.readInt();
			is.close();
		}catch (FileNotFoundException e) {
			e.printStackTrace();	
		}catch (IOException e) {
			e.printStackTrace();
		}
		if(tempbool)
		{
			Game_Loop.getInstance().setDragon_mode(2);
			Game_GUI.resetInstance();
			Game_GUI.getInstance(10);
			Game_GUI.getInstance().setStandard_maze(true); //checkbox is checked
			Game_GUI.getInstance().setDungeon_size(10);
			Game_GUI.getInstance().setNumber_dragons(2);
			Game_GUI.getInstance().setstateGame(" ");
			Game_GUI.setGame_finished(false);
			Game_GUI.getInstance().resetGame(10, 2, true);
		}
		else {
			Game_Loop.getInstance().setDragon_mode(mode);
			Game_GUI.resetInstance();
			Game_GUI.getInstance(size);
			Game_GUI.getInstance().setStandard_maze(false); //checkbox is not checked
			Game_GUI.getInstance().setDungeon_size(size);
			Game_GUI.getInstance().setNumber_dragons(ndrags);
			Game_GUI.getInstance().setstateGame(" ");
			Game_GUI.setGame_finished(false);
			Game_GUI.getInstance().resetGame(size, ndrags, false);
		}
		Game_GUI.getInstance().setKeys(commandKeys);
		Game_GUI.getInstance().setVisible(true);
	}
}

