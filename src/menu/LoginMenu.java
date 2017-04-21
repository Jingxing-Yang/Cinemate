package menu;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FilenameUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import client.*;

public class LoginMenu {
	private HashSet<String> genres;
	private HashSet<String> actions;
	private HashMap<String,Movie> movies;
	private HashMap<String,User> users;
	private User curUser;
	Document doc;
	
	public LoginMenu(HashSet<String> genres,HashSet<String>actions, HashMap<String,Movie>movies,HashMap<String,User>users,User curUser, Document doc)
	{
		this.genres = genres;
		this.actions = actions;
		this.movies = movies;
		this.users = users;
		this.curUser = curUser;
		this.doc = doc;
	}
	
	//main functioning method
	@SuppressWarnings("resource")
	public boolean show()
	{
		Scanner scan = new Scanner(System.in);
		while(true)
		{
			printMenu();
			String input = scan.next();
			switch (input)
			{
			//search users
			case "1":
				searchUsers();
				break;
			//search movies
			case "2":
				searchMovies();
				break;
			//view feed
			case "3":
				viewFeed();
				break;
			//view profile
			case "4":
				viewProfile();
				break;
			//logout
			case "5":
				return true;
			//exit
			case "6":
				return false;
			//error handling
			default:
				System.out.println("Invalid command. Enter a number between 1 and 6!");
			}
			
		}
	}
	
	//helper function, print all the options
	private void printMenu()
	{
		System.out.println("1. Search Users");
		System.out.println("2. Search Movies");
		System.out.println("3. View Feed");
		System.out.println("4. View Profile");
		System.out.println("5. Logout");
		System.out.println("6. Exit");
	}
	
	@SuppressWarnings("resource")
	private void viewProfile()
	{
		System.out.println(curUser.getFullName());
		System.out.println("username: "+ curUser.getUsername());
		
		System.out.println("password: "+ curUser.printPassword());
		
		//following
		System.out.println("Following:" );
		for(int i = 0; i < curUser.getFollowing().size();i++)
		{
			System.out.println("\t"+curUser.getFollowing().get(i));
		}
		
		// followers
		System.out.println("Followers:");
		//search through each user's followings
		for(int i = 0; i < curUser.getFollowers().size();i++)
		{
			System.out.println("\t"+curUser.getFollowers().get(i));
		}
		
	
		//confirm quit profile page
		Scanner scan = new Scanner(System.in);
		System.out.println("To go back to the login menu, please type 0");
		while(true)
		{
			String quit = scan.next();
			if(quit.equals("0"))
			{
				break;
			}
			else
			{
				System.out.println("Invalid command. To go back to the login menu, please type 0");
			}
		}		
	}
	
	
	@SuppressWarnings("resource")
	private void viewFeed()
	{
		System.out.println("Feed:");
		//show user's feed
		for(int i=0; i < curUser.getFeed().size();i++)
		{
			System.out.println("\t"+curUser.getFeed().get(i).printEvent(curUser.getUsername()));
		}
		
		for(int i = 0; i < curUser.getFollowing().size();i++)
		{
			User user = users.get(curUser.getFollowing().get(i));
			for(int j = 0; j < user.getFeed().size();j++)
			{
				System.out.println("\t"+user.getFeed().get(j).printEvent(user.getUsername()));
			}
		}
		
		//confirm quit profile page
		Scanner scan = new Scanner(System.in);
		System.out.println("To go back to the login menu, please type 0");
		while(true)
		{
			String quit = scan.next();
			if(quit.equals("0"))
			{
				break;
			}
			else
			{
				System.out.println("Invalid command. To go back to the login menu, please type 0");
			}
		}
		
		
		
	}
	
	private void searchMovies()
	{
		MovieSearchMenu movieSearchMenu = new MovieSearchMenu(movies);
		movieSearchMenu.show();
	}
	
	@SuppressWarnings("resource")
	private void searchUsers()
	{
		Scanner scan = new Scanner(System.in);
		boolean search = true;
		while(search)
		{
			System.out.print("Please enter the username you are searching for.\n->");
			String userName = scan.next();
			ArrayList<String> result = searchUsersHelper(userName);
			System.out.println(result.size()+" results:");
			for(int i = 0; i < result.size();i++)
			{
				System.out.println(result.get(i));
			}
			while(true)
			{
				System.out.println("Please choose from the following options:");
				System.out.println("1. Back to Login Menu");
				System.out.println("2. Search Again");
				String input = scan.next();
				if(input.equals("1"))
				{
					return;
				}
				else if(input.equals("2"))
				{
					break;
				}
				else
				{
					System.out.println("Invalid Input!");
				}
			}
			
		}
	}
	
