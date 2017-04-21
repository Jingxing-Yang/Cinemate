package client;

public class Event {
	private String action;
	private String movieName = "";
	private String movieId = "";
	private String rating = "";
	private String time = "";
	
	public Event(String action, String movieName, String rating)
	{
		this.action = action;
		this.movieName = movieName;
		this.rating = rating;
	}
	
	public Event(String action, String movieName,String movieID, String rating,String time)
	{
		this.action = action;
		this.movieName = movieName;
		this.movieId = movieID;
		this.rating = rating;
		this.time = time;
	}
	
	//none-rating events
	public Event(String action, String movieName)
	{
		this.action = action;
		this.movieName = movieName;
		this.rating = "";
	}
	
	public Event(Event event)
	{
		this.action = event.action;
		this.movieName = event.movieName;
		this.rating = event.rating;
		this.movieId = event.movieId;
		this.time = event.time;
	}
	
	public String getAction()
	{
		return action;
	}
	
	public String getMovieName()
	{
		return movieName;
	}
	
	public String getRating()
	{
		return rating;
	}
	
	public String printEvent(String username)
	{
		
		String event = username+" "+action+" "+ "the movie \""+movieName+"\"";
		if(rating!=null&&!rating.isEmpty())
		{
			event+="(rating: " + rating +")";
		}
		event+=".";
		return event;
	}
	public String getTime()
	{
		return time;
	}
	
	public String getMovieID()
	{
		return movieId;
	}
}
