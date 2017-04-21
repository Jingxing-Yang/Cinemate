package server;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import client.*;
public class MySQLDriver {
	private Connection conn;
	//private final String configFile = "rsrc/config.txt";
	private String ipaddress = "";
	private String db = "";
	private String user = "";
	private String password = "";
	
	public static final String addUser = "insert into Users(username,user_password,fname,lname,image) values(?,?,?,?,?);";
	public static final String selectTheUser = "select* from Users where username=?;";//find a specific user
	public static final String selectUserByUsername = "select username from Users where lower(username)=?;";//search case insensitive usernames
	public static final String selectUserByFname="select username from Users where lower(fname)=?;"; //search case insensitive fist names
	public static final String selectUserByLname="select username from Users where lower(lname)=?;";//search case insensitive last names
	public static final String addFollowingPair = "insert into Followingtable(username,follower) values(?,?);";//add a following pair
	public static final String selectFollowing = "select username from Followingtable where follower=?;";//select a user's following
	public static final String selectFollower = "select follower from Followingtable where username=?;";// select a user's follower
	public static final String removeFollowingPair = "delete from Followingtable where username=? and follower=?;";
	public static final String addEvent = "insert into Userevents(username,user_action,movieName,imdbID,rating,event_time) values(?,?,?,?,?,?);";
	public static final String selectEvent = "select* from Userevents where username=?;"; // select events of a user
	public static final String addMovie = "insert into Movies(imdbID,title,totalRating,ratingCount) values(?,?,?,?);";
	public static final String selectMovie= "select* from Movies where imdbID=?;";
	public static final String updateMovie= "update Movies set totalRating=?, ratingCount=? where imdbID=?;";
	public MySQLDriver()
	{
		try
		{
			new com.mysql.jdbc.Driver();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void connect()
	{
		FileReader fr;
		BufferedReader br;
		boolean test =  false;
			if(test)
			{
				try{
			fr = new FileReader("rsrc(text)/config.txt");
			br = new BufferedReader(fr);
			ipaddress = br.readLine();
			ipaddress = ipaddress.substring(ipaddress.indexOf(':')+1);
			db = br.readLine();
			db = db.substring(db.indexOf(':')+1);
			user = br.readLine();
			user = user.substring(user.indexOf(':')+1);
			password = br.readLine();
			password = password.substring(password.indexOf(':')+1);
			//System.out.println(ipaddress+" "+db+" "+user+" "+password);
				}
				catch(IOException ioe){}
			}
			else{
			//InputStream is = getClass().getClassLoader().getResourceAsStream("/config.txt");
			InputStream is = getClass().getResourceAsStream("/rsrc/config.txt");
			//InputStream is =getClass().getClassLoader().getResourceAsStream("../rsrc/config.txt");
			Scanner scan = new Scanner(is);
			ipaddress = scan.nextLine();
			ipaddress = ipaddress.substring(ipaddress.indexOf(':')+1);
			db = scan.nextLine();
			db = db.substring(db.indexOf(':')+1);
			user = scan.nextLine();
			user = user.substring(user.indexOf(':')+1);
			password = scan.nextLine();
			password = password.substring(password.indexOf(':')+1);
			//System.out.println(ipaddress+" "+db+" "+user+" "+password);
			}
	

		
		try
		{
			conn = DriverManager.getConnection("jdbc:mysql://"+ipaddress+"/"+db+
					"?user="+user+"&password="+password+"&useSSL=false");
			//System.out.println("connected");
		}
		catch(SQLException se)
		{
			se.printStackTrace();
		}
	}
	
	public void stop()
	{
		try
		{
			conn.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	//Tested
	public User addUser(String username, String password, String fname, String lname, String image ){
		try
		{
			//System.out.print;
			PreparedStatement ps = conn.prepareStatement(addUser);
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, fname);
			ps.setString(4, lname);
			ps.setString(5, image);
			ps.executeUpdate();
			return new User(username,password,fname,lname,image);
		}
		catch(SQLException se)
		{
			System.out.println(se.getMessage());
			return null;
		}
	}
	
	public boolean doesUserExist(String username)
	{
		try
		{
			PreparedStatement ps = conn.prepareStatement(selectTheUser);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				return true;
			}
			return false;
		}
		catch(SQLException se)
		{
			System.out.println(se.getMessage());
			return false;
		}
	}
	
	//Tested
	public User getUser(String username)
	{
		try
		{
			PreparedStatement ps = conn.prepareStatement(selectTheUser);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				String password = rs.getString("user_password");
				String fname = rs.getString("fname");
				String lname = rs.getString("lname");
				String image = rs.getString("image");
				return new User(username,password,fname,lname,image,getFollowing(username),getFollowers(username),getEvents(username));
			}
			return null;
		}
		catch(SQLException se)
		{
			System.out.println(se.getMessage());
			return null;
		}
	}
	
	//Tested
	public void addFollowingPair(String username, String follower)
	{
		try
		{
			PreparedStatement ps = conn.prepareStatement(addFollowingPair);
			ps.setString(1, username);
			ps.setString(2, follower);
			ps.executeUpdate();
		}
		catch(SQLException se)
		{
			System.out.println(se.getMessage());
		}
	}
	
	//Tested
	public void removeFollowingPair(String username, String follower)
	{
		try
		{
			PreparedStatement ps = conn.prepareStatement(removeFollowingPair);
			ps.setString(1, username);
			ps.setString(2, follower);
			ps.executeUpdate();
		}
		catch(SQLException se)
		{
			System.out.println(se.getMessage());
		}
	}
	
	//Tested
	public ArrayList<String> getFollowing(String username)
	{
		try
		{
			ArrayList<String> result = new ArrayList<String>();
			PreparedStatement ps = conn.prepareStatement(selectFollowing);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				result.add(rs.getString("username"));
			}
			return result;
		}
		catch(SQLException se)
		{
			System.out.println(se.getMessage());
			return null;
		}
	}
	
