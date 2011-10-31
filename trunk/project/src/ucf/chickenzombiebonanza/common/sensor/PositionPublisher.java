package ucf.chickenzombiebonanza.common.sensor;

import java.util.ArrayList;
import java.util.List;

import ucf.chickenzombiebonanza.common.GeocentricCoordinate;

public class PositionPublisher {
	
	private GeocentricCoordinate currentPosition = new GeocentricCoordinate();
	
	private List<PositionListener> positionListeners = new ArrayList<PositionListener>();
	
	public PositionPublisher() {
		
	}
	
	public void updatePosition(GeocentricCoordinate coordinate) {
		currentPosition = coordinate;
		for(PositionListener i : positionListeners) {
			i.receivePositionUpdate(currentPosition);
		}
	}
	
	public GeocentricCoordinate getCurrentPosition() {
		return currentPosition;
	}
	
	public void registerForPositionUpdates(PositionListener listener) {
		positionListeners.add(listener);
	}
	
	public void unregisterForPositionUpdates(PositionListener listener) {
		positionListeners.remove(listener);
	}

}
