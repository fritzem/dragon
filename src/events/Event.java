package events;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public interface Event {
	public void activate();
	public default String findAttribute(Element event, String find) throws Exception
	{
		NodeList properties = event.getElementsByTagName("property");

		for (int i = 0; i < properties.getLength(); i++) 
		{
			if (((Element)properties.item(i)).getAttribute("name").compareTo(find) == 0)
			{
				return (((Element)properties.item(i)).getAttribute("value"));
			}
		}
		throw new Exception("Not all properties specified");
	}
}
