package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

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

import menu.*;
import server.MySQLDriver;
/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		MySQLDriver driver = new MySQLDriver();
		driver.connect();
		StringBuffer sb = new StringBuffer();
		String line = null;
		String searchType = "";
		String searchInput = "";
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
		
		Gson gson = new Gson();
		response.setHeader("content-type","application/json");
		response.setCharacterEncoding("UTF-8");
		ArrayList<String> result = new ArrayList<String>();
		try{
			JsonElement jelement = new JsonParser().parse(sb.toString());
			JsonObject jobject = jelement.getAsJsonObject();
			searchType = jobject.get("searchType").getAsString();
			searchInput = jobject.get("searchInput").getAsString();
			System.out.println(searchType+" "+searchInput);
		}
		catch(Exception e){
			System.out.println("Error");
		}

		if(searchType.equals("user"))
		{
			result = driver.searchUser(searchInput);
			String jsonString = gson.toJson(gson.toJsonTree(result));
			response.getWriter().write(jsonString);

		}

		else{
			String jsonString = gson.toJson(gson.toJsonTree(result));
			response.getWriter().write(jsonString);
		}
	}

}
