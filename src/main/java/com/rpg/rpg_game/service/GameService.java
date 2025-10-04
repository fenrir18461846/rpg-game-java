package com.rpg.rpg_game.service;

import org.springframework.stereotype.Service;

import com.rpg.rpg_game.model.Enemy;
import com.rpg.rpg_game.model.EnemyType;
import com.rpg.rpg_game.model.GameState;
import com.rpg.rpg_game.model.Obstacle;
import com.rpg.rpg_game.model.ObstacleType;
import com.rpg.rpg_game.model.Player;

import java.util.*;

/**
 * Service principal gérant toute la logique du jeu RPG.
 * Singleton qui maintient l'état du jeu en mémoire.
 */
@Service
public class GameService {
    
    // État du jeu en mémoire (pour une session simple)
    private GameState currentGameState;
    private Random random = new Random();
    
    // Constantes
    private static final int MAP_WIDTH = 800;
    private static final int MAP_HEIGHT = 600;
    private static final int PLAYER_SPEED = 3;
    
    public GameService() {
        initializeGame();
    }
    
    /**
     * Initialise le jeu avec l'état de départ
     */
    private void initializeGame() {
        currentGameState = new GameState();
        
        // Créer le joueur
        Player player = new Player();
        player.setX(MAP_WIDTH / 2);
        player.setY(MAP_HEIGHT / 2);
        player.setHp(100);
        player.setMaxHp(100);
        player.setAttack(25);
        player.setDefense(5);
        player.setLevel(1);
        player.setExp(0);
        player.setScore(0);
        player.setAttacking(false);
        
        currentGameState.setPlayer(player);
        
        // Créer les ennemis
        List<Enemy> enemies = new ArrayList<>();
        enemies.add(createEnemy(1, 100, 80, EnemyType.GOBLIN));
        enemies.add(createEnemy(2, 400, 100, EnemyType.TROLL));
        enemies.add(createEnemy(3, 650, 120, EnemyType.RABBIT));
        enemies.add(createEnemy(4, 80, 300, EnemyType.GOBLIN));
        enemies.add(createEnemy(5, 350, 250, EnemyType.RABBIT));
        enemies.add(createEnemy(6, 700, 280, EnemyType.TROLL));
        enemies.add(createEnemy(7, 120, 480, EnemyType.TROLL));
        
        currentGameState.setEnemies(enemies);
        
        // Créer les obstacles
        List<Obstacle> obstacles = new ArrayList<>();
        obstacles.add(new Obstacle(50, 50, 32, 32, ObstacleType.TREE));
        obstacles.add(new Obstacle(350, 60, 48, 48, ObstacleType.ROCK));
        obstacles.add(new Obstacle(600, 80, 64, 32, ObstacleType.HOUSE));
        obstacles.add(new Obstacle(80, 250, 32, 32, ObstacleType.BUSH));
        obstacles.add(new Obstacle(650, 220, 48, 48, ObstacleType.ROCK));
        obstacles.add(new Obstacle(100, 420, 32, 32, ObstacleType.BUSH));
        obstacles.add(new Obstacle(420, 450, 48, 48, ObstacleType.ROCK));
        
        currentGameState.setObstacles(obstacles);
        
        System.out.println("🎮 Jeu initialisé: " + enemies.size() + " ennemis, " + obstacles.size() + " obstacles");
    }
    
    /**
     * Crée un ennemi avec ses caractéristiques
     */
    private Enemy createEnemy(int id, int x, int y, EnemyType type) {
        Enemy enemy = new Enemy();
        enemy.setId(id);
        enemy.setX(x);
        enemy.setY(y);
        enemy.setType(type);
        
        // Stats selon le type
        switch (type) {
            case GOBLIN:
                enemy.setHp(40);
                enemy.setMaxHp(40);
                enemy.setAttack(15);
                enemy.setSpeed(1.5);
                break;
            case TROLL:
                enemy.setHp(120);
                enemy.setMaxHp(120);
                enemy.setAttack(30);
                enemy.setSpeed(0.5);
                break;
            case RABBIT:
                enemy.setHp(25);
                enemy.setMaxHp(25);
                enemy.setAttack(8);
                enemy.setSpeed(2.5);
                break;
        }
        
        enemy.setAttacking(false);
        return enemy;
    }
    
    /**
     * Récupère l'état actuel du jeu
     */
    public GameState getCurrentGameState() {
        return currentGameState;
    }
    
