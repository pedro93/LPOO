package com.kilobolt.robotgame;

import java.util.List;
import java.util.Vector;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import com.kilobolt.framework.Game;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Input.TouchEvent;
import com.kilobolt.framework.Screen;
import com.kilobolt.framework.implementation.AndroidmyButton;

public class GameScreen extends Screen {
	enum GameState {
		Ready, Running, Paused, GameOver, YouWon
	}

	GameState state = GameState.Ready;

	// Variable Setup
	private static Background bg1, bg2;
	private static int numberTargets;
	private static Vector<Target> targets;
	private static DisplayMetrics dimension;
	private int livesLeft = 3;
	private int score =0;
	private Paint paint, paint2;
	private AndroidmyButton resumeButton,menuButton,gameOverButton;



	public GameScreen(Game game) {
		super(game);

		// Initialize game objects here
		bg1 = new Background(0, 0);
		bg2 = new Background(1700, 0);
		numberTargets = 1;
		targets = new Vector<Target>();
		dimension = new DisplayMetrics();
		for(int i = 0; i<numberTargets;i++)
		{
			Target aux = new Target(0,200,10000);
			targets.add(aux);
		}
		resumeButton = new AndroidmyButton(0, 0, 960, 270);
		menuButton = new AndroidmyButton(0, 271, 960, 269);
		gameOverButton = new AndroidmyButton(0, 0, 960, 540);
		// Defining a paint object
		paint = new Paint();
		paint.setTextSize(30);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);
		paint.setTypeface(Assets.myFont);

		paint2 = new Paint();
		paint2.setTextSize(100);
		paint2.setTextAlign(Paint.Align.CENTER);
		paint2.setAntiAlias(true);
		paint2.setColor(Color.WHITE);
		paint2.setTypeface(Assets.myFont);
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

		// We have four separate update methods in this example.
		// Depending on the state of the game, we call different update methods.
		// Refer to Unit 3's code. We did a similar thing without separating the
		// update methods.

		if (state == GameState.Ready)
		{updateReady(touchEvents);}
		else if (state == GameState.Running)
		{updateRunning(touchEvents, deltaTime);}
		else if (state == GameState.Paused)
		{updatePaused(touchEvents);}
		else if (state == GameState.GameOver)
		{updateGameOver(touchEvents);}
		else if(state == GameState.YouWon)
		{updateYouWon(touchEvents);}
	}

	private void updateReady(List<TouchEvent> touchEvents) {

		// This example starts with a "Ready" screen.
		// When the user touches the screen, the game begins.
		// state now becomes GameState.Running.
		// Now the updateRunning() method will be called!

		if (touchEvents.size() > 0)
		{
			state = GameState.Running;
		}
	}

	private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {

		// 1. All touch input is handled here:
		if(numberTargets ==0)
		{
			state = GameState.GameOver;
			return;
		}

		for(int i=0; i<touchEvents.size();i++)
		{
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_DOWN) 
			{
				for(int j=0; j<numberTargets;j++)
				{
					if(targets.get(j).isShot(event.x,event.y))
					{
						score++;
						Log.i("TARGET HIT","target["+j+"] in pos ("+event.x+", "+event.y+") was shot down!");
						numberTargets--;
					}
				}
			}
		}
		// 2. Check miscellaneous events like death:

		if (livesLeft == 0) {
			state = GameState.GameOver;
		}

		for(int j=0; j<numberTargets;j++)
		{
			if(!targets.get(j).isHit() && targets.get(j).isOnScreen())
				targets.get(j).update(deltaTime);
		}
		bg1.update();
		bg2.update();
	}

	private void updatePaused(List<TouchEvent> touchEvents) {
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				if (resumeButton.clicked(event)) {
					resume();
				}
				else if (menuButton.clicked(event)) {
					nullify();
					goToMenu();
				}
			}
		}
	}

	private void updateGameOver(List<TouchEvent> touchEvents) {
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_DOWN) {
				if (gameOverButton.clicked(event)) {
					SaveScore();
					nullify();
					game.setScreen(new MainMenuScreen(game));
					return;
				}
			}
		}

	}

	private void updateYouWon(List<TouchEvent> touchEvents) {
		// TODO Auto-generated method stub

	}

	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();

		g.drawImage(Assets.background, bg1.getBgX(), bg1.getBgY());
		g.drawImage(Assets.background, bg2.getBgX(), bg2.getBgY());
		g.drawImage(Assets.floor, 0, 0);
		// Secondly, draw the UI above the game elements.
		if (state == GameState.Ready)
			drawReadyUI();
		else if (state == GameState.Running)
		{
			drawRunningUI();
		}
		else if (state == GameState.Paused)
			drawPausedUI();
		else if (state == GameState.GameOver)
			drawGameOverUI();
		else if(state == GameState.YouWon)
			drawYouWonUI();

	}

	private void drawReadyUI() {
		Graphics g = game.getGraphics();
		g.drawARGB(155, 0, 0, 0);
		g.drawString("Tap to Start.", 480, 300, paint2);
	}

	private void drawRunningUI() {
		Graphics g = game.getGraphics();
		for (int i = 0; i < targets.size(); i++) {
			targets.get(i).paint(g,dimension);
		}
	}

	private void drawPausedUI() {
		Graphics g = game.getGraphics();
		// Darken the entire screen so you can display the Paused screen.
		g.drawARGB(155, 0, 0, 0);
		g.drawString("Resume", 475, 200, paint2);
		g.drawString("Menu", 475, 400, paint2);
	}

	private void drawGameOverUI() {
		Graphics g = game.getGraphics();
		g.drawRect(0, 0, 1281, 801, Color.BLACK);
		g.drawString("GAME OVER.", 480, 280, paint2);
		g.drawString("Tap to return.", 480, 320, paint);
	}

	private void drawYouWonUI() {
		// TODO Auto-generated method stub

	}

	private void nullify()
	{
		// Set all variables to null. You will be recreating them in the
		// constructor.
		paint = null;
		paint2 = null;
		bg1 = null;
		bg2 = null;
		targets.clear();
		targets = null;
		// Call garbage collector to clean up memory.
		System.gc();
	}

	private void SaveScore() {
		Intent i = new Intent(SampleGame.getInstance().getApplicationContext(), Input.class);
		i.putExtra("playerScore", score);
		SampleGame.getInstance().startActivityForResult(i, 0);
	}

	@Override
	public void pause() {
		if (state == GameState.Running)
			state = GameState.Paused;

	}

	@Override
	public void resume() {
		if (state == GameState.Paused)
			state = GameState.Running;
	}

	@Override
	public void dispose() {

	}

	@Override
	public void backButton() {
		pause();
	}

	private void goToMenu() {
		game.setScreen(new MainMenuScreen(game));
	}

	public static Background getBg1() {
		return bg1;
	}

	public static Background getBg2() {
		return bg2;
	}
}