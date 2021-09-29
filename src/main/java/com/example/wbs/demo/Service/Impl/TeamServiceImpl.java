package com.example.wbs.demo.Service.Impl;
import com.example.wbs.demo.Model.Team;
import com.example.wbs.demo.Model.TeamRequest;
import com.example.wbs.demo.Repository.TeamRepository;
import com.example.wbs.demo.Service.TeamService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }


    @Override
    public Team saveTeam(TeamRequest request) {
        return teamRepository.save(new Team(request.getName(),request.getCoach(),request.getDescription(),request.getOwner(),
                request.getStadium(),request.getImageURL()));
    }


    @Override
    public List<Team> getAllTeam() {
        return  teamRepository.findAll();
    }

    @Override
    public Team getTeam(Integer id) {
        return teamRepository.findById(id).orElseThrow();
    }
}
