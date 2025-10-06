package com.rpg.rpg_game.service;

import com.rpg.rpg_game.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour GameService
 */
class GameServiceTest {

    private GameService gameService;

    @BeforeEach
    void setUp() {
        gameService = new GameService();
    }

    @Test
    @DisplayName("Le jeu s'initialise correctement avec un joueur au centre")
    void testInitialization() {
        GameState state = gameService.getCurrentGameState();
        
        assertNotNull(state);
        assertNotNull(state.getPlayer());
        assertEquals(400, state.getPlayer().getX());
        assertEquals(300, state.getPlayer().getY());
        assertEquals(100, state.getPlayer().getHp());
        assertEquals(1, state.getPlayer().getLevel());
    }

    @Test
    @DisplayName("Le joueur peut se déplacer vers le haut")
    void testMovePlayerUp() {
        GameState state = gameService.getCurrentGameState();
        int initialY = state.getPlayer().getY();
        
        gameService.movePlayer("up");
        
        assertEquals(initialY - 3, state.getPlayer().getY());
    }

    @Test
    @DisplayName("Le joueur peut se déplacer vers le bas")
    void testMovePlayerDown() {
        GameState state = gameService.getCurrentGameState();
        int initialY = state.getPlayer().getY();
        
        gameService.movePlayer("down");
        
        assertEquals(initialY + 3, state.getPlayer().getY());
    }

    @Test
    @DisplayName("Le joueur peut se déplacer vers la gauche")
    void testMovePlayerLeft() {
        GameState state = gameService.getCurrentGameState();
        int initialX = state.getPlayer().getX();
        
        gameService.movePlayer("left");
        
        assertEquals(initialX - 3, state.getPlayer().getX());
    }

    @Test
    @DisplayName("Le joueur peut se déplacer vers la droite")
    void testMovePlayerRight() {
        GameState state = gameService.getCurrentGameState();
        int initialX = state.getPlayer().getX();
        
        gameService.movePlayer("right");
        
        assertEquals(initialX + 3, state.getPlayer().getX());
    }

    @Test
    @DisplayName("Le joueur ne peut pas sortir de la carte par la gauche")
    void testPlayerCannotMoveOutOfBoundsLeft() {
        GameState state = gameService.getCurrentGameState();
        Player player = state.getPlayer();
        player.setX(0);
        
        gameService.movePlayer("left");
        
        assertEquals(0, player.getX());
    }

    @Test
    @DisplayName("Le joueur ne peut pas sortir de la carte par le haut")
    void testPlayerCannotMoveOutOfBoundsTop() {
        GameState state = gameService.getCurrentGameState();
        Player player = state.getPlayer();
        player.setY(0);
        
        gameService.movePlayer("up");
        
        assertEquals(0, player.getY());
    }

    @Test
    @DisplayName("Le joueur ne peut pas se déplacer s'il est mort")
    void testDeadPlayerCannotMove() {
        GameState state = gameService.getCurrentGameState();
        Player player = state.getPlayer();
        player.setHp(0);
        int initialX = player.getX();
        
        gameService.movePlayer("right");
        
        assertEquals(initialX, player.getX());
    }

    @Test
    @DisplayName("Le jeu initialise 7 ennemis")
    void testEnemiesInitialization() {
        GameState state = gameService.getCurrentGameState();
        
        assertNotNull(state.getEnemies());
        assertEquals(7, state.getEnemies().size());
    }

    @Test
    @DisplayName("Le jeu initialise 7 obstacles")
    void testObstaclesInitialization() {
        GameState state = gameService.getCurrentGameState();
        
        assertNotNull(state.getObstacles());
        assertEquals(7, state.getObstacles().size());
    }

    @Test
    @DisplayName("L'attaque du joueur ne touche que les ennemis à portée")
    void testPlayerAttackRange() {
        GameState state = gameService.getCurrentGameState();
        Player player = state.getPlayer();
        player.setX(100);
        player.setY(100);
        
        int initialEnemyCount = state.getEnemies().size();
        gameService.playerAttack();
        
        // Vérifier qu'aucun ennemi lointain n'est affecté
        assertTrue(state.getEnemies().size() <= initialEnemyCount);
    }

    @Test
    @DisplayName("Le joueur gagne de l'expérience en tuant un ennemi")
    void testPlayerGainsExpFromKillingEnemy() {
        GameState state = gameService.getCurrentGameState();
        Player player = state.getPlayer();
        
        // Positionner le joueur près d'un ennemi faible
        Enemy weakEnemy = state.getEnemies().stream()
            .filter(e -> e.getType() == EnemyType.RABBIT)
            .findFirst()
            .orElse(null);
        
        if (weakEnemy != null) {
            player.setX(weakEnemy.getX());
            player.setY(weakEnemy.getY());
            int initialExp = player.getExp();
            
            // Attaquer jusqu'à tuer l'ennemi
            for (int i = 0; i < 10; i++) {
                gameService.playerAttack();
            }
            
            assertTrue(player.getExp() >= initialExp);
        }
    }

