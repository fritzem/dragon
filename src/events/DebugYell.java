package events;

public class DebugYell implements Event{
	
	public void activate()
	{
		System.out.println("The event has activated");
	}
}
