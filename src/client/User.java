package client;

import java.io.Serializable;
import java.util.ArrayList;

public class User {
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String fname;
	private String lname;
	private String imageURL;
	private ArrayList<String> following;
	private ArrayList<String> followers;
	private ArrayList<Event> feed;
	
	public User(String username,String password,String fname,String lname, String imageURL, ArrayList<String> following,ArrayList<Event> feed)
	{
		this.username = username;
		this.password = password;
		this.fname = fname;
		this.lname = lname;
		this.imageURL = imageURL;
		this.following = following;
		this.feed = feed;
		followers = new ArrayList<String>();
	}
	
	public User(String username,String password,String fname,String lname, String imageURL, ArrayList<String> following, ArrayList<String> followers,ArrayList<Event> feed)
	{
		this.username = username;
		this.password = password;
		this.fname = fname;
		this.lname = lname;
		this.imageURL = imageURL;
		this.following = following;
		this.followers = followers;
		this.feed = feed;
		
	}
	
	public User(String username,String password,String fname,String lname, String imageURL)
	{
		this.username = username;
		this.password = password;
		this.fname = fname;
		this.lname = lname;
		this.imageURL = imageURL;
		following = new ArrayList<String>();
		followers = new ArrayList<String>();
		feed = new ArrayList<Event>();
	}
	
	
	public String getUsername()
	{
		return username;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public boolean checkPassword(String input)
	{
		return password.equals(input);
	}
	
	public String printPassword()
	{
		StringBuffer temp = new StringBuffer(password);
		if(temp.length()>2)
		{
			for(int i = 1; i < temp.length()-1;i++)
			{
				temp.setCharAt(i,'*');
			}
		}
		return temp.toString();
	}
	
	public String getFname()
	{
		return fname;
	}
	
	public String getLname()
	{
		return lname;
	}
	
	public String getImageURL()
	{
		return imageURL;
	}
	
	public String getFullName()
	{
		return fname+" "+lname;
	}
	
	public ArrayList<String> getFollowing()
	{
		return following;
	}
	
	public ArrayList<String> getFollowers()
	{
		return followers;
	}
	
	public ArrayList<Event> getFeed()
	{
		return feed;
	}
	
	
	public void addFollowing(String name)
	{
		following.add(name);
	}
	
	public void addEvent(Event event)
	{
		feed.add(event);
	}
	
	public void addFollower(String name)
	{
		followers.add(name);
	}
	
	public void removeFollowing(String name)
	{
		following.remove(name);
	}
	
	public void removeFollower(String name)
	{
		followers.remove(name);
	}
	
}
