package client;

import java.util.ArrayList;

public class EventSet {
	private User user;
	private ArrayList<FeedEvent> feedEvents;
	public EventSet(User user, ArrayList<FeedEvent>events)
	{
		this.user = user;
		feedEvents = events;
	}
}
