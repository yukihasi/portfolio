package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.MainThread;

/**
 * Servlet implementation class Piano
 */
@WebServlet("/Piano")
public class Piano extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Piano() {
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
			request.setCharacterEncoding("UTF-8");
//			ServletContext application = this.getServletContext();
//			Main mainThread = (Main) application.getAttribute("mainThread");

			String skeyNum = request.getParameter("skeyNum");
			String flag = request.getParameter("flag");
			String modeChange = request.getParameter("modeChange");
//			String startTime = request.getParameter("startTime");
//			String endTime = request.getParameter("endTime");
			int KEYNUM = Integer.parseInt(skeyNum);
			boolean FLAG = Boolean.parseBoolean(flag);
			int MODECHANGE = Integer.parseInt(modeChange);
			if(FLAG ==true) {
				MainThread.main(KEYNUM,MODECHANGE);
			}else if(FLAG == false) {
				MainThread.stop(KEYNUM,MODECHANGE);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

		}

	}

}
