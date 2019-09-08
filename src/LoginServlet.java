
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.SqlConnection;
import users.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
			try (Connection conn = SqlConnection.getConnection()) {
				System.out.println("LoginServlet");
				String login = request.getParameter("login");
				String password = request.getParameter("password");
				User user = SqlConnection.SelectUser(login, password);
				String path = "/home.jsp";
				if (user != null) {
					path = "/home.jsp";
				} else {
					path = "/error.html";
				}
				//response.addHeader("user", user.getLogin());
					
				response.setHeader("user", user.getLogin());
				ServletContext servletContext = getServletContext();
				RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
				requestDispatcher.forward(request, response);
			}
		} catch (Exception ex) {
			System.out.println("Connection failed...");

			System.out.println(ex);
		}
	}

}