	public ArrayList<String> searchUsersHelper(String userName)
	{
		String userNameToLower = userName.toLowerCase(); 
		Iterator<User> it = users.values().iterator();
		ArrayList<String> result = new ArrayList<String>();
		while(it.hasNext())
		{
			User user = it.next();
			if(user.getUsername().toLowerCase().equals(userNameToLower))
			{
				result.add(user.getUsername());
			}
			else if(user.getFname().toLowerCase().equals(userNameToLower))
			{
				result.add(user.getUsername());
			}
			else if(user.getLname().toLowerCase().equals(userNameToLower))
			{
				result.add(user.getUsername());
			}
		}
		return result;
	}
	
	public MovieSearchMenu createMovieSearchMenu()
	{
		return new MovieSearchMenu(movies);
	}
	
	public User getCurrentUser()
	{
		return curUser;
	}
	public ArrayList<String> getCurrentUserFeed()
	{
		ArrayList<String> feed = new ArrayList<String>();
		for(int i=0; i < curUser.getFeed().size();i++)
		{
			feed.add(curUser.getFeed().get(i).printEvent(curUser.getUsername()));
		}
		for(int i = 0; i < curUser.getFollowing().size();i++)
		{
			User user = users.get(curUser.getFollowing().get(i));
			for(int j = 0; j < user.getFeed().size();j++)
			{
				feed.add(user.getFeed().get(j).printEvent(user.getUsername()));
			}
		}
		return feed;
	}
	
	//TODO:
	//Test reference, need to check if curUser points to the same location in the user in the userlist. 
	public void unfollow(String username)
	{
		curUser.removeFollowing(username);
		if(users.get(username)!=null)
		{
			users.get(username).removeFollower(curUser.getUsername());
		}
		//just checking if the reference is the same
		/*
		if(curUser!=users.get(curUser.getUsername()))
		{
			users.get(curUser.getUsername()).removeFollowing(username);
		}
		*/
	}
	
	public void removeFollowingFromFile(String username)
	{
		//System.out.println(doc.getBaseURI());
		Element user = (Element)getUserNode(curUser.getUsername());
		Node followingNode = user.getElementsByTagName("following").item(0);
		NodeList followingList = ((Element)followingNode).getElementsByTagName("username");
		for(int i = 0; i < followingList.getLength();i++)
		{
			//System.out.println(followingList.item(i).getTextContent());
			if(followingList.item(i).getTextContent().equals(username))
			{
				followingNode.removeChild(followingList.item(i));
				updateFile();
				return;
			}
		}
	
	}
	
	
	public void addFollowingToFile(String username)
	{
		Element user = (Element)getUserNode(curUser.getUsername());
		Element following = (Element)user.getElementsByTagName("following").item(0);
		Element newFollowing = doc.createElement("username");
		newFollowing.setTextContent(username);
		following.appendChild(newFollowing);
		updateFile();
	}
	
	public void addEvent(Event event)
	{
		curUser.addEvent(event);
	}
	
	public void addEventToFile(Event event)
	{
		Element user = (Element)getUserNode(curUser.getUsername());
		Element feed = (Element)(user.getElementsByTagName("feed").item(0));
		Element newEvent = doc.createElement("event");
		Element action = doc.createElement("action");
		action.setTextContent(event.getAction());
		Element movie = doc.createElement("movie");
		movie.setTextContent(event.getMovieName());
		Element rating = doc.createElement("rating");
		if(event.getRating()!=null)
		{
			rating.setTextContent(event.getRating());
		}
		
		newEvent.appendChild(action);
		newEvent.appendChild(movie);
		newEvent.appendChild(rating);
		
		feed.appendChild(newEvent);
		
		updateFile();
	}
	
	private Node getUserNode(String username)
	{
		NodeList userList = doc.getElementsByTagName("users").item(0).getChildNodes();
		for(int i = 0; i < userList.getLength();i++)
		{
			Node user = userList.item(i);
			NodeList childNodes = user.getChildNodes();
			for(int j = 0; j < childNodes.getLength();j++)
			{
				Node infoNode = childNodes.item(j);
				//find the current user node
				if(infoNode.getNodeName().equals("username")&&infoNode.getTextContent().equals(curUser.getUsername()))
				{
					return user;
				}
			}
		}
		return null;
	}
	
