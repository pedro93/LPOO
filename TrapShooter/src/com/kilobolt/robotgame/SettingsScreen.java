package com.kilobolt.robotgame;

import java.util.List;

import com.kilobolt.framework.Game;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Screen;
import com.kilobolt.framework.Input.TouchEvent;
import com.kilobolt.framework.implementation.AndroidmyButton;

public class SettingsScreen extends Screen{

	AndroidmyButton back = new AndroidmyButton(638, 285, 207, 167);
	AndroidmyButton audioOn = new AndroidmyButton(292,257,24,24);
	AndroidmyButton audioOff = new AndroidmyButton(435,257,24,24);
	AndroidmyButton easyMode = new AndroidmyButton(173,349,24,24);
	AndroidmyButton mediumMode = new AndroidmyButton(214,392,24,24);
	AndroidmyButton hardMode = new AndroidmyButton(164,431,24,24);

	public SettingsScreen(Game game) {
		super(game);
		audioOn.setSelected(true);
		mediumMode.setSelected(true);
	}

	@Override
	public void update(float deltaTime) {	
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {

				if (back.clicked(event)) 
				{
					backButton();
				}
				else if (audioOn.clicked(event)) 
				{
					audioOn.setSelected(true);
					audioOff.setSelected(false);
					Assets.theme.play();
				}
				else if (audioOff.clicked(event)) 
				{
					audioOff.setSelected(true);
					audioOn.setSelected(false);
					Assets.theme.stop();
				}
				else if (easyMode.clicked(event)) 
				{
					easyMode.setSelected(true);
					mediumMode.setSelected(false);
					hardMode.setSelected(false);
				}
				else if (mediumMode.clicked(event)) 
				{
					mediumMode.setSelected(true);
					easyMode.setSelected(false);
					hardMode.setSelected(false);
				}
				else if (hardMode.clicked(event)) 
				{
					hardMode.setSelected(true);
					mediumMode.setSelected(false);
					easyMode.setSelected(false);

				}
			}
		}
	}

	@Override
	public void paint(float deltaTime) {		
		Graphics g = game.getGraphics();
		g.drawImage(Assets.background, 0, 0);
		g.drawImage(Assets.floor, 0, 0);
		g.drawImage(Assets.settings, 0, 0);

		if (audioOn.isSelected()) 
		{
			g.drawImage(Assets.selectedOption, audioOn.getX(), audioOn.getY());
		}
		else if (audioOff.isSelected()) 
		{
			g.drawImage(Assets.selectedOption, audioOff.getX(), audioOff.getY());
		}
		
		if (easyMode.isSelected()) 
		{
			g.drawImage(Assets.selectedOption, easyMode.getX(), easyMode.getY());
		}
		else if (mediumMode.isSelected()) 
		{
			g.drawImage(Assets.selectedOption, mediumMode.getX(), mediumMode.getY());
		}
		else if (hardMode.isSelected()) 
		{
			g.drawImage(Assets.selectedOption, hardMode.getX(), hardMode.getY());
		}

	}

	@Override
	public void pause() {		
	}

	@Override
	public void resume() {		
	}

	@Override
	public void dispose() {		
	}

	@Override
	public void backButton() {	
		game.setScreen(new MainMenuScreen(game));
	}

}
