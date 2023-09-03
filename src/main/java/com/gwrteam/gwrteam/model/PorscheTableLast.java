package com.gwrteam.gwrteam.model;

import javax.persistence.*;

@Table(name = "porsche_last")
@Entity
public class PorscheTableLast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fin_pos")
    private Long id;
    @Column(name = "car")
    private String car;
    @Column(name = "name")
    private String name;
    @Column(name = "time")
    private String time;
    @Column(name = "points")
    private int points;

    public PorscheTableLast(String car, String name,  String time, int points) {
        this.car = car;
        this.name = name;
        this.time = time;
        this.points = points;
    }

    public PorscheTableLast() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

}