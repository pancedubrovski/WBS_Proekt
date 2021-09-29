package com.example.wbs.demo;

import com.example.wbs.demo.Model.Player;
import com.example.wbs.demo.Model.PlayerAward;
import com.example.wbs.demo.Model.Team;
import com.example.wbs.demo.Repository.PlayerAwardRepository;
import com.example.wbs.demo.Repository.PlayerRepository;
import com.example.wbs.demo.Repository.TeamRepository;
import lombok.Getter;

import org.springframework.stereotype.Component;
import org.apache.jena.query.*;


import javax.annotation.PostConstruct;
import java.util.*;

@Component
@Getter
public class DataHolder {


    public final TeamRepository teamRepository;
    public final PlayerRepository playerRepository;
    public final PlayerAwardRepository playerAwardRepository;

    String SPARQLEndpoint = "https://dbpedia.org/sparql";


    public DataHolder(TeamRepository teamRepository, PlayerRepository playerRepository, PlayerAwardRepository playerAwardRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
        this.playerAwardRepository = playerAwardRepository;
    }

    public String setStr(String[] str){
        return str[str.length -1].replace("_"," ");
    }

    public List<Team> getAllTeam(){
        List<Team> nbaTeam = new ArrayList<>();
        String query2 = " PREFIX db: <http://dbpedia.org/resource/>"+
                " SELECT distinct ?team"+
                " WHERE {" +
                "    ?team   ?b db:Category:National_Basketball_Association_teams"+
                " }";
        Query sparqlQuery = QueryFactory.create(query2);
        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService(SPARQLEndpoint, sparqlQuery)) {
            ResultSet resultSet = queryExecution.execSelect();
            while (resultSet.hasNext()) {
                QuerySolution solution = resultSet.nextSolution();
                String[] team = solution.get("team").toString().split("/");

                String teamName = team[team.length - 1];
                if(teamName.startsWith("Category")){
                    continue;
                }




                String query3 = " PREFIX dbr: <http://dbpedia.org/resource/> " +
                        "PREFIX dbo: <http://dbpedia.org/ontology/>"+
                        "select distinct ?owner ?imageUrl ?description ?stadium ?coach"  +
                        " WHERE { " +
                        " dbr:"+ teamName + " dbo:owner ?owner;" +
                        "dbo:abstract ?description;" +
                        "dbo:stadium ?stadium;" +
                        "dbo:thumbnail ?imageUrl;" +
                        "dbo:coach ?coach. " +
                        " FILTER (langMatches(lang(?description), \"en\")). " +
                        " } ";
                Query sparqlQuery2 = QueryFactory.create(query3);
                try (QueryExecution queryExecution1 = QueryExecutionFactory.sparqlService(SPARQLEndpoint, sparqlQuery2)) {
                    ResultSet resultSet1 = queryExecution1.execSelect();
                    while (resultSet1.hasNext()) {
                        QuerySolution solution1 = resultSet1.nextSolution();
                        String[] ownerArr = solution1.get("owner").toString().split("/");
                        String[] stadiumArr = solution1.get("stadium").toString().split("/");
                        String[] imageUrlArr = solution1.get("imageUrl").toString().split("/");
                        String[] descriptionArr = solution1.get("description").toString().split("/");
                        String[] coachArr = solution1.get("coach").toString().split("/");
                        String teamName1 = teamName.replace("_"," ");
                        nbaTeam.add(new Team(teamName1,coachArr[coachArr.length - 1].replace("_"," "),descriptionArr[descriptionArr.length - 1],
                                ownerArr[ownerArr.length - 1].replace("_"," "),stadiumArr[stadiumArr.length - 1].replace("_"," "),imageUrlArr[imageUrlArr.length - 1 ]));
                    }
                }
            }
        }
        return nbaTeam;
    }

    public List<Player> getPlayerName(String team,Integer id){
        List<Player> players = new ArrayList<>();
        String teamName = team.replace(" ","_");
        String query = "PREFIX dbr: <http://dbpedia.org/resource/> "+
                "PREFIX dbo: <http://dbpedia.org/ontology/> " +
                "SELECT * " +
                "WHERE { " +
                "    ?team dbo:draftTeam dbr:"+teamName+
                "                 }";
        Query sparqlQuery = QueryFactory.create(query);
        try (QueryExecution queryExecution1 = QueryExecutionFactory.sparqlService(SPARQLEndpoint, sparqlQuery)) {
            ResultSet resultSet = queryExecution1.execSelect();
            while(resultSet.hasNext()){
                QuerySolution solution = resultSet.nextSolution();
                if (solution.contains("team")) {
                    String[] playerArr = solution.get("team").toString().split("/");
                    String player = playerArr[playerArr.length - 1].replace("_", " ");
                    if(!playerArr[playerArr.length-1 ].contains("basketball") && !playerArr[playerArr.length-1].contains("Dominic_Pointer") && playerArr.length>0) {
                        String playerOrg = playerArr[playerArr.length -1].replace(".","").replace("'","");
                    //    System.out.println(playerArr[playerArr.length-1]);
                        String query2 = "PREFIX dbr: <http://dbpedia.org/resource/> " +
                                "PREFIX dbo: <http://dbpedia.org/ontology/> " +
                                "select * " +
                                "where { \n" +
                                " optional {dbr:"+playerOrg+" dbo:birthDate ?birthDate}. \n" +
                                "OPTIONAL {dbr:"+playerOrg+" dbo:birthPlace ?birthPlace}  \n" +
                                " OPTIONAL {dbr:"+playerOrg+" dbo:abstract ?description} \n" +
                                " OPTIONAL {dbr:"+playerOrg+" dbo:number ?number} \n" +
                                " OPTIONAL {dbr:"+playerOrg+" dbo:position ?position} \n" +
                                "  FILTER (langMatches(lang(?description), \"en\")). \n"+
                                " }";
                        Query sparqlQuery1 = QueryFactory.create(query2);
                        try (QueryExecution queryExecution2 = QueryExecutionFactory.sparqlService(SPARQLEndpoint, sparqlQuery1)) {
                            ResultSet resultSet1 = queryExecution2.execSelect();
                            while (resultSet1.hasNext()) {

                                QuerySolution solution1 = resultSet1.nextSolution();
                                String[] birthPlace = {"",""};
                                String[] award = {"",""};
                                String birthDate = "";
                                String[] position = {"",""};
                                String[] description = {"",""};
                                String[] number = {"",""};
                                if(solution1.get("birthPlace")!= null)
                                    birthPlace = solution1.get("birthPlace").toString().split("/");

                                if(solution1.get("birthDate")!=null) {
                                    System.out.println(solution1.get("birthDate"));
                                    birthDate = solution1.get("birthDate").toString().substring(0,10);
                                }
                                if(solution1.get("number")!=null)
                                    number = solution1.get("number").toString().split("/");
                                if(solution1.get("position")!=null)
                                    position = solution1.get("position").toString().split("/");
                                if(solution1.get("description")!=null)
                                    description = solution1.get("description").toString().split("/");


                                players.add(new Player(player,setStr(description),birthDate,
                                        setStr(birthPlace),setStr(number),setStr(position),id, getAward(playerOrg)));
                            }
                        }
                    }

                }
            }
        }
        return players;
    }
    public List<PlayerAward> getAward(String player){

        List<PlayerAward> awards = new ArrayList<>();
        String query = "PREFIX dbr: <http://dbpedia.org/resource/> " +
                "PREFIX dbo: <http://dbpedia.org/ontology/> " +
                "select * " +
                "where { \n" +
                " optional {dbr:"+player+" dbo:award ?award}. \n" +
                " }";
        Query sparqlQuery = QueryFactory.create(query);
        try (QueryExecution queryExecution2 = QueryExecutionFactory.sparqlService(SPARQLEndpoint, sparqlQuery)) {
            ResultSet resultSet1 = queryExecution2.execSelect();
            while (resultSet1.hasNext()) {
                String[] awardArray = {"",""};
                QuerySolution solution1 = resultSet1.nextSolution();
                if(solution1.get("award") != null)
                    awardArray = solution1.get("award").toString().split("/");
                awards.add(new PlayerAward(setStr(awardArray)));
            }

        }
        this.playerAwardRepository.saveAll(awards);


        return awards;
    }



    @PostConstruct
    public void init(){
        List<Team> teams = getAllTeam();
        this.teamRepository.saveAll(teams);
         teams.stream().filter(a->!a.getName().equals("Dominic_Pointer")).forEach(a->this.playerRepository.saveAll(getPlayerName(a.getName(),a.getId())));

    }
}
