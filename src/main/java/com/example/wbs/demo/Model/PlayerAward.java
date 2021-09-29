package com.example.wbs.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerAward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public String award;
    @ManyToOne
    public Player player;

    public PlayerAward(String award) {
        this.award = award;
    }
}
