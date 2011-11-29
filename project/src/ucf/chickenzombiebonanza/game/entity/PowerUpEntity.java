package ucf.chickenzombiebonanza.game.entity;

import java.util.ArrayList;
import java.util.List;

import ucf.chickenzombiebonanza.common.GeocentricCoordinate;
import ucf.chickenzombiebonanza.common.LocalOrientation;
import ucf.chickenzombiebonanza.game.item.InventoryObject;

public class PowerUpEntity extends GameEntity {
	
	private double activationDistance;
	
	private InventoryObject item;
	
	private final List<ObjectActivationListener> activationListeners = new ArrayList<ObjectActivationListener>(1);
		
	public PowerUpEntity(InventoryObject item, double activationDistance, GeocentricCoordinate position) {
		super(position, new LocalOrientation(), GameEntityTagEnum.POWER_UP);
		this.activationDistance = activationDistance;
	}

	@Override
	public void destroyEntity() {
		super.destroyEntity();
		activationListeners.clear();
	}

	@Override
	public void interactWith(GameEntity entity) {
		if (entity.getPosition().distanceFrom(getPosition()) < activationDistance) {
			if(entity.getTag() == GameEntityTagEnum.LIFEFORM) {
				LifeformEntity lifeform = (LifeformEntity)entity;
				if(!lifeform.isEnemy()) {
	 				lifeform.addItem(item);
					for (ObjectActivationListener i : activationListeners) {
						i.objectActivated(entity);
					}
    				destroyEntity();
    			}
			}
		}
	}
	
	public void addActivationListener(ObjectActivationListener listener) {
		activationListeners.add(listener);
	}
}
