package com.rpg.rpg_game.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GameViewController {
    
    @GetMapping("/")
    public String index() {
        return "game"; // Retourne le fichier index.html
    }
}