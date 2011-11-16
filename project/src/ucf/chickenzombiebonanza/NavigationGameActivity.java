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
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
	
	private static double lat;
    private static double lon;
	private List<Overlay> mapOverlays;
    private Drawable drawable1;
    private MyItemizedOverlay itemizedOverlay1;
    private boolean enemyIsDisplayed = false;
    private int latE6;
    private int lonE6;
    private MapController mapControl;
    private GeoPoint gp;
    private Button overlayButton;
    

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.main);

		// create a map view
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		mapView.setSatellite(false);

		mapController = mapView.getController();
		mapController.setZoom(14); // Zoom 1 is world view
		
		 // Convert lat/long in degrees into integers in microdegrees
        latE6 =  (int) (lat*1e6);
        lonE6 = (int) (lon*1e6);
        
        gp = new GeoPoint(latE6, lonE6);
        //mapControl.animateTo(gp);  
        
       
        
        

     // Button to control enemy overlay
        overlayButton = (Button)findViewById(R.id.doOverlay);
        overlayButton.setOnClickListener(new OnClickListener(){      
            public void onClick(View v) {	
            	setOverlay1();
            }
            public void setOverlay1(){	
                int enemyLength = enemy.length;
                // Create itemizedOverlay2 if it doesn't exist and display all three items
                if(! enemyIsDisplayed){
                mapOverlays = mapView.getOverlays();	
                //drawable1 = this.getClass().getResources().getDrawable(R.drawable.chickendrawing); 
                itemizedOverlay1 = new MyItemizedOverlay(drawable1); 
                // Display all three items at once
                for(int i=0; i<3; i++){
                   // itemizedOverlay1.addOverlayenemy[i]);
                }
                mapOverlays.add(itemizedOverlay1);
                //enemyIsDisplayed = !enemyIsDisplayed;
                // Remove each item successively with button clicks
                } else {			
                    itemizedOverlay1.removeItem(itemizedOverlay1.size()-1);
                    if((itemizedOverlay1.size() < 1))  enemyIsDisplayed = false;
                }    
                // Added symbols will be displayed when map is redrawn so force redraw now
                mapView.postInvalidate(); 
            }
        });

			
	}
	//array list to store enemy positions static storage
		private OverlayItem [] enemy = {
	            new OverlayItem( new GeoPoint(35952967,-83929158), "enemy 1", "zombie chicken 1"), 
	            new OverlayItem( new GeoPoint(35953000,-83928000), "enemy 2", "zombie chicken 2"),
	            new OverlayItem( new GeoPoint(35955000,-83929158), "enemy 3", "zombie chicken 3") 
	        };
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
				GameManager.getInstance().updateGameState(
						GameStateEnum.GAME_SETTINGS);
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