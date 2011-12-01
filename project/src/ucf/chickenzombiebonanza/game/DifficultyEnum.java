package ucf.chickenzombiebonanza.game;

public enum DifficultyEnum {
	EASY(10000, 5, 1, 100, 50, 1000),
	MEDIUM(7000, 10, 1, 75, 70, 2500),
	HARD(5000, 15, 1, 50, 100, 5000);
	
	private final int enemySpawnFrequency;
	
	private final int enemyHealth;
	
	private final int enemyCount;
	
	private final int playerHealth;
	
	private final int enemyDestroyedScore;
	
	private final int survivalScore;
	
	DifficultyEnum(int spawnFrequency, int enemyHealth, int enemyCount, int playerHealth, int enemyDestroyedScore, int survivalScore) {
		this.enemySpawnFrequency = spawnFrequency;
		this.enemyHealth = enemyHealth;
		this.enemyCount = enemyCount;
		this.playerHealth = playerHealth;
		this.enemyDestroyedScore = enemyDestroyedScore;
		this.survivalScore = survivalScore;
	}
	
	public int getEnemySpawnFrequency() {
		return enemySpawnFrequency;
	}
	
	public int getEnemyHealth() {
		return enemyHealth;
	}
	
	public int getEnemyCount() {
		return enemyCount;
	}
	
	public int getPlayerHealth() {
		return playerHealth;
	}
	
	public int getEnemyDestroyedScore() {
		return enemyDestroyedScore;
	}
	
	public int getSurvivalScore() {
		return survivalScore;
	}
}



