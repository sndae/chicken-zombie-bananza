package ucf.chickenzombiebonanza.game.item;

public class HealthInventoryObject extends InventoryObject {
	
	public static final HealthInventoryObject SMALL_HEALTH_KIT = new HealthInventoryObject("Small Health Kit", 10);
	
	public static final HealthInventoryObject MEDIUM_HEALTH_KIT = new HealthInventoryObject("Medium Health Kit", 20);
	
	public static final HealthInventoryObject LARGE_HEALTH_KIT = new HealthInventoryObject("Large Health Kit", 30);
	
	private int count = 1;
	
	private final int healingAmount;
	
	public HealthInventoryObject(String name, int amount) {
		super(name, InventoryObject.InventoryObjectTypeEnum.HEALTH);
		this.healingAmount = amount;
	}
	
	public int getHealingAmount() {
		return healingAmount;
	}
	
	public int getCount() {
		return count;
	}
	
	public void incrementCount(int amount) {
		count += amount;
	}
	
	public boolean use() {
		if(count > 0) {
			count -= 1;
			return true;
		}
		return false;
	}

}
