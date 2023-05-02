package com.utc.cnt6.PacmanGame;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

import javax.swing.ImageIcon;

public class Ghost extends Rectangle {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int GHOST_WIDTH = 32;    // chiều rộng của boss
	private static final int GHOST_HEIGHT = 32;	  // chiều cao của boss
	private static  final int G_SPEED = 1;
	private int random = 0, smart = 1, find_path = 0;
	private int state = smart;
	private int dir = -1; //direction
	private int right = 0, left = 1, up = 2, down = 3;
	public Random randomG;
	private int timer = 0;
	private int targetTime = 60*4;
	private int lastDir = -1;
	private boolean isbomNoKGL, isbomNoKGR, isbomNoKGU, isbomNoKGD; // kiểm tra độ rộng của bom nổ
	private boolean isghost, isbomNo;  //  kiểm tra trạng thái của quả bom
	private Image ghost;       // lưu hình ảnh của boss
	private int bomX,bomY;	   // lưu tọa độ của quả bom
	public Ghost(int x,int y) {
		randomG = new Random();
		setBounds(x, y,Ghost.GHOST_WIDTH, Ghost.GHOST_HEIGHT );
		dir = randomG.nextInt(4);
		isghost = true;
	}
	public void loadImageG() {
		ghost = new ImageIcon("res/Entity/ghost.gif").getImage();
	}
	public void render(Graphics g) {
		loadImageG();
		if (isghost) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.drawImage(ghost , x, y,null);
		}
	}
	
	public void tick() {
		boolean move = false;
		if(state == random) {
			if(dir == right) {
				if(canMove(x+G_SPEED,y) && canMove1(x+G_SPEED, y) ) {
					x+=G_SPEED;
				} else {
					dir = randomG.nextInt(4);
				}
			}else if(dir == left) {
				if(canMove(x-G_SPEED,y) && canMove1(x-G_SPEED, y)) {
					x-=G_SPEED;
				} else {
					dir = randomG.nextInt(4);
				}
			}else if(dir == up) {
				if(canMove(x,y-G_SPEED) && canMove1(x, y-G_SPEED)) {
					y-=G_SPEED;
				} else {
					dir = randomG.nextInt(4);
				}
			}else {
				if(canMove(x,y+G_SPEED)&& canMove1(x, y+G_SPEED)) {
					y+=G_SPEED;
				} else {
					dir = randomG.nextInt(4);
				}
			}
			timer++;
			if(timer == targetTime) {
				state = smart;
				timer = 0;
			}
		} else if(state == smart) {
			//follow player
			if(x<PacmanGame.pacman.x) {
				if(canMove(x+G_SPEED, y) && canMove1(x+G_SPEED, y)) {
					x+=G_SPEED;
					move = true;
					lastDir = right;
				}
			}
			if(x>PacmanGame.pacman.x) {
				if(canMove(x-G_SPEED, y) && canMove1(x-G_SPEED, y)) {
					x-=G_SPEED;
					move = true;
					lastDir =  left;
				}
			}
			if(y<PacmanGame.pacman.y) {
				if(canMove(x, y+G_SPEED) && canMove1(x, y+G_SPEED)) {
					y+=G_SPEED;
					move = true;
					lastDir = down;
				}
			}
			if(y>PacmanGame.pacman.y) {
				if(canMove(x, y-G_SPEED) &&canMove1(x, y-G_SPEED)) {
					y-=G_SPEED;
					move = true;
					lastDir = up;
				}
			}
			if(x == PacmanGame.pacman.x && y == PacmanGame.pacman.y) {
				move = true;
			}
			if(!move) {
				state = find_path;
			}
			timer++;
			if(timer == targetTime) {
				state = random;
				timer = 0;
			}
		}
		else if(state == find_path) {
			if(lastDir == right) {
				if(y < PacmanGame.pacman.y) {
					if(canMove(x, y+G_SPEED)&&canMove1(x, y+G_SPEED)) {
						y+=G_SPEED;
						state = smart;
					}
				} else {
					if(canMove(x, y-G_SPEED) && canMove1(x, y-G_SPEED)) {
						y-=G_SPEED;
						state = smart;
					}
				}
				if(canMove(x+G_SPEED, y)&&canMove1(x+G_SPEED, y)){
					x+=G_SPEED;
				}
			} else if(lastDir == left) {
				if(y < PacmanGame.pacman.y) {
					if(canMove(x, y+G_SPEED)&& canMove1(x, y+G_SPEED)) {
						y+=G_SPEED;
						state = smart;
					}
				} else {
					if(canMove(x, y-G_SPEED) && canMove1(x, y-G_SPEED)) {
						y-=G_SPEED;
						state = smart;
					}
				}
				if(canMove(x+G_SPEED, y) && canMove1(x-G_SPEED, y)){
					x+=G_SPEED;
				}
			} else if(lastDir == up) {
				if(x < PacmanGame.pacman.x) {
					if(canMove(x+G_SPEED, y)&& canMove1(x+G_SPEED, y)) {
						x+=G_SPEED;
						state = smart;
					}
				} else {
					if(canMove(x-G_SPEED, y) && canMove1(x-G_SPEED, y)) {
						x-=G_SPEED;
						state = smart;
					}
				}
				if(canMove(x, y-G_SPEED) && canMove1(x, y-G_SPEED)){
					y-=G_SPEED;
				}
			} else if(lastDir == down) {
				if(x < PacmanGame.pacman.x) {
					if(canMove(x+G_SPEED, y) &&canMove1(x+G_SPEED, y)) {
						x+=G_SPEED;
						state = smart;
					}
				} else {
					if(canMove(x-G_SPEED, y) && canMove1(x-G_SPEED, y)) {
						x-=G_SPEED;
						state = smart;
					}
				}
				if(canMove(x, y+G_SPEED) && canMove1(x, y+G_SPEED)){
					y+=G_SPEED;
				}
			}
			timer++;
			if(timer == targetTime) {
				state = random;
				timer = 0;
			}
		}
		if(isbomNo) {
			isbomNoKGL   = true;
			isbomNoKGR   = true;
			isbomNoKGU   = true;
			isbomNoKGD   = true;
			GameLevel level=PacmanGame.level;
			Rectangle boundTT = new Rectangle(bomX, bomY, 32, 32); 
			Rectangle boundKGL= new Rectangle(bomX-32*1, bomY, 32, 32);
			Rectangle boundKGR= new Rectangle(bomX+32*1, bomY, 32, 32);
			Rectangle boundKGU= new Rectangle(bomX, bomY-32*1, 32, 32);
			Rectangle boundKGD= new Rectangle(bomX, bomY+32*1, 32, 32);
			Rectangle boundCuoiL= new Rectangle(bomX-32*2, bomY, 32, 32);
			Rectangle boundCuoiR= new Rectangle(bomX+32*2, bomY, 32, 32);
			Rectangle boundCuoiU= new Rectangle(bomX, bomY-32*2, 32, 32);
			Rectangle boundCuoiD= new Rectangle(bomX, bomY+32*2, 32, 32);
			for(int a = 0; a < level.tiles.length; a++) {
				for(int b = 0; b < level.tiles[0].length;b++) {
					if(level.tiles[a][b] !=null) {
						if(boundKGL.intersects(level.tiles[a][b]))   isbomNoKGL   = false;			
//						if(boundCuoiL.intersects(level.tiles[a][b])) isbomNoCuoiL = false;
						if(boundKGR.intersects(level.tiles[a][b]))   isbomNoKGR   = false;
//						if(boundCuoiR.intersects(level.tiles[a][b])) isbomNoCuoiR = false;
						if(boundKGU.intersects(level.tiles[a][b])) 	 isbomNoKGU   = false;
//						if(boundCuoiU.intersects(level.tiles[a][b])) isbomNoCuoiU = false;
						if(boundKGD.intersects(level.tiles[a][b])) 	 isbomNoKGD   = false;	
//						if(boundCuoiD.intersects(level.tiles[a][b])) isbomNoCuoiD = false;
					}
					}
				}
			if(this.intersects(boundTT)) 	isghost =false;
			if(this.intersects(boundCuoiL) && isbomNoKGL)	isghost =false;
			if(this.intersects(boundCuoiR) && isbomNoKGR) isghost =false;;
			if(this.intersects(boundCuoiU) && isbomNoKGU) isghost =false;
			if(this.intersects(boundCuoiD) && isbomNoKGD) isghost =false;
			if(this.intersects(boundKGL)) 	isghost =false;
			if(this.intersects(boundKGR)) 	isghost =false;
			if(this.intersects(boundKGU))   isghost =false;
			if(this.intersects(boundKGD))   isghost =false;
		
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
	public void setBomXY(int x, int y) {
		this.bomX=x;
		this.bomY=y;
	}
	private boolean canMove1(int nextX, int nextY) {
		Rectangle bound = new Rectangle(nextX,nextY,width,height);
			if(bound.intersects(new Rectangle(bomX,bomY,32,32))) {
			return false;
		}
		return false;
	}
	public boolean isIsghost() {
		return isghost;
	}
	public void setIsbomNo(boolean bomNo) {
		this.isbomNo = bomNo;
	}
}
