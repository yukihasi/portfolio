package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Account;
import model.MainThread;
import model.NoiseThread;
import model.RecordingThread;

/**
 * Servlet implementation class Piano
 */
@WebServlet("/Recording")
public class Recording extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static int[] count = new int[30];
	private static int startTime,endTime = 0;
	private static ArrayList<Integer> orderKeysArrayList = new ArrayList<Integer>();
	private static ArrayList<Integer> orderTimesArrayList = new ArrayList<Integer>();
	private static int MODECHANGE;



    /**
     * @see HttpServlet#HttpServlet()
     */
    public Recording() {
        super();
        // TODO Auto-generated constructor stub
    }
//    public void destroy() {
//
//    	super.destroy();
//    }
//    public void init(ServletConfig config)throws ServletException {
//    	super.init(config);
//    	Main mainThread = new Main();
//    	
//    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String intervalTime;
			String time;
			String sampleRate;
			String modeChange;
			int TIME;
			int INTERVALTIME;
			float SAMPLERATE;
			int KEYNUM = 0;
			request.setCharacterEncoding("UTF-8");
			String recordFlag = request.getParameter("recordFlag");
			String noiseFlag = request.getParameter("noiseFlag");
			boolean RECORDFLAG = Boolean.parseBoolean(recordFlag);
			boolean NOISEFLAG = Boolean.parseBoolean(noiseFlag);
			if(RECORDFLAG == true) {
				modeChange = request.getParameter("modeChange");
				MODECHANGE = Integer.parseInt(modeChange);

	//			ServletContext application = this.getServletContext();
	//			Main mainThread = (Main) application.getAttribute("mainThread");
				String flag = request.getParameter("flag");
				String skeyNum = request.getParameter("skeyNum");

	//			String startTime = request.getParameter("startTime");
	//			String endTime = request.getParameter("endTime");

				boolean FLAG = Boolean.parseBoolean(flag);
				KEYNUM = Integer.parseInt(skeyNum);
				if(FLAG == true) {
					count[KEYNUM] = count[KEYNUM] + 1;
					startTime = (int)System.currentTimeMillis();
					MainThread.main(KEYNUM,MODECHANGE);
				}else if(FLAG == false) {
					MainThread.stop(KEYNUM,MODECHANGE);
					endTime = (int)System.currentTimeMillis();
					TIME = endTime-startTime;
					System.out.println(TIME);
					orderKeysArrayList.add(KEYNUM);
					orderTimesArrayList.add(TIME);
//					RecordingThread.set(count[KEYNUM],MODECHANGE);
				}
			}else if(RECORDFLAG == false){			
				if(NOISEFLAG == true) {
					intervalTime = request.getParameter("intervalTime");
					time = request.getParameter("time");
					sampleRate = request.getParameter("sampleRate");
					TIME = Integer.parseInt(time);
					INTERVALTIME = Integer.parseInt(intervalTime);
					SAMPLERATE = Float.parseFloat(sampleRate);
					NoiseThread.set(INTERVALTIME,TIME,SAMPLERATE);
				}
				HttpSession session = request.getSession();
				Account account = (Account)session.getAttribute("account");
				String TITLE = request.getParameter("musicTitle");
				RecordingThread.afterSet(orderKeysArrayList, orderTimesArrayList,MODECHANGE);
				RecordingThread.main(account,TITLE);
				RecordingThread.stop();
				//前回の続きから録音したい場合はArrayListの初期化は不要
				orderKeysArrayList = new ArrayList<Integer>();
				orderTimesArrayList = new ArrayList<Integer>();				
				count = new int[30];
				}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

		}
//		PianoLogic pianoLogic = new PianoLogic(KEYNUM);
//		if(FLAG) {
//			pianoLogic.start();
//		}else {
//			pianoLogic.interrupt();			
//		}
//		double hz = pianoLogic.getFREQ(KEYNUM);
//		byte[] buf =  pianoLogic.getSound(hz, 10000,0.8);
//		SourceDataLine sdl = pianoLogic.sound(buf);
//		try {
//			byte[] buf =  pianoLogic.getSound(hz,1000,0.8);
//			Clip clip = pianoLogic.createClip(buf);
//			clip.stop();
//			clip.flush();
//			clip.close();
//			sdl.stop();
//			sdl.close();
//			while (FLAG) {
//			    sdl.write(buf, 0, buf.length);
//			}
//		} catch (LineUnavailableException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		}
//		HttpSession session = request.getSession();
//		session.setAttribute("loginUser", user);
//		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/home.jsp");
//		dispatcher.forward(request, response);
// catch (InterruptedException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		}

	}

}
