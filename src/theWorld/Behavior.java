package theWorld;

public enum Behavior {
	STILL, WANDER;
	
	public static Behavior parse(String str) {
		switch (str.toUpperCase()) {
		case "STILL":
			return Behavior.STILL;
		case "WANDER":
			return Behavior.WANDER;
		default:
			return Behavior.STILL;
		}
	}
}
