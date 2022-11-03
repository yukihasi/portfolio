package model;

import java.util.Random;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class NoiseSound extends SoundParam{
	private Clip[] clips = new Clip[30];
	private Clip clip;
	private byte[][] bufs = new byte[30][];
	private byte[] buf;

//	 public void createBufs(int msecs, double vol)
//				throws LineUnavailableException {
//				for(int i = 0; i<super.getFREQS().length; i++) {
//					bufs[i] = new byte[(int)super.getSample_rate() * msecs / 1000];
//					for (int j=0; j<bufs[i].length; j++) {
//						double t= j / super.getSample_rate();
//	//					double angle = i / (SAMPLE_RATE / hz) * 2.0 * Math.PI;
//						double provisionalAngle = ((t * super.getFREQ(i) * 2) % 2) * 2 - 1; //矩形波
//						double angles = provisionalAngle;
//						bufs[i][j] = (byte)(Math.sin(angles) * 127.0 * vol);
//					}
//				}
//
//				// shape the front and back 10ms of the wave form
//				for(int i = 0; i<super.getFREQS().length; i++) {
//					for (int j=0; j < super.getSample_rate() / 100.0 && j < bufs[i].length / 2; j++) {
//						bufs[i][j] = (byte)(bufs[i][j] * j / (super.getSample_rate() / 100.0));
//						bufs[i][bufs[i].length-1-j] =
//						(byte)(bufs[i][bufs[i].length-1-j] * j / (super.getSample_rate() / 100.0));
//					}
//				}
//		}
//	 public void createBufs()
//				throws LineUnavailableException {
//				for(int i = 0; i<super.getFREQS().length; i++) {
//					bufs[i] = new byte[(int)super.getSample_rate() * super.getMsec() / 1000];
//					for (int j=0; j<bufs[i].length; j++) {
//						double t= j / super.getSample_rate();
//	//					double angle = i / (SAMPLE_RATE / hz) * 2.0 * Math.PI;
//						double provisionalAngle = ((t * super.getFREQ(i) * 2) % 2) * 2 - 1; //矩形波
//						double angles = provisionalAngle;
//						bufs[i][j] = (byte)(Math.sin(angles) * 127.0 * super.getVol());
//					}
//				}
//
//				// shape the front and back 10ms of the wave form
//				for(int i = 0; i<super.getFREQS().length; i++) {
//					for (int j=0; j < super.getSample_rate() / 100.0 && j < bufs[i].length / 2; j++) {
//						bufs[i][j] = (byte)(bufs[i][j] * j / (super.getSample_rate() / 100.0));
//						bufs[i][bufs[i].length-1-j] =
//						(byte)(bufs[i][bufs[i].length-1-j] * j / (super.getSample_rate() / 100.0));
//					}
//				}
//		}
	 
	 public void createBuf(int msecs, double vol)
				throws LineUnavailableException {
					buf = new byte[(int)super.getSample_rate() * msecs / 1000];
					for (int i=0; i<buf.length; i++) {
						Random rand = new Random();
						double tmp2 = -1.0*rand.nextDouble();
						double angle = tmp2;
						buf[i] = (byte)(Math.sin(angle) * 127.0 * vol);
					}

				// shape the front and back 10ms of the wave form
					for (int i=0; i < super.getSample_rate() / 100.0 && i < buf.length / 2; i++) {
						buf[i] = (byte)(buf[i] * i / (super.getSample_rate() / 100.0));
						buf[buf.length-1-i] =
						(byte)(buf[buf.length-1-i] * i / (super.getSample_rate() / 100.0));
					}
		}

	 public void createBuf()
				throws LineUnavailableException {
					buf = new byte[(int)super.getSample_rate() * super.getMsec() / 1000];
					for (int i=0; i<buf.length; i++) {
						Random rand = new Random();
						double tmp2 = -1.0*rand.nextDouble();
						double angle = tmp2;
						buf[i] = (byte)(Math.sin(angle) * 127.0 * super.getVol());
					}

				// shape the front and back 10ms of the wave form
					for (int i=0; i < super.getSample_rate() / 100.0 && i < buf.length / 2; i++) {
						buf[i] = (byte)(buf[i] * i / (super.getSample_rate() / 100.0));
						buf[buf.length-1-i] =
						(byte)(buf[buf.length-1-i] * i / (super.getSample_rate() / 100.0));
					}
		}
 
	 
//	 public void createClips() {
//			AudioFormat af = new AudioFormat(super.getSample_rate(),8,1,true,false);
//			for(int i = 0; i < super.getFREQS().length; i++) {
//				try {
//					clips[i] = AudioSystem.getClip();
//					clips[i].open(af, this.bufs[i], 0, this.bufs[i].length);
//					} catch (LineUnavailableException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//			}
//
//	}
	public void createClip() {
		AudioFormat af = new AudioFormat(super.getSample_rate(),8,1,true,false);
			try {
				clip = AudioSystem.getClip();
				clip.open(af, this.buf, 0, this.buf.length);
				} catch (LineUnavailableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}

	public Clip getClip() {
		return clip;
	}
	public void setClip(Clip clip) {
		this.clip = clip;
	}
	public Clip[] getClips() {
		return clips;
	}
	public void setClips(Clip[] clips) {
		this.clips = clips;
	}

	
	public byte[][] getBufs() {
		return bufs;
	}
	public void setBuf(byte[][] bufs) {
		this.bufs = bufs;
	}
	 


}
