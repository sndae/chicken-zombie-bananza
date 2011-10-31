package ucf.chickenzombiebonanza.game.item;

import java.util.ArrayList;
import java.util.List;

import ucf.chickenzombiebonanza.common.Vector3d;

public class WeaponInventoryObject extends InventoryObject {
	
	public class WeaponDischarge {
		private Vector3d trajectory;
		
		private int dischargeDamage;
		
		private double velocity;
		
		private double radius;
		
		public WeaponDischarge(Vector3d trajectory, int dischargeDamage,
				double velocity, double radius) {
			this.trajectory = trajectory;
			this.dischargeDamage = dischargeDamage;
			this.velocity = velocity;
			this.radius = radius;
		}
	}
	
	private final List<WeaponDischarge> weaponDischarges = new ArrayList<WeaponDischarge>();
	
	public WeaponInventoryObject(String name, int damage) {
		super(name);
	}
	
}
