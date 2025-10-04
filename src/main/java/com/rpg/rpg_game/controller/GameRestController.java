package com.rpg.rpg_game.controller;

import org.springframework.web.bind.annotation.*;

import com.rpg.rpg_game.dto.MoveRequest;
import com.rpg.rpg_game.model.GameState;
import com.rpg.rpg_game.service.GameService;

@RestController
@RequestMapping("/api")
public class GameRestController {
    
    private final GameService gameService;
    
    public GameRestController(GameService gameService) {
        this.gameService = gameService;
    }
    
    @GetMapping("/gamestate")
    public GameState getGameState() {
        return gameService.getCurrentGameState();
    }
    
    @PostMapping("/player/move")
    public GameState movePlayer(@RequestBody MoveRequest request) {
        return gameService.movePlayer(request.getDirection());
    }
    
    @PostMapping("/player/attack")
    public GameState attack() {
        return gameService.playerAttack();
    }
    
    @PostMapping("/update")
    public GameState update() {
        return gameService.updateGame();
    }
}
