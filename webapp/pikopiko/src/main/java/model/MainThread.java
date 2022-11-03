package model;

public class MainThread {
	static SquarePianoLogic[] squarePianoLogics = new SquarePianoLogic[30];
	static SinePianoLogic[] sinePianoLogics = new SinePianoLogic[30];
//	static PianoLogic pianoLogic = new PianoLogic();
	static int count;
	public static void stop(int keynum,int modeChange) {
		switch (modeChange) {
		case 1:{
			squarePianoLogics[keynum].interrupt();			
			break;
		}
		case 2:{
			sinePianoLogics[keynum].interrupt();			
			break;
		}
		default:
//			squarePianoLogics[keynum].interrupt();			
			throw new IllegalArgumentException("Unexpected value: " + modeChange);
		}
	}

	public static void main(int keynum,int modeChange){
		switch (modeChange) {
		case 1: {
			squarePianoLogics[keynum] = new SquarePianoLogic();
			try {
				squarePianoLogics[keynum].setKeynum(keynum);
				squarePianoLogics[keynum].start();
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			break;
		}
		case 2:{
			sinePianoLogics[keynum] = new SinePianoLogic();
			try {
				sinePianoLogics[keynum].setKeynum(keynum);
				sinePianoLogics[keynum].start();
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			break;			
		}
		default:
		throw new IllegalArgumentException("Unexpected value: " + modeChange);
		}
	}
}
//			
//		}
		


