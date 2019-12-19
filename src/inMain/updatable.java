package inMain;

public interface updatable {
	
	default public void addUpdate()
	{
		State.addUpdate(this);
	}
	default public void removeUpdate()
	{
		State.removeUpdate(this);
	}
	public void update(long delta);
}
