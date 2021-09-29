package com.example.wbs.demo.Controller;

import com.example.wbs.demo.Model.Player;
import com.example.wbs.demo.Model.Team;
import com.example.wbs.demo.Model.TeamRequest;
import com.example.wbs.demo.Service.PlayerService;
import com.example.wbs.demo.Service.TeamService;
import org.apache.jena.query.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;
    private final PlayerService playerService;
    public TeamController(TeamService teamService, PlayerService playerService) {
        this.teamService = teamService;
        this.playerService = playerService;
    }

    @GetMapping
    public List<Team> getAllTeam(){
        return teamService.getAllTeam();
//        return teamService.getAllTeam().stream().collect(Collectors.groupingBy(p -> p.getName())).values().stream()
//                .map(plans -> plans.stream().findFirst().get())
//                .collect(toList());
    }
    @GetMapping("/player")
    public List<String> getPlayerName(){
        String SPARQLEndpoint = "https://dbpedia.org/sparql";
        List<String> players = new ArrayList<>();
        String query = "PREFIX dbo: <http://dbpedia.org/ontology/> " +
                "PREFIX dbr: <http://dbpedia.org/resource/>" +
                "SELECT ?player " +
                "WHERE { " +
                " ?player dbo:draftTeam dbr:Cleveland_Cavaliers " +
                " } ";
        Query sparqlQuery = QueryFactory.create(query);
        try (QueryExecution queryExecution1 = QueryExecutionFactory.sparqlService(SPARQLEndpoint, sparqlQuery)) {
            ResultSet resultSet = queryExecution1.execSelect();
            while(resultSet.hasNext()){
                QuerySolution solution = resultSet.nextSolution();
                String[] playerArr = solution.get("player").toString().split("/");
                players.add(playerArr[playerArr.length - 1]);
            }
        }
        return players;
    }
    @GetMapping(path= "/{id}")
    public Team getTeamDetail(@PathVariable Integer id){
        return  teamService.getTeam(id);
    }






    @PostMapping
    public Team createTeam(@RequestBody TeamRequest request){
        return teamService.saveTeam(request);
    }


}
