package ucf.chickenzombiebonanza.game.entity;

import java.util.List;

public enum GameEntityTagEnum {
	LIFEFORM,
	WAYPOINT,
	POWER_UP;
	
	public boolean isInList(List<GameEntityTagEnum> tags) {
		for(GameEntityTagEnum i : tags) {
			if(i == this) {
				return true;
			}
		}
		return false;
	}
}
