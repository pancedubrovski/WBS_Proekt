package com.example.wbs.demo.Repository;

import com.example.wbs.demo.Model.PlayerAward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerAwardRepository extends JpaRepository<PlayerAward,Integer> {
}
