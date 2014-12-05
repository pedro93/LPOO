package com.kilobolt.framework.implementation;

import com.kilobolt.framework.Input.TouchEvent;

public class AndroidmyButton{

	int x,y,width,height;

	boolean selected;

	public AndroidmyButton(int xi, int yi, int widthi, int heighti)
	{
		this.x = xi;
		this.y=yi;
		this.width=widthi;
		this.height = heighti;
		selected=false;
	}

	public boolean clicked(TouchEvent event) {
		if (event.x > this.x && event.x < this.x + this.width - 1 && event.y > this.y
				&& event.y < this.y + this.height - 1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
