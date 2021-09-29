package com.example.wbs.demo.Service;

import com.example.wbs.demo.Model.Team;
import com.example.wbs.demo.Model.TeamRequest;
import java.util.*;


public interface TeamService {

    public Team saveTeam(TeamRequest request);
    List<Team> getAllTeam();
    Team getTeam(Integer id);
}
