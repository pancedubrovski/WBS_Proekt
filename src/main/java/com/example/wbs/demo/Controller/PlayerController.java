package com.example.wbs.demo.Controller;

import java.util.List;
import com.example.wbs.demo.Model.Player;
import com.example.wbs.demo.Service.PlayerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/player")
@CrossOrigin(origins = "*")
public class PlayerController {

    private final PlayerService playerService;
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping(path= "/{team}")
    public List<Player> getPlayersByTeam(@PathVariable Integer team){
        return playerService.getTeamPlayers(team);
    }
}
