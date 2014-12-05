package com.kilobolt.robotgame;

import java.util.Random;
import com.kilobolt.framework.Graphics;
import android.util.DisplayMetrics;
import android.util.Log;

public class Target {

	Animation explosion;
	private int radius;
	private int x; //x coordinate of top-left
	private int y; // y coordinate of top-left
	private double vx;
	private double vy;
	private boolean onScreen;
	private boolean hit;
	private int side;
	private float time;

	public Target(double landingX, double landingY, double flightTime)
	{
		flightTime/=1000;
		if(landingX < 100)
		{
			x = 960;
		}
		else
		{
			x = 0;
		}

		y = 400;

		radius = 100;
		time =0;
		// x = x + vx*t
		// y = y + vy*t + 1/2*9.8*t^2
		//Calculaing velocities.
		vx = (landingX - x)/flightTime;
		vy = -(landingY-y + 4.9*(flightTime*flightTime))/flightTime;
		onScreen = true;	
		hit = false;
	}

	public void update(float deltaTime)
	{
		if(x<0 || x>960)
			onScreen = false;
		else if(y<0 || y>540)
			onScreen = false;
		else {
			time+=deltaTime;
			time/=1000;
			vy = (vy + 9.8*time);
			x = (int) (x + vx*time);
			y = (int) (y + (vy*time + 4.9*time*time));
			if(radius >10)
				radius--;
			else {
				onScreen=false;
			}
			//			Log.i("updating targets"," Update time: "+deltaTime+", class time: "+time+", x:" +x + " ,y: " + y + ", vx: " + vx + ", vy: " + vy);		
		} 
	}

	public boolean isShot(int tx,int ty) {
		if(tx>=x && tx<=x+2*radius)
			if(ty>=y && ty<=y+2*radius)
			{
				hit = true;
				return true;
			}
		return false;
	}

	public int getx() {
		return x;
	}

	public void setx(int x) {
		this.x = x;
	}

	public int gety() {
		return y;
	}

	public void sety(int y) {
		this.y = y;
	}

	public double getvx() {
		return vx;
	}

	public void setvx(double vx) {
		this.vx = vx;
	}

	public double getvy() {
		return vy;
	}

	public void setvy(double vy) {
		this.vy = vy;
	}

	public boolean isOnScreen()
	{
		return onScreen;
	}
	public boolean isHit() {
		return hit;
	}
	public void setHit(boolean hit) {
		this.hit = hit;
	}

	public void setOnScreen(boolean onScreen) {
		this.onScreen = onScreen;
	}
	public int getRadius() {
		return radius;
	}
	public void incRadius(int value) {
		this.radius += value;
	}

	public void paint(Graphics g, DisplayMetrics dimension) {
		if(!hit && onScreen)
		{
			Log.i("paintin a target", "size in pixels: "+ this.radius/Assets.target.getHeight());
			//g.drawImage(Assets.target, x, y);
			g.drawTarget(Assets.target,(float) x,(float) y, (float) radius/Assets.target.getHeight());
		}
	}
}