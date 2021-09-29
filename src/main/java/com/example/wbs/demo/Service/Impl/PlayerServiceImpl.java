package com.example.wbs.demo.Service.Impl;

import com.example.wbs.demo.Model.Player;
import com.example.wbs.demo.Model.Team;
import com.example.wbs.demo.Repository.PlayerRepository;
import com.example.wbs.demo.Repository.TeamRepository;
import com.example.wbs.demo.Service.PlayerService;
import org.apache.jena.query.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }


    @Override
    public List<Player> getTeamPlayers(Integer team) {

        return this.playerRepository.findAllByTeamId(team).orElseThrow();
    }

//    @Override
//    public Player getPlayer(String name) {
//
//        String query = "PREFIX dbo: <http://dbpedia.org/ontology/> " +
//                "PREFIX dbr: <http://dbpedia.org/resource/> " +
//                "SELECT * " +
//                "WHERE { " +
//                "dbr:"+name+"  dbo:abstract  ?description; " +
//                "dbo:award ?award; " +
//                "dbo:birthDate ?birthDate; " +
//                "dbo:birthPlace ?place; " +
//                "dbo:number ?number; " +
//                "dbo:position ?position " +
//                "FILTER (langMatches(lang(?description), \"en\"))." +
//                "} ";
//        String SPARQLEndpoint = "https://dbpedia.org/sparql";
//        Player player=null;
//        Query sparqlQuery = QueryFactory.create(query);
//        try (QueryExecution queryExecution1 = QueryExecutionFactory.sparqlService(SPARQLEndpoint, sparqlQuery)) {
//            ResultSet resultSet = queryExecution1.execSelect();
//            while(resultSet.hasNext()){
//                QuerySolution solution = resultSet.nextSolution();
//                String[] description = solution.get("description").toString().split("/");
//                String[] award = solution.get("award").toString().split("/");
//                String[] birthDate = solution.get("birthDate").toString().split("/");
//                String[] place = solution.get("place").toString().split("/");
//                String[] number = solution.get("number").toString().split("/");
//                String[] position = solution.get("position").toString().split("/");
//             //   players.add(description[description.length - 1]);
////                player = new Player(name,description[description.length -1],award[award.length -1],
////                        birthDate[birthDate.length -1],place[place.length -1],number[number.length -1],
////                        position[position.length -1]);
////                this.playerRepository.save(player);
//            }
//        }
//
//        return player;
   // }
}
