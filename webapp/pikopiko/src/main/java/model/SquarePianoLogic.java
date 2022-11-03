package model;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class SquarePianoLogic extends Thread {
	private int count;
	private static float SAMPLE_RATE = 44100f;
	private int keyNum;
	private double[] angles = new double[30];
	private double[] hz = new double[30];
	private byte[] buf;
//	private Clip[] clip = new Clip[30];
	private Clip clip;
	private int modeChange = 0;
	private double angle = 0;
	private SquareSound squareSound = new SquareSound();

	private final double[] FREQ  = {220.0,
        233.081880,
        246.941650,
		261.626,
		277.183,
		293.665,
		311.127,
		329.628,
		349.228,
		369.994,
		391.995,
		415.305,
		440.000,
		466.164,
		493.883,
		523.251,
		554.365,
		587.330,
		622.254,
		659.255,
		698.456,
		739.989,
		783.991,
		830.609,
		880.000,
		932.328,
		987.767,
		1046.502,
		1108.731,
		1174.659
		};

	public void setKeynum(int keyNum) {
		 try {
			squareSound.createBuf(keyNum);
		} catch (LineUnavailableException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		this.keyNum = keyNum;
	}

	public SquarePianoLogic() {
		// TODO 自動生成されたコンストラクター・スタブ
		 squareSound.setMsec(1000);
		 squareSound.setSample_rate(44100f);
		 squareSound.setVol(0.8);
	}

	 public void run() {
		 squareSound.createClip();
		 clip = squareSound.getClip();
		 while (!this.isInterrupted()) {
		   clip.loop(Clip.LOOP_CONTINUOUSLY);
           System.out.println("is running");
        }
		 clip.stop();
		 clip.setFramePosition(0);
        System.out.println("Stopped");
	}
	 
}
