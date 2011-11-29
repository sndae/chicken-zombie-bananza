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

import geotransform.coords.Gcc_Coord_3d;
import geotransform.coords.Gdc_Coord_3d;
import geotransform.ellipsoids.WE_Ellipsoid;
import geotransform.transforms.Gcc_To_Gdc_Converter;

import java.util.List;

import ucf.chickenzombiebonanza.common.GeocentricCoordinate;
import ucf.chickenzombiebonanza.common.sensor.PositionListener;
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
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * 
 */
public class NavigationGameActivity extends AbstractGameMapActivity implements PositionListener {
	/** Called when the activity is first created. */

	private MapController mapController;
	private MapView mapView;
	
	private static double lat;
    private static double lon;
	private List<Overlay> mapOverlays;
    private Drawable drawable1;
    private Drawable drawable2;
    private Drawable drawable3;
    private Drawable drawable4;
    private MyItemizedOverlay itemizedOverlay1;
    private MyItemizedOverlay itemizedOverlay2;
    private MyItemizedOverlay itemizedOverlay3;
    private MyItemizedOverlay itemizedOverlay4;
    private int level;
    private double multiplier1a = 1.000001;
    private double multiplier1b = 1.000002;
    private double multiplier1c = 1.000003;
    private double multiplier1d = 1.000004;

