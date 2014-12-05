package com.kilobolt.robotgame;

import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import com.kilobolt.framework.Game;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Input.TouchEvent;
import com.kilobolt.framework.Screen;
import com.kilobolt.framework.implementation.AndroidmyButton;

public class HighScoreScreen extends Screen{

	AndroidmyButton back = new AndroidmyButton(638, 285, 207, 167);
	Paint paint;

	public HighScoreScreen(Game game) {
		super(game);

		paint = new Paint();
		paint.setTextSize(30);
		paint.setTextAlign(Paint.Align.LEFT);
		paint.setAntiAlias(true);
		paint.setColor(Color.rgb(23, 198, 17));
		paint.setTypeface(Assets.myFont);
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
		g.drawImage(Assets.highScore, 0, 0);
		int xi = 100;
		int yi = 230;

		for(int i=0; i<8;i++)
		{
			if(i<Assets.score.size())
			{
				g.drawString(Assets.scoreIndexToString(i), xi, yi, paint);
				yi+=40;
			}
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
