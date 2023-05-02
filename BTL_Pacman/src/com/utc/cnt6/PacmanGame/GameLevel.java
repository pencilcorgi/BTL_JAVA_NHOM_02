package com.utc.cnt6.PacmanGame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class GameLevel {
	public int Width;			// dùng để gán chiều rộng của map
	public int Height;			// dùng để gán chiều cao của map
	public Tile[][] tiles; 		// dùng để lưu trữ tọa độ của chướng ngại vật
	public List<Dots> dots;		// dùng để lưu trữ số lượng vật phẩm
	public List<Ghost> ghosts;	// dùng để lưu trữ số lượng boss
	public bom bomb;			// dùng để lưu trữ quả bom mà nhân vật tạo ra sau đó ném vào lớp bom để xử lý	
	
	public GameLevel(String Path) {
		dots = new ArrayList<>();
		ghosts = new ArrayList<>();
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(Path));
			this.Width = map.getWidth();
			this.Height = map.getHeight();
			int[] pixels = new int[this.Width*this.Height];
			tiles = new Tile[this.Width][this.Height];
			map.getRGB(0, 0, this.Width, this.Height, pixels, 0, this.Width);
			for(int a = 0; a < this.Width; a++) {
				for(int b = 0; b < this.Height; b++) {
					int val = pixels[a + (b * this.Width)];
					
					if(val == 0xFF000000) {
						//tile
						tiles[a][b] = new Tile(a*32,b*32);
					} else if(val == 0xFF0000FF) {
						//Pacman	
						PacmanGame.pacman.x = a*32;
						PacmanGame.pacman.y = b*32;
					} else if(val == 0xFFFF0000) {
						//ghost
						ghosts.add(new Ghost(a*32,b*32));
						dots.add(new Dots(a*32,b*32));
					} else if (val == 0xFFFFFFFF) {
						//Dots
						dots.add(new Dots(a*32,b*32));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void tick() {
		for(int i = 0; i < ghosts.size();i++) {
			if(!ghosts.get(i).isIsghost()) {
				ghosts.remove(i);
			}
		}
		for(int i = 0; i < ghosts.size();i++) {
			ghosts.get(i).tick();
		}
	}
	
	public void render(Graphics g) {
		for(int x = 0; x < this.Width; x++) {
			for(int y = 0; y < this.Height; y++) {
				if(tiles[x][y] != null) {
					tiles[x][y].render(g);
				}
			}
		}
		for(int i = 0; i < dots.size();i++) {
			dots.get(i).render(g);
		}
		
		for(int i = 0; i < ghosts.size();i++) {
			ghosts.get(i).render(g);
		}
	}
	public void setBomXY(int x, int y) {
		for(int i = 0; i < ghosts.size();i++) {
			ghosts.get(i).setBomXY(x, y);
		}
	}
	public void setisBomNo(boolean isbomno) {
		for(int i = 0; i < ghosts.size();i++) {
			ghosts.get(i).setIsbomNo(isbomno);
		}
	}

}
