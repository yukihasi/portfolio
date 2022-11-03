package model;

import java.util.ArrayList;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

import dao.AccountDAO;

public class RecordingLogic extends Thread {
	private ArrayList<Integer> orderKeys = new ArrayList<Integer>();
	private ArrayList<Integer> orderTimes = new ArrayList<Integer>();
	private ArrayList<Clip> clips = new ArrayList<Clip>();
	private SquareSound squareSound = new SquareSound();
	private SineSound sineSound = new SineSound();
	private boolean flag;

//	private Clip[] clip = new Clip[30];
//	private Clip[][] clip = new Clip[30][];
	private int modeChange = 0;
//	private static int msec = 10;
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





	//	byte[] buf = new byte[(int)SAMPLE_RATE * msec / 1000];
	public RecordingLogic(ArrayList<Integer> orderKeys,ArrayList<Integer> orderTimes) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.orderKeys.addAll(orderKeys);
		this.orderTimes.addAll(orderTimes);
		squareSound.setSample_rate(44100f);
		sineSound.setSample_rate(44100f);
	}
	
	public boolean execute(Account account,String title) {
		AccountDAO dao = new AccountDAO();
		Recorder recorder = dao.createRecord(account,orderKeys,orderTimes,title,modeChange);
		if (recorder == null) {
			flag = false;
			return flag;
		}else {
			flag = true;
			return flag;
		}

	}
	
	public void createRecord() {
		switch (modeChange) {
		case 1: {
			for(int i = 0; i<orderKeys.size(); i++) {
				try {
					squareSound.createBuf(orderTimes.get(i), 0.8, orderKeys.get(i));
				} catch (LineUnavailableException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
				squareSound.createClip();
				clips.add(squareSound.getClip());
				}
			
			break;
		}
		case 2:{
			for(int i = 0; i<orderKeys.size(); i++) {
				try {
					sineSound.createBuf(orderTimes.get(i), 0.8, orderKeys.get(i));
				} catch (LineUnavailableException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
				sineSound.createClip();
				clips.add(sineSound.getClip());
				}
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + modeChange);
		}
	}

	public void setMode(int modeChange) {
		  this.modeChange = modeChange;
	}
	
	 public void run() {
		 for(int i=0; i < clips.size(); i++) {
		 try {
			clips.get(i).start();
			Thread.sleep(this.orderTimes.get(i));
			clips.get(i).stop();
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		 }
	 }

	
}
	




	
	
	
