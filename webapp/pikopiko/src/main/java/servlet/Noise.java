package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.NoiseThread;

/**
 * Servlet implementation class Noise
 */
@WebServlet("/Noise")
public class Noise extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Noise() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		try {
			request.setCharacterEncoding("UTF-8");
			String noiseFlag = request.getParameter("noiseFlag");
			boolean NOISEFLAG = Boolean.parseBoolean(noiseFlag);
			if(NOISEFLAG == true) {
				String intervalTime = request.getParameter("intervalTime");
				String time = request.getParameter("time");
				String sampleRate = request.getParameter("sampleRate");
				int TIME = Integer.parseInt(time);
				int INTERVALTIME = Integer.parseInt(intervalTime);
				float SAMPLERATE = Float.parseFloat(sampleRate);
				NoiseThread.set(INTERVALTIME,TIME,SAMPLERATE);
				NoiseThread.main();
			}else if(NOISEFLAG == false) {
				NoiseThread.stop();
			}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
	
			}

	}

}