	//Tested
	public ArrayList<String> getFollowers(String username)
	{
		try
		{
			ArrayList<String> result = new ArrayList<String>();
			PreparedStatement ps = conn.prepareStatement(selectFollower);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				result.add(rs.getString("follower"));
			}
			return result;
		}
		catch(SQLException se)
		{
			System.out.println(se.getMessage());
			return null;
		}
	}
	
	//Tested
	public void addEvent(String username, Event event)
	{
		try
		{
			PreparedStatement ps = conn.prepareStatement(addEvent);
			ps.setString(1, username);
			ps.setString(2, event.getAction());
			ps.setString(3, event.getMovieName());
			ps.setString(4, event.getMovieID());
			ps.setInt(5, Integer.parseInt(event.getRating()));
			ps.setString(6, event.getTime());
			ps.executeUpdate();
		}
		catch(SQLException se)
		{
			System.out.println(se.getMessage());
		}
	}
	
	//Tested
	public ArrayList<Event> getEvents(String username)
	{
		ArrayList<Event> result = new ArrayList<Event>();
		try
		{
			PreparedStatement ps = conn.prepareStatement(selectEvent);
			ps.setString(1,username);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				String action = rs.getString("user_action");
				String movieName = rs.getString("movieName");
				String movieID = rs.getString("imdbID");
				int rating = rs.getInt("rating");
				String time = rs.getString("event_time");
				result.add(new Event(action,movieName,movieID,Integer.toString(rating),time));
			}
			return result;
		}
		catch(SQLException se)
		{
			System.out.println(se.getMessage());
			return null;
		}
	}
	
	public ArrayList<FeedEvent> getFeedEvents(String username)
	{
		ArrayList<FeedEvent> result = new ArrayList<FeedEvent>();
		User user = getUser(username);
		try
		{
			PreparedStatement ps = conn.prepareStatement(selectEvent);
			ps.setString(1,username);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				String action = rs.getString("user_action");
				String movieName = rs.getString("movieName");
				String movieID = rs.getString("imdbID");
				int rating = rs.getInt("rating");
				String time = rs.getString("event_time");
				Event event = new Event(action,movieName,movieID,Integer.toString(rating),time);
				result.add(new FeedEvent(user.getUsername(),user.getFullName(),user.getImageURL(),event));
			}
			return result;
		}
		catch(SQLException se)
		{
			System.out.println(se.getMessage());
			return null;
		}
	}
	
	//return a list of usernames that satisfy the search
	public ArrayList<String> searchUser(String input)
	{
		ArrayList<String> result = new ArrayList<String>();
		try
		{
			PreparedStatement ps;
			ResultSet rs;
			//search by username
			ps = conn.prepareStatement(selectUserByUsername);
			ps.setString(1, input);
			rs = ps.executeQuery();
			while(rs.next())
			{
				String username = rs.getString("username");
				if(!result.contains(username))
				{
					result.add(username);
				}
			}
			
			
			//search by fname
			ps = conn.prepareStatement(selectUserByFname);
			ps.setString(1, input);
			rs = ps.executeQuery();
			while(rs.next())
			{
				String username = rs.getString("username");
				if(!result.contains(username))
				{
					result.add(username);
				}
			}
			
			//search by lname
			ps = conn.prepareStatement(selectUserByLname);
			ps.setString(1, input);
			rs = ps.executeQuery();
			while(rs.next())
			{
				String username = rs.getString("username");
				if(!result.contains(username))
				{
					result.add(username);
				}
			}
			
			return result;
		}
		catch(SQLException se)
		{
			System.out.println(se.getMessage());
			return null;
		}
	}
	
	public boolean doesMovieExist(String imdbID)
	{
		try
		{
			PreparedStatement ps = conn.prepareStatement(selectMovie);
			ps.setString(1, imdbID);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				return true;
			}
			return false;
		}
		catch(SQLException se)
		{
			System.out.println(se.getMessage());
			return false;
		}
	}
	
	public MovieR selectMovie(String imdbID)
	{
		try
		{
			MovieR movie;
			PreparedStatement ps = conn.prepareStatement(selectMovie);
			ps.setString(1, imdbID);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				String title = rs.getString("title");
				int totalRating = rs.getInt("totalRating");
				int ratingCount = rs.getInt("ratingCount");
				movie = new MovieR(imdbID,title,totalRating,ratingCount);
				return movie;
			}
			return null;
		}
		catch(SQLException se)
		{
			System.out.println(se.getMessage());
			return null;
		}
	}
	
	

	public void addMovie(MovieR movie)
	{
		if(!doesMovieExist(movie.getImdbID()))
		{
			try
			{
				PreparedStatement ps = conn.prepareStatement(addMovie);
				ps.setString(1, movie.getImdbID());
				ps.setString(2, movie.getTitle());
				ps.setInt(3, movie.getTotalRating());
				ps.setInt(4, movie.getRatingCount());
				ps.executeUpdate();
			}
			catch(SQLException se)
			{
				System.out.println(se.getMessage());
			}
		}
	}
	
	//wont check 
	public void updateMovie(MovieR movie)
	{
		if(doesMovieExist(movie.getImdbID()))
		{
			try
			{
				PreparedStatement ps = conn.prepareStatement(updateMovie);
				ps.setInt(1, movie.getTotalRating());
				ps.setInt(2, movie.getRatingCount());
				ps.setString(3, movie.getImdbID());
				ps.executeUpdate();
			}
			catch(SQLException se)
			{
				System.out.println(se.getMessage());
			}
		}
	}
	
	public ArrayList<FeedEvent> getFeed(User user)
	{
		ArrayList<FeedEvent> feed = new ArrayList<FeedEvent>();
		for(String following:user.getFollowing())
		{
			//System.out.println(following);
			feed.addAll(getFeedEvents(following));
		}
		return feed;
	}
	
	
	public static void main(String args[])
	{
		MySQLDriver m = new MySQLDriver();
		m.connect();
		m.addUser("a", "a", "a", "a", "a");
		m.addUser("b", "b", "a", "b", "b");
		m.addUser("c", "c", "A", "A", "c");
		m.addFollowingPair("b", "a");
		m.addFollowingPair("c", "a");
		m.addFollowingPair("a", "b");
		m.addFollowingPair("a", "c");
		m.addEvent("a", new Event("action1","MovieName1","movieID1","1","NO1"));
		m.addEvent("a", new Event("action2","MovieName2","movieID2","2","NO2"));
		m.removeFollowingPair("b", "a");
		User user = m.getUser("a");
		System.out.println("username: "+user.getUsername());
		System.out.println("password: "+user.getPassword());
		System.out.println("fname: "+user.getFname());
		System.out.println("lname: "+user.getLname());
		System.out.println("image: "+user.getImageURL());
		System.out.println("Following: ");
		for(int i = 0; i < user.getFollowing().size();i++)
		{
			System.out.println(user.getFollowing().get(i));
		}
		System.out.println("Followers: ");
		for(int i = 0; i < user.getFollowers().size();i++)
		{
			System.out.println(user.getFollowers().get(i));
		}
		System.out.println("Events:");
		for(int i = 0; i < user.getFeed().size();i++)
		{
			Event event = user.getFeed().get(i);
			System.out.println(event.getAction()+" "+event.getMovieName()+" "+event.getMovieID()+" "+event.getRating()+" "+event.getTime());
		}
		
		MovieR movie = new MovieR("IMDB1","movieTitle",1,1);
		m.addMovie(movie);
		MovieR r = m.selectMovie("IMDB1");
		System.out.println(r.getImdbID()+" "+r.getTitle()+" "+r.getTotalRating()+" "+r.getRatingCount());
		r.addRating(4);
		System.out.println(r.getImdbID()+" "+r.getTitle()+" "+r.getTotalRating()+" "+r.getRatingCount());
		m.updateMovie(r);
		r = m.selectMovie("IMDB1");
		System.out.println(r.getImdbID()+" "+r.getTitle()+" "+r.getTotalRating()+" "+r.getRatingCount());
		ArrayList<String> searchTest = m.searchUser("a");
		for(int i = 0; i < searchTest.size();i++)
		{
			System.out.println(searchTest.get(i)+" ");
		}
	}
	
	
}
