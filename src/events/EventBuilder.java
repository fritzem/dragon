package events;

import org.w3c.dom.Element;

public class EventBuilder {
	public static Event buildEvent(Element event)
	{
		return new DebugYell();
	}
}
