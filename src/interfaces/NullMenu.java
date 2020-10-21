package interfaces;

public class NullMenu implements IMenu {
	//Nothing happened
	public boolean execute(IMenu m) {
		return false;
	}
	public boolean execute() {
		return false;
	}
	public void close() { }

}
