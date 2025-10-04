package com.rpg.rpg_game.model;

/**
 * Modèle représentant un obstacle dans le jeu RPG.
 */
public class Obstacle {
    private int x;
    private int y;
    private int width;
    private int height;
    private ObstacleType type;
    private int zoneX;
    private int zoneY;
    private boolean solid;
    
    // Constructeurs
    public Obstacle() {
        this.solid = true; // Par défaut, les obstacles bloquent le passage
    }
    
    public Obstacle(int x, int y, int width, int height, ObstacleType type) {
    // public Obstacle(int x, int y, int width, int height, ObstacleType type, int zoneX, int zoneY) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
        // this.zoneX = zoneX;
        // this.zoneY = zoneY;
        this.solid = true;
    }
    
    // Getters et Setters
    public int getX() {
        return x;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public int getY() {
        return y;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public int getWidth() {
        return width;
    }
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
    
    public ObstacleType getType() {
        return type;
    }
    
    public void setType(ObstacleType type) {
        this.type = type;
    }
    
    // public int getZoneX() {
    //     return zoneX;
    // }
    
    // public void setZoneX(int zoneX) {
    //     this.zoneX = zoneX;
    // }
    
    // public int getZoneY() {
    //     return zoneY;
    // }
    
    // public void setZoneY(int zoneY) {
    //     this.zoneY = zoneY;
    // }
    
    public boolean isSolid() {
        return solid;
    }
    
    public void setSolid(boolean solid) {
        this.solid = solid;
    }
    
    // Méthodes utilitaires
    
    /**
     * Vérifie si un point se trouve à l'intérieur de l'obstacle
     */
    public boolean contains(int pointX, int pointY) {
        return pointX >= x && pointX < x + width && 
               pointY >= y && pointY < y + height;
    }
    
    /**
     * Vérifie si cet obstacle entre en collision avec un rectangle
     */
    public boolean collidesWith(int rectX, int rectY, int rectWidth, int rectHeight) {
        return x < rectX + rectWidth &&
               x + width > rectX &&
               y < rectY + rectHeight &&
               y + height > rectY;
    }
    
    /**
     * Obtient la couleur de l'obstacle selon son type
     */
    public String getColor() {
        if (type == null) return "#666666";
        
        switch (type) {
            case TREE: return "#4CAF50";
            case ROCK: return "#9E9E9E";
            case BUSH: return "#8BC34A";
            case HOUSE: return "#795548";
            case WATER: return "#2196F3";
            default: return "#666666";
        }
    }
    
    /**
     * Vérifie si l'obstacle peut être traversé
     */
    public boolean isPassable() {
        return !solid || type == ObstacleType.BUSH; // Les buissons peuvent être traversés mais ralentissent
    }
    
    /**
     * Obtient la pénalité de vitesse pour traverser cet obstacle (si possible)
     */
    public double getSpeedPenalty() {
        if (!solid) return 1.0; // Pas de pénalité
        
        switch (type) {
            case BUSH: return 0.5; // 50% de vitesse dans les buissons
            case WATER: return 0.3; // 30% de vitesse dans l'eau
            default: return 0.0; // Impossible de traverser
        }
    }
    
    @Override
    public String toString() {
        return String.format("Obstacle{type=%s, x=%d, y=%d, size=%dx%d, zone=(%d,%d), solid=%s}", 
                           type, x, y, width, height, zoneX, zoneY, solid);
    }
}