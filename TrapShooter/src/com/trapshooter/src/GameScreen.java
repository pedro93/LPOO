package com.trapShooter.src;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;

import com.trapShooter.framework.Game;
import com.trapShooter.framework.Graphics;
import com.trapShooter.framework.Screen;
import com.trapShooter.framework.Input.TouchEvent;
import com.trapShooter.implementation.AndroidmyButton;

public class GameScreen extends Screen {
	enum GameState {
		GameMode,Ready,Running, Paused, GameOver
	}

	private static double Flightime = 1000;

	GameState state = GameState.GameMode;

	// Variable Setup
	private static Background bg1, bg2;
	private static int numberTargets;
	private static Vector<Target> targets;
	private static DisplayMetrics dimension;
	private int livesLeft = 3;
	private double timeLeft=6000.0;
	private int score =0;
	private int ammo =2;
	private Paint paint, paint2;
	private AndroidmyButton resumeButton,menuButton,gameOverButton,arcadeModeButton, timeTrialButton;
	private Timer myThread;
	public final int dt = 1;
	private float time;

	public GameScreen(Game game) {
		super(game);
		switch (startGame.gameDificulty) {
		case 1: //easy
			Flightime*=4;
			break;
		case 3: //hard
			Flightime/=4;
			break;
		default: //medium
			break;
		}
		time =0;
		// Initialize game objects here
		bg1 = new Background(0, 0);
		bg2 = new Background(1700, 0);
		numberTargets = 4;
		targets = new Vector<Target>();
		dimension = new DisplayMetrics();
		Random myrand = new Random();
		int side,x,y;
		for(int i = 0; i<numberTargets;i++)
		{
			side = myrand.nextInt(2);
			y = myrand.nextInt(50)+400;
			if(side ==0)
				x =0;
			else
				x = 960;

			Target aux = new Target(x,y,Flightime);
			targets.add(aux);
		}
		resumeButton = new AndroidmyButton(0, 0, 960, 270);
		menuButton = new AndroidmyButton(0, 271, 960, 269);
		gameOverButton = new AndroidmyButton(0, 0, 960, 540);
		arcadeModeButton = new AndroidmyButton(0, 271, 960, 269);
		timeTrialButton = new AndroidmyButton(0, 0, 960, 540);
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
		myThread = new Timer();
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

		// We have four separate update methods in this example.
		// Depending on the state of the game, we call different update methods.
		// Refer to Unit 3's code. We did a similar thing without separating the
		// update methods.

		if(state==GameState.GameMode)
		{updateGameMode(touchEvents);}
		else if (state == GameState.Ready)
		{updateReady(touchEvents);}
		else if (state == GameState.Running)
		{updateRunning(touchEvents, deltaTime);}
		else if (state == GameState.Paused)
		{updatePaused(touchEvents);}
		else if (state == GameState.GameOver)
		{updateGameOver(touchEvents);}
		
	}

