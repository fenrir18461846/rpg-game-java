package com.rpg.rpg_game.model;

/**
 * Modèle représentant le joueur dans le jeu RPG.
 */
public class Player {
    private int x;
    private int y;
    private int hp;
    private int maxHp;
    private int attack;
    private int defense;
    private int level;
    private int exp;
    private int score;
    private String direction;
    private boolean isAttacking;
    
    // Constructeurs
    public Player() {
        this.direction = "down";
        this.isAttacking = false;
    }
    
    public Player(int x, int y, int hp, int maxHp, int attack, int defense) {
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.maxHp = maxHp;
        this.attack = attack;
        this.defense = defense;
        this.level = 1;
        this.exp = 0;
        this.score = 0;
        this.direction = "down";
        this.isAttacking = false;
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
    
    public int getHp() {
        return hp;
    }
    
    public void setHp(int hp) {
        this.hp = hp;
    }
    
    public int getMaxHp() {
        return maxHp;
    }
    
    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }
    
    public int getAttack() {
        return attack;
    }
    
    public void setAttack(int attack) {
        this.attack = attack;
    }
    
    public int getDefense() {
        return defense;
    }
    
    public void setDefense(int defense) {
        this.defense = defense;
    }
    
    public int getLevel() {
        return level;
    }
    
    public void setLevel(int level) {
        this.level = level;
    }
    
    public int getExp() {
        return exp;
    }
    
    public void setExp(int exp) {
        this.exp = exp;
    }
    
    public int getScore() {
        return score;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
    
    public String getDirection() {
        return direction;
    }
    
    public void setDirection(String direction) {
        this.direction = direction;
    }
    
    public boolean isAttacking() {
        return isAttacking;
    }
    
    public void setAttacking(boolean attacking) {
        isAttacking = attacking;
    }
    
    // Méthodes utilitaires
    public boolean isAlive() {
        return hp > 0;
    }
    
    public void heal(int amount) {
        this.hp = Math.min(maxHp, hp + amount);
    }
    
    public void takeDamage(int damage) {
        this.hp = Math.max(0, hp - Math.max(1, damage - defense));
    }
    
    public void gainExp(int expPoints) {
        this.exp += expPoints;
        checkLevelUp();
    }
    
    private void checkLevelUp() {
        int expRequired = level * 100;
        while (exp >= expRequired) {
            levelUp();
            expRequired = level * 100;
        }
    }
    
    private void levelUp() {
        level++;
        exp = 0;
        maxHp += 20;
        hp = maxHp;
        attack += 5;
        defense += 2;
    }
    
    @Override
    public String toString() {
        return String.format("Player{x=%d, y=%d, hp=%d/%d, level=%d, attack=%d, defense=%d, score=%d}", 
                           x, y, hp, maxHp, level, attack, defense, score);
    }
}