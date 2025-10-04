package com.rpg.rpg_game.dto;

/**
 * DTO pour les requêtes de mouvement du joueur.
 */
public class MoveRequest {
    private String direction;
    
    public MoveRequest() {}
    
    public MoveRequest(String direction) {
        this.direction = direction;
    }
    
    public String getDirection() {
        return direction;
    }
    
    public void setDirection(String direction) {
        this.direction = direction;
    }
}
