package ucf.chickenzombiebonanza.common.sensor.impl;

import geotransform.coords.Gcc_Coord_3d;
import geotransform.coords.Gdc_Coord_3d;
import geotransform.ellipsoids.WE_Ellipsoid;
import geotransform.transforms.Gdc_To_Gcc_Converter;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import ucf.chickenzombiebonanza.common.GeocentricCoordinate;
import ucf.chickenzombiebonanza.common.sensor.PositionPublisher;

public class GpsListener extends PositionPublisher implements LocationListener {

	@Override
	public void onLocationChanged(Location location) {
		Gdc_Coord_3d gdc = new Gdc_Coord_3d(location.getLatitude(),location.getLongitude(),location.getAltitude());
		Gcc_Coord_3d gcc = new Gcc_Coord_3d();
		Gdc_To_Gcc_Converter.Convert(gdc, gcc);
		GeocentricCoordinate geo = new GeocentricCoordinate(gcc.x,gcc.y,gcc.z);
		this.updatePosition(geo);
	}

	@Override
	public void onProviderDisabled(String provider) {
		// Do nothing
	}

	@Override
	public void onProviderEnabled(String provider) {
		// Do nothing		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// Do nothing
	}

}
