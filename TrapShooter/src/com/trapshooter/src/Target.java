package com.trapShooter.src;

import java.util.Random;

import android.util.DisplayMetrics;
import android.util.Log;

import com.trapShooter.framework.Graphics;

public class Target {

	private Animation explosion;
	private int side,radius;
	private int x; //x coordinate of top-left
	private int y; // y coordinate of top-left
	private double vx;
	private double vy;
	private boolean onScreen,active;
	private boolean hit, unlockNext;
	private float time;

	public Target(double landingX, double landingY, double flightTime)
	{
		flightTime/=1000;
		if(landingX < 100)
		{
			side=1;
			x = 960;
		}
		else
		{
			side=0;
			x = 0;
		}

		Random myrand=new Random();
		y =myrand.nextInt(200) +250;

		radius = 100;
		time =0;
		// x = x + vx*t
		// y = y + vy*t + 1/2*9.8*t^2
		//Calculaing velocities.z
		vx = (landingX - x)/flightTime;
		vy = - (landingY-y + 4.9*(flightTime*flightTime))/flightTime;

		unlockNext=false;
		onScreen = true;
		active = false;
		hit = false;
		time=0;
		explosion = new Animation();
		explosion.addFrame(Assets.plate1, 100);
		explosion.addFrame(Assets.plate2, 100);
		explosion.addFrame(Assets.plate3, 100);
		explosion.addFrame(Assets.plate4, 100);
		explosion.addFrame(Assets.plate5, 100);
		explosion.addFrame(Assets.plate6, 100);

	}

	public void reset(double landingX, double landingY, double flightTime)
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
		Random myrand=new Random();
		y =myrand.nextInt(200)+250;

		radius = 100;
		time =0;
		// x = x + vx*t
		// y = y + vy*t + 1/2*9.8*t^2
		//Calculaing velocities.z
		vx = (landingX - x)/flightTime;
		vy = - (landingY-y + 4.9*(flightTime*flightTime))/flightTime;
		unlockNext=false;
		onScreen = true;
		active = false;
		hit = false;
		time=0;
	}

	public void update(float deltaTime)
	{
		deltaTime/=1000;
	
		if(x<0 || x>960)
		{
			onScreen = false;
			active = false;

		}
		else if(y<0 || y>540)
		{
			onScreen = false;
			active = false;

		}
		else {
			time+=deltaTime;
			vy = (vy + 9.8*time);
			x = (int) (x + vx*time);
			y = (int) (y + (vy*time + 4.9*time*time));
			if(hit)
			{
				if(!explosion.update(32)) //reached end of animation
					active = false;
			}
			else if(radius >50)
			{
				if(radius%2==0)
					radius--;
			}
		} 
		return;
	}

	public boolean isUnlockNext() {
		return unlockNext;
	}

	public void setUnlockNext(boolean unlockNext) {
		this.unlockNext = unlockNext;
	}

	public boolean isShot(int tx,int ty) {
		if(tx>=x && tx<=x+2*radius)
		{
			if(ty>=y && ty<=y+2*radius)
			{
				hit = true;
				return true;
			}
		}
		return false;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isActive() {
		return active;
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
			g.drawTarget(Assets.target,(float) x,(float) y, (float) radius/Assets.target.getHeight());
		}
		else if (hit && onScreen) {
			g.drawTarget(explosion.getImage(), (float) x,(float) y, (float) radius/Assets.target.getHeight());
		}
	}
}