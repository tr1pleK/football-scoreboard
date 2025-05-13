package org.example;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FootballScoreboard {
    private final Map<String , Match> matches = new HashMap<>();

    private String generateMatchesStringKey(String homeTeam, String awayTeam){
        return homeTeam + "-" + awayTeam;
    }

    public void startGame(String homeTeam, String awayTeam){
        String matchKey = generateMatchesStringKey(homeTeam, awayTeam);
        if(matches.containsKey(matchKey)) throw new IllegalArgumentException("Math already exist");
        matches.put(matchKey, new Match(homeTeam, awayTeam));
    }


    public void finishGame(String homeTeam, String awayTeam){
        String matchKey = generateMatchesStringKey(homeTeam, awayTeam);
        if(!matches.containsKey(matchKey)) throw new IllegalArgumentException("Match dose not exist");
        matches.remove(matchKey);
    }

    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        String matchKey = generateMatchesStringKey(homeTeam, awayTeam);
        Match match = matches.get(matchKey);
        if (match == null) {
            throw new IllegalArgumentException("Match not found");
        }
        match.setHomeScore(homeScore);
        match.setAwayScore(awayScore);
    }

    public List<Match> getSummary() {
        return matches.values().stream()
                .sorted(Comparator
                        .comparingInt((Match m) -> m.getHomeScore() + m.getAwayScore()).reversed()
                        .thenComparing(Match::getStartTime).reversed()
                )
                .collect(Collectors.toList());
    }
}
