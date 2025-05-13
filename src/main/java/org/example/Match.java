package org.example;

import java.time.LocalDateTime;

public class Match {
    private final String homeTeam;
    private final String awayTeam;
    private int  homeScore = 0;
    private int awayScore = 0;
    private final LocalDateTime startTime;

    public Match(String homeTeam, String awayTeam){
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
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
