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

import client.Event;
import client.Movie;
import client.MovieR;
import client.User;
import menu.LoginMenu;
import server.MySQLDriver;

/**
 * Servlet implementation class MovieServlet
 */
@WebServlet("/MovieServlet")
public class MovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MovieServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		StringBuffer sb = new StringBuffer();
		String line = null;
		String message = "";
		String movieId = "";
		String movieTitle = "";
		JsonObject jobject = new JsonObject();
		
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
			jobject = jelement.getAsJsonObject();
			message = jobject.get("message").getAsString();
			movieId = jobject.get("movieId").getAsString();
			movieTitle = jobject.get("title").getAsString();
			
			//System.out.println(message+" "+movieTitle);
		}
		catch(Exception e){
			System.out.println("Movie Servlet Error");
		}
		LoginMenu loginMenu = (LoginMenu)session.getAttribute("loginMenu");
		User user = (User)session.getAttribute("user");
		MySQLDriver driver = new MySQLDriver();
		driver.connect();
		Gson gson = new Gson();
		response.setHeader("content-type","application/json");
		response.setCharacterEncoding("UTF-8");
		if(message.equals("loadMovie"))
		{
			MovieR movie = driver.selectMovie(movieId);
			if(movie==null){
				movie = new MovieR(movieId,movieTitle,0,0);
				driver.addMovie(movie);
			}
			//System.out.println("Movie Sent: " + movie.getTitle());
			String jsonString = gson.toJson(gson.toJsonTree(movie));
			response.getWriter().write(jsonString);
		}	
		else if(message.equals("non-rate-action"))
		{
			String action = jobject.get("action").getAsString();
			String time = jobject.get("time").getAsString();
			//System.out.println("Action received: "+action+" "+movieTitle);
			Event event = new Event(action,movieTitle,movieId,"0",time);
			driver.addEvent(user.getUsername(), event);
			user.addEvent(event);
			String jsonString = gson.toJson(gson.toJsonTree(user));
			response.getWriter().write(jsonString);
		}
		else if(message.equals("Rated"))
		{
			String rating = jobject.get("rating").getAsString();
			int ratingNum = Integer.parseInt(rating);
			String time = jobject.get("time").getAsString();
			//Movie movie = loginMenu.getMovie(movieTitle);
			//movie.addRating(ratingNum);
			//loginMenu.updateMovieRatingToFile(movie);
			
			Event event = new Event(message,movieTitle,movieId,rating,time);
			user.addEvent(event);
			driver.addEvent(user.getUsername(), event);
			MovieR movie = driver.selectMovie(movieId);
			movie.addRating(ratingNum);
			driver.updateMovie(movie);
			JsonObject wrapper = new JsonObject();
			wrapper.add("movie", gson.toJsonTree(movie));
			wrapper.add("curUser", gson.toJsonTree(user));
			String jsonString = gson.toJson(wrapper);
			response.getWriter().write(jsonString);
		}
		driver.stop();
	}

}
