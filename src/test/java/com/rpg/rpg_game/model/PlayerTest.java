package com.rpg.rpg_game.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour le modèle Player
 */
class PlayerTest {

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player(100, 100, 100, 100, 25, 5);
    }

    @Test
    @DisplayName("Le joueur est vivant avec HP > 0")
    void testPlayerIsAlive() {
        assertTrue(player.isAlive());
    }

    @Test
    @DisplayName("Le joueur est mort avec HP = 0")
    void testPlayerIsDead() {
        player.setHp(0);
        assertFalse(player.isAlive());
    }

    @Test
    @DisplayName("Le soin ne dépasse pas le max HP")
    void testHealDoesNotExceedMaxHp() {
        player.setHp(50);
        player.heal(100);
        
        assertEquals(player.getMaxHp(), player.getHp());
    }

    @Test
    @DisplayName("Le soin fonctionne normalement")
    void testHealWorks() {
        player.setHp(50);
        player.heal(30);
        
        assertEquals(80, player.getHp());
    }

    @Test
    @DisplayName("Les dégâts prennent en compte la défense")
    void testTakeDamageWithDefense() {
        int initialHp = player.getHp();
        player.takeDamage(20);
        
        assertEquals(initialHp - (20 - 5), player.getHp());
    }

    @Test
    @DisplayName("Les dégâts font au moins 1 de dégâts")
    void testTakeDamageMinimumOne() {
        int initialHp = player.getHp();
        player.takeDamage(3); // 3 - 5 défense = -2, mais min 1
        
        assertEquals(initialHp - 1, player.getHp());
    }

    @Test
    @DisplayName("Les HP ne descendent pas en dessous de 0")
    void testHpCannotGoNegative() {
        player.takeDamage(200);
        
        assertTrue(player.getHp() >= 0);
    }

    @Test
    @DisplayName("Gagner de l'expérience déclenche le level up")
    void testGainExpTriggersLevelUp() {
        player.setExp(99);
        player.gainExp(1);
        
        assertEquals(2, player.getLevel());
    }

    @Test
    @DisplayName("Le level up augmente les stats")
    void testLevelUpIncreasesStats() {
        int initialMaxHp = player.getMaxHp();
        int initialAttack = player.getAttack();
        int initialDefense = player.getDefense();
        
        player.setExp(100);
        player.gainExp(0);
        
        assertEquals(2, player.getLevel());
        assertTrue(player.getMaxHp() > initialMaxHp);
        assertTrue(player.getAttack() > initialAttack);
        assertTrue(player.getDefense() > initialDefense);
        assertEquals(player.getMaxHp(), player.getHp()); // Soigné complètement
    }

    @Test
    @DisplayName("Le level up remet l'EXP à 0")
    void testLevelUpResetsExp() {
        player.setExp(100);
        player.gainExp(0);
        
        assertEquals(0, player.getExp());
    }

    @Test
    @DisplayName("La direction par défaut est 'down'")
    void testDefaultDirection() {
        Player newPlayer = new Player();
        assertEquals("down", newPlayer.getDirection());
    }

    @Test
    @DisplayName("Le joueur n'attaque pas par défaut")
    void testNotAttackingByDefault() {
        Player newPlayer = new Player();
        assertFalse(newPlayer.isAttacking());
    }

    @Test
    @DisplayName("ToString retourne les informations du joueur")
    void testToString() {
        String result = player.toString();
        
        assertTrue(result.contains("Player{"));
        assertTrue(result.contains("x=100"));
        assertTrue(result.contains("y=100"));
        assertTrue(result.contains("hp=100"));
        assertTrue(result.contains("level=1"));
    }
}
