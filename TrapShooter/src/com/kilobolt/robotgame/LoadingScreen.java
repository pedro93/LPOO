package com.kilobolt.robotgame;

import java.io.File;

import android.util.Log;

import com.kilobolt.framework.Game;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Graphics.ImageFormat;
import com.kilobolt.framework.Screen;

public class LoadingScreen extends Screen {
	public LoadingScreen(Game game) {
		super(game);
	}

	@Override
	public void update(float deltaTime) {
		Graphics g = game.getGraphics();
		Assets.menu = g.newImage("TrapShooterMenu.png", ImageFormat.RGB565);
		Assets.highScore = g.newImage("TrapShooterHighcores.png", ImageFormat.RGB565);
		Assets.help = g.newImage("TrapShooterHelp.png", ImageFormat.RGB565);
		Assets.background = g.newImage("BG.png", ImageFormat.RGB565);
		Assets.floor = g.newImage("OnlyGround.png", ImageFormat.RGB565);
		Assets.target = g.newImage("plate.png", ImageFormat.RGB565);
		Assets.settings = g.newImage("TrapShooterSettings.png", ImageFormat.RGB565);
		Assets.selectedOption = g.newImage("settingsX.png", ImageFormat.RGB565);
		
		File file = SampleGame.getInstance().getBaseContext().getFileStreamPath("highScore.sav");
		Log.i("checking if highscore file exists:", ""+SampleGame.getInstance().getBaseContext().getFileStreamPath("highScore.sav"));
		System.out.printf("trying to load from: ", SampleGame.getInstance().getBaseContext().getFileStreamPath("highScore.sav"));
		if(file.exists())
		{
			Log.i("checking if highscore file exists:","it does");
			SampleGame.getInstance().readScoreFromFile();
		}
		
		game.setScreen(new MainMenuScreen(game));
	}

	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();
		g.drawImage(Assets.splash, 0, 0);
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

	}
}