package model;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class NoiseLogic extends Thread{
	private float SAMPLE_RATE = 22000f;
//	private Clip[] clip = new Clip[30];
	private Clip clip;

	private byte[] buf;
	private int interval;
	private int time;
	private NoiseSound noiseSound = new NoiseSound();
	
	public NoiseLogic(int time,float sampleRate) {
		// TODO 自動生成されたコンストラクター・スタブ
		noiseSound.setMsec(time);
		noiseSound.setSample_rate(sampleRate);
		noiseSound.setVol(0.8);
		try {
			noiseSound.createBuf();
		} catch (LineUnavailableException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	public void run() {
		noiseSound.createClip();
		clip = noiseSound.getClip();
		 while(!this.isInterrupted())  {
//            System.out.println(name + " is running");
	    	   try {
	    	    clip.start();					
	    	    sleep(time);
	    	    clip.stop();
	    	    sleep(interval);
	    	    clip.setFramePosition(0);
				} catch (InterruptedException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
					break;
				}
	            System.out.println("is running");
			   }
		 clip.stop();
	        System.out.println("Stopped");
	}
	public void setInterval(int intervalTime) {
		// TODO 自動生成されたメソッド・スタブ
		this.interval = intervalTime;
	}
	public void setTime(int time) {
		// TODO 自動生成されたメソッド・スタブ
		this.time = time;
		
	}
}
