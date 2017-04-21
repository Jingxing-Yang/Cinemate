package client;

import java.util.ArrayList;


public class Movie {
	private String title;
	private String director;
	private String image;
	private ArrayList<String> writers;
	private String year;
	private String genre;
	private String description;
	private String totalRating;
	private String ratingCount;
	private ArrayList<Actor> actors;
	
	public Movie(String title,String director, String image, ArrayList<String> writers,
			String year, String genre, String description, String totalRating, String ratingCount, ArrayList<Actor> actors)
	{
		this.title = title;
		this.director = director;
		this.image = image;
		this.writers = writers;
		this.year = year;
		this.genre = genre;
		this.description = description;
		this.totalRating = totalRating;
		this.ratingCount = ratingCount;
		this.actors = actors;
	}
	
	//getter functions
	public String getTitle()
	{
		return title;
	}
	
	public String getDirector()
	{
		return director;
	}
	
	public String getImage()
	{
		return image;
	}
	
	public ArrayList<String> getWriters()
	{
		return writers;
	}
	
	public String getYear()
	{
		return year;
	}
	
	public String getGenre()
	{
		return genre;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	//String Value of totalRating
	public String getTotalRatingString()
	{
		return totalRating;
	}
	
	public double getTotalRating()
	{
		return Double.parseDouble(totalRating);
	}
	
	
	public String getRatingCountString()
	{
		return ratingCount;
	}
	
	public int getRatingCount()
	{
		return Integer.parseInt(ratingCount);
	}
	
	public ArrayList<Actor> getActors()
	{
		return actors;
	}
	
	public void addRating(int rating)
	{
		if(totalRating==null||totalRating.equals(""))
		{
			totalRating = "0";
		}
		if(ratingCount==null||ratingCount.equals(""))
		{
			ratingCount = "0";
		}
		totalRating = ((Integer)(Integer.parseInt(totalRating)+rating)).toString();
		ratingCount = ((Integer)(Integer.parseInt(ratingCount)+1)).toString();
	}
	
}