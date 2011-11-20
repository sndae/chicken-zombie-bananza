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

import java.util.List;

import ucf.chickenzombiebonanza.game.GameManager;
import ucf.chickenzombiebonanza.game.GameStateEnum;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import android.content.Context;

import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 
 */
public class NavigationGameActivity extends AbstractGameMapActivity {
	/** Called when the activity is first created. */

	private MapController mapController;
	private MapView mapView;
	
	private static double lat=28.53551;
    private static double lon=-81.382256;
    private static double lat2=28.53561;
    private static double lon2=-81.382266;
	private List<Overlay> mapOverlays;
    private Drawable drawable1;
    private MyItemizedOverlay itemizedOverlay1;
    private boolean enemyIsDisplayed = false;
    private int latE6;
    private int lonE6;
    private int latE61;
    private int lonE62;
    private MapController mapControl;
    private GeoPoint gp;
    private GeoPoint gp1;
    private GeoPoint gp2;
    private Button overlayButton;
    

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.main);
		//setContentView(R.layout.mapme);
		
		// create a map view
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		mapView.setSatellite(false);

		mapController = mapView.getController();
		mapController.setZoom(21); // Zoom 1 is world view
		
		 // Convert lat/long in degrees into integers in microdegrees
        latE6 =  (int) (lat*1e6);
        lonE6 = (int) (lon*1e6);
        
        latE61 =  (int) (lat2*1e6);
        lonE62 = (int) (lon2*1e6);
        
       
        
       
        
        //gp = new GeoPoint(latE6, lonE6);       
        //mapController.animateTo(gp);
        
        
        // Set up location manager for determining present location of phone
        LocationManager locman;
        Location loc;
        String provider = LocationManager.GPS_PROVIDER;        
        locman = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
        loc = locman.getLastKnownLocation(provider);
        lat = loc.getLatitude();
        lon = loc.getLongitude();
        GeoPoint newPoint = new GeoPoint((int)(lat*1e6),(int)(lon*1e6)); 	   
        mapController.animateTo(newPoint);
        
        
        //if want satellite view this allows us to zoom in more as we will need for the map
        mapView.setSatellite(!mapView.isSatellite());
        
        
       
        
        
        

        //arraylist of posible enemy postions
        OverlayItem [] enemy = {
            new OverlayItem( new GeoPoint(latE6, lonE6), "enemy 1", "more details"), 
            new OverlayItem( new GeoPoint(latE61,lonE62), "enemy 1", "more details") 
             
        };
        
        mapOverlays = mapView.getOverlays();
        drawable1 = this.getResources().getDrawable(R.drawable.chickendrawing2);
        itemizedOverlay1 = new MyItemizedOverlay(drawable1);
          
        for (int i=0; i<2 ;i++) {
        	itemizedOverlay1.addOverlay(enemy[i]);
        }
        mapOverlays.add(itemizedOverlay1);
        
             
        
        //this will refresh map i think?
        mapView.postInvalidate();
        
      
        

	}
	
	 
	//when you push the s key it will go to satellite view and back
	public boolean onKeyDown(int keyCode, KeyEvent e){
        if(keyCode == KeyEvent.KEYCODE_S){
            mapView.setSatellite(!mapView.isSatellite());
            return true;
        
        }
            return(super.onKeyDown(keyCode, e));
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.navigation_menu, menu);
	    return true;
	}
	
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			case R.id.settingsoption:
				GameManager.getInstance().updateGameState(GameStateEnum.GAME_SETTINGS);
				return true;
			case R.id.launchshootinggame:
				GameManager.getInstance().updateGameState(GameStateEnum.GAME_SHOOTING);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onBackPressed() {
		return;
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}