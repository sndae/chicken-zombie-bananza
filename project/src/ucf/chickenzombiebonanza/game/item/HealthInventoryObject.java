package ucf.chickenzombiebonanza.game.item;

public class HealthInventoryObject extends InventoryObject {
	
	private final int amount;
	
	public HealthInventoryObject(String name, int amount) {
		super(name);
		this.amount = amount;
	}
	
	public int getHealingAmount() {
		return amount;
	}

}
