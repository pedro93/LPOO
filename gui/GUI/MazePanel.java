package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.security.auth.x500.X500Principal;
import javax.swing.JPanel;
import Game_Logic.*;
import maze.Labirynth;

public class MazePanel extends JPanel {

	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paint(g);
		
		g2d.setColor(Color.BLACK);
		System.out.printf("Width value: %d, Height Value: %d \n", Labirynth.getInstance().COL_SIZE*15, Labirynth.getInstance().ROW_SIZE*15);
		g2d.drawRect(10, 11, Labirynth.getInstance().COL_SIZE*15, Labirynth.getInstance().ROW_SIZE*15);
		g2d.drawString("X", 200,150);
		g2d.drawRect(200, 150, 10, 10);
	}
}
