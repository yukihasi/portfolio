package model;

public class SoundParam {
	private float sample_rate;
	private int msec;
	private double vol;
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
	public SoundParam() {
		// TODO 自動生成されたコンストラクター・スタブ
	}
	public float getSample_rate() {
		return sample_rate;
	}
	public void setSample_rate(float sample_rate) {
		this.sample_rate = sample_rate;
	}
	public int getMsec() {
		return msec;
	}
	public void setMsec(int msec) {
		this.msec = msec;
	}
	public double[] getFREQS() {
		return FREQ;
	}
	public double getFREQ(int num) {
		return FREQ[num];
	}

	public double getVol() {
		return vol;
	}
	public void setVol(double vol) {
		this.vol = vol;
	}



}
