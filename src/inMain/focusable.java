package inMain;
public interface focusable {

	default public void addFocus()
	{
		State.addFocus(this);
	}
	default public void clearFocus()
	{
		State.popFocus();
	}
	public abstract void input();
}
