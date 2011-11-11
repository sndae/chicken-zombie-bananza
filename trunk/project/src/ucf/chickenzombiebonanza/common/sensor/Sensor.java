package ucf.chickenzombiebonanza.common.sensor;

import java.util.ArrayList;
import java.util.List;

public abstract class Sensor {
	private final List<SensorStatusListener> listeners = new ArrayList<SensorStatusListener>();
	
	private boolean sensorIsActive = false;
	
	public boolean isSensorActive() {
		return sensorIsActive;
	}
	
	protected void setSensorState(boolean isActive) {
		boolean beforeState = sensorIsActive;
		sensorIsActive = isActive;
		if(sensorIsActive && beforeState == false) {
			for(SensorStatusListener i : listeners) {
				i.onSensorActive();
			}
		} else if(!sensorIsActive && beforeState == true) {
			for(SensorStatusListener i : listeners) {
				i.onSensorInactive();
			}
		}
	}
	
	public void registerSensorStatusListener(SensorStatusListener listener) {
		if(!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}
	
	public void unregisterSensorStatusListener(SensorStatusListener listener) {
		listeners.remove(listener);
	}
}
