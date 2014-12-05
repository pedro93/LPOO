package com.trapShooter.src;

import java.io.File;

import com.trapShooter.framework.Game;
import com.trapShooter.framework.Graphics;
import com.trapShooter.framework.Screen;
import com.trapShooter.framework.Graphics.ImageFormat;

public class LoadingScreen extends Screen {
	public LoadingScreen(Game game) {
		super(game);
	}

	@Override
	public void update(float deltaTime) {
		Graphics g = game.getGraphics();

		Assets.background = g.newImage("BG.png", ImageFormat.RGB565);
		Assets.floor = g.newImage("OnlyGround.png", ImageFormat.RGB565);
		
		Assets.menu = g.newImage("TrapShooterMenu.png", ImageFormat.RGB565);
		Assets.highScore = g.newImage("TrapShooterHighcores.png", ImageFormat.RGB565);
		Assets.help = g.newImage("TrapShooterHelp.png", ImageFormat.RGB565);
		Assets.settings = g.newImage("TrapShooterSettings.png", ImageFormat.RGB565);
		
		Assets.selectedOption = g.newImage("settingsX.png", ImageFormat.RGB565);

		Assets.target = g.newImage("plate.png", ImageFormat.RGB565);
		Assets.gunShot = startGame.getInstance().getAudio().createSound("gunshot.mp3");
		Assets.reload = startGame.getInstance().getAudio().createSound("reload.mp3");
		Assets.ammo = g.newImage("ammo.png", ImageFormat.RGB565);
		
		Assets.plate1 = g.newImage("platebreaking1.png", ImageFormat.RGB565);
		Assets.plate2 = g.newImage("platebreaking2.png", ImageFormat.RGB565);
		Assets.plate3 = g.newImage("platebreaking3.png", ImageFormat.RGB565);
		Assets.plate4 = g.newImage("platebreaking4.png", ImageFormat.RGB565);
		Assets.plate5 = g.newImage("platebreaking5.png", ImageFormat.RGB565);
		Assets.plate6 = g.newImage("platebreaking6.png", ImageFormat.RGB565);
		
		File file = startGame.getInstance().getBaseContext().getFileStreamPath("highScore.sav");
		if(file.exists())
		{
			startGame.getInstance().readScoreFromFile();
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