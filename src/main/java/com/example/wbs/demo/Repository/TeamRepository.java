package com.example.wbs.demo.Repository;

import com.example.wbs.demo.Model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.*;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team,Integer> {

    Optional<Team> findById(Integer name);


}
