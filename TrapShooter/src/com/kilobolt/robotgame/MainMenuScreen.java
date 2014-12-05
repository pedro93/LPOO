package com.kilobolt.robotgame;

import java.util.List;

import android.util.Log;

import com.kilobolt.framework.Game;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Input.TouchEvent;
import com.kilobolt.framework.Screen;
import com.kilobolt.framework.implementation.AndroidmyButton;

public class MainMenuScreen extends Screen {
	AndroidmyButton NewGame = new AndroidmyButton(623, 25, 307, 97);
	AndroidmyButton HighScore = new AndroidmyButton(615, 157, 331, 105);
	AndroidmyButton Help = new AndroidmyButton(611, 294, 312, 104);
	AndroidmyButton Settings = new AndroidmyButton(611, 429, 307, 100);

	public MainMenuScreen(Game game) {
		super(game);
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {

				if (NewGame.clicked(event)) 
				{
					game.setScreen(new GameScreen(game));
				}
				else if (HighScore.clicked(event)) {
					game.setScreen(new HighScoreScreen(game));
				}
				else if (Help.clicked(event)) {
					game.setScreen(new HelpScreen(game));
				}
				else if(Settings.clicked(event)) {
					game.setScreen(new SettingsScreen(game));
				}
				else {

				}
			}
		}
	}

	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();
		g.drawImage(Assets.background, 0, 0);
		g.drawImage(Assets.floor, 0, 0);
		g.drawImage(Assets.menu, 0, 0);
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
		Log.i("back button action", "was pressed");
		SampleGame.getInstance().writeScoreToFile();
	}
}
