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

import util.*;
import client.*;
import menu.*;
import server.*;


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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		StringBuffer sb = new StringBuffer();
		String line = "";
		String username = "";
		String password = "";
		
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
		String data = sb.toString();
		
		try{
			JsonElement jelement = new JsonParser().parse(sb.toString());
			JsonObject jobject = jelement.getAsJsonObject();
			username = jobject.get("username").getAsString();
			password = jobject.get("password").getAsString();
			}
			catch(Exception e)
			{
				System.out.println("Error");
			}

		
		
		String loginError = "";
		MySQLDriver driver = new MySQLDriver();
		driver.connect();
		response.setHeader("content-type","application/json");
		response.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		JsonObject wrapper = new JsonObject();

		StringChecker sc = new StringChecker();
		//TODO:put warning above each text input
		if(sc.isBlank(username)||sc.isBlank(password))
		{
			loginError = "input your username/password";
			JsonElement errorgson = gson.toJsonTree(loginError);
			wrapper.add("error", errorgson);
			String gsonString = gson.toJson(wrapper);
			response.getWriter().write(gsonString);
		}
		else
		{
			User user = driver.getUser(username);
			if(user==null)
			{
				loginError = "Invalid username";
				JsonElement errorgson = gson.toJsonTree(loginError);
				wrapper.add("error", errorgson);
				String gsonString = gson.toJson(wrapper);
				response.getWriter().write(gsonString);
			}
			else if(!user.checkPassword(password))
			{
				loginError = "Invalid password";
				JsonElement errorgson = gson.toJsonTree(loginError);
				wrapper.add("error", errorgson);
				String gsonString = gson.toJson(wrapper);
				response.getWriter().write(gsonString);
			}
			else
			{
				//session.setAttribute("MySQLDriver",driver);
				session.setAttribute("user",user);
				
				loginError = "None";
				JsonElement usergson = gson.toJsonTree(user);
				wrapper.add("currentUser", usergson);
				JsonElement errorgson = gson.toJsonTree(loginError);
				wrapper.add("error", errorgson);
				String gsonString = gson.toJson(wrapper);
				//System.out.print(gsonString);
				response.getWriter().write(gsonString);
			}
		}
		driver.stop();
		
	}


}
