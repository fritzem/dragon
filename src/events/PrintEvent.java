package events;

import org.w3c.dom.Element;

public class PrintEvent implements Event{
	private String message;
	public PrintEvent()
	{
		message = "There was an error creating this event.";
	}
	public PrintEvent(String e)
	{
		message = "Error creating event: " + e;
	}
	public PrintEvent(Element event) throws Exception
	{
		message = findAttribute(event, "message");
	}
	public void activate()
	{
		System.out.println(message);
	}
}
