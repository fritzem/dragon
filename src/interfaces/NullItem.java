package interfaces;

public class NullItem implements MenuItem{	
	public boolean execute() {
		return false;
	}

	public String getName() {
		return "Null";
	}

}
