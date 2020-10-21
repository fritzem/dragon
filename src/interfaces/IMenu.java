package interfaces;

public interface IMenu {
	public boolean execute();
	public boolean execute(IMenu m);
	public void close();
}
