package ucf.chickenzombiebonanza.game.entity;

import ucf.chickenzombiebonanza.common.GeocentricCoordinate;
import ucf.chickenzombiebonanza.common.LocalOrientation;
import ucf.chickenzombiebonanza.game.item.HasInventory;
import ucf.chickenzombiebonanza.game.item.InventoryObject;

/**
 * @author Jolene
 *
 */
public class LifeformEntity extends GameEntity implements HasInventory {
	
	private int health;
	
	/*status used for zombie chicken objects.
	 * 0. standing
	 * 1. walking
	 * 2. attacking
	 * 3. injured
	 * 4. dead
	*/
	private LifeformEntityStateEnum state;
	
	public LifeformEntity(GeocentricCoordinate position, LocalOrientation orientation) {
		super(position, orientation, GameEntityTagEnum.LIFEFORM);
		this.health = 100;
		this.state = LifeformEntityStateEnum.ALIVE;
		
	}
	
	public LifeformEntity(int health, LifeformEntityStateEnum status, GeocentricCoordinate position, LocalOrientation orientation){
		super(position, orientation, GameEntityTagEnum.LIFEFORM);
		this.health = health;
		this.state = status;
		
	}
	
	
	
	int getHealth(){
		return health;
	}
	
	LifeformEntityStateEnum getStatus(){
		return state;
	}
	
	void setHealth(int newhealth){
		this.health = newhealth;
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



	/* (non-Javadoc)
	 * @see ucf.chickenzombiebonanza.game.entity.GameEntity#destroyEntity()
	 */
	@Override
	public void destroyEntity() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	
}