    /**
     * Déplace le joueur
     */
    public GameState movePlayer(String direction) {
        Player player = currentGameState.getPlayer();
        
        if (player.getHp() <= 0) {
            return currentGameState; // Joueur mort, pas de mouvement
        }
        
        int oldX = player.getX();
        int oldY = player.getY();
        int newX = player.getX();
        int newY = player.getY();
        
        // Calculer nouvelle position
        switch (direction.toLowerCase()) {
            case "up":
                newY -= PLAYER_SPEED;
                break;
            case "down":
                newY += PLAYER_SPEED;
                break;
            case "left":
                newX -= PLAYER_SPEED;
                break;
            case "right":
                newX += PLAYER_SPEED;
                break;
        }

        System.out.println("DEBUG: Direction=" + direction + ", Old=(" + oldX + "," + oldY + "), New=(" + newX + "," + newY + ")");
        
        // Vérifier les collisions avec les obstacles
        boolean collision = checkCollision(newX, newY, 16, currentGameState.getObstacles());
        
        System.out.println("DEBUG: Collision=" + collision + ", InBounds=" + (newX >= 0 && newX <= MAP_WIDTH && newY >= 0 && newY <= MAP_HEIGHT));

        // Vérifier les limites de la carte
        if (!collision && newX >= 0 && newX <= MAP_WIDTH && newY >= 0 && newY <= MAP_HEIGHT) {
            player.setX(newX);
            player.setY(newY);
            System.out.println("DEBUG: Mouvement appliqué! Position finale=(" + player.getX() + "," + player.getY() + ")");
        } else {
            System.out.println("DEBUG: Mouvement bloqué!");
        }
        
        return currentGameState;
    }
    
    /**
     * Vérifie les collisions avec les obstacles
     */
    private boolean checkCollision(int x, int y, int size, List<Obstacle> obstacles) {
        // Le joueur est un carré centré sur (x, y)
        int halfSize = size / 2;
        int playerLeft = x - halfSize;
        int playerTop = y - halfSize;
        int playerRight = x + halfSize;
        int playerBottom = y + halfSize;
        
        for (Obstacle obstacle : obstacles) {
            // Vérification AABB (Axis-Aligned Bounding Box)
            if (playerRight > obstacle.getX() && 
                playerLeft < obstacle.getX() + obstacle.getWidth() &&
                playerBottom > obstacle.getY() && 
                playerTop < obstacle.getY() + obstacle.getHeight()) {

                System.out.println("COLLISION DÉTECTÉE!");
                System.out.println("  Joueur: left=" + playerLeft + ", right=" + playerRight + ", top=" + playerTop + ", bottom=" + playerBottom);
                System.out.println("  Obstacle: x=" + obstacle.getX() + ", y=" + obstacle.getY() + ", w=" + obstacle.getWidth() + ", h=" + obstacle.getHeight());
                
                return true; // Collision détectée
            }
        }
        return false;
    }
    
    /**
     * Calcule la distance entre deux points
     */
    private double calculateDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
    
    /**
     * Attaque du joueur
     */
    public GameState playerAttack() {
        Player player = currentGameState.getPlayer();
        
        if (player.getHp() <= 0 || player.isAttacking()) {
            return currentGameState;
        }
        
        player.setAttacking(true);
        
        List<Enemy> enemies = currentGameState.getEnemies();
        int attackRange = 50;
        int hitCount = 0;
        
        Iterator<Enemy> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            Enemy enemy = iterator.next();
            double distance = calculateDistance(player.getX(), player.getY(), enemy.getX(), enemy.getY());
            
            if (distance <= attackRange) {
                // Calculer les dégâts avec variation aléatoire
                int damage = player.getAttack() + random.nextInt(10) - 5;
                damage = Math.max(1, damage);
                
                enemy.setHp(enemy.getHp() - damage);
                hitCount++;
                
                System.out.println("⚔️ Joueur attaque " + enemy.getType() + " pour " + damage + " dégâts");
                
                // Si l'ennemi est mort
                if (enemy.getHp() <= 0) {
                    int expGain = getExpValue(enemy.getType());
                    int scoreGain = getScoreValue(enemy.getType());
                    
                    player.setExp(player.getExp() + expGain);
                    player.setScore(player.getScore() + scoreGain);
                    
                    System.out.println("💀 " + enemy.getType() + " éliminé! +" + expGain + " EXP, +" + scoreGain + " points");
                    
                    // Level up si nécessaire
                    checkLevelUp(player);
                    
                    iterator.remove();
                }
            }
        }
        
        // Désactiver l'attaque après un court délai (simulé)
        player.setAttacking(false);
        
        if (hitCount > 0) {
            System.out.println("✨ " + hitCount + " ennemi(s) touché(s)");
        }
        
