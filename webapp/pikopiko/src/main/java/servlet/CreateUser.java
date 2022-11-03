package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CreateUserLogic;
import model.User;

/**
 * Servlet implementation class CreateUser
 */
@WebServlet("/CreateUser")
public class CreateUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateUser() {
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
		CreateUserLogic createUserLogic = new CreateUserLogic();
		boolean isCreate = createUserLogic.execute(user);
		
		if(isCreate) {
//			HttpSession session = request.getSession();
//			session.setAttribute("createUser", user);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/createdUser.jsp");
			dispatcher.forward(request, response);
		}else {
//			request.setAttribute("loginlogic", loginLogic);	
//			RequestDispatcher dispatcher = request.getRequestDispatcher("./");
//			dispatcher.forward(request, response);
			response.sendRedirect("/pikopiko");
		}		
	}

}
