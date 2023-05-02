package com.utc.cnt6.PacmanGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Pacman extends Rectangle {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int tamX,tamY,tam1X,tam1Y;
	private static final int PACMAN_WIDTH = 32;
	private static final int PACMAN_HEIGHT = 32;
	private int t=0;
	private BombThread  bombThread;
	private ChayNguocThread chayNguocThread=  new ChayNguocThread();;
	private int score=0,lives = 3;
	public boolean RIGHT,LEFT,UP,DOWN,A;
	private Image right,left,down,up,heart;
	private boolean isBombHien = false, isBombHien1 = true, isBombNo = false, isbom,isXY=false;
	private boolean isbomNoKGL, isbomNoKGR, isbomNoKGU, isbomNoKGD, ischaynguoc=false;
	private int P_SPEED = 4;
	private bom bomb;
	public int bomX, bomY;
	public Pacman(int x, int y) {
		setBounds(x,y,Pacman.PACMAN_WIDTH,Pacman.PACMAN_HEIGHT);
	}
	
	public int getLives() {
		return lives;
	}


	public void tick() {
		if(ischaynguoc==true) {
			if(RIGHT && canMove(x-P_SPEED,y)) {
				if(canMove1(x-P_SPEED, y) || isbom) {
						if(isbom) {
//							x+=P_SPEEDB;
							x=tam1X-32;
							y=tam1Y;
						}
						else{
							x -=P_SPEED;
						}
						isbom = false;					
					}
				
			}
			if(LEFT && canMove(x+P_SPEED,y)) {
				
				if(canMove1(x+P_SPEED, y) || isbom) {
					
					if(isbom) {
//						x-=P_SPEEDB;
						x=tam1X+32;
						y=tam1Y;
					}
					else {
						x +=P_SPEED;
					}
					isbom = false;
				}
			}
			if(UP && canMove(x,y + P_SPEED)) {
				if(canMove1(x, y + P_SPEED) || isbom) {
					if(isbom) {
//						y-=P_SPEEDB;
						x=tam1X;
						y=tam1Y-32;
					}
					else {
						y +=P_SPEED;
					}
					isbom = false;
				}
				
			}
			if(DOWN && canMove(x,y - P_SPEED)) {
				if(canMove1(x, y-P_SPEED) || isbom) {
					if(isbom) {
//						y+=P_SPEEDB;
						x=tam1X;
						y=tam1Y-32;
					}
					else {
						y-=P_SPEED;
					}
					isbom = false;
				}
			}

		}
			if(A) {
				if(!isBombHien & !isBombNo) {
					bomX =	((int) Math.round(x/32))*32 ;
					bomY =  ((int) Math.round(y/32))*32 ; 
					tam1X=bomX;
					tam1Y=bomY;
					tamX=-5;
					tamY=-5;
					isXY=false;
					bomb = new bom(bomX, bomY);
					isbom = true;
					bombThread = new BombThread();
					isBombHien = true;
					isBombHien1=true;	
				}
		}
		if(ischaynguoc==false) {
		if(RIGHT && canMove(x+P_SPEED,y)) {
			if(canMove1(x+P_SPEED, y) || isbom) {
					if(isbom) {
//						x+=P_SPEEDB;
						x=tam1X+32;
						y=tam1Y;
					}
					else{
						x +=P_SPEED;
					}
					isbom = false;					
				}
			
		}
		if(LEFT && canMove(x-P_SPEED,y)) {
			
			if(canMove1(x-P_SPEED, y) || isbom) {
				
				if(isbom) {
//					x-=P_SPEEDB;
					x=tam1X-32;
					y=tam1Y;
				}
				else {
					x -=P_SPEED;
				}
				isbom = false;
			}
		}
		if(UP && canMove(x,y - P_SPEED)) {
			if(canMove1(x, y - P_SPEED) || isbom) {
				if(isbom) {
//					y-=P_SPEEDB;
					x=tam1X;
					y=tam1Y-32;
				}
				else {
					y -=P_SPEED;
				}
				isbom = false;
			}
			
		}
		if(DOWN && canMove(x,y + P_SPEED)) {
			if(canMove1(x, y+P_SPEED) || isbom) {
				if(isbom) {
//					y+=P_SPEEDB;
					x=tam1X;
					y=tam1Y+32;
				}
				else {
					y+=P_SPEED;
				}
				isbom = false;
			}
		}
		if(A) {
			if(!isBombHien & !isBombNo) {
				bomX =	((int) Math.round(x/32))*32 ;
				bomY =  ((int) Math.round(y/32))*32 ; 
				tam1X=bomX;
				tam1Y=bomY;
				tamX=-5;
				tamY=-5;
				isXY=false;
				bomb = new bom(bomX, bomY);
				isbom = true;
				bombThread = new BombThread();
				isBombHien = true;
				isBombHien1=true;	
			}
		}
		}
		GameLevel level = PacmanGame.level;
		for(int i = 0; i < level.dots.size();i++) {
			if(this.intersects(level.dots.get(i))) {
				if((level.dots.get(i).getA()-10)/32*32==x/32*32 && (level.dots.get(i).getB()-10)/32*32==y/32*32) {
					
					chayNguocThread.start();
						ischaynguoc=chayNguocThread.isIschaynguoc();
				}
				level.dots.remove(i);
				score+=1;
				break;
			}
		}
		ischaynguoc = chayNguocThread.isIschaynguoc();
		for(int i = 0; i < PacmanGame.level.ghosts.size(); i++ ) {
			if(this.intersects(PacmanGame.level.ghosts.get(i))) {
					this.lives--;
					x=32*5;
					y=32*5;
			}
			if(lives==0) {
				PacmanGame.setSTATE(PacmanGame.getPause());
			}
		}
		if(isBombNo) {
			isbomNoKGL   = true;
			isbomNoKGR   = true;
			isbomNoKGU   = true;
			isbomNoKGD   = true;
	
		Rectangle boundTT = new Rectangle(tamX, tamY, 32, 32); 
		Rectangle boundKGL= new Rectangle(tamX-32*1, tamY, 32, 32);
		Rectangle boundKGR= new Rectangle(tamX+32*1, tamY, 32, 32);
		Rectangle boundKGU= new Rectangle(tamX, tamY-32*1, 32, 32);
		Rectangle boundKGD= new Rectangle(tamX, tamY+32*1, 32, 32);
		Rectangle boundCuoiL= new Rectangle(tamX-32*2, tamY, 32, 32);
		Rectangle boundCuoiR= new Rectangle(tamX+32*2, tamY, 32, 32);
		Rectangle boundCuoiU= new Rectangle(tamX, tamY-32*2, 32, 32);
		Rectangle boundCuoiD= new Rectangle(tamX, tamY+32*2, 32, 32);
		for(int a = 0; a < level.tiles.length; a++) {
			for(int b = 0; b < level.tiles[0].length;b++) {
				if(level.tiles[a][b] !=null) {
					if(boundKGL.intersects(level.tiles[a][b]))   isbomNoKGL   = false;			
//					if(boundCuoiL.intersects(level.tiles[a][b])) isbomNoCuoiL = false;
					if(boundKGR.intersects(level.tiles[a][b]))   isbomNoKGR   = false;
//					if(boundCuoiR.intersects(level.tiles[a][b])) isbomNoCuoiR = false;
					if(boundKGU.intersects(level.tiles[a][b])) 	 isbomNoKGU   = false;
//					if(boundCuoiU.intersects(level.tiles[a][b])) isbomNoCuoiU = false;
					if(boundKGD.intersects(level.tiles[a][b])) 	 isbomNoKGD   = false;	
//					if(boundCuoiD.intersects(level.tiles[a][b])) isbomNoCuoiD = false;
				}
				}
			}
		
		if(this.intersects(boundTT)) {
			lives--;
			x=32;
			y=32;
		}
		if(this.intersects(boundCuoiL) && isbomNoKGL) {
			lives--;
			x=32;
			y=32;
		}
		if(this.intersects(boundCuoiR) && isbomNoKGR) {
			lives--;
			x=32;
			y=32;
		}
		if(this.intersects(boundCuoiU) && isbomNoKGU) {
			lives--;
			x=32;
			y=32;
		}
		if(this.intersects(boundCuoiD) && isbomNoKGD) {
			lives--;
			x=32;
			y=32;
		}
		if(this.intersects(boundKGL)) {
			lives--;
			x=32;
			y=32;
		}
		if(this.intersects(boundKGR)) {
			lives--;
			x=32;
			y=32;
		}
		if(this.intersects(boundKGU)) {
			lives--;
			x=32;
			y=32;
		}
		if(this.intersects(boundKGD)) {
			lives--;
			x=32;
			y=32;
		}
		}	
		if(level.dots.size()==0) {
			//win
			PacmanGame.pacman = new Pacman(0,0);
			PacmanGame.level = new GameLevel("/Map/Map2.png");
			return;
		}
	}
	
	private boolean canMove(int nextX, int nextY) {
		Rectangle bound = new Rectangle(nextX,nextY,width,height);
		GameLevel level = PacmanGame.level;
		for(int a = 0; a < level.tiles.length; a++) {
			for(int b = 0; b < level.tiles[0].length;b++) {
				if(level.tiles[a][b] !=null) {
					if(bound.intersects(level.tiles[a][b])) {
						return false;
					}
				}
			}
		}
		
		return true;
	}
	private boolean canMove1(int nextX, int nextY) {
		Rectangle bound = new Rectangle(nextX,nextY,width,height);
		if(bound.intersects(new Rectangle(bomX,bomY,32,32))) {
			return false;
		}
		return true;
	}
	
	public void loadImage() {
		up = new ImageIcon("res/Entity/up.gif").getImage();
		down = new ImageIcon("res/Entity/down.gif").getImage();
		right = new ImageIcon("res/Entity/right.gif").getImage();
		left = new ImageIcon("res/Entity/left.gif").getImage();
		heart = new ImageIcon("res/Entity/heart.png").getImage();
	}
	
	public void drawScore(Graphics gs) {
		gs.setFont(new Font("Arial", Font.BOLD, 25));
		gs.setColor(Color.red);
		String s = "Score: " + score;
		//System.out.println(s);
		gs.drawString(s, 32*21, 36*14);;
	}
	
	public void render(Graphics g) {
		loadImage();
		Graphics2D g2d = (Graphics2D) g;
		if(RIGHT || t == 0) {
			t=0;
			g2d.drawImage(right, x, y, null);
		}
		if(LEFT || t == 1) {
			t=1;
			g2d.drawImage(left, x, y, null);
		}
		if(UP || t == 2) {
			t=2;
			g2d.drawImage(up, x, y, null);
		}
		if(DOWN || t == 3) {
			t=3;
			g2d.drawImage(down, x, y, null);
		}
		GameLevel level = PacmanGame.level;
		if(isBombHien && !isBombNo) {
			level.setBomXY(tam1X, tam1Y);
			level.setisBomNo(isBombNo);
			bomb.render(g);
			if(isBombHien1) {
				bombThread.start();	
			}
			isBombHien=bombThread.isBomHien();
			isBombNo=bombThread.isBomNo();
			bomb.setBomNo(isBombNo);
//			System.out.println("hello is bom no" + isBombHien+ " "+ isBombNo);
			isBombHien1 = false;
			
			
		}
		if(isBombNo) {
			level.setisBomNo(isBombNo);
			bomb.render(g);
			bomb.setBomHien(isBombHien1);
			isBombNo=bombThread.isBomNo();
			isXY=bombThread.isXY();
			if(isBombNo && tamX==-5 && tamY==-5) {
				tamX=bomX;
				tamY=bomY;
				bomX=0;
				bomY=0;
			}
		}
		else {
			if(isXY==true) {
				tamX=-5;
				tamY=-5;
				level.setBomXY(tamX, tamY);
			}
		}

		for(int i = 0; i < lives;i++) {
			g2d.drawImage(heart, i*32, 32*15, null);
		}
	}
}
