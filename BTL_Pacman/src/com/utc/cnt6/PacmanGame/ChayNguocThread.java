package com.utc.cnt6.PacmanGame;

public class ChayNguocThread extends Thread{
		private boolean ischaynguoc;	
		public ChayNguocThread() {
			// TODO Auto-generated constructor stub
			ischaynguoc=false;
			
		}
	public void run() {
		try {
			ischaynguoc=true;
			Thread.sleep(5000);
			ischaynguoc=false;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean isIschaynguoc() {
		return ischaynguoc;
	}
}
