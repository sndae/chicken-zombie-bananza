package ucf.chickenzombiebonanza.game.entity;

import ucf.chickenzombiebonanza.common.GeocentricCoordinate;
import ucf.chickenzombiebonanza.common.LocalOrientation;

public class PowerUpEntity extends GameEntity {
	
	private double activationDistance;
	
	public PowerUpEntity(double activationDistance, GeocentricCoordinate position, LocalOrientation orientation) {
		super(position,orientation);
		this.activationDistance = activationDistance;
	}

	@Override
	public void destroyEntity() {
		// TODO Auto-generated method stub

	}

	@Override
	public void interactWith(GameEntity entity) {
		// TODO Auto-generated method stub

	}

}
