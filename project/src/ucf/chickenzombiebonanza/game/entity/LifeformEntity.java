package ucf.chickenzombiebonanza.game.entity;

import ucf.chickenzombiebonanza.common.GeocentricCoordinate;
import ucf.chickenzombiebonanza.common.LocalOrientation;
import ucf.chickenzombiebonanza.common.sensor.OrientationPublisher;
import ucf.chickenzombiebonanza.common.sensor.PositionPublisher;
import ucf.chickenzombiebonanza.game.item.HasInventory;
import ucf.chickenzombiebonanza.game.item.InventoryObject;

/**
 * @author Jolene
 *
 */
public class LifeformEntity extends GameEntity implements HasInventory {
	
	private int maxHealth, currentHealth;
	
	private boolean isEnemy = false;
	
	private LifeformEntityStateEnum state;
	
	public LifeformEntity(boolean isEnemy, int health, PositionPublisher positionPublisher, OrientationPublisher orientationPublisher) {
		super(positionPublisher, orientationPublisher, GameEntityTagEnum.LIFEFORM);
		this.isEnemy = isEnemy;
		this.maxHealth = health;
		this.currentHealth = health;
		this.state = LifeformEntityStateEnum.ALIVE;
	}
	
	public LifeformEntity(boolean isEnemy, int health, GeocentricCoordinate position, LocalOrientation orientation){
		super(position, orientation, GameEntityTagEnum.LIFEFORM);
		this.isEnemy = isEnemy;
		this.maxHealth = health;
		this.currentHealth = health;
		this.state = LifeformEntityStateEnum.ALIVE;
	}	
	
	int getHealth(){
		return currentHealth;
	}
	
	LifeformEntityStateEnum getStatus(){
		return state;
	}
	
	private void setHealth(int currentHealth){
		this.currentHealth = currentHealth > maxHealth ? maxHealth : currentHealth;
	}
	
	public int getCurrentHealth() {
		return this.currentHealth;
	}
	
	public boolean isDead() {
		return this.getCurrentHealth() <= 0;
	}
	
	void setStatus(LifeformEntityStateEnum newstatus){
		this.state = newstatus;
	}
	
	public boolean isEnemy() {
		return isEnemy;
	}

	@Override
	public void interactWith(GameEntity entity) {
		if(entity.getTag() == GameEntityTagEnum.LIFEFORM) {
			LifeformEntity lifeform = (LifeformEntity)entity;
			if(this.isEnemy() && !lifeform.isEnemy()) {
				lifeform.damageEntity(5);
			}
		}
	}

	@Override
	public boolean addItem(InventoryObject item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void destroyEntity() {
		super.destroyEntity();		
	}
	
	public void damageEntity(int damage) {
		setHealth(currentHealth - damage);
		if(currentHealth < 0) {
			destroyEntity();
		}
	}
	
	public void healEntity() {
		setHealth(maxHealth);
	}
	
	public void healEntity(int amount) {
		setHealth(currentHealth + amount);
	}
}
