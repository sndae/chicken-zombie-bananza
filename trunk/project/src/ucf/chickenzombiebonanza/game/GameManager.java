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
package ucf.chickenzombiebonanza.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

import ucf.chickenzombiebonanza.common.GeocentricCoordinate;
import ucf.chickenzombiebonanza.common.sensor.OrientationPublisher;
import ucf.chickenzombiebonanza.common.sensor.PositionPublisher;
import ucf.chickenzombiebonanza.game.entity.GameEntity;
import ucf.chickenzombiebonanza.game.entity.GameEntityListener;
import ucf.chickenzombiebonanza.game.entity.GameEntityStateListener;
import ucf.chickenzombiebonanza.game.entity.GameEntityTagEnum;
import ucf.chickenzombiebonanza.game.entity.LifeformEntity;

/**
 * 
 */
public class GameManager implements GameSettingsChangeListener {

	private static GameManager instance = null;

	private final List<GameStateListener> stateListeners = new ArrayList<GameStateListener>();

	private final List<GameEntity> gameEntities = new ArrayList<GameEntity>();

	private final List<GameEntityListenerData> gameEntityListeners = new ArrayList<GameEntityListenerData>();

	private final GameSettings gameSettings = new GameSettings();
	
	private GameEntity playerEntity = null;

	public static GameManager getInstance() {
		if (instance == null) {
			instance = new GameManager();
			instance.init();
		}
		return instance;
	}

	private GameManager() {
		gameSettings.registerGameSettingsChangeListener(this);
	}

	private void init() {

	}

	public void start(final PositionPublisher positionPublisher,
			final OrientationPublisher orientationPublisher) {
		updateGameState(GameStateEnum.GAME_LOADING);
		
		playerEntity = new LifeformEntity(positionPublisher, orientationPublisher);

		final AtomicBoolean loadingScreenDurationMet = new AtomicBoolean(false);

		// Start a timer to make the loading screen stay up a minimum period of
		// time
		Timer waitTimer = new Timer();
		waitTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				loadingScreenDurationMet.set(true);
			}
		}, 5000);

		Thread loadThread = new Thread() {
			@Override
			public void run() {
				while (!loadingScreenDurationMet.get()) {
					synchronized (this) {
						try {
							this.wait(100);
						} catch (InterruptedException e) {
						}
					}
				}

				updateGameState(GameStateEnum.GAME_NAVIGATION);
			}
		};

		loadThread.start();
	}
	
	public GameEntity getPlayerEntity() {
	    return playerEntity;
	}
	
	/**
	 * 
	 * @return
	 */
	public GameSettings getGameSettings() {
		return gameSettings;
	}

	/**
	 * 
	 * @param state
	 * @param obj
	 */
	public void updateGameState(GameStateEnum state, Object obj) {
		for (GameStateListener i : stateListeners) {
			i.gameStateChanged(state, obj);
		}
	}

	/**
	 * 
	 * @param state
	 */
	public void updateGameState(GameStateEnum state) {
		updateGameState(state, null);
	}

	public void addGameEntity(final GameEntity entity) {
		gameEntities.add(entity);
		entity.registerGameEntityStateListener(new GameEntityStateListener() {
			@Override
			public void onGameEntityDestroyed(GameEntity listener) {
				GameManager.this.removeGameEntity(entity);
			}
		});

		for (GameEntityListenerData i : gameEntityListeners) {
			if (i.filterTags == null
					|| GameManager.sharesTag(i.filterTags, entity.getTags())) {
				i.listener.onGameEntityAdded(entity);
			}
		}
	}

	public void removeGameEntity(GameEntity entity) {
		if (gameEntities.contains(entity)) {
			gameEntities.remove(entity);
			for (GameEntityListenerData i : gameEntityListeners) {
				if (i.filterTags == null
						|| GameManager
								.sharesTag(i.filterTags, entity.getTags())) {
					i.listener.onGameEntityDeleted(entity);
				}
			}
		}
	}

	/**
	 * Add an object to receive notifications when the game state changes
	 * 
	 * @param listener
	 *            The object to receive notifications
	 */
	public void addStateListener(GameStateListener listener) {
		stateListeners.add(listener);
	}

	/**
	 * Remove an object that receive notifications about the game state changes
	 * 
	 * @param listener
	 *            The object that receives notifications
	 */
	public void removeStateListener(GameStateListener listener) {
		stateListeners.add(listener);
	}

	public void registerGameEntityListener(GameEntityListener listener) {
		gameEntityListeners.add(new GameEntityListenerData(listener));
	}
	
	public void registerGameEntityListener(GameEntityListener listener,
           GameEntityTagEnum[] filter) {
	   List<GameEntityTagEnum> filterList = new ArrayList<GameEntityTagEnum>();
	   Collections.addAll(filterList, filter);
       gameEntityListeners.add(new GameEntityListenerData(listener, filterList));
   }

	public void registerGameEntityListener(GameEntityListener listener,
			List<GameEntityTagEnum> filter) {
		gameEntityListeners.add(new GameEntityListenerData(listener, filter));
	}

	public void unregisterGameEntityListener(GameEntityListener listener) {
		List<GameEntityListenerData> toRemove = new ArrayList<GameEntityListenerData>();
		for (GameEntityListenerData i : gameEntityListeners) {
			if (i.listener == listener) {
				toRemove.add(i);
			}
		}
		for (GameEntityListenerData i : toRemove) {
			gameEntityListeners.remove(i);
		}
	}

	@Override
	public void onPlayAreaCenterChanged(GeocentricCoordinate position) {

	}

	@Override
	public void onPlayAreaRadiusChanged(float radius) {

	}

	@Override
	public void onGameDifficultyChanged(DifficultyEnum difficulty) {

	}

	private class GameEntityListenerData {
		public final GameEntityListener listener;
		public final List<GameEntityTagEnum> filterTags;

		public GameEntityListenerData(GameEntityListener listener) {
			this.listener = listener;
			this.filterTags = null;
		}

		public GameEntityListenerData(GameEntityListener listener,
				List<GameEntityTagEnum> filterTags) {
			this.listener = listener;
			this.filterTags = filterTags;
		}
	}

	public static boolean sharesTag(List<GameEntityTagEnum> list1,
			List<GameEntityTagEnum> list2) {
		if (list1 != null && list2 != null) {
			for (GameEntityTagEnum i : list1) {
				for (GameEntityTagEnum j : list2) {
					if (i == j) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
