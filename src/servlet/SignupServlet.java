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
import menu.MainMenu;
import server.MySQLDriver;
import util.StringChecker;

/**
 * Servlet implementation class SignupServlet
 */
@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignupServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		StringBuffer sb = new StringBuffer();
		String line = "";
		String fullname = "";
		String username = "";
		String password = "";
		String image = "";
		//System.out.println("message received");
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
			fullname = jobject.get("fullname").getAsString();
			username = jobject.get("username").getAsString();
			password = jobject.get("password").getAsString();
			image = jobject.get("image").getAsString();
			}
		catch(Exception e)
		{
			System.out.println("Error");
		}
		
		if(fullname!=null){
			fullname.trim();
		}
		if(username!=null){
			username.trim();
		}
		if(password!=null){
			password.trim();
		}
		if(image!=null){
			image.trim();
		}
		
		
		String signupError = "";
		MySQLDriver driver = new MySQLDriver();
		driver.connect();
		response.setHeader("content-type","application/json");
		response.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		JsonObject wrapper = new JsonObject();

		StringChecker sc = new StringChecker();
		if(sc.isBlank(fullname)){
			signupError = "input your fullname";
			JsonElement errorgson = gson.toJsonTree(signupError);
			wrapper.add("error", errorgson);
			String gsonString = gson.toJson(wrapper);
			response.getWriter().write(gsonString);
		}
		else if(sc.isBlank(username)){
			signupError = "input your username";
			JsonElement errorgson = gson.toJsonTree(signupError);
			wrapper.add("error", errorgson);
			String gsonString = gson.toJson(wrapper);
			response.getWriter().write(gsonString);
		}
		else if(sc.isBlank(password)){
			signupError = "input your password";
			JsonElement errorgson = gson.toJsonTree(signupError);
			wrapper.add("error", errorgson);
			String gsonString = gson.toJson(wrapper);
			response.getWriter().write(gsonString);
		}
		else if(sc.isBlank(image)){
			signupError = "input your imageURL";
			JsonElement errorgson = gson.toJsonTree(signupError);
			wrapper.add("error", errorgson);
			String gsonString = gson.toJson(wrapper);
			response.getWriter().write(gsonString);
		}
		else if(driver.doesUserExist(username))
		{
			signupError = "username already exists";
			JsonElement errorgson = gson.toJsonTree(signupError);
			wrapper.add("error", errorgson);
			String gsonString = gson.toJson(wrapper);
			response.getWriter().write(gsonString);
		}
		else
		{
			String fname = fullname.substring(0, fullname.indexOf(' '));
			String lname = fullname.substring(fullname.indexOf(' ')+1);
			User user = new User(username,password,fname,lname,image);
			driver.addUser(username, password, fname, lname, image);
			//session.setAttribute("loginMenu",loginMenu);
			session.setAttribute("user",user);
			signupError = "None";
			JsonElement errorgson = gson.toJsonTree(signupError);
			wrapper.add("error", errorgson);
			JsonElement usergson = gson.toJsonTree(user);
			wrapper.add("currentUser", usergson);
			String gsonString = gson.toJson(wrapper);
			response.getWriter().write(gsonString);
		}
		driver.stop();
	}

}
