package model;

import java.util.ArrayList;

public class RecordingThread {
	static RecordingLogic recordingLogic;
	//鍵盤の記録
	static ArrayList<Integer> orderKeys = new ArrayList<Integer>();
	static ArrayList<Integer> orderTimes = new ArrayList<Integer>();
	static int doCount;
	//ノイズの記録
	static int intervalTime;
	static int time;
	static float sampleRate;

	//モードの記録
	static int modeChangeNum;

//	static int[][] list;
//	static long[][] oldSoundRecord = new long[30][];
//	static long[][] newSoundRecord = new long[30][];

	public RecordingThread() {
		// TODO 自動生成されたコンストラクター・スタブ
	}
	public static void reset() {
	}
	//配列セット
	public static void afterSet(ArrayList<Integer> orderKeysArrayList,ArrayList<Integer> orderTimesArrayList,int musicMode) {
		orderKeys.addAll(orderKeysArrayList);
		orderTimes.addAll(orderTimesArrayList);		
		modeChangeNum = musicMode;
	}
	
//	public static void set(int count,int modeChange) {
//		doCount = count;
//		modeChangeNum = modeChange;	
//	}
	public static void stop() {
		System.out.println("recordingStop");
		orderKeys = new ArrayList<Integer>();
		orderTimes = new ArrayList<Integer>();
	}

	public static void main(Account account,String title){
		recordingLogic = new RecordingLogic(orderKeys,orderTimes);
		byte[] buf = null;
//		if(flag == true) {
//				PianoLogic.setKeynum(keynum);
		try {
			recordingLogic.setMode(modeChangeNum);
			recordingLogic.createRecord();
			recordingLogic.execute(account,title);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
	
	public static void  startRun() {
		recordingLogic.start();		
	}
	public static void  stopRun() {
		recordingLogic.interrupt();
		System.out.println("playBackStopped");

	}

}
//			
//		}
		


