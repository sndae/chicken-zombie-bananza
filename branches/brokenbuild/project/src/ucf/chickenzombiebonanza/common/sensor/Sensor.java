package ucf.chickenzombiebonanza.common.sensor;

import java.util.ArrayList;
import java.util.List;

public abstract class Sensor {
	private final List<SensorStatusListener> listeners = new ArrayList<SensorStatusListener>();
	
	private SensorStateEnum sensorState = SensorStateEnum.INACTIVE;
	
	public abstract void onPause();
	
	public abstract void onResume();
	
	public boolean isSensorActive() {
		return sensorState == SensorStateEnum.ACTIVE;
	}
	
	public void pauseSensor() {
	    setSensorState(SensorStateEnum.PAUSED);
	}
	
	public void resumeSensor() {
	    setSensorState(SensorStateEnum.INACTIVE);
	}
	
	public SensorStateEnum getSensorState() {
	    return sensorState;
	}
	
	protected void setSensorState(SensorStateEnum state) {
	    SensorStateEnum beforeState = sensorState;
	    sensorState = state;
		if(beforeState != sensorState) {
		    if(SensorStateEnum.PAUSED == sensorState) {
		        onPause();
		    } else if(SensorStateEnum.PAUSED == beforeState) {
		        onResume();
		    }
		    
			for(SensorStatusListener i : listeners) {
				i.onSensorStatusChange(sensorState);
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
