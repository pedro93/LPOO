package com.trapShooter.src;

import java.io.Serializable;

@SuppressWarnings("serial")
public class playerRecord implements Serializable{
	private String name;
	private Integer score;

	public playerRecord(String string, int i) {
		name = string;
		score = i;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
}