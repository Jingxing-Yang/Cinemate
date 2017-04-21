package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import client.*;
import menu.*;
import server.MySQLDriver;

/**
 * Servlet implementation class FeedServlet
 */
@WebServlet("/FeedServlet")
public class FeedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FeedServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("Feed Request Received");
		HttpSession session = request.getSession();
		MySQLDriver driver = new MySQLDriver();
		driver.connect();
		User curUser = (User)session.getAttribute("user");
		System.out.println(curUser.getUsername());
		ArrayList<FeedEvent> feedEvent = driver.getFeed(curUser);
		response.setHeader("content-type","application/json");
		response.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		String jsonString = gson.toJson(feedEvent);
		response.getWriter().write(jsonString);
	}
}