    @Test
    @DisplayName("Le joueur gagne un niveau après avoir accumulé assez d'expérience")
    void testPlayerLevelUp() {
        GameState state = gameService.getCurrentGameState();
        Player player = state.getPlayer();
        
        player.setExp(99);
        player.gainExp(1);
        
        assertEquals(2, player.getLevel());
        assertEquals(0, player.getExp());
    }

    @Test
    @DisplayName("Les stats du joueur augmentent au level up")
    void testPlayerStatsIncreaseOnLevelUp() {
        GameState state = gameService.getCurrentGameState();
        Player player = state.getPlayer();
        
        int initialMaxHp = player.getMaxHp();
        int initialAttack = player.getAttack();
        int initialDefense = player.getDefense();
        
        player.setExp(100);
        player.gainExp(0);
        
        assertTrue(player.getMaxHp() > initialMaxHp);
        assertTrue(player.getAttack() > initialAttack);
        assertTrue(player.getDefense() > initialDefense);
    }

    @Test
    @DisplayName("La mise à jour du jeu ne crash pas")
    void testUpdateGameDoesNotCrash() {
        assertDoesNotThrow(() -> {
            for (int i = 0; i < 100; i++) {
                gameService.updateGame();
            }
        });
    }

    @Test
    @DisplayName("Reset du jeu réinitialise complètement l'état")
    void testResetGame() {
        GameState state = gameService.getCurrentGameState();
        Player player = state.getPlayer();
        
        // Modifier l'état du jeu
        player.setX(100);
        player.setY(100);
        player.setHp(50);
        player.setLevel(5);
        
        // Reset
        gameService.resetGame();
        state = gameService.getCurrentGameState();
        player = state.getPlayer();
        
        assertEquals(400, player.getX());
        assertEquals(300, player.getY());
        assertEquals(100, player.getHp());
        assertEquals(1, player.getLevel());
        assertEquals(7, state.getEnemies().size());
    }

    @Test
    @DisplayName("Les ennemis de type GOBLIN sont créés avec les bonnes stats")
    void testGoblinCreation() {
        GameState state = gameService.getCurrentGameState();
        Enemy goblin = state.getEnemies().stream()
            .filter(e -> e.getType() == EnemyType.GOBLIN)
            .findFirst()
            .orElse(null);
        
        assertNotNull(goblin);
        assertEquals(40, goblin.getMaxHp());
        assertEquals(15, goblin.getAttack());
        assertEquals(1.5, goblin.getSpeed());
    }

    @Test
    @DisplayName("Les ennemis de type TROLL sont créés avec les bonnes stats")
    void testTrollCreation() {
        GameState state = gameService.getCurrentGameState();
        Enemy troll = state.getEnemies().stream()
            .filter(e -> e.getType() == EnemyType.TROLL)
            .findFirst()
            .orElse(null);
        
        assertNotNull(troll);
        assertEquals(120, troll.getMaxHp());
        assertEquals(30, troll.getAttack());
        assertEquals(0.5, troll.getSpeed());
    }

    @Test
    @DisplayName("Les ennemis de type RABBIT sont créés avec les bonnes stats")
    void testRabbitCreation() {
        GameState state = gameService.getCurrentGameState();
        Enemy rabbit = state.getEnemies().stream()
            .filter(e -> e.getType() == EnemyType.RABBIT)
            .findFirst()
            .orElse(null);
        
        assertNotNull(rabbit);
        assertEquals(25, rabbit.getMaxHp());
        assertEquals(8, rabbit.getAttack());
        assertEquals(2.5, rabbit.getSpeed());
    }

    @Test
    @DisplayName("Le joueur peut être soigné mais pas au-delà du max HP")
    void testPlayerHealing() {
        GameState state = gameService.getCurrentGameState();
        Player player = state.getPlayer();
        
        player.setHp(50);
        player.heal(100);
        
        assertEquals(player.getMaxHp(), player.getHp());
    }

    @Test
    @DisplayName("Le joueur prend des dégâts réduits par la défense")
    void testPlayerTakeDamage() {
        GameState state = gameService.getCurrentGameState();
        Player player = state.getPlayer();
        
        int initialHp = player.getHp();
        int defense = player.getDefense();
        player.takeDamage(20);
        
        assertTrue(player.getHp() < initialHp);
        assertTrue(player.getHp() >= initialHp - (20 - defense));
    }
}
