package ucf.chickenzombiebonanza.game.item;

public class HealthInventoryObject extends InventoryObject {
	
	public static final HealthInventoryObject SMALL_HEALTH_KIT = new HealthInventoryObject("Small Health Kit", 10);
	
	public static final HealthInventoryObject MEDIUM_HEALTH_KIT = new HealthInventoryObject("Medium Health Kit", 20);
	
	public static final HealthInventoryObject LARGE_HEALTH_KIT = new HealthInventoryObject("Large Health Kit", 30);
	
	private final int amount;
	
	public HealthInventoryObject(String name, int amount) {
		super(name, InventoryObject.InventoryObjectTypeEnum.HEALTH);
		this.amount = amount;
	}
	
	public int getHealingAmount() {
		return amount;
	}

}