	//can only search by actor, genre and movie titles
	//else will return an empty arrayList
	public ArrayList<String> searchMovies(String movieName, String searchType)
	{
		if(searchType.equals("genre"))
		{
			return searchByGenreHelper(movieName);
		}
		else if(searchType.equals("actor"))
		{
			return searchByActorHelper(movieName);
		}
		else if(searchType.equals("title"))
		{
			return searchByTitleHelper(movieName);
		}
		return new ArrayList<String>();
	}
	
	//helper function to search movies by actor name
	//return a list of movies given a actor name
	private ArrayList<String> searchByActorHelper(String name)
	{
		String nameToLower = name.toLowerCase();
		ArrayList<String> result = new ArrayList<String>();
		Iterator<Movie> it = movies.values().iterator();
		while(it.hasNext())
		{
			Movie movie = it.next();
			for(int i = 0; i < movie.getActors().size();i++)
			{
				if(nameToLower.equals(movie.getActors().get(i).getFullname().toLowerCase()))
				{
					result.add(movie.getTitle());
					break;
				}
			}
		}
		return result;
	}
	
	private ArrayList<String> searchByTitleHelper(String title)
	{
		ArrayList<String> result = new ArrayList<String>();
		String titleToLower = title.toLowerCase();
		Iterator<Movie> it = movies.values().iterator();
		while(it.hasNext())
		{
			Movie movie = it.next();
			if(titleToLower.equals(movie.getTitle().toLowerCase()))
			{
				result.add(movie.getTitle());
			}
		}
		return result;
	}
	
	private ArrayList<String> searchByGenreHelper(String genre)
	{
		ArrayList<String> result = new ArrayList<String>();
		String genreToLower = genre.toLowerCase();
		Iterator<Movie> it = movies.values().iterator();
		while(it.hasNext())
		{
			Movie movie = it.next();
			if(genreToLower.equals(movie.getGenre().toLowerCase()))
			{
				result.add(movie.getTitle());
			}
		}
		return result;
	}
	
	public void updateMovieRatingToFile(Movie movie)
	{
		Element movieNode = (Element)findMovieNode(movie.getTitle());
		Element totalRating = (Element)movieNode.getElementsByTagName("rating-total").item(0);
		Element ratingCount = (Element)movieNode.getElementsByTagName("rating-count").item(0);
		totalRating.setTextContent(movie.getTotalRatingString());
		ratingCount.setTextContent(movie.getRatingCountString());
		updateFile();
	}
	
	private Node findMovieNode(String movieTitle)
	{
		NodeList movieList = doc.getElementsByTagName("movies").item(0).getChildNodes();
		for(int i = 0; i < movieList.getLength();i++)
		{
			Node movie = movieList.item(i);
			NodeList childNodes = movie.getChildNodes();
			for(int j = 0; j < childNodes.getLength();j++)
			{
				Node infoNode = childNodes.item(j);
				//find the current user node
				if(infoNode.getNodeName().equals("title")&&infoNode.getTextContent().equals(movieTitle))
				{
					return movie;
				}
			}
		}
		return null;
	}
	
	public ArrayList<FeedEvent> getEventsForFeed()
	{
		ArrayList<FeedEvent> result = new ArrayList<FeedEvent>();
		for(int i = 0; i < curUser.getFollowing().size();i++)
		{
			User user = users.get(curUser.getFollowing().get(i));
			ArrayList<Event> events = user.getFeed();
			for(int j = 0; j < events.size();j++)
			{
				String movieurl = movies.get(events.get(j).getMovieName()).getImage();
				FeedEvent fe = new FeedEvent(user.getUsername(),user.getFullName(),user.getImageURL(),events.get(j));
				result.add(fe);
			}
		}
		return result;
	}
	
	public User findUser(String userName)
	{
		return users.get(userName);
	}
	
	public void follow(String username)
	{
		curUser.addFollowing(username);
		users.get(username).addFollower(curUser.getUsername());
	}
	
	//update local file
	private void updateFile()
	{
		try{
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		String fileName = FilenameUtils.getName(doc.getBaseURI());
		//System.out.println(fileName);
		StreamResult result = new StreamResult(new File(fileName));
		transformer.transform(source, result);
		//System.out.println("File Saved");
		} 
		catch (TransformerException tfe) 
		{
			tfe.printStackTrace();
		}
	}
	
	public Movie getMovie(String title)
	{
		return movies.get(title);
	}
}



