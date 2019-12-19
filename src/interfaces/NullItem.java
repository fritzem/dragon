package interfaces;

public class NullItem extends Menu{

	public NullItem() {
		super(1, 1, 1, 1, "Null");
	}
	
	public boolean execute() {
		return false;
	}

	public void input() {
		
	}

}
