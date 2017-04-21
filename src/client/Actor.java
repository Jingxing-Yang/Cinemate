package client;

public class Actor {
	String fname;
	String lname;
	String image;
	public Actor(String fname, String lname, String image)
	{
		this.fname = fname;
		this.lname = lname;
		this.image = image;
	}
	
	public String getFname()
	{
		return fname;
	}
	
	public String getLname()
	{
		return lname;
	}
	
	public String getFullname()
	{
		return fname + " " + lname;
	}
	
	public String getImage()
	{
		return image;
	}
	
}
