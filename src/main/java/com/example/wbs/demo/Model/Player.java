package com.example.wbs.demo.Model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String name;
    @Column(length = 4024)
    String description;
    String birthdayPlace;
    String birthdayDate;
    String number;
    String position;
    Integer teamId;
    @OneToMany
    List<PlayerAward> playerAwardList;


    public Player(String name, String description, String birthdayDate,
                  String birthdayPlace, String number, String position,Integer team,List<PlayerAward> playerAwards) {
        this.name = name;
        this.description = description;
        this.birthdayDate = birthdayDate;
        this.birthdayPlace = birthdayPlace;
        this.number = number;
        this.position = position;
        this.teamId = team;
        this.playerAwardList = playerAwards;


    }

}
