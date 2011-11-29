package ucf.chickenzombiebonanza.game.item;

public class InventoryObject {
	
	private final String name;
	
	private final InventoryObjectTypeEnum type;
	
	public enum InventoryObjectTypeEnum {
		WEAPON,
		HEALTH,
	}
	
	public InventoryObject(String name, InventoryObjectTypeEnum type) {
		this.name = name;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public InventoryObjectTypeEnum getType() {
		return type;
	}

}
