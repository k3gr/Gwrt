package com.gwrteam.gwrteam.model;

import javax.persistence.*;

@Table(name = "porsche_main")
@Entity
public class PorscheTableMain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fin_pos")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "wins")
    private int wins;
    @Column(name = "avgstart")
    private int avgstart;
    @Column(name = "avgfin")
    private int avgfin;
    @Column(name = "points")
    private int points;

    public PorscheTableMain(String name, int wins, int avgstart, int avgfin, int points) {
        this.name = name;
        this.wins = wins;
        this.avgstart = avgstart;
        this.avgfin = avgfin;
        this.points = points;
    }

    public PorscheTableMain() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int league_points) {
        this.points = league_points;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getAvgstart() {
        return avgstart;
    }

    public void setAvgstart(int avgstart) {
        this.avgstart = avgstart;
    }

    public int getAvgfin() {
        return avgfin;
    }

    public void setAvgfin(int avgfin) {
        this.avgfin = avgfin;
    }
}

