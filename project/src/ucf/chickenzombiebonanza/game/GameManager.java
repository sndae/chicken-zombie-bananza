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

import geotransform.ellipsoids.WE_Ellipsoid;
import geotransform.transforms.Gdc_To_Gcc_Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ucf.chickenzombiebonanza.common.GeocentricCoordinate;
import ucf.chickenzombiebonanza.game.entity.GameEntity;

public class GameManager implements GameSettingsChangeListener {

    private static GameManager instance = null;

    private List<GameStateListener> stateListeners = new ArrayList<GameStateListener>();

    private List<GameEntity> gameEntities = new ArrayList<GameEntity>();

    private final GameSettings gameSettings = new GameSettings();

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

    public void start() {

	updateGameState(GameStateEnum.GAME_LOADING);
	long startTime = System.currentTimeMillis();
	Gdc_To_Gcc_Converter.Init(new WE_Ellipsoid());
	long endTime = System.currentTimeMillis();
	final long timeLeft = 5000 - (endTime - startTime);
	if (timeLeft > 0) {
	    Timer waitTimer = new Timer();
	    waitTimer.schedule(new TimerTask() {
		@Override
		public void run() {
		    updateGameState(GameStateEnum.GAME_NAVIGATION);

		}
	    }, timeLeft);
	} else {
	    updateGameState(GameStateEnum.GAME_NAVIGATION);
	}

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

    @Override
    public void onPlayAreaCenterChanged(GeocentricCoordinate position) {

    }

    @Override
    public void onPlayAreaRadiusChanged(float radius) {

    }

}
