package ucf.chickenzombiebonanza.game.item;

public abstract class InventoryObject {
	
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
	
	@Override
	public boolean equals(Object object) {
		if(object instanceof InventoryObject) {
			InventoryObject otherObject = (InventoryObject)object;
			if(otherObject.getName().equals(getName())) {
				return true;
			}
		}
		return false;
	}
	
	public abstract boolean use();
	
	public String toString() {
		return "InventoryObject [Name:" + getName() + ", Type:" + getType().toString() + "]";
	}

}
