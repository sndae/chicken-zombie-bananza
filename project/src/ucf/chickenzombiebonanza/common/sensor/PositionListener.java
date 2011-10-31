package ucf.chickenzombiebonanza.common.sensor;

import ucf.chickenzombiebonanza.common.GeocentricCoordinate;

public interface PositionListener {
	
	public void receivePositionUpdate(GeocentricCoordinate pt);

}
