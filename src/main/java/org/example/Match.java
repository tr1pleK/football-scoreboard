package org.example;

import java.time.LocalDateTime;

public class Match {
    private final String homeTeam;
    private final String awayTeam;
    private int  homeScore;
    private int awayScore;
    private final LocalDateTime startTime;

    public Match(String homeTeam, String awayTeam){
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = 0;
        this.awayScore = 0;
        this.startTime = LocalDateTime.now();
    }

    public int getAwayScore() {
        return awayScore;
    }
    public int getHomeScore() {
        return homeScore;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public String getAwayTeam() {
        return awayTeam;
    }
    public String getHomeTeam() {
        return homeTeam;
    }

    public void setAwayScore(int awayScore) {
        if(awayScore < 0) throw new IllegalArgumentException("Score cannot be negative");
        this.awayScore = awayScore;
    }

    public void setHomeScore(int homeScore) {
        if (homeScore < 0) throw new IllegalArgumentException("Score cannot be negative");
        this.homeScore = homeScore;
    }

}
