package com.gwrteam.gwrteam.web.dto;

public class PlayerRegistrationDto {

    private Long user_id;
    private Long league_id;
    private String car;
    private String team;

    public PlayerRegistrationDto(Long user_id, Long league_id, String car, String team) {
        this.user_id = user_id;
        this.league_id = league_id;
        this.car = car;
        this.team = team;
    }

    public PlayerRegistrationDto() {

    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getLeague_id() {
        return league_id;
    }

    public void setLeague_id(Long league_id) {
        this.league_id = league_id;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }
}
