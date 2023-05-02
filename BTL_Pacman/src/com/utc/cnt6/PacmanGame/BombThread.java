package com.utc.cnt6.PacmanGame;

public class BombThread extends Thread{
	private boolean isBomHien, isBomNo,isXY ;
	
	public BombThread() {
		isBomHien = true;
		isBomNo = false;
		isXY=false;
	}
	public void run() {
			try {
				
				Thread.sleep(4000);
				
				isBomNo = true;
				isBomHien = false;
				
				Thread.sleep(400);
				isBomNo = false;
				isXY=true;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	public boolean isXY() {
		return isXY;
	}
	public void setXY(boolean isXY) {
		this.isXY = isXY;
	}
	public boolean isBomHien() {
		return isBomHien;
	}
	public boolean isBomNo() {
		return isBomNo;
	}
	}
	
