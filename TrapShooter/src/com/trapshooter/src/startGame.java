package com.trapShooter.src;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import android.content.Intent;
import android.util.Log;

import com.trapShooter.framework.Screen;
import com.trapShooter.implementation.AndroidGame;

public class startGame extends AndroidGame{
	boolean firstTime =true;

	private static startGame singleton = null;
	public static int gameDificulty = 2;
	public static boolean GameMode;

	public static startGame getInstance(){
		if(singleton == null){
			singleton = new startGame();
		}
		return singleton;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == 0) 
		{
			if (data.hasExtra("playerName") && data.hasExtra("playerScore")) 
			{
				playerRecord auxPair = new playerRecord(data.getExtras().getString("playerName"), data.getExtras().getInt("playerScore"));
				if(data.getExtras().getBoolean("mode"))
					{Assets.ArcadeScore.addElement(auxPair);}
				else 
					{Assets.timeScore.addElement(auxPair);}
				Assets.orderScore();
			}
			if(data.hasExtra("mode"))
				GameMode = data.getExtras().getBoolean("mode");
			if(data.hasExtra("audio"))
				Assets.useAudio = data.getExtras().getBoolean("audio");
			if(data.hasExtra("gameDificulty"))
				gameDificulty = data.getExtras().getInt("gameDificulty");	
		}
		if(!Assets.useAudio)
			Assets.theme.stop();
	} 

	@Override
	public Screen getInitScreen() {
		if(firstTime)
		{
			Assets.load(this);
			singleton = this;
			firstTime = false;
		}
		return new SplashLoadingScreen(this);
	}

	public void writeScoreToFile(){
		FileOutputStream fOut;
		try {
			fOut = openFileOutput("highScore.sav",MODE_MULTI_PROCESS);
			ObjectOutputStream osw = new ObjectOutputStream(fOut);
			osw.writeInt(Assets.ArcadeScore.size());
			for(int i=0; i<Assets.ArcadeScore.size();i++)
			{
				osw.writeObject(Assets.ArcadeScore.get(i));
			}
			osw.writeInt(Assets.timeScore.size());
			for(int i=0; i<Assets.timeScore.size();i++)
			{
				osw.writeObject(Assets.timeScore.get(i));
			}
			osw.close();
			fOut.close();
		} catch (FileNotFoundException e) {
			Log.e("Writing score file", "File not found");
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("Writing score file", "IO error, could not write to file");
			e.printStackTrace();
		};
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	public void readScoreFromFile()
	{
		FileInputStream fIn;

		try {
			fIn = openFileInput("highScore.sav");
			ObjectInputStream isw = new ObjectInputStream(fIn);
			int numberOfScores = isw.readInt();
			Assets.ArcadeScore.clear();
			for(int i=0; i<numberOfScores;i++)
			{
				playerRecord aux= (playerRecord) isw.readObject();
				Assets.ArcadeScore.add(aux);
			}
			numberOfScores = isw.readInt();
			Assets.timeScore.clear();
			for(int i=0; i<numberOfScores;i++)
			{
				playerRecord aux= (playerRecord) isw.readObject();
				Assets.timeScore.add(aux);
			}
			isw.close();
			fIn.close();
		} catch (FileNotFoundException e1) {
			Log.e("Reading score file", "File not found");
			e1.printStackTrace();
		} catch (StreamCorruptedException e) {
			Log.e("Reading score file", "Stream corrupted");
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("Reading score file", "IO error could not read from file");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			Log.e("Reading score file", "Class playerRecord not found during file reading");
			e.printStackTrace();
		}
	}

	@Override
	public void onBackPressed() {
		getCurrentScreen().backButton();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		Assets.theme.pause();
	}
}
