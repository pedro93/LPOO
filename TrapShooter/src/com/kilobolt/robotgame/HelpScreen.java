package com.kilobolt.robotgame;

import java.util.List;

import com.kilobolt.framework.Game;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Screen;
import com.kilobolt.framework.Input.TouchEvent;
import com.kilobolt.framework.implementation.AndroidmyButton;

public class HelpScreen extends Screen{

	AndroidmyButton back = new AndroidmyButton(638, 285, 207, 167);

	
	public HelpScreen(Game game) {
		super(game);
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
			}
		}
	}

	@Override
	public void paint(float deltaTime) {		
		Graphics g = game.getGraphics();
		g.drawImage(Assets.background, 0, 0);
		g.drawImage(Assets.floor, 0, 0);
		g.drawImage(Assets.help, 0, 0);
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
