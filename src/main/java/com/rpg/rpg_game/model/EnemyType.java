package com.rpg.rpg_game.model;

/**
 * Énumération représentant les différents types d'ennemis.
 */
public enum EnemyType {
    GOBLIN("Goblin", "Ennemi agressif qui poursuit le joueur"),
    TROLL("Troll", "Ennemi qui patrouille et attaque si le joueur s'approche"),
    RABBIT("Rabbit", "Créature fuyante qui s'enfuit quand le joueur approche");
    
    private final String displayName;
    private final String description;
    
    EnemyType(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getDescription() {
        return description;
    }
    
    /**
     * Retourne le comportement associé au type d'ennemi
     */
    public String getBehavior() {
        switch (this) {
            case GOBLIN: return "aggressive";
            case TROLL: return "patrol";
            case RABBIT: return "flee";
            default: return "neutral";
        }
    }
    
    /**
     * Retourne la valeur d'expérience gagnée en tuant cet ennemi
     */
    public int getExpValue() {
        switch (this) {
            case GOBLIN: return 20;
            case TROLL: return 50;
            case RABBIT: return 10;
            default: return 0;
        }
    }
    
    /**
     * Retourne la valeur de score gagnée en tuant cet ennemi
     */
    public int getScoreValue() {
        switch (this) {
            case GOBLIN: return 50;
            case TROLL: return 150;
            case RABBIT: return 25;
            default: return 0;
        }
    }
}