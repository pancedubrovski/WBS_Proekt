package com.example.wbs.demo.Repository;

import com.example.wbs.demo.Model.Player;
import java.util.*;

import com.example.wbs.demo.Model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Integer> {


    Optional<List<Player>> findAllByTeamId(Integer name);

}
