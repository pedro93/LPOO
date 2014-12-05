package com.trapShooter.src;

import java.util.List;

import com.trapShooter.framework.Game;
import com.trapShooter.framework.Graphics;
import com.trapShooter.framework.Screen;
import com.trapShooter.framework.Input.TouchEvent;
import com.trapShooter.implementation.AndroidmyButton;

public class SettingsScreen extends Screen{

	AndroidmyButton back = new AndroidmyButton(638, 285, 207, 167);
	AndroidmyButton audioOn = new AndroidmyButton(235,250,100,40);
	AndroidmyButton audioOff = new AndroidmyButton(350,250,125,40);
	AndroidmyButton easyMode = new AndroidmyButton(61,426,130,40);
	AndroidmyButton mediumMode = new AndroidmyButton(61,381,200,40);
	AndroidmyButton hardMode = new AndroidmyButton(61,341,170,40);

	public SettingsScreen(Game game) {
		super(game);
		switch (startGame.gameDificulty) {
		case 1:
			easyMode.setSelected(true);
			mediumMode.setSelected(false);
			hardMode.setSelected(false);
			break;
		case 2:
			easyMode.setSelected(false);
			mediumMode.setSelected(true);
			hardMode.setSelected(false);
			break;
		case 3:
			easyMode.setSelected(false);
			mediumMode.setSelected(false);
			hardMode.setSelected(true);
			break;
		}
		if(Assets.useAudio)
		{
			audioOn.setSelected(true);
			audioOff.setSelected(false);
		}
		else
		{
			audioOn.setSelected(false);
			audioOff.setSelected(true);
		}
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
					Assets.useAudio = true;
				}
				else if (audioOff.clicked(event)) 
				{
					audioOff.setSelected(true);
					audioOn.setSelected(false);
					Assets.theme.stop();
					Assets.useAudio = false;
				}
				else if (easyMode.clicked(event)) 
				{
					startGame.gameDificulty =1;
					easyMode.setSelected(true);
					mediumMode.setSelected(false);
					hardMode.setSelected(false);
				}
				else if (mediumMode.clicked(event)) 
				{
					startGame.gameDificulty =2;
					mediumMode.setSelected(true);
					easyMode.setSelected(false);
					hardMode.setSelected(false);
				}
				else if (hardMode.clicked(event)) 
				{
					startGame.gameDificulty =3;
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
			g.drawImage(Assets.selectedOption, 293, 257);
		}
		else if (audioOff.isSelected()) 
		{
			g.drawImage(Assets.selectedOption, 436, 257);
		}

		if (easyMode.isSelected()) 
		{
			g.drawImage(Assets.selectedOption, 164, 431);
		}
		else if (mediumMode.isSelected()) 
		{
			g.drawImage(Assets.selectedOption, 214, 392);
		}
		else if (hardMode.isSelected()) 
		{
			g.drawImage(Assets.selectedOption, 173, 349);
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
