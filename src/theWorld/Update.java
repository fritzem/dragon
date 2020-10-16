package theWorld;

import java.util.ArrayList;
import java.util.Iterator;

import inMain.updatable;

public class Update {
	private static Iterator<updatable> upIt;
	private ArrayList<updatable> updates;
	private ArrayList<updatable> tempUpdates;
	
	
	public Update() {
		updates = new ArrayList<updatable>();
		tempUpdates = new ArrayList<updatable>(); 
	}
	
	public Update(updatable up) {
		updates = new ArrayList<updatable>(0);
		tempUpdates = new ArrayList<updatable>(0);
		tempUpdates.add(up);
	}
	
	public void addUpdate(updatable update)
	{
		tempUpdates.add(update);
	}
	
	public void removeUpdate(updatable update)
	{
		updates.remove(update);
		updates.trimToSize();
	}
	
	public boolean update(long delta)
	{
		updateArraylist();
		upIt = updates.iterator();
		while (upIt.hasNext())
		{
			updatable u = upIt.next();
			if (u.update(delta))
				upIt.remove();
		}
		updates.trimToSize();
		return (updates.isEmpty());
	}
	
	public void updateArraylist()
	{
		tempUpdates.trimToSize();
		for (updatable u : tempUpdates)
		{
			updates.add(u);
		}
		updates.trimToSize();
		tempUpdates = new ArrayList<updatable>();
	}
}
