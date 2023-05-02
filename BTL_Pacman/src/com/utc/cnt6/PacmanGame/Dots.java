package com.utc.cnt6.PacmanGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Dots extends Rectangle{
//	private static final int DOT_WIDTH = 32;
//	private static final int DOT_HEIGHT = 32;
	private int A=298,B=426;
	
	public Dots(int x, int y) {
		setBounds(x+10, y+10, 8, 8);
	}
	
	public void render(Graphics g) {
		
		if(x==A && y==B) {
			g.setColor(Color.red);
			g.fillRect(x, y, width, height);
		}
		else{
//			System.out.println(x+" "+y);
			g.setColor(Color.YELLOW);
			g.fillRect(x, y, width, height);
			}
	}

	public int getA() {
		return A;
	}

	public int getB() {
		return B;
	}
	
}
