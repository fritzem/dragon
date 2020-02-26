package interfaces;

import items.Torch;

public class InventoryMenu extends SelectionMenu{

	public InventoryMenu() {
		super(144, 40, 96, 32, 1, 1, "ITEM");
		yOffset = 8;
		showName = false;
	}

	public void getMenu() {
		list[0][0] = new Torch();
	}

}
