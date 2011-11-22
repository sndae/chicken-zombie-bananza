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

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ucf.chickenzombiebonanza.android.opengl.ShootingGameGLES20Renderer;
import ucf.chickenzombiebonanza.common.GeocentricCoordinate;
import ucf.chickenzombiebonanza.common.LocalOrientation;
import ucf.chickenzombiebonanza.game.GameManager;
import ucf.chickenzombiebonanza.game.GameStateEnum;
import ucf.chickenzombiebonanza.game.entity.GameEntity;
import ucf.chickenzombiebonanza.game.entity.GameEntityListener;
import ucf.chickenzombiebonanza.game.entity.GameEntityTagEnum;
import ucf.chickenzombiebonanza.game.entity.LifeformEntity;
import ucf.chickenzombiebonanza.game.entity.LifeformEntityStateEnum;
import android.app.ProgressDialog;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Window;
import android.view.WindowManager;

/**
 * 
 */
public class ShootingGameActivity extends AbstractGameActivity implements GameEntityListener {
	
    private final List<GameEntity> gameEntities = new ArrayList<GameEntity>();
    
	private GLSurfaceView glView;
	
	private ShootingGameGLES20Renderer renderer;
	
	private PowerManager.WakeLock wakeLock;
	
	private GeocentricCoordinate shootingGameLocation;
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        renderer = new ShootingGameGLES20Renderer(this);
        glView = new ShootingGameSurfaceView(this);
        setContentView(glView);
        		
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "DoNotDimScreen");
	}
	
	@Override
	protected void onStart() {
	    super.onStart();
        GameManager.getInstance().getPlayerEntity().getOrientationPublisher().registerForOrientationUpdates(renderer);
        GameManager.getInstance().registerGameEntityListener(this, new GameEntityTagEnum[]{GameEntityTagEnum.LIFEFORM});
        
        
        final ProgressDialog dialog = ProgressDialog.show(ShootingGameActivity.this, "Waiting", "Waiting for player position...", true);
        Thread loadThread = new Thread() {
            @Override
            public void run() {
                while(GameManager.getInstance().getPlayerEntity().getPosition().isZero()) {
                    synchronized(this) {
                        try {
                            
                            this.wait(10);
                        } catch (InterruptedException e) {
                        }
                    }
                }
                dialog.dismiss();
                shootingGameLocation = GameManager.getInstance().getPlayerEntity().getPosition();
                
                Timer spawnEnemyTimer = new Timer();
                spawnEnemyTimer.schedule(new TimerTask() {

                    @Override
                    public void run() {
                        GeocentricCoordinate randomCoordinate = GeocentricCoordinate.randomPointAround(ShootingGameActivity.this.getGameLocation(), 8, 3);
                        final GameEntity newEnemy = new LifeformEntity(5, LifeformEntityStateEnum.ALIVE, randomCoordinate, new LocalOrientation());
                        GameManager.getInstance().addGameEntity(newEnemy);
                        Timer killTimer = new Timer();
                        killTimer.schedule(new TimerTask(){
                            @Override
                            public void run() {
                                newEnemy.destroyEntity();
                            }}, 5000);                
                    }
                    
                }, 0, 10000);
                
            }
        };
        
        loadThread.start();

	}
	
    @Override
    protected void onResume() {
        super.onResume();
        glView.onResume();
        GameManager.getInstance().getPlayerEntity().getOrientationPublisher().resumeSensor();
        wakeLock.acquire();
    }
	
	@Override
	protected void onPause() {
		super.onPause();
		glView.onPause();
		GameManager.getInstance().getPlayerEntity().getOrientationPublisher().pauseSensor();
		wakeLock.release();
	}
	
    @Override
    protected void onStop() {
        super.onStop();
        GameManager.getInstance().unregisterGameEntityListener(this);
        GameManager.getInstance().getPlayerEntity().getOrientationPublisher().unregisterForOrientationUpdates(renderer);
    }
	
	@Override
	public void onDestroy() {
	    super.onDestroy();
	    
	}
	
	@Override
	public void onBackPressed() {
		//TODO: This is only temporary, needs to query the user first to ensure that they want to exit.
		GameManager.getInstance().updateGameState(GameStateEnum.GAME_NAVIGATION);
	}
	
	public GeocentricCoordinate getGameLocation() {
	    return shootingGameLocation;
	}
	
	public List<GameEntity> getGameEntities() {
	    return gameEntities;
	}
	
	private class ShootingGameSurfaceView extends GLSurfaceView {
		public ShootingGameSurfaceView(Context context) {
			super(context);
			
			setEGLContextClientVersion(2);
			setRenderer(ShootingGameActivity.this.renderer);
		}
	}

    @Override
    public void onGameEntityAdded(GameEntity entity) {
        synchronized (gameEntities) {
            if (!gameEntities.contains(entity)) {
                gameEntities.add(entity);
            }
        }
    }

    @Override
    public void onGameEntityDeleted(GameEntity entity) {
        synchronized (gameEntities) {
            gameEntities.remove(entity);
        }
    }
}
