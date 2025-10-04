package com.rpg.rpg_game.model;

/**
 * Modèle représentant un ennemi dans le jeu RPG.
 */
public class Enemy {
    private int id;
    private int x;
    private int y;
    private int hp;
    private int maxHp;
    private int attack;
    private double speed;
    private EnemyType type;
    private int zoneX;
    private int zoneY;
    private boolean isAttacking;
    private long lastAttack;
    private double patrolDirection;
    private int aggroDistance;
    
    // Constructeurs
    public Enemy() {
        this.isAttacking = false;
        this.lastAttack = 0;
    }
    
    public Enemy(int id, int x, int y, EnemyType type, int zoneX, int zoneY) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.type = type;
        this.zoneX = zoneX;
        this.zoneY = zoneY;
        this.isAttacking = false;
        this.lastAttack = 0;
        this.patrolDirection = Math.random() * 2 * Math.PI;
        
        // Initialiser les stats selon le type
        initializeStats();
    }
    
    /**
     * Initialise les statistiques selon le type d'ennemi
     */
    private void initializeStats() {
        switch (type) {
            case GOBLIN:
                this.hp = 40;
                this.maxHp = 40;
                this.attack = 15;
                this.speed = 1.2;
                this.aggroDistance = 100;
                break;
            case TROLL:
                this.hp = 120;
                this.maxHp = 120;
                this.attack = 30;
                this.speed = 0.8;
                this.aggroDistance = 80;
                break;
            case RABBIT:
                this.hp = 25;
                this.maxHp = 25;
                this.attack = 5;
                this.speed = 2.8;
                this.aggroDistance = 70;
                break;
        }
    }
    
    // Getters et Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
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
    
    public double getSpeed() {
        return speed;
    }
    
    public void setSpeed(double speed) {
        this.speed = speed;
    }
    
    public EnemyType getType() {
        return type;
    }
    
    public void setType(EnemyType type) {
        this.type = type;
        initializeStats(); // Réinitialiser les stats si le type change
    }
    
    public int getZoneX() {
        return zoneX;
    }
    
    public void setZoneX(int zoneX) {
        this.zoneX = zoneX;
    }
    
    public int getZoneY() {
        return zoneY;
    }
    
    public void setZoneY(int zoneY) {
        this.zoneY = zoneY;
    }
    
    public boolean isAttacking() {
        return isAttacking;
    }
    
    public void setAttacking(boolean attacking) {
        isAttacking = attacking;
    }
    
    public long getLastAttack() {
        return lastAttack;
    }
    
    public void setLastAttack(long lastAttack) {
        this.lastAttack = lastAttack;
    }
    
    public double getPatrolDirection() {
        return patrolDirection;
    }
    
    public void setPatrolDirection(double patrolDirection) {
        this.patrolDirection = patrolDirection;
    }
    
    public int getAggroDistance() {
        return aggroDistance;
    }
    
    public void setAggroDistance(int aggroDistance) {
        this.aggroDistance = aggroDistance;
    }
    
    // Méthodes utilitaires
    public boolean isAlive() {
        return hp > 0;
    }
    
    public void takeDamage(int damage) {
        this.hp = Math.max(0, hp - damage);
    }
    
    public boolean canAttack(long currentTime, int attackCooldown) {
        return currentTime - lastAttack > attackCooldown;
    }
    
    public int getSize() {
        switch (type) {
            case GOBLIN: return 16;
            case TROLL: return 32;
            case RABBIT: return 12;
            default: return 16;
        }
    }
    
    public int getAttackRange() {
        switch (type) {
            case GOBLIN: return 25;
            case TROLL: return 40;
            case RABBIT: return 15;
            default: return 20;
        }
    }
    
    public int getAttackCooldown() {
        switch (type) {
            case GOBLIN: return 1000; // 1 seconde
            case TROLL: return 2000;  // 2 secondes
            case RABBIT: return 800;  // 0.8 seconde
            default: return 1000;
        }
    }
    
    public String getColor() {
        switch (type) {
            case GOBLIN: return "#FF5722";
            case TROLL: return "#9C27B0";
            case RABBIT: return "#8BC34A";
            default: return "#666666";
        }
    }
    
    @Override
    public String toString() {
        return String.format("Enemy{id=%d, type=%s, x=%d, y=%d, hp=%d/%d, zone=(%d,%d)}", 
                           id, type, x, y, hp, maxHp, zoneX, zoneY);
    }
}