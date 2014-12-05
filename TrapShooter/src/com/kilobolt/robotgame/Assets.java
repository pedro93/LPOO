package com.kilobolt.robotgame;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.util.Log;
import android.util.Pair;

import com.kilobolt.framework.Image;
import com.kilobolt.framework.Music;
import com.kilobolt.framework.Sound;

@SuppressLint("NewApi")
public class Assets {

	public static Image menu, highScore, settings, help, splash, background, target, floor;
	public static Image button, selectedOption;
	public static Sound click;
	public static Music theme;
	public static Typeface myFont;
	public static Vector<Pair<String, Integer>> score;
	public static boolean useAudio = true;

	public static void load(SampleGame sampleGame) {
		theme = sampleGame.getAudio().createMusic("menutheme.mp3");
		myFont = Typeface.createFromAsset(sampleGame.getAssets(),"blambotcustom.ttf"); 
		theme.setLooping(true);
		theme.setVolume(0.85f);
		theme.play();
		Assets.score = new Vector<Pair<String,Integer>>();
	}	

	@SuppressLint("NewApi")
	public static String scoreIndexToString(int index)
	{
		StringBuilder result = new StringBuilder();
		result.append(score.get(index).first);
		result.append('-');
		result.append(score.get(index).second);
		return result.toString();
	}

	public static void orderScore() {
		Comparator<Pair<String, Integer>> myComparator = new Comparator<Pair<String, Integer>>(){
			@Override
			public int compare(Pair<String, Integer> arg0,Pair<String, Integer> arg1) {
				return arg0.second.compareTo(arg1.second);
			}
		};
		Collections.sort(score, myComparator);

		if(score.size()>8)
		{
			Log.i("Highscore vector reduction needed", "current size: " + score.size());
			for(int i=score.size();i>8;i--)
			{
				score.remove(i);
				Log.i("Highscore vector reduction needed", "removing index: " + i);
			}
			Log.i("Highscore vector reduction needed", "finl size: " + score.size());
		}
	}
}
