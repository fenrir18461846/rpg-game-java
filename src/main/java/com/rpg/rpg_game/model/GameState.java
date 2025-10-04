package com.rpg.rpg_game.model;

import java.util.List;
import java.util.ArrayList;

/**
 * Modèle représentant l'état complet du jeu RPG.
 */
public class GameState {
    private Player player;
    private List<Enemy> enemies;
    private List<Obstacle> obstacles;
    private long gameTime;
    private boolean gameRunning;
    private boolean paused;
    private String lastAction;
    private String message;
    
    // Constructeurs
    public GameState() {
        this.enemies = new ArrayList<>();
        this.obstacles = new ArrayList<>();
        this.gameTime = 0;
        this.gameRunning = true;
        this.paused = false;
        this.lastAction = "";
        this.message = "";
    }
    
    // Getters et Setters
    public Player getPlayer() {
        return player;
    }
    
    public void setPlayer(Player player) {
        this.player = player;
    }
    
    public List<Enemy> getEnemies() {
        return enemies;
    }
    
    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }
    
    public List<Obstacle> getObstacles() {
        return obstacles;
    }
    
    public void setObstacles(List<Obstacle> obstacles) {
        this.obstacles = obstacles;
    }
    
    public long getGameTime() {
        return gameTime;
    }
    
    public void setGameTime(long gameTime) {
        this.gameTime = gameTime;
    }
    
    public boolean isGameRunning() {
        return gameRunning;
    }
    
    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
    }
    
    public boolean isPaused() {
        return paused;
    }
    
    public void setPaused(boolean paused) {
        this.paused = paused;
    }
    
    public String getLastAction() {
        return lastAction;
    }
    
    public void setLastAction(String lastAction) {
        this.lastAction = lastAction;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    // Méthodes utilitaires
    public void incrementGameTime() {
        this.gameTime++;
    }
    
    public int getEnemyCount() {
        return enemies.size();
    }
    
    public int getAliveEnemyCount() {
        return (int) enemies.stream().filter(Enemy::isAlive).count();
    }
    
    public void removeDeadEnemies() {
        enemies.removeIf(enemy -> !enemy.isAlive());
    }
    
    public boolean isGameOver() {
        return !gameRunning || (player != null && !player.isAlive());
    }
    
    public boolean isVictory() {
        return gameRunning && getAliveEnemyCount() == 0;
    }
    
    public void addEnemy(Enemy enemy) {
        this.enemies.add(enemy);
    }
    
    public void addObstacle(Obstacle obstacle) {
        this.obstacles.add(obstacle);
    }
    
    /**
     * Obtient la zone actuelle du joueur
     */
    public Zone getPlayerZone() {
        if (player == null) return new Zone(0, 0);
        
        int zoneWidth = 800 / 3; // Largeur d'une zone
        int zoneHeight = 600 / 3; // Hauteur d'une zone
        
        int zoneX = Math.max(0, Math.min(2, player.getX() / zoneWidth));
        int zoneY = Math.max(0, Math.min(2, player.getY() / zoneHeight));
        
        return new Zone(zoneX, zoneY);
    }
    
    /**
     * Vérifie si une zone est adjacente à la zone du joueur
     */
    public boolean isZoneAdjacent(int zoneX, int zoneY) {
        Zone playerZone = getPlayerZone();
        int distance = Math.abs(playerZone.getX() - zoneX) + Math.abs(playerZone.getY() - zoneY);
        return distance <= 1;
    }
    
    @Override
    public String toString() {
        return String.format("GameState{player=%s, enemies=%d, obstacles=%d, time=%d, running=%s}", 
                           player, getEnemyCount(), obstacles.size(), gameTime, gameRunning);
    }
    
    /**
     * Classe interne pour représenter une zone
     */
    public static class Zone {
        private int x;
        private int y;
        
        public Zone(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        public int getX() {
            return x;
        }
        
        public int getY() {
            return y;
        }
        
        @Override
        public String toString() {
            return String.format("Zone(%d,%d)", x, y);
        }
    }
}