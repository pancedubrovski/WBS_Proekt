package com.example.wbs.demo.Model;

import lombok.*;
import javax.persistence.*;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Team")
@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String name;
    @Column(length = 8024)
    String description;
    String owner;
    String stadium;
    String imageURl;
    String coach;

    public Team(String name,String coach,String description, String owner, String stadium, String imageURl) {
        this.name = name;
        this.coach = coach;
        this.description = description;
        this.owner = owner;
        this.stadium = stadium;
        this.imageURl = imageURl;
    }

}
