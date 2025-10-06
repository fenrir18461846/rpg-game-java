package com.rpg.rpg_game.controller;

import com.rpg.rpg_game.model.GameState;
import com.rpg.rpg_game.service.GameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests d'intégration pour GameRestController
 */
@WebMvcTest(GameRestController.class)
class GameRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @Test
    @DisplayName("GET /api/gamestate retourne l'état du jeu")
    void testGetGameState() throws Exception {
        GameState mockState = new GameState();
        when(gameService.getCurrentGameState()).thenReturn(mockState);

        mockMvc.perform(get("/api/gamestate"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("POST /api/player/move accepte une direction valide")
    void testMovePlayerWithValidDirection() throws Exception {
        GameState mockState = new GameState();
        when(gameService.movePlayer(any())).thenReturn(mockState);

        mockMvc.perform(post("/api/player/move")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"direction\":\"up\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("POST /api/player/attack retourne un état de jeu")
    void testPlayerAttack() throws Exception {
        GameState mockState = new GameState();
        when(gameService.playerAttack()).thenReturn(mockState);

        mockMvc.perform(post("/api/player/attack"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("POST /api/update met à jour le jeu")
    void testUpdateGame() throws Exception {
        GameState mockState = new GameState();
        when(gameService.updateGame()).thenReturn(mockState);

        mockMvc.perform(post("/api/update"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
