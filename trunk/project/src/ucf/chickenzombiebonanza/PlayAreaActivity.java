/**
 * Copyright (c) 2011, Chicken Zombie Bonanza Project
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Chicken Zombie Bonanza Project nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL CHICKEN ZOMBIE BONANZA PROJECT BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package ucf.chickenzombiebonanza;

import ucf.chickenzombiebonanza.common.sensor.PositionListener;
import geotransform.coords.Gcc_Coord_3d;
import geotransform.coords.Gdc_Coord_3d;
import geotransform.ellipsoids.WE_Ellipsoid;
import geotransform.transforms.Gcc_To_Gdc_Converter;

import java.util.List;

import ucf.chickenzombiebonanza.common.GeocentricCoordinate;
import ucf.chickenzombiebonanza.game.GameManager;
import ucf.chickenzombiebonanza.game.GameStateEnum;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


public class PlayAreaActivity extends AbstractGameMapActivity implements PositionListener {

	private MapController mapController;
	private MapView mapView;
	
    
    public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.main);
		
		// create a map view
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		mapView.setSatellite(false);

		mapController = mapView.getController();
		mapController.setZoom(21); // Zoom 1 is world view
    
    
   
		
		mapView.setSatellite(!mapView.isSatellite());
		mapView.postInvalidate();
		centerOnLocation();
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	
	
	@Override
	public void receivePositionUpdate(GeocentricCoordinate pt) {
		Gcc_To_Gdc_Converter.Init(new WE_Ellipsoid());
		Gcc_Coord_3d gcc = new Gcc_Coord_3d(pt.getX(),pt.getY(),pt.getZ());
		Gdc_Coord_3d gdc = new Gdc_Coord_3d();
		Gcc_To_Gdc_Converter.Convert(gcc, gdc);
		mapController.animateTo(new GeoPoint((int)(gdc.latitude * 1E6), (int)(gdc.longitude * 1E6)));	
	}
	
	private void centerOnLocation() {
		GeocentricCoordinate pt = GameManager.getInstance().getPlayerEntity().getPosition();

		Gcc_To_Gdc_Converter.Init(new WE_Ellipsoid());
		Gcc_Coord_3d gcc = new Gcc_Coord_3d(pt.getX(),pt.getY(),pt.getZ());
		Gdc_Coord_3d gdc = new Gdc_Coord_3d();
		Gcc_To_Gdc_Converter.Convert(gcc, gdc);
		mapController.animateTo(new GeoPoint((int)(gdc.latitude * 1E6), (int)(gdc.longitude * 1E6)));	
	        }
	
	
	// when you push the s key it will go to satellite view and back
		// when you push the c key, it will center back to the user
		public boolean onKeyDown(int keyCode, KeyEvent e) {
			if (keyCode == KeyEvent.KEYCODE_S) {
				mapView.setSatellite(!mapView.isSatellite());
				return true;

			}
			
			else if (keyCode == KeyEvent.KEYCODE_C) {
				centerOnLocation();
			}
			return (super.onKeyDown(keyCode, e));
		}
	
	
}