        return currentGameState;
    }
    
    /**
     * Vérifie et gère le level up
     */
    private void checkLevelUp(Player player) {
        int expRequired = player.getLevel() * 100;
        
        if (player.getExp() >= expRequired) {
            player.setLevel(player.getLevel() + 1);
            player.setExp(0);
            player.setMaxHp(player.getMaxHp() + 20);
            player.setHp(player.getMaxHp()); // Soigner complètement
            player.setAttack(player.getAttack() + 5);
            player.setDefense(player.getDefense() + 2);
            
            System.out.println("🎉 LEVEL UP! Niveau " + player.getLevel());
        }
    }
    
    /**
     * Retourne l'expérience gagnée pour un type d'ennemi
     */
    private int getExpValue(EnemyType type) {
        switch (type) {
            case GOBLIN: return 20;
            case TROLL: return 50;
            case RABBIT: return 15;
            default: return 10;
        }
    }
    
    /**
     * Retourne les points gagnés pour un type d'ennemi
     */
    private int getScoreValue(EnemyType type) {
        switch (type) {
            case GOBLIN: return 100;
            case TROLL: return 250;
            case RABBIT: return 50;
            default: return 10;
        }
    }
    
    /**
     * Met à jour l'IA des ennemis et l'état du jeu
     */
    public GameState updateGame() {
        Player player = currentGameState.getPlayer();
        
        if (player.getHp() <= 0) {
            return currentGameState; // Game over
        }
        
        // Mettre à jour chaque ennemi
        for (Enemy enemy : currentGameState.getEnemies()) {
            updateEnemyAI(enemy, player);
        }
        
        return currentGameState;
    }
    
    /**
     * IA d'un ennemi
     */
    private void updateEnemyAI(Enemy enemy, Player player) {
        double distance = calculateDistance(enemy.getX(), enemy.getY(), player.getX(), player.getY());
        
        // Comportement selon le type
        switch (enemy.getType()) {
            case GOBLIN: // Agressif
                if (distance < 100) {
                    moveTowardsPlayer(enemy, player);
                    tryAttackPlayer(enemy, player, distance);
                }
                break;
                
            case TROLL: // Patrouille
                if (distance < 80) {
                    moveTowardsPlayer(enemy, player);
                    tryAttackPlayer(enemy, player, distance);
                } else {
                    // Patrouille aléatoire
                    if (random.nextInt(100) < 5) {
                        enemy.setX(enemy.getX() + (random.nextInt(3) - 1));
                        enemy.setY(enemy.getY() + (random.nextInt(3) - 1));
                    }
                }
                break;
                
            case RABBIT: // Fuit
                if (distance < 70) {
                    moveAwayFromPlayer(enemy, player);
                }
                break;
        }
    }
    
    /**
     * Déplace l'ennemi vers le joueur
     */
    private void moveTowardsPlayer(Enemy enemy, Player player) {
        int dx = player.getX() - enemy.getX();
        int dy = player.getY() - enemy.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        
        if (distance > 0) {
            int moveX = (int)((dx / distance) * enemy.getSpeed());
            int moveY = (int)((dy / distance) * enemy.getSpeed());
            
            enemy.setX(enemy.getX() + moveX);
            enemy.setY(enemy.getY() + moveY);
        }
    }
    
    /**
     * Déplace l'ennemi loin du joueur
     */
    private void moveAwayFromPlayer(Enemy enemy, Player player) {
        int dx = player.getX() - enemy.getX();
        int dy = player.getY() - enemy.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        
        if (distance > 0) {
            int moveX = (int)(-(dx / distance) * enemy.getSpeed());
            int moveY = (int)(-(dy / distance) * enemy.getSpeed());
            
            enemy.setX(enemy.getX() + moveX);
            enemy.setY(enemy.getY() + moveY);
        }
    }
    
    /**
     * Tente d'attaquer le joueur
     */
    private void tryAttackPlayer(Enemy enemy, Player player, double distance) {
        int attackRange = 30;
        
        if (distance <= attackRange && !enemy.isAttacking()) {
            enemy.setAttacking(true);
            
            int damage = enemy.getAttack() - player.getDefense();
            damage = Math.max(1, damage);
            
            player.setHp(Math.max(0, player.getHp() - damage));
            
            System.out.println("💥 " + enemy.getType() + " attaque le joueur pour " + damage + " dégâts");
            
            // Désactiver l'attaque
            enemy.setAttacking(false);
        }
    }
    
    /**
     * Réinitialise le jeu
     */
    public GameState resetGame() {
        initializeGame();
        return currentGameState;
    }
}
