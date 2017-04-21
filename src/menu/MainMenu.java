package menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import client.*;

import org.apache.commons.io.FilenameUtils;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.*;

public class MainMenu {
	private HashSet<String> genres;
	private HashSet<String> actions;
	private HashMap<String, Movie> movies;
	private HashMap<String,User> users;
	Document doc;
	
	public MainMenu(String fileName)
	{
		genres = new HashSet<String>();
		actions = new HashSet<String>();
		movies = new HashMap<String, Movie>();
		users = new HashMap<String,User>();
		System.out.println("Start");
		readFile(fileName);
		ArrayList<String> errorMessage = checkErrors();
		if(!errorMessage.isEmpty())
		{
			for(int i = 0; i<errorMessage.size();i++)
			{
				System.out.println(errorMessage.get(i));
			}
		}
	}
	
	public MainMenu()
	{
		genres = new HashSet<String>();
		actions = new HashSet<String>();
		movies = new HashMap<String, Movie>();
		users = new HashMap<String,User>();
	}
	
	//constructor helper function to parse in data from XML
	//code pasted from https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm
	public boolean readFile (String fileName)
	{
		try
		{
			clearContents();
			File inputFile = new File(fileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			
			//getGenres
			NodeList genreList = ((Element)doc.getElementsByTagName("genres").item(0)).getElementsByTagName("genre");
			for(int i = 0; i < genreList.getLength();i++)
			{
				genres.add(genreList.item(i).getTextContent());
			}
	
			
			//getActions
			NodeList actionList = ((Element)doc.getElementsByTagName("actions").item(0)).getElementsByTagName("action");
			for(int i = 0; i < actionList.getLength();i++)
			{
				actions.add(actionList.item(i).getTextContent());
			}

			
			//get movie information
			NodeList movieList = ((Element)doc.getElementsByTagName("movies").item(0)).getElementsByTagName("movie");
			for(int i = 0; i < movieList.getLength();i++)
			{
				Movie movie = movieParser(movieList.item(i));
				movies.put(movie.getTitle(), movie);
			}
		
			
			//get user information
			NodeList userList = ((Element)doc.getElementsByTagName("users").item(0)).getElementsByTagName("user");
			for(int i = 0; i < userList.getLength();i++)
			{
				User user = userParser(userList.item(i));
				users.put(user.getUsername(),user);
			}
	
			return true;
			
		}catch (Exception e) 
		{
	         return false;
		}
		
	}
	
	//set up followers
	public void setupFollowers()
	{
		try{
			Iterator<User> it = users.values().iterator();
			while(it.hasNext())
			{
				User temp = it.next();
				for(int i = 0; i<temp.getFollowing().size();i++)
				{
					users.get(temp.getFollowing().get(i)).addFollower(temp.getUsername());
				}
			}
		}
		catch(NullPointerException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	
	//helper function to generate a Movie from the movie node
	private Movie movieParser(Node movieNode)
	{
		String title;
		String director;
		String image;
		ArrayList<String> writers = new ArrayList<String>();
		String year;
		String genre;
		String description;
		String totalRating;
		String ratingCount;
		ArrayList<Actor> actors = new ArrayList<Actor>();
		
		title = ((Element)movieNode).getElementsByTagName("title").item(0).getTextContent();
		director = ((Element)movieNode).getElementsByTagName("director").item(0).getTextContent();
		image = ((Element)movieNode).getElementsByTagName("image").item(0).getTextContent();
		NodeList writerList = ((Element)movieNode).getElementsByTagName("writer");
		for(int i = 0; i < writerList.getLength();i++)
		{
			writers.add(writerList.item(i).getTextContent());
		}
		year = ((Element)movieNode).getElementsByTagName("year").item(0).getTextContent();
		genre = ((Element)movieNode).getElementsByTagName("genre").item(0).getTextContent();
		description = ((Element)movieNode).getElementsByTagName("description").item(0).getTextContent();
		totalRating = ((Element)movieNode).getElementsByTagName("rating-total").item(0).getTextContent();
		ratingCount = ((Element)movieNode).getElementsByTagName("rating-count").item(0).getTextContent();
		//parse in actors
		NodeList actorList = ((Element)movieNode).getElementsByTagName("actor");
		for(int i = 0; i < actorList.getLength();i++)
		{
			Node actorNode = actorList.item(i);
			String fname = "";
			String lname = "";
			String actorImage = "";
			NodeList childNodes = actorNode.getChildNodes();
			for(int j = 0; j < childNodes.getLength();j++)
			{
				if(childNodes.item(j).getNodeName().equals("fname"))
				{
					fname = childNodes.item(j).getTextContent();
				}
				if(childNodes.item(j).getNodeName().equals("lname"))
				{
					lname = childNodes.item(j).getTextContent();
				}
				if(childNodes.item(j).getNodeName().equals("image"))
				{
					actorImage = childNodes.item(j).getTextContent();
				}
			}
			Actor actor = new Actor(fname,lname,actorImage);
			actors.add(actor);
		}
		
		return new Movie(title,director, image,writers,year,genre,description,totalRating, ratingCount,actors);
	}
	
	//helper function to generate a User from the user node
	private User userParser(Node userNode)
	{
		String username = new String();
		String password = new String();
		String fname = new String();
		String lname = new String();
		String imageURL = new String();
		ArrayList<String> following = new ArrayList<String>();
		ArrayList<Event> feed = new ArrayList<Event>();
		NodeList childNodes = userNode.getChildNodes();
		for(int i = 0; i < childNodes.getLength();i++)
		{
			if(childNodes.item(i).getNodeName().equals("username"))
			{
				username = childNodes.item(i).getTextContent();
			}
			else if(childNodes.item(i).getNodeName().equals("password"))
			{
				password = childNodes.item(i).getTextContent();
			}
			else if(childNodes.item(i).getNodeName().equals("fname"))
			{
				fname = childNodes.item(i).getTextContent();
			}
			else if(childNodes.item(i).getNodeName().equals("lname"))
			{
				lname = childNodes.item(i).getTextContent();
			}
			else if(childNodes.item(i).getNodeName().equals("image"))
			{
				imageURL = childNodes.item(i).getTextContent();
			}
			else if(childNodes.item(i).getNodeName().equals("following"))
			{
				
				NodeList followingList = childNodes.item(i).getChildNodes();
				for(int j = 0; j < followingList.getLength();j++)
				{
					if(followingList.item(j).getNodeType() == Node.ELEMENT_NODE)
					{
						following.add(followingList.item(j).getTextContent());
					}
				}
				
				
			}
			
			else if(childNodes.item(i).getNodeName().equals("feed"))
			{
				NodeList eventList = childNodes.item(i).getChildNodes();
				for(int j = 0; j < eventList.getLength();j++)
				{
					if(eventList.item(j).getNodeType() == Node.ELEMENT_NODE)
					{
						feed.add(eventParser(eventList.item(j)));
					}
				}
			}
			
		}
		return new User(username,password,fname,lname,imageURL,following,feed);
	}
	
	private Event eventParser(Node eventNode)
	{
		String action = new String();
		String movieName = new String();
		String rating = new String();
		NodeList childNodes = eventNode.getChildNodes();
		for(int i = 0; i < childNodes.getLength();i++)
		{
			if(childNodes.item(i).getNodeName().equals("action"))
			{
				action = childNodes.item(i).getTextContent();
			}
			else if(childNodes.item(i).getNodeName().equals("movie"))
			{
				movieName = childNodes.item(i).getTextContent();
			}
			else if(childNodes.item(i).getNodeName().equals("rating"))
			{
				rating = childNodes.item(i).getTextContent();
			}
		}
		return new Event(action,movieName,rating);
	}
	
	//show the options that that the user have
	public void show()
	{
		boolean open = true;
		Scanner scan = new Scanner(System.in);
		while(open)
		{
			System.out.println("1. Login");
			System.out.println("2. Exit");
			
			String option = scan.next();
			if(option.equals("1"))
			{
				open = login();
			}
			else if(option.equals("2"))
			{
				open = false;
			}
			else
			{
				System.out.println("You have entered an invalid command, please try again.");
			}
			
		}
		scan.close();
	}
	
	//login helper function
	//show user name and password login interface
	@SuppressWarnings("resource")
	private boolean login()
	{
		//boolean open = true;
		Scanner scan = new Scanner(System.in);
		
		System.out.print("Please enter your username\n->");
		int num = 3;
		User user = null;
		boolean inputCorrect = false;
		while(num>0)
		{
			String inputName = scan.next();
			//check if input user name is correct
			if(users.containsKey(inputName))
			{
				user = users.get(inputName);
				inputCorrect = true;
				break;
			}
			if(!users.containsKey(inputName))
			{
				num--;
				if(num==0)
				{
					break;
				}
				System.out.println("Invalid username. You have "+num+"  more chances to enter a valid username.");
			}
		}
		//when the user input wrong user name 3 times
		if(!inputCorrect)
		{
			return true;
		}
		
		//now check the password
		System.out.print("Please enter your password.\n-> ");
		num = 3;
		inputCorrect = false;
		while(num>0)
		{
			String inputPassword = scan.next();
			//when the user enters the correct password
			if(user.checkPassword(inputPassword))
			{
				inputCorrect = true;
				break;
			}
			else
			{
				num--;
				if(num==0)
				{
					break;
				}
				System.out.println("Incorrect password. You have "+num+"  more chances to enter the correct username.");
			}
		}
		if(!inputCorrect)
		{
			//scan.close();
			return true;
		}
		
		LoginMenu loginMenu = new LoginMenu(genres,actions,movies,users,user,doc);
		
		return loginMenu.show();
		
	}

	//return error message else return an empty string
	public ArrayList<String> checkErrors()
	{
		ArrayList<String> errorMessage = new ArrayList<String>();
		//There are no movie objects found under the <movies> tag
		if(movies.isEmpty())
		{
			errorMessage.add( "No Movies found");
		}
		
		
		//A movie has a non-existing genre listed
		//Any of the non-String fields of movies have a String value 
		Iterator<Movie> movieIt = movies.values().iterator();
		while(movieIt.hasNext())
		{
			Movie movie = movieIt.next();
			if(!genres.contains(movie.getGenre()))
			{
				errorMessage.add("Nonexistent genre of movie \""+movie.getTitle()+"\".");
			}
			try
			{
				Integer.parseInt(movie.getYear());
			}
			catch(NumberFormatException e)
			{
				errorMessage.add("Year of movie \""+movie.getTitle()+"\" is a none-integer value");
			}
			if(movie.getTotalRatingString()!=null&&!movie.getTotalRatingString().isEmpty())
			{
				try
				{
					Double.parseDouble(movie.getTotalRatingString());
				}
				catch(NumberFormatException e)
				{
					errorMessage.add("Rating of movie \""+movie.getTitle()+"\" is not a numeric value");
				}
			}
		}
		
		Iterator<User> userIt = users.values().iterator();
		while(userIt.hasNext())
		{
			User user = userIt.next();
			//check invalid user names for followings
			for(int i = 0;i < user.getFollowing().size();i++)
			{
				if(!users.containsKey(user.getFollowing().get(i)))
				{
					errorMessage.add("Nonexsitent username \""+user.getFollowing().get(i)+"\" in "
							+user.getUsername()+"\'s following list.");
				}
			}
			//check events
			for(int i = 0; i < user.getFeed().size();i++)
			{
				Event event= user.getFeed().get(i);
				//check invalid events
				if(!actions.contains(event.getAction()))
				{
					errorMessage.add("Invalid action name \""+event.getAction()+"\" in "
							+user.getUsername()+"\'s events");
				}
				//check if there's a rating when a user rated a movie
				if(event.getAction().equals("Rated")&&(event.getRating()==null||event.getRating().isEmpty()))
				{
					errorMessage.add("The movie \""+event.getMovieName()+"\" does not have a rating when rated by "
							+user.getUsername());
				}
				
				
				//check if the rating value is valid and the if the action is "Rated".
				if(event.getRating()!=null&&!event.getRating().isEmpty())
				{
					try
					{
						if(!event.getAction().equals("Rated"))
						{
							errorMessage.add("The movie \""+event.getMovieName()+"\"s rating does not have a correspondent action \"Rated\" in "
									+user.getUsername()+"\'s events");
						}
						Double.parseDouble(event.getRating());
					}
					catch(NumberFormatException e)
					{
						errorMessage.add("Invalid rating of movie \""+event.getMovieName()+"\" in "
								+user.getUsername()+"\'s events");
					}
				}
			}
		}
		
		return errorMessage;
	}
	
	public void clearContents()
	{
		genres.clear();
		actions.clear();
		movies.clear();
		users.clear();
		doc = null;
	}

	public User findUser(String userName)
	{
		return users.get(userName);
	}
	
	public LoginMenu createLoginMenu(User curUser)
	{
		return new LoginMenu(genres,actions,movies,users,curUser,doc);
	}
	
	//add user to the array: won't modify the file
	public void addUser(User user)
	{
		users.put(user.getUsername(), user);
	}
	
	public void addUserToFile(User user)
	{
		Element usersNode = (Element)doc.getElementsByTagName("users").item(0);
		Element newUser = doc.createElement("user");
		Element username = doc.createElement("username");
		username.setTextContent(user.getUsername());
		Element password = doc.createElement("password");
		password.setTextContent(user.getPassword());
		Element fname = doc.createElement("fname");
		fname.setTextContent(user.getFname());
		Element lname = doc.createElement("lname");
		lname.setTextContent(user.getLname());
		Element image = doc.createElement("image");
		image.setTextContent(user.getImageURL());
		Element following = doc.createElement("following");
		Element feed = doc.createElement("feed");
		
		newUser.appendChild(username);
		newUser.appendChild(password);
		newUser.appendChild(fname);
		newUser.appendChild(lname);
		newUser.appendChild(image);
		newUser.appendChild(following);
		newUser.appendChild(feed);
		
		usersNode.appendChild(newUser);
		updateFile();
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
}
