package com.trapShooter.src;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;
import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.util.Log;

import com.trapShooter.framework.Image;
import com.trapShooter.framework.Music;
import com.trapShooter.framework.Sound;

@SuppressLint("NewApi")
public class Assets {

	private static Assets singleton = null;

	public static Assets getInstance(){
		if(singleton == null){
			singleton = new Assets();
		}
		return singleton;
	}
	
	public static Image menu, highScore, settings, help, splash, background, target, floor;
	public static Image button, selectedOption, ammo;
	public static Image plate1, plate2, plate3, plate4, plate5, plate6;
	public static Sound gunShot,reload;
	public static Music theme;
	public static Typeface myFont;
	public static Vector<playerRecord> ArcadeScore, timeScore;
	public static boolean useAudio = true;

	public static void load(startGame sampleGame) {
		theme = sampleGame.getAudio().createMusic("menutheme.mp3");
		myFont = Typeface.createFromAsset(sampleGame.getAssets(),"blambotcustom.ttf"); 
		theme.setLooping(true);
		theme.setVolume(0.85f);
		theme.play();
		Assets.ArcadeScore = new Vector<playerRecord>();
		Assets.timeScore = new Vector<playerRecord>();

	}	

	@SuppressLint("NewApi")
	public static String scoreIndexToString(Vector<playerRecord> vector, int index)
	{
		StringBuilder result = new StringBuilder();
		result.append(vector.get(index).getName());
		result.append('-');
		result.append(vector.get(index).getScore());
		return result.toString();
	}

	public static void orderScore() {
		Comparator<playerRecord> myComparator = new Comparator<playerRecord>(){
			@Override
			public int compare(playerRecord arg0,playerRecord arg1) {
				return arg1.getScore().compareTo(arg0.getScore());
			}
		};
		Collections.sort(ArcadeScore, myComparator);
		Collections.sort(timeScore, myComparator);

		if(ArcadeScore.size()>8)
		{
			Log.i("Highscore vector reduction needed", "current size: " + ArcadeScore.size());
			for(int i=ArcadeScore.size();i>8;i--)
			{
				ArcadeScore.remove(i);
				Log.i("Highscore vector reduction needed", "removing index: " + i);
			}
			Log.i("Highscore vector reduction needed", "finl size: " + ArcadeScore.size());
		}
	}
}
