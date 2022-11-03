package model;

public class NoiseThread {
	static NoiseLogic noiseLogic;
	static int intervalTime;
	static int time;
	static float sampleRate;

	public static void stop() {
		noiseLogic.interrupt();
	}
	
	public static void main(){
		noiseLogic = new NoiseLogic(time,sampleRate);
		noiseLogic.setInterval(intervalTime);
		noiseLogic.setTime(time);
		noiseLogic.start();
	}

	public static void set(int INTERVALTIME,int TIME, float SAMPLERATE) {
		// TODO 自動生成されたメソッド・スタブ
		intervalTime = INTERVALTIME;
		time = TIME;
		sampleRate = SAMPLERATE;
	}
	
}