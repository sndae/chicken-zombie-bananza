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
	
	/*status used for zombie chicken objects.
	 * 0. standing
	 * 1. walking
	 * 2. attacking
	 * 3. injured
	 * 4. dead
	*/
	private LifeformEntityStateEnum state;
	
	public LifeformEntity(PositionPublisher positionPublisher, OrientationPublisher orientationPublisher) {
		super(positionPublisher, orientationPublisher, GameEntityTagEnum.LIFEFORM);
		this.maxHealth = 100;
		this.currentHealth = 100;
		this.state = LifeformEntityStateEnum.ALIVE;
	}
	
	public LifeformEntity(int health, LifeformEntityStateEnum status, GeocentricCoordinate position, LocalOrientation orientation){
		super(position, orientation, GameEntityTagEnum.LIFEFORM);
		this.maxHealth = health;
		this.currentHealth = health;
		this.state = status;
	}	
	
	int getHealth(){
		return currentHealth;
	}
	
	LifeformEntityStateEnum getStatus(){
		return state;
	}
	
	void setHealth(int currentHealth){
		this.currentHealth = currentHealth;
	}
	
	void setStatus(LifeformEntityStateEnum newstatus){
		this.state = newstatus;
	}

	/* (non-Javadoc)
	 * @see ucf.chickenzombiebonanza.game.entity.GameEntity#interactWith(ucf.chickenzombiebonanza.game.entity.GameEntity)
	 */
	@Override
	public void interactWith(GameEntity entity) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see ucf.chickenzombiebonanza.game.item.HasInventory#addItem(ucf.chickenzombiebonanza.game.item.InventoryObject)
	 */
	@Override
	public boolean addItem(InventoryObject item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void destroyEntity() {
		super.destroyEntity();		
	}
	
	
	
	
	
	
}
