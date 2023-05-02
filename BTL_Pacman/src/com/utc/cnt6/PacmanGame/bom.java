package com.utc.cnt6.PacmanGame;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

	public class bom  extends Rectangle {
	private boolean isBomNo,isBomHien ;			// dùng để xác định trạng thái của quả bom
	private Image bomHien, bomNoTT, bomNoKGL, bomNoKGR, bomNoKGU, bomNoKGD,bomNoCuoiL,bomNoCuoiR,bomNoCuoiU,bomNoCuoiD; // dùng để lưu trữa ảnh của bom
	private boolean isbomNoKGL, isbomNoKGR, isbomNoKGU, isbomNoKGD, isbomNoCuoiL, isbomNoCuoiR, isbomNoCuoiU, isbomNoCuoiD; // dùng để kiểm soát xem đoạn bom có được hiện hay không
	
	public bom(int x, int y) {
		setBounds(x, y, 32, 32);
		isBomHien = true;
		isBomNo = false;
	}

	
	public void loadImage() {
		bomHien = new ImageIcon("res/sprites/bomb.png").getImage();
		bomNoTT = new ImageIcon("res/sprites/bomb_exploded2.png").getImage();
		bomNoKGL = new ImageIcon("res/sprites/explosion_horizontal2.png").getImage();
		bomNoKGR = new ImageIcon("res/sprites/explosion_horizontal2.png").getImage();
		bomNoKGU = new ImageIcon("res/sprites/explosion_vertical2.png").getImage();
		bomNoKGD = new ImageIcon("res/sprites/explosion_vertical2.png").getImage();
		bomNoCuoiL = new ImageIcon("res/sprites/explosion_horizontal_left_last2.png").getImage();
		bomNoCuoiR = new ImageIcon("res/sprites/explosion_horizontal_right_last2.png").getImage();
		bomNoCuoiU = new ImageIcon("res/sprites/explosion_vertical_top_last2.png").getImage();
		bomNoCuoiD = new ImageIcon("res/sprites/explosion_vertical_down_last2.png").getImage();
	}
	public void render(Graphics g) {
		loadImage();
		
	
		
		Graphics2D g2d = (Graphics2D) g;
		if(isBomHien) {	
			g2d.drawImage(bomHien, x, y, 32, 32, null);
		}
		
		if(isBomNo)	{
			isbomNoKGL   = true;
			isbomNoKGR   = true;
			isbomNoKGU   = true;
			isbomNoKGD   = true;
			isbomNoCuoiL = true;
			isbomNoCuoiR = true;
			isbomNoCuoiU = true;
			isbomNoCuoiD = true;
			Rectangle boundKGL= new Rectangle(x-32*1, y, 32, 32);
			Rectangle boundKGR= new Rectangle(x+32*1, y, 32, 32);
			Rectangle boundKGU= new Rectangle(x, y-32*1, 32, 32);
			Rectangle boundKGD= new Rectangle(x, y+32*1, 32, 32);
			Rectangle boundCuoiL= new Rectangle(x-32*2, y, 32, 32);
			Rectangle boundCuoiR= new Rectangle(x+32*2, y, 32, 32);
			Rectangle boundCuoiU= new Rectangle(x, y-32*2, 32, 32);
			Rectangle boundCuoiD= new Rectangle(x, y+32*2, 32, 32);
			GameLevel level = PacmanGame.level;
			g2d.drawImage(bomNoTT, x, y, 32, 32, null);
			for(int a = 0; a < level.tiles.length; a++) {
				for(int b = 0; b < level.tiles[0].length;b++) {
					if(level.tiles[a][b] !=null) {
						if(boundKGL.intersects(level.tiles[a][b]))   isbomNoKGL   = false;			
						if(boundCuoiL.intersects(level.tiles[a][b])) isbomNoCuoiL = false;
						if(boundKGR.intersects(level.tiles[a][b]))   isbomNoKGR   = false;
						if(boundCuoiR.intersects(level.tiles[a][b])) isbomNoCuoiR = false;
						if(boundKGU.intersects(level.tiles[a][b])) 	 isbomNoKGU   = false;
						if(boundCuoiU.intersects(level.tiles[a][b])) isbomNoCuoiU = false;
						if(boundKGD.intersects(level.tiles[a][b])) 	 isbomNoKGD   = false;	
						if(boundCuoiD.intersects(level.tiles[a][b])) isbomNoCuoiD = false;
					}
					}
				}
			if(isbomNoKGL)  g2d.drawImage(bomNoKGL, x-32*1, y, 32, 32, null); else isbomNoCuoiL=false;
			if(isbomNoKGR)  g2d.drawImage(bomNoKGR, x+32*1, y, 32, 32, null); else isbomNoCuoiR=false;
			if(isbomNoKGU)  g2d.drawImage(bomNoKGU, x, y-32*1, 32, 32, null); else isbomNoCuoiU=false;
			if(isbomNoKGD)  g2d.drawImage(bomNoKGD, x, y+32*1, 32, 32, null); else isbomNoCuoiD=false;
			if(isbomNoCuoiL)g2d.drawImage(bomNoCuoiL, x-32*2, y, 32, 32, null);
			if(isbomNoCuoiR)g2d.drawImage(bomNoCuoiR, x+32*2, y, 32, 32, null);
			if(isbomNoCuoiU)g2d.drawImage(bomNoCuoiU, x, y-32*2, 32, 32, null);
			if(isbomNoCuoiD)g2d.drawImage(bomNoCuoiD, x, y+32*2, 32, 32, null);
		}
							
	
}
	
	public void setBomHien(boolean isBomHien) {
		this.isBomHien = isBomHien;
	}
	public void setBomNo(boolean isBomNo) {
		this.isBomNo = isBomNo;
	}
	
	}
