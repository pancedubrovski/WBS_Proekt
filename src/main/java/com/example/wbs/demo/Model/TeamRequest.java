package com.example.wbs.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TeamRequest {


    String name;
    String coach;
    String description;
    String owner;
    String stadium;
    String imageURL;
}
