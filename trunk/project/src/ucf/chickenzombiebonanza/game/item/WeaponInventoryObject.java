package ucf.chickenzombiebonanza.game.item;

public class WeaponInventoryObject extends InventoryObject {
	
	public static final WeaponInventoryObject PISTOL_WEAPON = new WeaponInventoryObject("Pistol", 10, 1.0f, 9999);
	
	public static final WeaponInventoryObject REVOLVER_WEAPON = new WeaponInventoryObject("Revolver", 30, 1.5f, 30);
	
	public static final WeaponInventoryObject SHOTGUN_WEAPON = new WeaponInventoryObject("Shotgun", 30, 2.0f, 30);
	
	private final int damageDone;
	
	private final float damageRadius;
	
	private int currentAmmo;
	
	public WeaponInventoryObject(String name, int damage, float radius, int maxAmmo) {
		super(name, InventoryObjectTypeEnum.WEAPON);
		this.damageDone = damage;
		this.damageRadius = radius;
		this.currentAmmo = maxAmmo;
	}
	
	public int getDamageDone() {
		return damageDone;
	}
	
	public float getFireRadius() {
		return damageRadius;
	}
	
	public int getAmmoLeft() {
		return currentAmmo;
	}
	
	public void addAmmo(int amount) {
		currentAmmo += amount;
	}
	
	public boolean fire() {
		if(currentAmmo > 0) {
			currentAmmo -= 1;
			return true;
		}
		return false;
	}
	
}