    @Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.main);
		
		// create a map view
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		mapView.setSatellite(false);

		mapController = mapView.getController();
		mapController.setZoom(21); // Zoom 1 is world view
		
		final ProgressDialog dialog = ProgressDialog.show(this, "Waiting for player position",
				"Waiting for GPS position...", true);
		Thread loadThread = new Thread() {
			@Override
			public void run() {
				while (GameManager.getInstance().getPlayerEntity()
						.getPosition().isZero()) {
					synchronized (this) {
						try {

							this.wait(10);
						} catch (InterruptedException e) {
						}
					}
				}
				dialog.dismiss();

				// if want satellite view this allows us to zoom in more as we
				// will need for the map
				mapView.setSatellite(!mapView.isSatellite());

				// need to make enemies appear in range of center point then
				// move to it
				level = 3;

				// arraylist of posible enemy postions
				OverlayItem[] enemy = {
						new OverlayItem(new GeoPoint(
								(int) ((multiplier1b * lat * 1e6)),
								(int) ((lon * 1e6))), "enemy 1", "more details"),
						new OverlayItem(new GeoPoint((int) ((lat * 1e6)),
								(int) ((multiplier1a * lon * 1e6))), "enemy 1",
								"more details"),
						new OverlayItem(new GeoPoint(
								(int) ((lat / multiplier1b * 1e6)),
								(int) ((lon * 1e6))), "enemy 1", "more details"),
						new OverlayItem(new GeoPoint((int) ((lat * 1e6)),
								(int) (((lon / multiplier1a) * 1e6))),
								"enemy 1", "more details"),
						new OverlayItem(new GeoPoint(
								(int) ((multiplier1d * lat * 1e6)),
								(int) ((lon * 1e6))), "enemy 1", "more details"),
						new OverlayItem(new GeoPoint((int) ((lat * 1e6)),
								(int) ((multiplier1c * lon * 1e6))), "enemy 1",
								"more details"),
						new OverlayItem(new GeoPoint(
								(int) ((lat / multiplier1d * 1e6)),
								(int) ((lon * 1e6))), "enemy 1", "more details"),
						new OverlayItem(new GeoPoint((int) ((lat * 1e6)),
								(int) (((lon / multiplier1c) * 1e6))),
								"enemy 1", "more details")

				};

				OverlayItem[] powerups = {
						new OverlayItem(new GeoPoint(
								(int) ((multiplier1c * lat * 1e6)),
								(int) ((lon * 1e6))), "enemy 1", "more details"),
						new OverlayItem(new GeoPoint((int) ((lat * 1e6)),
								(int) ((multiplier1b * lon * 1e6))), "enemy 1",
								"more details"),
						new OverlayItem(new GeoPoint(
								(int) ((lat / multiplier1c * 1e6)),
								(int) ((lon * 1e6))), "enemy 1", "more details"),
						new OverlayItem(new GeoPoint((int) ((lat * 1e6)),
								(int) (((lon / multiplier1b) * 1e6))),
								"enemy 1", "more details")

				};

				OverlayItem[] waypoints = {
						new OverlayItem(new GeoPoint(
								(int) ((multiplier1d * lat * 1e6)),
								(int) ((lon * 1e6))), "enemy 1", "more details"),
						new OverlayItem(new GeoPoint((int) ((lat * 1e6)),
								(int) ((multiplier1c * lon * 1e6))), "enemy 1",
								"more details"),
						new OverlayItem(new GeoPoint(
								(int) ((lat / multiplier1d * 1e6)),
								(int) ((lon * 1e6))), "enemy 1", "more details"),
						new OverlayItem(new GeoPoint((int) ((lat * 1e6)),
								(int) (((lon / multiplier1c) * 1e6))),
								"enemy 1", "more details")

				};
				
				OverlayItem[] hero = { new OverlayItem(new GeoPoint(
						(int) (lat * 1e6), (int) (lon * 1e6)), "hero 1",
						"more details")

				};

				mapOverlays = mapView.getOverlays();
				drawable1 = NavigationGameActivity.this.getResources().getDrawable(
						R.drawable.chickendrawing2);
				itemizedOverlay1 = new MyItemizedOverlay(drawable1);
				drawable2 = NavigationGameActivity.this.getResources().getDrawable(R.drawable.hero);
				itemizedOverlay2 = new MyItemizedOverlay(drawable2);
				drawable3 = NavigationGameActivity.this.getResources().getDrawable(R.drawable.powerup);
				itemizedOverlay3 = new MyItemizedOverlay(drawable3);
				drawable4 = NavigationGameActivity.this.getResources()
						.getDrawable(R.drawable.waypoint);
				itemizedOverlay4 = new MyItemizedOverlay(drawable4);

				putHeroOnScreen(hero);
				putEnemiesOnScreen(enemy);
				putPowerupsOnScreen(powerups);
				putWaypointsOnScreen(waypoints);

				// this will refresh map i think?
				mapView.postInvalidate();
			}
		};

		loadThread.start();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		GameManager.getInstance().getPlayerEntity().getPositionPublisher().pauseSensor();
		GameManager.getInstance().getPlayerEntity().getPositionPublisher().unregisterForPositionUpdates(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		GameManager.getInstance().getPlayerEntity().getPositionPublisher()
				.resumeSensor();
		GameManager.getInstance().getPlayerEntity().getPositionPublisher().registerForPositionUpdates(this);
		if (GameManager.getInstance().getPlayerEntity().isDead()) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder
					.setMessage(
							"You have died. Your score this session was ####. Would you like to try again?")
					.setCancelable(false).setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									GameManager.getInstance().restart();
								}
							}).setNegativeButton("Surrender",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									GameManager.getInstance().restart();
									NavigationGameActivity.this
											.moveTaskToBack(true);
								}
							});
			AlertDialog alert = builder.create();
			alert.show();
		}
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	private void putHeroOnScreen(OverlayItem[] hero) {
		// add hero
		for (int i = 0; i < 1; i++) {
			itemizedOverlay2.addOverlay(hero[i]);
		}

		mapOverlays.add(itemizedOverlay2);
	}

	private void putWaypointsOnScreen(OverlayItem[] waypoints) {
		// add waypoints
		for (int i = 0; i < 4; i++) {
			itemizedOverlay4.addOverlay(waypoints[i]);
		}

		mapOverlays.add(itemizedOverlay4);
	}

	private void putPowerupsOnScreen(OverlayItem[] powerups) {
		// add powerups
		for (int i = 0; i < 4; i++) {
			itemizedOverlay3.addOverlay(powerups[i]);
		}

		mapOverlays.add(itemizedOverlay3);
	}

	private void putEnemiesOnScreen(OverlayItem[] enemy) {

		if (level == 1) {
			// add enemies
			for (int i = 0; i < 4; i++) {
				itemizedOverlay1.addOverlay(enemy[i]);
			}
			mapOverlays.add(itemizedOverlay1);
		} else if (level == 2) {
			// add enemies
			for (int i = 0; i < 6; i++) {
				itemizedOverlay1.addOverlay(enemy[i]);
			}
			mapOverlays.add(itemizedOverlay1);
		} else if (level == 3) {
			// add enemies
			for (int i = 0; i < 8; i++) {
				itemizedOverlay1.addOverlay(enemy[i]);
			}
			mapOverlays.add(itemizedOverlay1);
		}
	}

	// when you push the s key it will go to satellite view and back
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
		this.moveTaskToBack(true);
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
	
}