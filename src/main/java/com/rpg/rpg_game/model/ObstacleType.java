package com.rpg.rpg_game.model;

/**
 * Énumération représentant les différents types d'obstacles.
 */
public enum ObstacleType {
    TREE("Arbre", "Un grand arbre qui bloque le passage", true),
    ROCK("Rocher", "Un rocher solide infranchissable", true),
    BUSH("Buisson", "Un buisson qui peut être traversé lentement", false),
    HOUSE("Maison", "Une construction solide", true),
    WATER("Eau", "Une zone d'eau qui ralentit les mouvements", false);
    
    private final String displayName;
    private final String description;
    private final boolean solid;
    
    ObstacleType(String displayName, String description, boolean solid) {
        this.displayName = displayName;
        this.description = description;
        this.solid = solid;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public boolean isSolid() {
        return solid;
    }
    
    /**
     * Retourne la couleur associée au type d'obstacle
     */
    public String getColor() {
        switch (this) {
            case TREE: return "#4CAF50";
            case ROCK: return "#9E9E9E";
            case BUSH: return "#8BC34A";
            case HOUSE: return "#795548";
            case WATER: return "#2196F3";
            default: return "#666666";
        }
    }
    
    /**
     * Retourne le facteur de vitesse pour traverser cet obstacle
     * 1.0 = vitesse normale, 0.5 = 50% de vitesse, 0.0 = impossible
     */
    public double getSpeedFactor() {
        switch (this) {
            case TREE: return 0.0;  // Impossible de traverser
            case ROCK: return 0.0;  // Impossible de traverser
            case BUSH: return 0.5;  // 50% de vitesse
            case HOUSE: return 0.0; // Impossible de traverser
            case WATER: return 0.3; // 30% de vitesse
            default: return 1.0;
        }
    }
    
    /**
     * Vérifie si cet obstacle peut être détruit
     */
    public boolean isDestructible() {
        switch (this) {
            case BUSH: return true;  // Les buissons peuvent être détruits
            case TREE: return false; // Les arbres sont permanents
            case ROCK: return false; // Les rochers sont permanents
            case HOUSE: return false; // Les maisons sont permanentes
            case WATER: return false; // L'eau ne peut pas être détruite
            default: return false;
        }
    }
    
    /**
     * Retourne les points de vie de l'obstacle (pour les obstacles destructibles)
     */
    public int getHitPoints() {
        switch (this) {
            case BUSH: return 10;   // Les buissons ont 10 HP
            case TREE: return 100;  // Les arbres auraient 100 HP s'ils étaient destructibles
            case ROCK: return 200;  // Les rochers auraient 200 HP s'ils étaient destructibles
            default: return Integer.MAX_VALUE; // Indestructible
        }
    }
}