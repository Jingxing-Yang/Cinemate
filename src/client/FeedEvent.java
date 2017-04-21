package client;

public class FeedEvent extends Event{
	String username;
	String fullname;
	String profileImg;
	String movieImg = "";

	public FeedEvent(String username,String fullname, String profileImg, Event event )
	{
		super(event);
		this.username = username;
		this.fullname = fullname;
		this.profileImg = profileImg;
	}
}
