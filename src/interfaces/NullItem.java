package interfaces;

public class NullItem implements MenuItem{	
	public boolean execute(IMenu m) {
		return false;
	}

	public String getName() {
		return "Null";
	}

}
