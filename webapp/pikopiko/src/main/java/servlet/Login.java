package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Account;
import model.ConstLogic;
import model.LoginLogic;
import model.User;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static int failureNum = 0;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
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
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String pass = request.getParameter("pass");
		User user = new User(name, pass);
		LoginLogic loginLogic = new LoginLogic();
		Account account = loginLogic.execute(user);
		ConstLogic constLogic = new ConstLogic();
		int count = constLogic.getTitleCount(account);
		ArrayList<String> titleList = constLogic.getTitle(account);		

		if(account != null) {
			HttpSession session = request.getSession();
			session.setAttribute("count", count);
			session.setAttribute("titleList", titleList);
			session.setAttribute("account", account);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/home.jsp");
			dispatcher.forward(request, response);
		}else {
//			HttpSession session = request.getSession();
//			session.setAttribute("failureNum", failureNum);	
//			RequestDispatcher dispatcher = request.getRequestDispatcher("./");
//			dispatcher.forward(request, response);
//			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/index.jsp");
			response.sendRedirect("/pikopiko/jsp/index.jsp");
		}		
	}

}
