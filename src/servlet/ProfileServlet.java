package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import client.User;
import menu.LoginMenu;
import server.MySQLDriver;

/**
 * Servlet implementation class ProfileServlet
 */
@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User curUser = (User)session.getAttribute("user");
		StringBuffer sb = new StringBuffer();
		String line = null;
		String message = "";
		String username = "";
		
		try{
			BufferedReader br = request.getReader();
			while((line=br.readLine())!=null)
			{
				sb.append(line);
			}
		}
		catch(IOException ioe){
			System.out.println("Reqeust Error");
		}
		
		try{
			JsonElement jelement = new JsonParser().parse(sb.toString());
			JsonObject jobject = jelement.getAsJsonObject();
			message = jobject.get("message").getAsString();
			username = jobject.get("username").getAsString();
		}
		catch(Exception e){
			System.out.println("Error");
		}
		MySQLDriver driver = new MySQLDriver();
		driver.connect();
		Gson gson = new Gson();
		response.setHeader("content-type","application/json");
		response.setCharacterEncoding("UTF-8");
		if(message.equals("loadProfile"))
		{
			User user = driver.getUser(username);
			String jsonString = gson.toJson(gson.toJsonTree(user));
			response.getWriter().write(jsonString);
		}
		
		if(message.equals("follow"))
		{
			User user = driver.getUser(username);
			curUser.addFollowing(username);
			user.addFollower(curUser.getUsername());
			driver.addFollowingPair(username, curUser.getUsername());
			JsonObject wrapper = new JsonObject();
			JsonElement curUserJson = gson.toJsonTree(curUser);
			wrapper.add("curUser", curUserJson);
			JsonElement userJson = gson.toJsonTree(user);
			wrapper.add("user",userJson);
			String jsonString = gson.toJson(wrapper);
			response.getWriter().write(jsonString);
		}
		if(message.equals("unfollow"))
		{
			User user = driver.getUser(username);
			user.removeFollower(curUser.getUsername());
			curUser.removeFollowing(username);
			driver.removeFollowingPair(username, curUser.getUsername());
			JsonObject wrapper = new JsonObject();
			JsonElement curUserJson = gson.toJsonTree(curUser);
			wrapper.add("curUser", curUserJson);
			JsonElement userJson = gson.toJsonTree(user);
			wrapper.add("user",userJson);
			String jsonString = gson.toJson(wrapper);
			response.getWriter().write(jsonString);
		}
		
	}


}
