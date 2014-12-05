package com.kilobolt.robotgame;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.util.Pair;

import com.kilobolt.framework.Screen;
import com.kilobolt.framework.implementation.AndroidGame;

@SuppressLint("NewApi")
public class SampleGame extends AndroidGame{
	boolean firstTime =true;

	private static SampleGame singleton = null;

	public static SampleGame getInstance(){
		if(singleton == null){
			singleton = new SampleGame();
		}
		return singleton;
	}

	@SuppressLint("NewApi")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == 0) 
		{
			if (data.hasExtra("playerName") && data.hasExtra("playerScore")) 
			{
				Pair<String, Integer> auxPair = new Pair<String, Integer>(data.getExtras().getString("playerName"), (Integer) data.getExtras().getInt("playerScore"));
				Assets.score.addElement(auxPair);
				Assets.orderScore();
				return;
			}
		}
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
		Log.i("Saving score", "Entrou no writeFile");
		FileOutputStream fOut;
		try {
			fOut = openFileOutput("highScore.sav",MODE_WORLD_WRITEABLE);
			ObjectOutputStream osw = new ObjectOutputStream(fOut);
			osw.writeInt(Assets.score.size());
			Log.i("Saving score", "Starting write loop");
			for(int i=0; i<Assets.score.size();i++)
			{
				osw.writeChars(Assets.score.get(i).first);
				osw.writeInt(Assets.score.get(i).second);
				Log.i("saving score", "saved: "+Assets.score.get(i).first+ "- "+Assets.score.get(i).second);
			}
			Log.i("Saving score", "finished write loop");
			osw.close();
			fOut.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		Log.i("KIll process", "leaving app");
		android.os.Process.killProcess(android.os.Process.myPid());
}

public void readScoreFromFile()
{
	Log.i("loading score", "entrou funcao");
	FileInputStream fIn;
	
	try {
		fIn = openFileInput("highScore.sav");
		ObjectInputStream isw = new ObjectInputStream(fIn);
		int numberOfScores = isw.readInt();
		Assets.score.clear();
		Log.i("loading score", "limpou o vector de tamanho:" + numberOfScores);
		for(int i=0; i<numberOfScores;i++)
		{
			String aux= (String) isw.readObject();
			Integer auxInt= (Integer) isw.readInt();
			Pair<String, Integer> auxPair = new Pair<String, Integer>(aux, auxInt);
			Assets.score.add(auxPair);
			Log.i("reading score", "read: "+Assets.score.get(i).first+ "- "+Assets.score.get(i).second);
			System.out.printf("writing from file: %s, %d \n", Assets.score.get(i).first,Assets.score.get(i).second);
		}
		Log.i("loading score", "preencheu o vector");
		isw.close();
		fIn.close();
	} catch (FileNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (StreamCorruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
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

	Assets.theme.play();
}

@Override
public void onPause() {
	super.onPause();
	Assets.theme.pause();
}
}
