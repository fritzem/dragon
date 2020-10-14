package events;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class EventBuilder {
	public static Event buildEvent(Element event)
	{
		NodeList properties = event.getElementsByTagName("property");
		String type = "Notvalid";
		for (int i = 0; i < properties.getLength(); i++) 
		{
			if (((Element)properties.item(i)).getAttribute("name").compareTo("type") == 0)
			{
				type = (((Element)properties.item(i)).getAttribute("value"));
				break;
			}
		}
		try {
			switch (type)
			{
				case "warp":
					return new WarpEvent(event);
				case "print":
					return new PrintEvent(event);
				case "indoor":
					return new IndoorEvent();
				case "outdoor":
					return new OutdoorEvent();
				default:
					throw new Exception("No valid event type specified.");
			}
		} catch (Exception e)
		{
			return new PrintEvent(e.toString());
		}
		
	}
}
