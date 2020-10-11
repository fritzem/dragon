package interfaces;

public class MenuFactory {
	//Simple factory to generate menus from id
	public static IMenu getMenu(String id) {
		switch (id.toUpperCase()) {
		case "TEST":
			return new TextMenu("Hello");
		default:
			return new NullMenu();	
		}
	}
}
