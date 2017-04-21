package client;

//movie object for use in DB
public class MovieR {
	private String imdbID;
	private String title;
	private int totalRating = 0;
	private int ratingCount = 0;
	public MovieR(String imdbID, String title, int totalRating, int ratingCount)
	{
		this.imdbID = imdbID;
		this.title = title;
		this.totalRating = totalRating;
		this.ratingCount = ratingCount;
	}
	
	public String getImdbID()
	{
		return imdbID;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public int getTotalRating()
	{
		return totalRating;
	}
	
	public int getRatingCount()
	{
		return ratingCount;
	}
	
	public void addRating(int rating)
	{
		totalRating+=rating;
		ratingCount++;
	}
}
