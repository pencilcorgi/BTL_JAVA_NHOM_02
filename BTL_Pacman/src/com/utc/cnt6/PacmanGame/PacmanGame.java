package com.utc.cnt6.PacmanGame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class PacmanGame extends Canvas implements Runnable, KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean Running = false;
	private static final int SCREEN_WIDTH = 832;
	private static final int SCREEN_HEIGHT = 512;
	private static final int SCREEN_SIZE = SCREEN_WIDTH * SCREEN_HEIGHT;
	private static final String TITLE = "Pacman";
	
	private Thread thread;
	public static Pacman pacman;
	public static GameLevel level;
	public static bom bomb;
	
	private static final String START_TITLE = "PRESS ENTER TO START THE GAME";
	private static final String CONTINUE_TITLE = "PRESS SPACE TO CONTINUE THE GAME";
	private static final int PAUSE = 0, START = 1,CONTINUE = 2;
	private static int STATE = -1; //Trạng thái
	
	public boolean isEnter = false,isSpace = false;
	private int time = 0;
	private int targetFrame = 40;
	private boolean showStartTitle = true;
	
	
public static int getSTATE() {
		return STATE;
	}

	public static void setSTATE(int sTATE) {
		STATE = sTATE;
	}

	public static int getPause() {
		return PAUSE;
	}

	public static int getStart() {
		return START;
	}

public PacmanGame() {
		Dimension d = new Dimension(PacmanGame.SCREEN_WIDTH,PacmanGame.SCREEN_HEIGHT);
		setPreferredSize(d);
		setMaximumSize(d);
		setMinimumSize(d);
		
		addKeyListener(this);
		
		STATE = PAUSE;
		pacman = new Pacman(PacmanGame.SCREEN_WIDTH/2,PacmanGame.SCREEN_HEIGHT/2);
		level = new GameLevel("/Map/Map.png");
	}
	
	public static int getScreenSize() {
		return SCREEN_SIZE;
	}	

	//đồng bộ hóa tránh xảy ra xung đột
	public synchronized void start() {
		if(Running) {
			return;
		}
		Running = true;
		thread = new Thread(this);
		thread.start();  // khi gọi đến hàm này sẽ chuyển đến game loop
	}
	
	public synchronized void stop() {
		if(!Running) {
			return;
		}
		Running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void tick() {
		if(STATE == START) {
			pacman.tick();
			level.tick();
		} else if(STATE == PAUSE || STATE == CONTINUE) {
			time++;
			if(time == targetFrame) {
				time = 0;
				if(showStartTitle) {
					showStartTitle = false;
				} else {
					showStartTitle = true;
				}
			}
		}
		if(isEnter) {
			isEnter = false;
			pacman = new Pacman(PacmanGame.SCREEN_WIDTH/2,PacmanGame.SCREEN_HEIGHT/2);
			level = new GameLevel("/Map/Map.png");
		}
	}
	
	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics gs = bs.getDrawGraphics();
		gs.setColor(Color.black);
		gs.fillRect(0,0, PacmanGame.SCREEN_WIDTH, PacmanGame.SCREEN_HEIGHT);
		if(STATE == START) {
			pacman.render(gs);
			level.render(gs);
			pacman.drawScore(gs);
		} else if(STATE == PAUSE) {
			int menuHeight = 150;
			int menuWidth = 500;
			int menuX = PacmanGame.SCREEN_WIDTH / 2 - menuWidth / 2;
			int menuY = PacmanGame.SCREEN_HEIGHT / 2 - menuHeight / 2;
			gs.setColor(Color.white);
			gs.setFont(new Font(Font.DIALOG, Font.BOLD, 23));
			if(showStartTitle) {
				gs.drawString(START_TITLE, menuX + 40, menuY + 85);
			}
		} else if(STATE == CONTINUE) {
			int menuHeight = 150;
			int menuWidth = 500;
			int menuX = PacmanGame.SCREEN_WIDTH / 2 - menuWidth / 2;
			int menuY = PacmanGame.SCREEN_HEIGHT / 2 - menuHeight / 2;
			gs.setColor(Color.white);
			gs.setFont(new Font(Font.DIALOG, Font.BOLD, 23));
			if(showStartTitle) {
				gs.drawString(CONTINUE_TITLE, menuX + 40, menuY + 85);
			}
		}
		gs.dispose();
		bs.show();
	}
	
	//gameloop
	@Override
	public void run() {
		requestFocus(); //không cần ấn vào cửa sổ window vẫn có thể chạy game	
		int fps = 0;
		double timer = System.currentTimeMillis();
		long lastTime = System.nanoTime();
		double targetTick = 60.0;
		double delta = 0;
		double ns = 1000000000/ targetTick;
		while(Running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			while(delta >= 1) {
				tick();
				render();
				fps++;
				delta--; // giữ cho game luôn chạy trong khoảng 60fps
			}
			if(System.currentTimeMillis() - timer >= 1000) {
				fps = 0;
				timer += 1000;
			}
			
		}
		
		stop(); // dừng lại khi kết thúc trò chơi
	}
	
	public static void main(String[] args) throws InterruptedException {
		PacmanGame pacmangame = new PacmanGame();
		JFrame Frame = new JFrame();
		Frame.setTitle(pacmangame.TITLE);
		Frame.add(pacmangame);
		Frame.setResizable(false);   //ngăn ngừa người khác thay đổi kích thước của frame
		Frame.pack();                //trình quản lý bố cục khung
		Frame.setLocationRelativeTo(null);
		Frame.setVisible(true);
		Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pacmangame.start();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(STATE == START) {
			if(e.getKeyCode()== KeyEvent.VK_RIGHT) {
				pacman.RIGHT = true;
			}
			if(e.getKeyCode()== KeyEvent.VK_LEFT) {
				pacman.LEFT = true;
			}
			if(e.getKeyCode()== KeyEvent.VK_UP) {
				pacman.UP = true;
			}
			if(e.getKeyCode()== KeyEvent.VK_DOWN) {
				pacman.DOWN = true;
			}
			if(e.getKeyCode()== KeyEvent.VK_SPACE) {
				STATE = CONTINUE;
			}
			if(e.getKeyCode() == KeyEvent.VK_A) {
				pacman.A = true;
			}
		} else if(STATE == PAUSE) {
			if(e.getKeyCode()== KeyEvent.VK_ENTER) {
				STATE = START;
				isEnter = true;
			}
		} else if(STATE == CONTINUE) {
			if(e.getKeyCode()== KeyEvent.VK_SPACE) {
				STATE = START;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()== KeyEvent.VK_RIGHT) {
			pacman.RIGHT = false;
		}
		if(e.getKeyCode()== KeyEvent.VK_LEFT) {
			pacman.LEFT = false;
		}
		if(e.getKeyCode()== KeyEvent.VK_UP) {
			pacman.UP = false;
		}
		if(e.getKeyCode()== KeyEvent.VK_DOWN) {
			pacman.DOWN = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_A) {
			pacman.A = false;
		}
	}
}