	private void updateGameMode(List<TouchEvent> touchEvents) {
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				if (arcadeModeButton.clicked(event)) {
					startGame.GameMode = false;
				}
				else if (timeTrialButton.clicked(event)) {
					startGame.GameMode = true;
					timeLeft =60;
				}
				state = GameState.Ready;
			}
		}
	}

	private void updateReady(List<TouchEvent> touchEvents) {

		if (touchEvents.size() > 0)
		{
			state = GameState.Running;
		}
	}

	private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
		if(!startGame.GameMode)
			timeLeft-=2*deltaTime;
		myThread.schedule(new TimerTask() {

			@Override
			public void run() {
				updateTargets(dt);
			}
		}, dt);

		// 1. All touch input is handled here:
		if(numberTargets ==0)
		{
			state = GameState.GameOver;
			return;
		}

		if(ammo==0)
		{
			if(Assets.useAudio)
				Assets.reload.play(0.85f);
			ammo =2;
		}

		for(int i=0; i<touchEvents.size();i++)
		{
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_DOWN) 
			{
				if(Assets.useAudio)
					Assets.gunShot.play(0.85f);
				ammo--;
				for(int j=0; j<numberTargets;j++)
				{
					if(targets.get(j).isActive() && targets.get(j).isShot(event.x,event.y))
					{
						score+=startGame.gameDificulty;
					}
				}
			}
		}
		// 2. Check miscellaneous events like death:
		if(startGame.GameMode)
		{
			if (livesLeft == 0) {
				state = GameState.GameOver;
			}
		}
		else {
			if(timeLeft <=0)
				state = GameState.GameOver;
		}

		bg1.update();
		bg2.update();
	}

	private void updateTargets(float deltaTime) {

		time +=deltaTime;
		for(int j=0; j<numberTargets;j++)
		{
			if(!targets.get(j).isOnScreen() && !targets.get(j).isHit())
			{
				if(startGame.GameMode)
					livesLeft--;
				targets.get(j).setActive(false);
			}
			if(time/3.0>1.0)
			{
				if(time>3.0)
				{
					time -=3;
				}
				if(j>=numberTargets-1)
				{	
					targets.get(0).setActive(true);
				}
				else
				{
					targets.get(j+1).setActive(true);
				}
			}
			if(!targets.get(j).isActive())
			{
				Random myrand = new Random();
				int side,x,y;
				side = myrand.nextInt(2);
				y = myrand.nextInt(200)+400;
				if(side ==0)
					x =0;
				else
					x = 960;
				targets.get(j).reset(x, y,Flightime);
			}
			targets.get(j).update(deltaTime);	

		}


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



	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();

		g.drawImage(Assets.background, bg1.getBgX(), bg1.getBgY());
		g.drawImage(Assets.background, bg2.getBgX(), bg2.getBgY());
		g.drawImage(Assets.floor, 0, 0);
		g.drawString("Score: "+score, 90, 50, paint);
		if(startGame.GameMode) //time trial
		{
			g.drawString("Lives: ", 800, 50, paint);
			for(int i=0; i<livesLeft;i++)
				g.drawString("X", 870+i*30, 50, paint);
		}
		else
		{
			g.drawString("Time: "+(int)timeLeft/100, 800, 50, paint);
		}
		// Secondly, draw the UI above the game elements.

		if (state == GameState.GameMode)
		{drawGameModeUI();}
		else if (state == GameState.Ready)
		{drawReadyUI();}
		else if (state == GameState.Running)
		{drawRunningUI();}
		else if (state == GameState.Paused)
		{drawPausedUI();}
		else if (state == GameState.GameOver)
		{drawGameOverUI();}
		
	}

	private void drawGameModeUI() {
		Graphics g = game.getGraphics();
		// Darken the entire screen so you can display the Paused screen.
		g.drawARGB(155, 0, 0, 0);
		g.drawString("Arcade Mode", 475, 200, paint2);
		g.drawString("Time Trial", 475, 400, paint2);

	}

	private void drawReadyUI() {
		Graphics g = game.getGraphics();
		g.drawARGB(155, 0, 0, 0);
		g.drawString("Tap to Start.", 480, 300, paint2);
	}

	private void drawRunningUI() {
		Graphics g = game.getGraphics();
		for(int i=0;i<ammo;i++)
		{g.drawImage(Assets.ammo, i*50+15, 490);}

		for (int i = 0; i < targets.size(); i++) {
			if(targets.get(i).isActive())
			{
				targets.get(i).paint(g,dimension);
			}
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
		Intent i = new Intent(startGame.getInstance().getApplicationContext(), Input.class);
		i.putExtra("audio", Assets.useAudio);
		i.putExtra("gameDificulty", startGame.gameDificulty);
		if(startGame.GameMode)
		{
			if(Assets.ArcadeScore.size()>=8)
			{
				if(score > Assets.ArcadeScore.get(Assets.ArcadeScore.size()-1).getScore())
				{
					i.putExtra("playerScore", score);
					i.putExtra("mode", startGame.GameMode);
				}
			}
			else {
				i.putExtra("playerScore", score);
				i.putExtra("mode", startGame.GameMode);
			}
		}
		else {
			if(Assets.timeScore.size()>=8)
			{
				if(score > Assets.timeScore.get(Assets.timeScore.size()-1).getScore())
				{
					i.putExtra("playerScore", score);
					i.putExtra("mode", startGame.GameMode);
				}
			}
			else {
				i.putExtra("playerScore", score);
				i.putExtra("mode", startGame.GameMode);
			}
		}
		startGame.getInstance().startActivityForResult(i, 0);
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